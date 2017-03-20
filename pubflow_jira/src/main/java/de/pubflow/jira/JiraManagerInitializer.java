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
package de.pubflow.jira;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ofbiz.core.entity.GenericEntityException;

import com.atlassian.applinks.api.ApplicationId;
import com.atlassian.applinks.api.ApplicationType;
import com.atlassian.applinks.api.TypeNotInstalledException;
import com.atlassian.applinks.api.application.jira.JiraApplicationType;
import com.atlassian.applinks.spi.application.ApplicationIdUtil;
import com.atlassian.applinks.spi.link.ApplicationLinkDetails;
import com.atlassian.applinks.spi.link.MutableApplicationLink;
import com.atlassian.applinks.spi.link.MutatingApplicationLinkService;
import com.atlassian.applinks.spi.manifest.ManifestNotFoundException;
import com.atlassian.applinks.spi.util.TypeAccessor;
import com.atlassian.jira.bc.project.ProjectCreationData;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.properties.APKeys;
import com.atlassian.jira.config.properties.ApplicationProperties;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.jira.issue.fields.screen.FieldScreen;
import com.atlassian.jira.issue.fields.screen.FieldScreenScheme;
import com.atlassian.jira.issue.fields.screen.FieldScreenTab;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.permission.PermissionSchemeManager;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.workflow.JiraWorkflow;
import com.atlassian.jira.workflow.WorkflowManager;
import com.atlassian.jira.workflow.WorkflowScheme;
import com.atlassian.jira.workflow.WorkflowSchemeManager;
import com.atlassian.oauth.Consumer;
import com.atlassian.oauth.Consumer.SignatureMethod;
import com.atlassian.oauth.serviceprovider.ServiceProviderConsumerStore;
import com.atlassian.oauth.util.RSAKeys;
import com.atlassian.sal.api.message.I18nResolver;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;

import de.pubflow.common.PropLoader;
import de.pubflow.jira.accessors.JiraObjectCreator;
import de.pubflow.jira.accessors.JiraObjectGetter;
import de.pubflow.jira.accessors.JiraObjectManipulator;
import de.pubflow.jira.misc.CustomFieldDefinition;
import de.pubflow.server.core.workflow.WorkflowBroker;
import de.pubflow.server.core.workflow.types.AbstractWorkflow;
import de.pubflow.server.core.workflow.types.CVOOTo4DIDWorkflow;
import de.pubflow.server.core.workflow.types.EPrintsWorkflow;
import de.pubflow.server.core.workflow.types.RawToOCNWorkflow;

/**
 * 
 * @author arl
 *
 *         Jira Manager Core. The initializer creates on startup all for PubFlow
 *         required features, projects and workflows.
 * 
 *         Still some work to do: - add some more features - proper exception
 *         handling
 *
 *         - prevent and fix usage of generic types in newIssueType(),
 *         initProject(...)! (deprecated) - loads of other things....
 *
 *
 *         Things to mind: Jira objects are updated automatically when
 *         setXy(...) is used
 *
 */

@SuppressWarnings("PMD.ExcessiveImports")
public class JiraManagerInitializer {
	/**
	 * Logger for debuging and info messages.
	 */
	private static final Logger LOGGER = Logger.getLogger(JiraManagerInitializer.class);

	/**
	 * the custom fields to create on start up of the pubflow plugin
	 */
	public final List<CustomField> customFieldsCache = new LinkedList<CustomField>();

	/**
	 * The project Manager is used very often and is bound to a variable instead
	 * of calling the ComponentAccessor all the time
	 */
	private final ProjectManager projectManager = ComponentAccessor.getProjectManager();

	/**
	 * Creates a new Jira project
	 * 
	 * @param projectName the name of the new project
	 * @param projectKey the project's key
	 * @param user the ApplicationUser that holds the project (lead of the
	 *            project)
	 * 
	 * @return returns the created project object
	 * @throws Exception
	 */

