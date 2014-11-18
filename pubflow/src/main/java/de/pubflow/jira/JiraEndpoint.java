package de.pubflow.jira;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(targetNamespace = "pubflow.de")//(endpointInterface = "de.pubflow.jira.ws.IJiraEndpoint")
@SOAPBinding(style = Style.DOCUMENT)
public class JiraEndpoint{


	/**
	 * Creates a new Issue in Jira
	 * 
	 * @param projectKey : the projects key
	 * @param issueTypeName : determines which issue type should be used as issue scheme  
	 * @param comment : value for default field 'comment'
	 * @param parameters : map of custom field values (name : value)
	 * @return returns the issue id 
	 * 
	 **/
	
	public static String createIssue(String projectKey, String workflowName, String summary, String description, HashMap<String, String> parameters, String reporter) {
		try {
			
			return JiraManagerCore.newIssue(projectKey, workflowName, summary, JiraManagerPlugin.user, description, parameters, reporter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	/**
	 * Creates a new IssueType in Jira
	 * 
	 * @param projectKey : the projects key
	 * @param issueTypeName : 
	 * @param parameters : list of custom fields (name : default value)
	 * @return returns the issue type id
	 */
	
	public static String createIssueType(String projectKey, String issueTypeName, HashMap<String, String> parameters) {

//TODO
//		String id = null; 
//
//		try {
//			id = JiraManagerPlugin.jiraManaCore.newIssueType(projectKey, issueTypeName, parameters);
//
//		} catch (GenericEntityException e) {
//			e.printStackTrace();
//		}		
//		return id;
		return null;
	}

	/**
	 * Changes the status of an issue
	 * 
	 * @param issueKey  : issue key
	 * @param statusName : has to be a preexisiting status name, eg. provided by getStatusNames(..) 
	 * @return returns true if the change has been processed successfully
	 */
	
	public static boolean changeStatus(String issueKey, String statusName) {
		return JiraManagerCore.changeStatus(issueKey, statusName);
	}

	/**
	 * Adds a new comment to an issue
	 * 
	 * @param issueKey
	 * @param comment 
	 * @return returns if the new comment has been added successful
	 */
	
	public static boolean addIssueComment(String issueKey, String comment){
		if(JiraManagerCore.addIssueComment(issueKey, comment, JiraManagerPlugin.user) == null){
			return false;
		}else{
			return true;
		}
	}


	/**
	 * Get available status names
	 * 
	 * @param projectKey : the projects key
	 * @return returns a string array of all available status names
	 */
	
	public static LinkedList<String> getStatusNames(String projectKey){
		List<String> statusNames = JiraManagerCore.getStatusNames(projectKey);
		LinkedList<String> namesList = new LinkedList<String>();

		namesList.addAll(statusNames);

		return namesList;
	}


	/**
	 * Creates a new Jira project
	 * 
	 * @param projectName : the name of the new project
	 * @param projectKey : the project's key
	 * @param workflowXML : the Jira workflow, can be null
	 * @param steps : list of statuses (steps) provided by the assigned workflow
	 * 
	 * @return returns true if project has been created successfully
	 */
	
	public static boolean createProject(String projectName, String projectKey, String workflowXML, LinkedList<String> steps) {
		try {
			JiraManagerCore.initProject(projectName, projectKey, JiraManagerPlugin.user, false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Appends a file to an issue
	 * 
	 * @param issueKey
	 * @param barray
	 * @param fileName
	 * @param type
	 */
	
	public static boolean addAttachment(String issueKey, byte[]barray, String fileName, String type){

		System.out.println("RECEIVED ATTACHMENT ------------------------------------------------>");
		System.out.println(issueKey);
		System.out.println(barray.length);
		System.out.println(fileName);
		System.out.println(type);


		JiraManagerCore.addAttachment(issueKey, barray, fileName, type, JiraManagerPlugin.user);

		return true;
	}

	public static boolean addWorkflow(String projectKey, String workflowXML){
		JiraManagerCore.addWorkflow(projectKey, workflowXML, JiraManagerPlugin.user);

		return true;
	}

	public static void removeAttachment(long attachmentId) {
		// TODO Auto-generated method stub

	}
}