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
package org.camunda.bpm.engine.rest.sub.management;

import org.camunda.bpm.engine.rest.dto.management.JobDefinitionDto;
import org.camunda.bpm.engine.rest.dto.management.JobDefinitionSuspensionStateDto;
import org.camunda.bpm.engine.rest.dto.runtime.JobDefinitionPriorityDto;
import org.camunda.bpm.engine.rest.dto.runtime.RetriesDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author roman.smirnov
 */
public interface JobDefinitionResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  JobDefinitionDto getJobDefinition();

  @PUT
  @Path("/suspended")
  @Consumes(MediaType.APPLICATION_JSON)
  void updateSuspensionState(JobDefinitionSuspensionStateDto dto);

  @PUT
  @Path("/retries")
  @Consumes(MediaType.APPLICATION_JSON)
  void setJobRetries(RetriesDto dto);

  @PUT
  @Path("/jobPriority")
  @Consumes(MediaType.APPLICATION_JSON)
  void setJobPriority(JobDefinitionPriorityDto dto);

}
