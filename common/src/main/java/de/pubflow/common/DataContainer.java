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
package de.pubflow.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import de.pubflow.common.entity.JiraAttachment;
import de.pubflow.common.entity.JiraComment;
import de.pubflow.common.entity.JiraIssue;


public class DataContainer{	
	private HashMap<String, Object> map;
	private LinkedList<JiraAttachment> attachments;
	private LinkedList<JiraComment> comments;
	private LinkedList<JiraIssue> issues;
	private String defaultIssueKey;
	
	public DataContainer(String defaultIssueKey){
		this.defaultIssueKey = defaultIssueKey;

		issues = new LinkedList<JiraIssue>();
		attachments = new LinkedList<JiraAttachment>();
		comments = new LinkedList<JiraComment>();
		map =  new HashMap<String, Object>();
	}

	public String getDefaultIssueKey() {
		return defaultIssueKey;
	}

	public void setDefaultIssueKey(String defaultIssueKey) {
		this.defaultIssueKey = defaultIssueKey;
	}

	public Set<Entry<String, Object>> entrySet(){
		return map.entrySet();
	}

	public void newJiraIssue(String workflowName, String summary, String description, String reporter, HashMap<String, String> parameters){
		issues.add(new JiraIssue(workflowName, summary, description, parameters, reporter));
	}
	
	public void newJiraComment(String text){
		comments.add(new JiraComment(defaultIssueKey, text));
	}

	public void newJiraAttachment(String fileName, byte[] data){
		attachments.add(new JiraAttachment(defaultIssueKey, "", fileName, data));
	}

	public LinkedList<JiraComment> getJiraCommentsAndFlush(){
		LinkedList<JiraComment> comments =  (LinkedList<JiraComment>) this.comments.clone();
		this.comments = new LinkedList<JiraComment>();
		return comments;
	}
	
	public LinkedList<JiraIssue> getJiraIssuesAndFlush(){
		LinkedList<JiraIssue> issues =  (LinkedList<JiraIssue>) this.issues.clone();
		this.issues = new LinkedList<JiraIssue>();
		return issues;
	}

	public LinkedList<JiraAttachment> getJiraAttachmentsAndFlush(){
		LinkedList<JiraAttachment> attachments =  (LinkedList<JiraAttachment>) this.attachments.clone();
		this.attachments = new LinkedList<JiraAttachment>();
		return attachments;
	}

	public String get(Object key) throws Exception {
		try{
			return (String) map.get(key);
		}catch(Exception e){
			throw new Exception("set with key " + key + "is protected!");
		}
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	public boolean containsKey(String key){
		return map.containsKey(key);
	}
}