/**
 * Copyright (C) 2016 Marc Adolf, Arnd Plumhoff (http://www.pubflow.uni-kiel.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.pubflow.server.core.workflow.types;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.pubflow.jira.misc.CustomFieldDefinition;

/**
 * Contains all necessary information for the EPrints-Workflow
 * 
 * @author Marc Adolf
 *
 */
public class EPrintsWorkflow extends AbstractWorkflow {

	public EPrintsWorkflow() {
		super("EPRINTS", "de.pubflow.EPRINTS", "/EPRINTS.xml", "/workflow/EPrintsWorkflow");
	}

	@Override
	public List<CustomFieldDefinition> getCustomFields() {
		return new LinkedList<CustomFieldDefinition>();
	}

}