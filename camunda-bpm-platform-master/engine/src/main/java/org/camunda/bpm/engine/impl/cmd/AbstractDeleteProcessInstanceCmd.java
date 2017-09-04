/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.impl.cmd;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.exception.NotValidException;
import org.camunda.bpm.engine.history.UserOperationLogEntry;
import org.camunda.bpm.engine.impl.ActivityExecutionTreeMapping;
import org.camunda.bpm.engine.impl.ProcessInstanceModificationBuilderImpl;
import org.camunda.bpm.engine.impl.cfg.CommandChecker;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionManager;
import org.camunda.bpm.engine.impl.persistence.entity.PropertyChange;

import java.util.Collections;
import org.camunda.bpm.engine.impl.pvm.delegate.ModificationObserverBehavior;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.pvm.runtime.PvmExecutionImpl;
import org.camunda.bpm.engine.impl.util.EnsureUtil;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.Execution;


import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotNull;

/**
 * Created by aakhmerov on 16.09.16.
 * <p>
 * Provide common logic for process instance deletion operations.
 * Permissions checking and single process instance removal included.
 */
public abstract class AbstractDeleteProcessInstanceCmd {

  protected boolean externallyTerminated;
  protected String deleteReason;
  protected boolean skipCustomListeners;

  protected void checkDeleteProcessInstance(ExecutionEntity execution, CommandContext commandContext) {
    for (CommandChecker checker : commandContext.getProcessEngineConfiguration().getCommandCheckers()) {
      checker.checkDeleteProcessInstance(execution);
    }
  }

  protected void deleteProcessInstance(
      final CommandContext commandContext,
      String processInstanceId,
      String deleteReason,
      final boolean skipCustomListeners,
      boolean externallyTerminated,
      final boolean skipIoMappings) {
    ensureNotNull(BadUserRequestException.class, "processInstanceId is null", "processInstanceId", processInstanceId);

    // fetch process instance
    ExecutionManager executionManager = commandContext.getExecutionManager();
    final ExecutionEntity execution = executionManager.findExecutionById(processInstanceId);

    ensureNotNull(BadUserRequestException.class, "No process instance found for id '" + processInstanceId + "'", "processInstance", execution);

    checkDeleteProcessInstance(execution, commandContext);

    // delete process instance
    commandContext
        .getExecutionManager()
        .deleteProcessInstance(processInstanceId, deleteReason, false, skipCustomListeners, externallyTerminated, skipIoMappings);

    final ExecutionEntity superExecution = execution.getSuperExecution();
    if (superExecution != null) {
      commandContext.runWithoutAuthorization(new Callable<Void>() {
        public Void call() {
          ProcessInstanceModificationBuilderImpl builder = (ProcessInstanceModificationBuilderImpl) new ProcessInstanceModificationBuilderImpl(commandContext, superExecution.getProcessInstanceId())
            .cancelActivityInstance(superExecution.getActivityInstanceId());
          builder.execute(false, skipCustomListeners, skipIoMappings);
          return null;
        }
      });

    }

    // create user operation log
    commandContext.getOperationLogManager()
        .logProcessInstanceOperation(UserOperationLogEntry.OPERATION_TYPE_DELETE, processInstanceId,
            null, null, Collections.singletonList(PropertyChange.EMPTY_CHANGE));
  }

}
