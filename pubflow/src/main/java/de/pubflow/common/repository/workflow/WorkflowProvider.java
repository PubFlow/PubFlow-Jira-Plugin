/***************************************************************************
 * Copyright 2012 by
 *  + Christian-Albrechts-University of Kiel
 *    + Department of Computer Science
 *      + Software Engineering Group 
 *  and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
/**
 * @author arl
 *
 */

package de.pubflow.common.repository.workflow;

import de.pubflow.common.entity.repository.WorkflowEntity;
import de.pubflow.common.repository.abstractRepository.interaction.BasicProvider;
import de.pubflow.common.repository.abstractRepository.repository.ERepositoryName;
import de.pubflow.common.repository.abstractRepository.storageAdapter.FSStorageAdapter;


public class WorkflowProvider extends BasicProvider<WorkflowEntity>{

	private static WorkflowProvider wfp;

	private WorkflowProvider() {
		super(ERepositoryName.WORKFLOW, new FSStorageAdapter());
	}

	public static WorkflowProvider getInstance(){
		if(wfp == null){
			wfp = new WorkflowProvider();
		}
		return wfp;
	}
}