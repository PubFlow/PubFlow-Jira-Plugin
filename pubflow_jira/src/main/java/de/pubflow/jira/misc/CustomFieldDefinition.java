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
package de.pubflow.jira.misc;


public class CustomFieldDefinition {

	public enum CustomFieldType{
		TEXT("com.atlassian.jira.plugin.system.customfieldtypes:textfield"),
		TEXTAREA("com.atlassian.jira.plugin.system.customfieldtypes:textarea"),
		USERPICKER("com.atlassian.jira.plugin.system.customfieldtypes:userpicker"),
		DATEPICKER("com.atlassian.jira.plugin.system.customfieldtypes:datepicker"),
		DATETIME("com.atlassian.jira.plugin.system.customfieldtypes:datetime"),
		VERSION("com.atlassian.jira.plugin.system.customfieldtypes:version"),
		MULTIVERSION("com.atlassian.jira.plugin.system.customfieldtypes:multiversion"),
		SELECT("com.atlassian.jira.plugin.system.customfieldtypes:select"),
		MULTISELECT("com.atlassian.jira.plugin.system.customfieldtypes:multiselect"),
		MULTISELECTCHECKBOX("com.atlassian.jira.plugin.system.customfieldtypes:multicheckboxes"),
		CASCADESELECT("com.atlassian.jira.plugin.system.customfieldtypes:cascadingselect"),
		MULTIGROUPPICKER("com.atlassian.jira.plugin.system.customfieldtypes:multigrouppicker"),
		LABEL("com.atlassian.jira.plugin.system.customfieldtypes:labels"),
		RADIOBUTTONS("com.atlassian.jira.plugin.system.customfieldtypes:radiobuttons"),
		GROUPPICKER("com.atlassian.jira.plugin.system.customfieldtypes:grouppicker");
		
		private String type = "";

		CustomFieldType(String type) {
			this.setType(type);
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	private String name;
	private CustomFieldType type;
	private boolean required;
	private String[] screens;

	/**
	 * @param name
	 * @param type
	 * @param required
	 * @param screens
	 */
	public CustomFieldDefinition(String name, CustomFieldType type, boolean required, String[] screens) {
		this.name = name;
		this.type = type;
		this.required = required;
		this.screens = screens;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type.getType();
	}

	public void setType(CustomFieldType type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String[] getScreens() {
		return screens;
	}

	public void setScreens(String[] screens) {
		this.screens = screens;
	}
}
