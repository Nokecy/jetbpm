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
package org.camunda.bpm.engine.impl;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.NativeHistoricTaskInstanceQuery;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;


public class NativeHistoricTaskInstanceQueryImpl extends AbstractNativeQuery<NativeHistoricTaskInstanceQuery, HistoricTaskInstance> implements NativeHistoricTaskInstanceQuery {

  private static final long serialVersionUID = 1L;

  public NativeHistoricTaskInstanceQueryImpl(CommandContext commandContext) {
    super(commandContext);
  }

  public NativeHistoricTaskInstanceQueryImpl(CommandExecutor commandExecutor) {
    super(commandExecutor);
  }


 //results ////////////////////////////////////////////////////////////////
  
  public List<HistoricTaskInstance> executeList(CommandContext commandContext, Map<String, Object> parameterMap, int firstResult, int maxResults) {
    return commandContext
      .getHistoricTaskInstanceManager()
      .findHistoricTaskInstancesByNativeQuery(parameterMap, firstResult, maxResults);
  }
  
  public long executeCount(CommandContext commandContext, Map<String, Object> parameterMap) {
    return commandContext
      .getHistoricTaskInstanceManager()
      .findHistoricTaskInstanceCountByNativeQuery(parameterMap);
  }

}
