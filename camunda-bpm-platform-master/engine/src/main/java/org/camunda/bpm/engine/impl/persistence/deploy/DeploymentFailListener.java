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
package org.camunda.bpm.engine.impl.persistence.deploy;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;

import org.camunda.bpm.engine.impl.cfg.TransactionListener;
import org.camunda.bpm.engine.impl.cmd.UnregisterDeploymentCmd;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;

public class DeploymentFailListener implements TransactionListener {

  protected CommandExecutor commandExecutor;
  protected Set<String> deploymentIds;

  public DeploymentFailListener(String deploymentId, CommandExecutor commandExecutor) {
    this.deploymentIds = Collections.singleton(deploymentId);
    this.commandExecutor = commandExecutor;
  }

  public DeploymentFailListener(Set<String> deploymentIds, CommandExecutor commandExecutor) {
    this.deploymentIds = deploymentIds;
    this.commandExecutor = commandExecutor;
  }

  public void execute(CommandContext commandContext) {
    //unregister deployment without authorization
    commandExecutor.execute(new Command<Void>() {
      @Override
      public Void execute(final CommandContext commandContext) {
        commandContext.runWithoutAuthorization(new Callable<Void>() {
          @Override
          public Void call() throws Exception {
            new UnregisterDeploymentCmd(deploymentIds).execute(commandContext);
            return null;
          }
        });
        return null;
      }
    });
  }

}