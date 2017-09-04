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
package org.camunda.bpm.integrationtest.functional.dmn;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.camunda.bpm.engine.history.HistoricDecisionInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.integrationtest.util.AbstractFoxPlatformIntegrationTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Philipp Ossler
 */
@RunWith(Arquillian.class)
public class DmnHistoryTest extends AbstractFoxPlatformIntegrationTest {

  @Deployment
  public static WebArchive processArchive() {

    return initWebArchiveDeployment()
        .addAsResource("org/camunda/bpm/integrationtest/functional/dmn/BusinessRuleProcess.bpmn20.xml", "BusinessRuleProcess.bpmn20.xml")
        .addAsResource("org/camunda/bpm/integrationtest/functional/dmn/Example.dmn11.xml", "Example.dmn11.xml");

  }

  @Test
  public void testHistoricDecisionInstance() {

    VariableMap variables = Variables.createVariables().putValue("status", "bronze").putValue("sum", 100);
    ProcessInstance pi = runtimeService.startProcessInstanceByKey("testProcess", variables);

    HistoricDecisionInstance historicDecisionInstance = historyService.createHistoricDecisionInstanceQuery().includeInputs().includeOutputs().singleResult();
    assertThat(historicDecisionInstance, is(notNullValue()));
    assertThat(historicDecisionInstance.getDecisionDefinitionKey(), is("decision"));
    assertThat(historicDecisionInstance.getDecisionDefinitionName(), is("Check Order"));

    assertThat(historicDecisionInstance.getInputs().size(), is(2));
    assertThat(historicDecisionInstance.getOutputs().size(), is(2));

    Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
    taskService.complete(task.getId());
  }

}