	public Project initProject(final String projectName, final String projectKey, final ApplicationUser user,
			final boolean kill) throws Exception {

		LOGGER.debug(
				"initProject - projectName : " + projectName + " / projectKey : " + projectKey + " / kill : " + kill);
		final PermissionSchemeManager permissionSchemeManager = ComponentAccessor.getPermissionSchemeManager();

		if (user != null) {
			LOGGER.debug("initProject - user : " + user.getUsername());
		} else {
			LOGGER.error("initProject - user null");
			throw new Exception("User is null");
		}

		Project project = projectManager.getProjectObjByKey(projectKey);

		if (project == null) {
			final int avatarId = 10100;
			final ProjectCreationData projectData = new ProjectCreationData.Builder().withName(projectName)
					.withLead(user).withKey(projectKey).withDescription("Data Pulication Workflows")
					.withType("business").withAvatarId(new Long(avatarId)).build();
			project = projectManager.createProject(user, projectData);
			permissionSchemeManager.addDefaultSchemeToProject(project);
			ComponentAccessor.getNotificationSchemeManager().addDefaultSchemeToProject(project);
			LOGGER.info("initProject: created a new project with projectKey " + projectKey);
		} else {
			LOGGER.debug("initProject: project with projectKey " + projectKey + " already exists");
		}

		return project;
	}

	/**
	 * Initializes the issue types, issue type scheme and maps them to a project
	 *
	 * @author abar
	 * @param projectKey the project's key to add the issue type and scheme to
	 * @param issueTypeName the name of the issue type to create
	 */
	public void initIssueManagement(final String projectKey, final String issueTypeName, final String workflowID)
			throws CreateException {
		final Project project = projectManager.getProjectObjByKey(projectKey);
		JiraObjectCreator.createIssueType(project, issueTypeName, workflowID);
		final FieldConfigScheme issueTypeScheme = JiraObjectCreator.createIssueTypeScheme(project);

		JiraObjectManipulator.addIssueTypeSchemeToProject(issueTypeScheme, project);
	}

	/**
	 * 
	 * Creates all fieldscreens that will be mapped to the corresponding IDs in
	 * the workflows used by PubFlow.
	 * 
	 * @param customFields
	 * @param issueTypeName
	 * @param customFieldIdsTest
	 * @param project
	 * @return
	 * @throws Exception
	 */
	public void initFieldScreens(final List<CustomFieldDefinition> customFields, final String issueTypeName,
			final List<Long> customFieldIdsTest, final Project project) throws Exception {
		final JiraWorkflow jiraWorkflow = ComponentAccessor.getWorkflowManager().getWorkflow(issueTypeName);
		final Map<String, List<CustomFieldDefinition>> availableActionFieldScreens = new HashMap<String, List<CustomFieldDefinition>>();

		final CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();

		for (final CustomFieldDefinition customFieldDefinition : customFields) {
			for (final String customFieldID : customFieldDefinition.getScreens()) {
				// 'init' new screens, the id in the map represents the Screen
				// (id)
				if (availableActionFieldScreens.get(customFieldID) == null) {
					final List<CustomFieldDefinition> sameKeyDefs = new LinkedList<CustomFieldDefinition>();
					sameKeyDefs.add(customFieldDefinition);
					availableActionFieldScreens.put(customFieldID, sameKeyDefs);
					// add to existing screens
				} else {
					availableActionFieldScreens.get(customFieldID).add(customFieldDefinition);
				}
				LOGGER.debug("initFieldScreens: transition screen grouping loops / id : " + customFieldID + " / name : "
						+ customFieldDefinition.getName());
			}
		}

		for (final Entry<String, List<CustomFieldDefinition>> e : availableActionFieldScreens.entrySet()) {
			final List<String> customFieldIds = new LinkedList<String>();

			for (final CustomFieldDefinition c : e.getValue()) {
				LOGGER.debug("initHumbleScreens: transition screen id loops / c.getName() : " + c.getName() + "_"
						+ issueTypeName);
				final Collection<CustomField> foundCustomFields = customFieldManager
						.getCustomFieldObjectsByName(c.getName());

				// should be created beforehand
				if (foundCustomFields != null) {
					customFieldIds.add(foundCustomFields.iterator().next().getId());
				} else {
					LOGGER.error("initHumbleScreens: custom field is null / c.getName() : " + c.getName() + "_"
							+ issueTypeName);
				}
			}

			final FieldScreen fieldScreen = JiraObjectCreator.createActionScreen(issueTypeName + "Action" + e.getKey());
			final List<FieldScreenTab> fieldScreenTabs = fieldScreen.getTabs();

			FieldScreenTab jobTab = null;
			for (final FieldScreenTab fieldScreenTab : fieldScreenTabs) {
				if (fieldScreenTab.getName().equals("Job")) {
					jobTab = fieldScreenTab;
					break;
				}
			}

			if (jobTab == null) {
				jobTab = fieldScreen.addTab("Job");
			}

			for (final String customFieldId : customFieldIds) {
				if (jobTab.getFieldScreenLayoutItem(customFieldId) == null) {
					jobTab.addFieldScreenLayoutItem(customFieldId);
				}
			}

			final Collection<ActionDescriptor> allActions = jiraWorkflow.getAllActions();
			final Map<String, String> metaAttributes = new HashMap<String, String>();
			metaAttributes.put("jira.fieldscreen.id", Long.toString(fieldScreen.getId()));
			for (final ActionDescriptor aActions : allActions) {
				if (aActions.getId() == Integer.parseInt(e.getKey())) {
					aActions.setView(fieldScreen.getName());
					aActions.setMetaAttributes(metaAttributes);
				}
			}
		}
	}

