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

import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotNull;

import java.util.Date;
import java.util.List;

import org.camunda.bpm.engine.exception.NotValidException;
import org.camunda.bpm.engine.history.CleanableHistoricDecisionInstanceReport;
import org.camunda.bpm.engine.history.CleanableHistoricDecisionInstanceReportResult;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;

public class CleanableHistoricDecisionInstanceReportImpl extends AbstractQuery<CleanableHistoricDecisionInstanceReport, CleanableHistoricDecisionInstanceReportResult> implements CleanableHistoricDecisionInstanceReport {

  private static final long serialVersionUID = 1L;

  protected String[] decisionDefinitionIdIn;
  protected String[] decisionDefinitionKeyIn;

  protected Date currentTimestamp;

  public CleanableHistoricDecisionInstanceReportImpl(CommandExecutor commandExecutor) {
    super(commandExecutor);
  }

  @Override
  public CleanableHistoricDecisionInstanceReport decisionDefinitionIdIn(String... decisionDefinitionIds) {
    ensureNotNull(NotValidException.class, "", "decisionDefinitionIdIn", (Object[]) decisionDefinitionIds);
    this.decisionDefinitionIdIn = decisionDefinitionIds;
    return this;
  }

  @Override
  public CleanableHistoricDecisionInstanceReport decisionDefinitionKeyIn(String... decisionDefinitionKeys) {
    ensureNotNull(NotValidException.class, "", "decisionDefinitionKeyIn", (Object[]) decisionDefinitionKeys);
    this.decisionDefinitionKeyIn = decisionDefinitionKeys;
    return this;
  }

  @Override
  public long executeCount(CommandContext commandContext) {
    checkQueryOk();
    return commandContext
        .getHistoricDecisionInstanceManager()
        .findCleanableHistoricDecisionInstancesReportCountByCriteria(this);
  }

  @Override
  public List<CleanableHistoricDecisionInstanceReportResult> executeList(CommandContext commandContext, Page page) {
    checkQueryOk();
    return commandContext
        .getHistoricDecisionInstanceManager()
        .findCleanableHistoricDecisionInstancesReportByCriteria(this, page);
  }

  public String[] getDecisionDefinitionIdIn() {
    return decisionDefinitionIdIn;
  }

  public void setDecisionDefinitionIdIn(String[] decisionDefinitionIdIn) {
    this.decisionDefinitionIdIn = decisionDefinitionIdIn;
  }

  public String[] getDecisionDefinitionKeyIn() {
    return decisionDefinitionKeyIn;
  }

  public void setDecisionDefinitionKeyIn(String[] decisionDefinitionKeyIn) {
    this.decisionDefinitionKeyIn = decisionDefinitionKeyIn;
  }

  public Date getCurrentTimestamp() {
    return currentTimestamp;
  }

  public void setCurrentTimestamp(Date currentTimestamp) {
    this.currentTimestamp = currentTimestamp;
  }

}
