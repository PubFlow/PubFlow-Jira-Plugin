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
package de.pubflow.server.core.restConnection;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

import de.pubflow.common.IJiraRestConnector;
import de.pubflow.server.core.jira.JiraEndpoint;
import de.pubflow.server.core.workflow.WorkflowBroker;
import de.pubflow.server.core.workflow.messages.ReceivedWorkflowAnswer;

@Path(JiraRestConnector.basePath)
public class JiraRestConnector implements IJiraRestConnector {

	private static final String jiraRestPath = "/jira/rest/receiver/1.0";
	static final String basePath = "/pubflow/issues";
	private static final String answerPath = "/{issueKey}/result";

	@POST
	@Path("/{issueKey}/status")
	public Response changeStatus(@PathParam("issueKey") String issueKey, @FormParam("statusName") String statusName) {
		JiraEndpoint.changeStatus(issueKey, statusName);
		try {
			return Response.status(204).entity(null).build();
		} catch (Exception e) {
			return Response.status(500).entity(null).build();
		}
	}

	@POST
	@Path("/{issueKey}/attachments")
	public Response addAttachment(@PathParam("issueKey") String issueKey, @FormParam("barray") byte[] barray,
			@FormParam("fileName") String fileName, @FormParam("type") String type) {
		JiraEndpoint.addAttachment(issueKey, barray, fileName, type);
		try {
			return Response.status(204).entity(null).build();
		} catch (Exception e) {
			return Response.status(500).entity(null).build();
		}
	}

	@POST
	@Path("/{issueKey}/comments")
	public Response addIssueComment(@PathParam("issueKey") String issueKey, @FormParam("comment") String comment) {
		JiraEndpoint.addIssueComment(issueKey, comment);
		try {
			return Response.status(204).entity(null).build();
		} catch (Exception e) {
			return Response.status(500).entity(null).build();
		}
	}

	@POST
	@Path("/")
	public Response createIssue(@FormParam("issueTypeName") String issueTypeName, @FormParam("summary") String summary,
			@FormParam("description") String description, @FormParam("parameters") HashMap<String, String> parameters,
			@FormParam("reporter") String reporter) {
		JiraEndpoint.createIssue(issueTypeName, summary, description, parameters, reporter);
		try {
			return Response.status(204).entity(null).build();
		} catch (Exception e) {
			return Response.status(500).entity(null).build();
		}
	}

	@POST
	@AnonymousAllowed
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(answerPath)
	public Response receiveWorkflowAnswer(@PathParam("issueKey") String issueKey, ReceivedWorkflowAnswer wfAnswer) {
		WorkflowBroker.getInstance().receiveWorkflowAnswer(issueKey, wfAnswer);
		return Response.ok().build();
	}

	/**
	 * 
	 * @return the complete URL for callback for generic answers of the Workflow
	 *         Engine
	 * @throws MalformedURLException
	 * @throws UnknownHostException
	 */
	public static String getCallbackAddress() throws UnknownHostException {
		// TODO is this the right place for this?

		// TODO set port dynamically (@ startup)
		return "http://" + InetAddress.getLocalHost().getHostAddress().toString() + ":2990" + jiraRestPath + basePath
				+ answerPath;
	}

}