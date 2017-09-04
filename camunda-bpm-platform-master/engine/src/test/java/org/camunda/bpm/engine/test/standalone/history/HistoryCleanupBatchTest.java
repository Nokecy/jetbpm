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

package org.camunda.bpm.engine.test.standalone.history;

import java.util.Map;

import org.camunda.bpm.engine.impl.test.ResourceProcessEngineTestCase;
import org.junit.Test;

public class HistoryCleanupBatchTest extends ResourceProcessEngineTestCase {

  public HistoryCleanupBatchTest() {
    super("org/camunda/bpm/engine/test/standalone/history/camunda.cfg.xml");
  }

  @Test
  public void testBatchHistoryTimeToLiveConfiguration() {

    String batchOperationHistoryTimeToLive = processEngineConfiguration.getBatchOperationHistoryTimeToLive();
    assertEquals("P5D", batchOperationHistoryTimeToLive);

    Map<String, String> map = processEngineConfiguration.getBatchOperationsForHistoryCleanup();
    assertEquals(9, map.size());
    assertEquals("P10D", map.get("instance-migration"));
    assertEquals("P7D", map.get("instance-modification"));
    assertEquals("P5D", map.get("instance-restart"));
    assertEquals("P3D", map.get("uknown-operation"));
  }

}
