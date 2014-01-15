
package de.pubflow.components.jiraConnector.wsArtifacts;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.pubflow package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateIssueResponse_QNAME = new QName("pubflow.de", "createIssueResponse");
    private final static QName _AddIssueComment_QNAME = new QName("pubflow.de", "addIssueComment");
    private final static QName _AddAttachmentResponse_QNAME = new QName("pubflow.de", "addAttachmentResponse");
    private final static QName _CreateIssueType_QNAME = new QName("pubflow.de", "createIssueType");
    private final static QName _CreateProject_QNAME = new QName("pubflow.de", "createProject");
    private final static QName _GetStatusNames_QNAME = new QName("pubflow.de", "getStatusNames");
    private final static QName _CreateProjectResponse_QNAME = new QName("pubflow.de", "createProjectResponse");
    private final static QName _AddAttachment_QNAME = new QName("pubflow.de", "addAttachment");
    private final static QName _CreateIssue_QNAME = new QName("pubflow.de", "createIssue");
    private final static QName _GetStatusNamesResponse_QNAME = new QName("pubflow.de", "getStatusNamesResponse");
    private final static QName _AddWorkflow_QNAME = new QName("pubflow.de", "addWorkflow");
    private final static QName _ChangeStatus_QNAME = new QName("pubflow.de", "changeStatus");
    private final static QName _ChangeStatusResponse_QNAME = new QName("pubflow.de", "changeStatusResponse");
    private final static QName _RemoveAttachment_QNAME = new QName("pubflow.de", "removeAttachment");
    private final static QName _AddIssueCommentResponse_QNAME = new QName("pubflow.de", "addIssueCommentResponse");
    private final static QName _AddWorkflowResponse_QNAME = new QName("pubflow.de", "addWorkflowResponse");
    private final static QName _RemoveAttachmentResponse_QNAME = new QName("pubflow.de", "removeAttachmentResponse");
    private final static QName _CreateIssueTypeResponse_QNAME = new QName("pubflow.de", "createIssueTypeResponse");
    private final static QName _AddAttachmentArg1_QNAME = new QName("", "arg1");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.pubflow
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddWorkflow }
     * 
     */
    public AddWorkflow createAddWorkflow() {
        return new AddWorkflow();
    }

    /**
     * Create an instance of {@link AddAttachment }
     * 
     */
    public AddAttachment createAddAttachment() {
        return new AddAttachment();
    }

    /**
     * Create an instance of {@link GetStatusNames }
     * 
     */
    public GetStatusNames createGetStatusNames() {
        return new GetStatusNames();
    }

    /**
     * Create an instance of {@link RemoveAttachment }
     * 
     */
    public RemoveAttachment createRemoveAttachment() {
        return new RemoveAttachment();
    }

    /**
     * Create an instance of {@link RemoveAttachmentResponse }
     * 
     */
    public RemoveAttachmentResponse createRemoveAttachmentResponse() {
        return new RemoveAttachmentResponse();
    }

    /**
     * Create an instance of {@link ChangeStatusResponse }
     * 
     */
    public ChangeStatusResponse createChangeStatusResponse() {
        return new ChangeStatusResponse();
    }

    /**
     * Create an instance of {@link GetStatusNamesResponse }
     * 
     */
    public GetStatusNamesResponse createGetStatusNamesResponse() {
        return new GetStatusNamesResponse();
    }

    /**
     * Create an instance of {@link CreateIssueType.Arg2 }
     * 
     */
    public CreateIssueType.Arg2 createCreateIssueTypeArg2() {
        return new CreateIssueType.Arg2();
    }

    /**
     * Create an instance of {@link ChangeStatus }
     * 
     */
    public ChangeStatus createChangeStatus() {
        return new ChangeStatus();
    }

    /**
     * Create an instance of {@link CreateIssueType.Arg2 .Entry }
     * 
     */
    public CreateIssueType.Arg2 .Entry createCreateIssueTypeArg2Entry() {
        return new CreateIssueType.Arg2 .Entry();
    }

    /**
     * Create an instance of {@link CreateIssue }
     * 
     */
    public CreateIssue createCreateIssue() {
        return new CreateIssue();
    }

    /**
     * Create an instance of {@link CreateProject }
     * 
     */
    public CreateProject createCreateProject() {
        return new CreateProject();
    }

    /**
     * Create an instance of {@link CreateIssueResponse }
     * 
     */
    public CreateIssueResponse createCreateIssueResponse() {
        return new CreateIssueResponse();
    }

    /**
     * Create an instance of {@link AddWorkflowResponse }
     * 
     */
    public AddWorkflowResponse createAddWorkflowResponse() {
        return new AddWorkflowResponse();
    }

    /**
     * Create an instance of {@link AddAttachmentResponse }
     * 
     */
    public AddAttachmentResponse createAddAttachmentResponse() {
        return new AddAttachmentResponse();
    }

    /**
     * Create an instance of {@link CreateProjectResponse }
     * 
     */
    public CreateProjectResponse createCreateProjectResponse() {
        return new CreateProjectResponse();
    }

    /**
     * Create an instance of {@link CreateIssueType }
     * 
     */
    public CreateIssueType createCreateIssueType() {
        return new CreateIssueType();
    }

    /**
     * Create an instance of {@link AddIssueCommentResponse }
     * 
     */
    public AddIssueCommentResponse createAddIssueCommentResponse() {
        return new AddIssueCommentResponse();
    }

    /**
     * Create an instance of {@link CreateIssue.Arg4 .Entry }
     * 
     */
    public CreateIssue.Arg4 .Entry createCreateIssueArg4Entry() {
        return new CreateIssue.Arg4 .Entry();
    }

    /**
     * Create an instance of {@link AddIssueComment }
     * 
     */
    public AddIssueComment createAddIssueComment() {
        return new AddIssueComment();
    }

    /**
     * Create an instance of {@link CreateIssue.Arg4 }
     * 
     */
    public CreateIssue.Arg4 createCreateIssueArg4() {
        return new CreateIssue.Arg4();
    }

    /**
     * Create an instance of {@link CreateIssueTypeResponse }
     * 
     */
    public CreateIssueTypeResponse createCreateIssueTypeResponse() {
        return new CreateIssueTypeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateIssueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "createIssueResponse")
    public JAXBElement<CreateIssueResponse> createCreateIssueResponse(CreateIssueResponse value) {
        return new JAXBElement<CreateIssueResponse>(_CreateIssueResponse_QNAME, CreateIssueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddIssueComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "addIssueComment")
    public JAXBElement<AddIssueComment> createAddIssueComment(AddIssueComment value) {
        return new JAXBElement<AddIssueComment>(_AddIssueComment_QNAME, AddIssueComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAttachmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "addAttachmentResponse")
    public JAXBElement<AddAttachmentResponse> createAddAttachmentResponse(AddAttachmentResponse value) {
        return new JAXBElement<AddAttachmentResponse>(_AddAttachmentResponse_QNAME, AddAttachmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateIssueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "createIssueType")
    public JAXBElement<CreateIssueType> createCreateIssueType(CreateIssueType value) {
        return new JAXBElement<CreateIssueType>(_CreateIssueType_QNAME, CreateIssueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "createProject")
    public JAXBElement<CreateProject> createCreateProject(CreateProject value) {
        return new JAXBElement<CreateProject>(_CreateProject_QNAME, CreateProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStatusNames }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "getStatusNames")
    public JAXBElement<GetStatusNames> createGetStatusNames(GetStatusNames value) {
        return new JAXBElement<GetStatusNames>(_GetStatusNames_QNAME, GetStatusNames.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "createProjectResponse")
    public JAXBElement<CreateProjectResponse> createCreateProjectResponse(CreateProjectResponse value) {
        return new JAXBElement<CreateProjectResponse>(_CreateProjectResponse_QNAME, CreateProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAttachment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "addAttachment")
    public JAXBElement<AddAttachment> createAddAttachment(AddAttachment value) {
        return new JAXBElement<AddAttachment>(_AddAttachment_QNAME, AddAttachment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateIssue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "createIssue")
    public JAXBElement<CreateIssue> createCreateIssue(CreateIssue value) {
        return new JAXBElement<CreateIssue>(_CreateIssue_QNAME, CreateIssue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStatusNamesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "getStatusNamesResponse")
    public JAXBElement<GetStatusNamesResponse> createGetStatusNamesResponse(GetStatusNamesResponse value) {
        return new JAXBElement<GetStatusNamesResponse>(_GetStatusNamesResponse_QNAME, GetStatusNamesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddWorkflow }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "addWorkflow")
    public JAXBElement<AddWorkflow> createAddWorkflow(AddWorkflow value) {
        return new JAXBElement<AddWorkflow>(_AddWorkflow_QNAME, AddWorkflow.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "changeStatus")
    public JAXBElement<ChangeStatus> createChangeStatus(ChangeStatus value) {
        return new JAXBElement<ChangeStatus>(_ChangeStatus_QNAME, ChangeStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "changeStatusResponse")
    public JAXBElement<ChangeStatusResponse> createChangeStatusResponse(ChangeStatusResponse value) {
        return new JAXBElement<ChangeStatusResponse>(_ChangeStatusResponse_QNAME, ChangeStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveAttachment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "removeAttachment")
    public JAXBElement<RemoveAttachment> createRemoveAttachment(RemoveAttachment value) {
        return new JAXBElement<RemoveAttachment>(_RemoveAttachment_QNAME, RemoveAttachment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddIssueCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "addIssueCommentResponse")
    public JAXBElement<AddIssueCommentResponse> createAddIssueCommentResponse(AddIssueCommentResponse value) {
        return new JAXBElement<AddIssueCommentResponse>(_AddIssueCommentResponse_QNAME, AddIssueCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddWorkflowResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "addWorkflowResponse")
    public JAXBElement<AddWorkflowResponse> createAddWorkflowResponse(AddWorkflowResponse value) {
        return new JAXBElement<AddWorkflowResponse>(_AddWorkflowResponse_QNAME, AddWorkflowResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveAttachmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "removeAttachmentResponse")
    public JAXBElement<RemoveAttachmentResponse> createRemoveAttachmentResponse(RemoveAttachmentResponse value) {
        return new JAXBElement<RemoveAttachmentResponse>(_RemoveAttachmentResponse_QNAME, RemoveAttachmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateIssueTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pubflow.de", name = "createIssueTypeResponse")
    public JAXBElement<CreateIssueTypeResponse> createCreateIssueTypeResponse(CreateIssueTypeResponse value) {
        return new JAXBElement<CreateIssueTypeResponse>(_CreateIssueTypeResponse_QNAME, CreateIssueTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = AddAttachment.class)
    public JAXBElement<byte[]> createAddAttachmentArg1(byte[] value) {
        return new JAXBElement<byte[]>(_AddAttachmentArg1_QNAME, byte[].class, AddAttachment.class, ((byte[]) value));
    }

}