	/**
	 * Initializes the Look&Feel.
	 */
<<<<<<< .merge_file_yl9831
	private static JiraWorkflow initWorkflow(String projectKey, String workflowXML, ApplicationUser user,
			String issueTypeName) {
		if (user != null) {
			log.debug("initWorkflow: user : " + user.getUsername());
		} else {
			log.error("initWorkflow: user is null");
		}

		JiraWorkflow jiraWorkflow = JiraObjectCreator.addWorkflow(projectKey, workflowXML, user, issueTypeName);
		WorkflowScheme workflowScheme = JiraObjectCreator.createWorkflowScheme(projectKey, user, jiraWorkflow,
				issueTypeName);
		JiraObjectManipulator.addWorkflowToProject(workflowScheme, projectManager.getProjectObjByKey(projectKey));

		return jiraWorkflow;

	}

	private static void createAppLinks() throws ManifestNotFoundException, NoSuchAlgorithmException,
			InvalidKeySpecException, InstantiationException, IllegalAccessException, TypeNotInstalledException {
		final String TD_APPLICATION_LINK_URL = "pubflow.application.link.url";
		final String TD_APPLICATION_LINK_NAME = "pubflow.application.link.name";
		final String TD_CONSUMER_KEY = "pubflow.consumer.key";
		final String TD_CONSUMER_NAME = "pubflow.consumer.name";
		final String TD_CONSUMER_PUBLIC_KEY = "pubflow.consumer.public.key";

		final I18nResolver i18nResolver = JiraManagerPlugin.i18nResolver;
		final MutatingApplicationLinkService appLinkService = JiraManagerPlugin.applicationLinkService;
		final ServiceProviderConsumerStore serviceProviderConsumerStore = JiraManagerPlugin.serviceProviderConsumerStore;
		URI linkUrl = URI.create(TD_APPLICATION_LINK_URL);
		ApplicationId appId = ApplicationIdUtil.generate(URI.create(TD_APPLICATION_LINK_NAME));

		if (appLinkService.getApplicationLink(appId) == null) {
			ApplicationLinkDetails link = ApplicationLinkDetails.builder().name(appId.get()).displayUrl(linkUrl)
					.rpcUrl(linkUrl).build();

			ApplicationType appType = findGenericApplicationType();

			MutableApplicationLink appLink = appLinkService.addApplicationLink(appId, appType, link);

			String consumerKey = i18nResolver.getText(TD_CONSUMER_KEY);
			String consumerName = i18nResolver.getText(TD_CONSUMER_NAME);
			String consumerPublicKey = i18nResolver.getText(TD_CONSUMER_PUBLIC_KEY);

			PublicKey publicKey = RSAKeys.fromPemEncodingToPublicKey(consumerPublicKey);
			Consumer consumer = 
					Consumer.key(consumerKey)
					.name(consumerName).publicKey(publicKey).twoLOAllowed(true)
					.twoLOImpersonationAllowed(true).executingTwoLOUser("PubFlow")
					.signatureMethod(SignatureMethod.RSA_SHA1).build();
			
			serviceProviderConsumerStore.put(consumer);
			appLink.putProperty("oauth.incoming.consumerkey", consumer.getKey());
		}

	}

