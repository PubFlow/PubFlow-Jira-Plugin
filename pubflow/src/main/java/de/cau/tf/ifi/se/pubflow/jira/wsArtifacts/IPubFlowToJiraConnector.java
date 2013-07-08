
package de.cau.tf.ifi.se.pubflow.jira.wsArtifacts;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IPubFlowToJiraConnector", targetNamespace = "http://webservice.jira.pubflow.de/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IPubFlowToJiraConnector {


    /**
     * 
     * @param params
     * @param comment
     * @param wfName
     * @return
     *     returns long
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createIssue", targetNamespace = "http://webservice.jira.pubflow.de/", className = "de.pubflow.jira.webservice.CreateIssue")
    @ResponseWrapper(localName = "createIssueResponse", targetNamespace = "http://webservice.jira.pubflow.de/", className = "de.pubflow.jira.webservice.CreateIssueResponse")
    @Action(input = "http://webservice.jira.pubflow.de/IPubFlowToJiraConnector/createIssueRequest", output = "http://webservice.jira.pubflow.de/IPubFlowToJiraConnector/createIssueResponse")
    public long createIssue(
        @WebParam(name = "wfName", targetNamespace = "")
        String wfName,
        @WebParam(name = "comment", targetNamespace = "")
        String comment,
        @WebParam(name = "params", targetNamespace = "")
        de.cau.tf.ifi.se.pubflow.jira.wsArtifacts.CreateIssue.Params params);

    /**
     * 
     * @param params
     * @param wfName
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createIssueType", targetNamespace = "http://webservice.jira.pubflow.de/", className = "de.pubflow.jira.webservice.CreateIssueType")
    @ResponseWrapper(localName = "createIssueTypeResponse", targetNamespace = "http://webservice.jira.pubflow.de/", className = "de.pubflow.jira.webservice.CreateIssueTypeResponse")
    @Action(input = "http://webservice.jira.pubflow.de/IPubFlowToJiraConnector/createIssueTypeRequest", output = "http://webservice.jira.pubflow.de/IPubFlowToJiraConnector/createIssueTypeResponse")
    public String createIssueType(
        @WebParam(name = "wfName", targetNamespace = "")
        String wfName,
        @WebParam(name = "params", targetNamespace = "")
        de.cau.tf.ifi.se.pubflow.jira.wsArtifacts.CreateIssueType.Params params);

    /**
     * 
     * @param statusId
     * @param issueId
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "changeStatus", targetNamespace = "http://webservice.jira.pubflow.de/", className = "de.pubflow.jira.webservice.ChangeStatus")
    @ResponseWrapper(localName = "changeStatusResponse", targetNamespace = "http://webservice.jira.pubflow.de/", className = "de.pubflow.jira.webservice.ChangeStatusResponse")
    @Action(input = "http://webservice.jira.pubflow.de/IPubFlowToJiraConnector/changeStatusRequest", output = "http://webservice.jira.pubflow.de/IPubFlowToJiraConnector/changeStatusResponse")
    public boolean changeStatus(
        @WebParam(name = "issueId", targetNamespace = "")
        long issueId,
        @WebParam(name = "statusId", targetNamespace = "")
        String statusId);

    /**
     * 
     * @param issueId
     * @param comment
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addIssueComment", targetNamespace = "http://webservice.jira.pubflow.de/", className = "de.pubflow.jira.webservice.AddIssueComment")
    @ResponseWrapper(localName = "addIssueCommentResponse", targetNamespace = "http://webservice.jira.pubflow.de/", className = "de.pubflow.jira.webservice.AddIssueCommentResponse")
    @Action(input = "http://webservice.jira.pubflow.de/IPubFlowToJiraConnector/addIssueCommentRequest", output = "http://webservice.jira.pubflow.de/IPubFlowToJiraConnector/addIssueCommentResponse")
    public boolean addIssueComment(
        @WebParam(name = "issueId", targetNamespace = "")
        long issueId,
        @WebParam(name = "comment", targetNamespace = "")
        String comment);

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getStatusNames", targetNamespace = "http://webservice.jira.pubflow.de/", className = "de.pubflow.jira.webservice.GetStatusNames")
    @ResponseWrapper(localName = "getStatusNamesResponse", targetNamespace = "http://webservice.jira.pubflow.de/", className = "de.pubflow.jira.webservice.GetStatusNamesResponse")
    @Action(input = "http://webservice.jira.pubflow.de/IPubFlowToJiraConnector/getStatusNamesRequest", output = "http://webservice.jira.pubflow.de/IPubFlowToJiraConnector/getStatusNamesResponse")
    public List<String> getStatusNames();

}