	private static ApplicationType findGenericApplicationType() {
		// need GenericApplicationType but can not to use
		// typeAccessor.getApplicationType(GenericApplicationType.class) because
		// API bug
		final TypeAccessor typeAccessor = JiraManagerPlugin.typeAccessor;
		ApplicationType applicationType = typeAccessor.getApplicationType(JiraApplicationType.class);

		// find GenericApplicationType from all enable application types
		for (ApplicationType enabledApplicationType : typeAccessor.getEnabledApplicationTypes()) {
			if (enabledApplicationType.getClass().getName().contains("GenericApplicationType")) {
				applicationType = enabledApplicationType;
				break;
			}
		}

		return applicationType;
	}

	/**
	 * Initializes the Look&Feel
	 */
	public static void initJiraSettings() {
=======
	public void initJiraSettings() {
>>>>>>> .merge_file_lYzWMn
		final ApplicationProperties applicationProperties = ComponentAccessor.getApplicationProperties();
		applicationProperties.setString(APKeys.JIRA_BASEURL,
				PropLoader.getInstance().getProperty("JIRA_BASEURL", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_MODE,
				PropLoader.getInstance().getProperty("JIRA_MODE", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_TITLE,
				PropLoader.getInstance().getProperty("JIRA_TITLE", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_TOP_BGCOLOUR,
				PropLoader.getInstance().getProperty("JIRA_LF_TOP_BGCOLOUR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_TOP_HIGHLIGHTCOLOR,
				PropLoader.getInstance().getProperty("JIRA_LF_TOP_HIGHLIGHTCOLOR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_TOP_SEPARATOR_BGCOLOR,
				PropLoader.getInstance().getProperty("JIRA_LF_TOP_SEPARATOR_BGCOLOR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_TOP_TEXTCOLOUR,
				PropLoader.getInstance().getProperty("JIRA_LF_TOP_TEXTCOLOUR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_TOP_TEXTHIGHLIGHTCOLOR,
				PropLoader.getInstance().getProperty("JIRA_LF_TOP_TEXTHIGHLIGHTCOLOR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_MENU_BGCOLOUR,
				PropLoader.getInstance().getProperty("JIRA_LF_MENU_BGCOLOUR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_MENU_SEPARATOR,
				PropLoader.getInstance().getProperty("JIRA_LF_MENU_SEPARATOR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_MENU_TEXTCOLOUR,
				PropLoader.getInstance().getProperty("JIRA_LF_MENU_TEXTCOLOUR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_HERO_BUTTON_BASEBGCOLOUR,
				PropLoader.getInstance().getProperty("JIRA_LF_HERO_BUTTON_BASEBGCOLOUR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_HERO_BUTTON_TEXTCOLOUR,
				PropLoader.getInstance().getProperty("JIRA_LF_HERO_BUTTON_TEXTCOLOUR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_TEXT_ACTIVE_LINKCOLOUR,
				PropLoader.getInstance().getProperty("JIRA_LF_TEXT_ACTIVE_LINKCOLOUR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_TEXT_HEADINGCOLOUR,
				PropLoader.getInstance().getProperty("JIRA_LF_TEXT_HEADINGCOLOUR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_TEXT_LINKCOLOUR,
				PropLoader.getInstance().getProperty("JIRA_LF_TEXT_LINKCOLOUR", JiraManagerInitializer.class));
		applicationProperties.setString(APKeys.JIRA_LF_LOGO_URL,
				PropLoader.getInstance().getProperty("JIRA_LF_LOGO_URL", JiraManagerInitializer.class));

		// SMTPMailServerImpl smtp = new SMTPMailServerImpl();
		// smtp.setName("Mail Server");
		// smtp.setDescription("");
		// smtp.setDefaultFrom("pubflow@bough.de");
		// smtp.setPrefix("[pubflow]");
		// smtp.setPort("587");
		// smtp.setMailProtocol(MailProtocol.SMTP);
		// smtp.setHostname("mail.bough.de");
		// smtp.setUsername("wp10598327-pubflow");
		// smtp.setPassword("kidoD3l77");
		// smtp.setTlsRequired(true);
		// try {
		// ComponentAccessor.getMailServerManager().create(smtp);
		// } catch (MailException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * Initializes the whole PubFlow project.
	 * 
	 * Set application properties. "PubFlow" project will be initialized. First,
	 * all required user groups, statuses, IssueTypes, workflows, schemes,
	 * custom fields and fieldscreens are created. Afterwards all the status,
	 * issue types, workflows and custom fields are mapped to their screens and
	 * schemes.
	 * 
	 * @author arl, abar
	 * 
	 */
	public void initPubFlowProject() throws GenericEntityException, KeyManagementException, UnrecoverableKeyException,
			NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		final ApplicationProperties applicationPropertiesManager = ComponentAccessor.getApplicationProperties();
		applicationPropertiesManager.setString(APKeys.JIRA_TITLE, "PubFlow Jira");
		applicationPropertiesManager.setString(APKeys.JIRA_MODE, "Private");
		applicationPropertiesManager.setString(APKeys.JIRA_BASEURL, "http://maui.informatik.uni-kiel.de:2990/jira/");

		initJiraSettings();

		final String projectKey = "PUB";
		Project project = projectManager.getProjectObjByName("PubFlow");

		try {
			if (project == null) {

				final JiraDefaultUser defaultUserCreator = new JiraDefaultUser();
				// Add users and return the project owner
				final ApplicationUser owningUser = defaultUserCreator.addDefaultUser();

				LOGGER.debug("initPubfowProject: created users and usergroups for PubFlow");

				project = initProject("PubFlow", projectKey, owningUser, false);
			}

			final List<String> statuses = new LinkedList<String>();

			// the order of the statuses is important for the id
			// the id has to be the same as in the xml for jira
			// this is the way how jira does things

			// should already exist in Jira with ID=1
			statuses.add("Open");

			// Ready for Convertion by Data Management ID: 10000
			// quickfix: 10100
			statuses.add("Ready for Convertion by Data Management");
			// Ready for OCN-Import already ID: 10001
			// quickfix: 10101
			statuses.add("CVOO-Import");
			// Prepare for PubFlow ID: 10002
			// quickfix: 10102
			statuses.add("Prepared for PubFlow");
			// Data Processing by PubFlow ID: 10003
			// quickfix: 10103
			statuses.add("Data Processing by PubFlow");
			// Ready for Pangaea-Import ID: 10004
			// quickfix: 10104
			statuses.add("Ready for Pangaea-Import");
			// Data Needs Correction ID: 10005
			// quickfix: 10105
			statuses.add("Data Needs Correction");
			// Waiting for DOI ID: 10006
			// quickfix: 10106
			statuses.add("Waiting for DOI");
			// should already exist in Jira with ID=6
			statuses.add("Closed");
			// Done ID: 10007
			// quickfix:10001
			statuses.add("Done");
			// Rejected ID: 10008
			// quickfix: 10107
			statuses.add("Rejected");
			// Pangaea Data Upload ID:10009
			// quickfix: 10108
			statuses.add("Pangaea Data Upload");
			// should be (test it):
			// Pangaea Data Upload ID:10010
			// quickfix: 10109
			statuses.add("Aquire ORCIDs");

			// add new statuses at the end
			// TODO is there a more generic solution?

			JiraObjectCreator.addStatuses(projectKey, statuses);

		} catch (final Exception exception) {
			exception.printStackTrace();
			return;
		}

		final UserManager userManager = ComponentAccessor.getUserManager();
		final ApplicationUser user = userManager.getUserByName("PubFlow");

		// add workflows

		final List<AbstractWorkflow> workflowsToAdd = new LinkedList<>();
		workflowsToAdd.add(new EPrintsWorkflow());
		// workflowsToAdd.add(new OCNTo4DWorkflow());
		// workflowsToAdd.add(new CVOOTo4DWorkflow());
		workflowsToAdd.add(new CVOOTo4DIDWorkflow());
		workflowsToAdd.add(new RawToOCNWorkflow());

		// for testing purposes
		// workflowsToAdd.add(new OldOCNWorkflow());

		// add the workflows one after another
		for (final AbstractWorkflow workflow : workflowsToAdd) {
			try {
				addNewWorkflow(workflow, project, user);
			} catch (final Exception exception) {
				LOGGER.info("Could not add Workflow: " + workflow.getWorkflowName());
				LOGGER.debug("", exception);
			}
		}

		try {
			createAppLinks();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManifestNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TypeNotInstalledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Create a new workflow in PubFlow
	 * 
	 * @param workflow an abstract workflow
	 * @param project the project the workflow to add the workflow to
	 * @param user a user with the permission to create a workflow
	 * 
	 * @throws Exception
	 */
	private void addNewWorkflow(final AbstractWorkflow workflow, final Project project, final ApplicationUser user)
			throws Exception {
		LOGGER.info("Adding Worklow: " + workflow.getWorkflowName() + "to project key: " + project.getKey());
		LOGGER.info("Using XML file located at: " + workflow.getJiraWorkflowXMLPath());

		final String workflowName = workflow.getWorkflowName();
		final WorkflowManager workflowManager = ComponentAccessor.getWorkflowManager();
		JiraWorkflow jiraWorkflow = workflowManager.getWorkflow(workflowName);

		final String workflowXMLString = JiraManagerPlugin.getTextResource(workflow.getJiraWorkflowXMLPath());
		initIssueManagement(project.getKey(), workflowName, workflow.getWorkflowID());

		// TODO verify if this step does the right thing
		jiraWorkflow = createWorkflowAndMapIssueTypeIDs(project.getKey(), workflow, user, workflowXMLString);

		final IssueType issueType = JiraObjectGetter.getIssueTypeByName(workflowName);
		final WorkflowSchemeManager workflowSchemeManager = ComponentAccessor.getWorkflowSchemeManager();

		try {
			workflowSchemeManager.addWorkflowToScheme(workflowSchemeManager.getWorkflowScheme(project),
					jiraWorkflow.getName(), issueType.getId());
			LOGGER.info("initWorkflow: added the workflow: " + jiraWorkflow.getName()
					+ " to the workflowscheme of the project: " + project.getName());
		} catch (final GenericEntityException e) {
			// TODO Auto-generated catch block
			LOGGER.error("initWorkflow: Couldn't  add the workflow: " + jiraWorkflow.getName()
					+ " to the workflowscheme of the project: " + project.getName());
			e.printStackTrace();
		}

<<<<<<< .merge_file_yl9831
		List<CustomFieldDefinition> customFields = workflow.getCustomFields();
		List<Long> customFieldIds = JiraObjectCreator.createCustomFields(customFields, project, workflowName);
=======
		final List<CustomFieldDefinition> customFields = workflow.getCustomFields();
		final List<Long> customFieldIds = JiraObjectCreator.createCustomFields(customFields, project, workflowName);
>>>>>>> .merge_file_lYzWMn
		// TODO use screenNames in initHumbleScreens
		// List<String> screenNames = workflow.getScreenNames();
		final FieldScreenScheme fieldScreenScheme = createBasicFieldScreens(workflow);
		// FieldScreenScheme fieldScreenScheme = initHumbleScreens(customFields,
		// workflowName, customFieldIds, project);
		initFieldScreens(customFields, workflowName, customFieldIds, project);

		JiraObjectManipulator.addIssueTypeScreenSchemeToProject(project, fieldScreenScheme, issueType);

		// register Workflow with WorkflowBroker
		// this should happen even if the Workflow is already saved by jira
		WorkflowBroker.addWorkflow(workflow);

	}

	/**
	 * Gathers the steps defined in the Workflow XML files and maps the id's of
	 * the status accordingly
	 * 
	 * @param projectKey the project's key of to add a workflow to
	 * @param workflow the workflow to be mapped to the project
	 * @param user a user with the permission to map a workflow to a project
	 * @param workflowXMLString
	 *            the XML that defines the workflow
	 */
	@SuppressWarnings("unchecked")
	private JiraWorkflow createWorkflowAndMapIssueTypeIDs(final String projectKey, final AbstractWorkflow workflow,
			final ApplicationUser user, final String workflowXMLString) {

		final String issueTypeName = workflow.getWorkflowName();

		JiraWorkflow jiraWorkflow = ComponentAccessor.getWorkflowManager().getWorkflow(issueTypeName);

		// only create workflow, if it doesn't exist
		if (jiraWorkflow != null) {
			LOGGER.info("Issuetype ID mapping: " + issueTypeName + " expected, but doesnt exist");
			return jiraWorkflow;
		}

		jiraWorkflow = JiraObjectCreator.addWorkflow(projectKey, workflowXMLString, user, issueTypeName);

		LOGGER.debug("Issuetype ID mapping:  " + issueTypeName);

		final List<String> statuses = JiraManagerPlugin.getSteps(workflowXMLString);

		final Map<String, String> statusMap = JiraObjectCreator.addStatuses(projectKey, statuses);

		// Map Steps to Statuses
		// for each defined step lookup the meta attributes and update the
		// jira.status.id accordingly
		for (final StepDescriptor step : (List<StepDescriptor>) jiraWorkflow.getDescriptor().getSteps()) {
			LOGGER.info("newIssueType - step : " + step.getId() + " / " + step.getName());
			final String stepName = step.getName();

			if (statuses.contains(stepName)) {
				final String statusID = statusMap.get(stepName);
				LOGGER.info("newIssueType - step : workflow contains step " + stepName);

				// TODO can harm be done if some status exists here and no id is
				// there?
				if (!statusID.isEmpty()) {
					for (final Entry<String, String> entry : (Set<Entry<String, String>>) step.getMetaAttributes()
							.entrySet()) {
						if (entry.getKey().equals("jira.status.id")) {
							step.getMetaAttributes().put("jira.status.id", statusID);
							LOGGER.info("newIssueType - setting jira.status.id : " + statusID + " in step");
						}
					}
				}
			}

			// ComponentAccessor.getWorkflowManager().updateWorkflow(issueTypeName
			// + WORKFLOW_APPENDIX, jiraWorkflow);
			LOGGER.info("newIssueType - updating workflow " + jiraWorkflow.getName() + " / user : " + user.getName());
			ComponentAccessor.getWorkflowManager().updateWorkflow(user, jiraWorkflow);
		}

		final WorkflowScheme workflowScheme = JiraObjectCreator.createWorkflowScheme(projectKey, user, jiraWorkflow,
				issueTypeName);
		JiraObjectManipulator.addWorkflowToProject(workflowScheme, projectManager.getProjectObjByKey(projectKey));

		return jiraWorkflow;
	}

	/**
	 * Creates the fieldscreen to create, edit and delete a workflow. This
	 * fieldscreens can take default mappings from Jira.
	 * 
	 * @param workflow the workflow to add the fieldscreens to
	 * 
	 * @return FieldScreenScheme with the fieldscreens to create, edit and
	 *         view an issue for a workflow
	 * 
	 * @throws Exception
	 */
	private FieldScreenScheme createBasicFieldScreens(final AbstractWorkflow workflow) throws Exception {
		final FieldScreen fieldScreenCreate = JiraObjectCreator
				.createActionScreen(workflow.getScreenNames().get("create"));
		final FieldScreen fieldScreenView = JiraObjectCreator.createActionScreen(workflow.getScreenNames().get("view"));
		final FieldScreen fieldScreenEdit = JiraObjectCreator.createActionScreen(workflow.getScreenNames().get("edit"));

		final FieldScreenScheme fieldScreenScheme = JiraObjectCreator.createNewFieldScreenScheme(fieldScreenCreate,
				fieldScreenView, fieldScreenEdit, workflow.getWorkflowName());

		return fieldScreenScheme;

	}
}