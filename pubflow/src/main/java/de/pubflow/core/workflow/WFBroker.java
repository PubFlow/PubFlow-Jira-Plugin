package de.pubflow.core.workflow;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.camel.Consume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pubflow.common.entity.repository.WorkflowEntity;
import de.pubflow.common.entity.workflow.JBPMPubflow;
import de.pubflow.common.entity.workflow.PubFlow;
import de.pubflow.common.entity.workflow.WFParamList;
import de.pubflow.common.enumerartion.WFType;
import de.pubflow.common.exception.WFException;
import de.pubflow.common.repository.workflow.WorkflowProvider;
import de.pubflow.core.communication.message.MessageToolbox;
import de.pubflow.core.communication.message.workflow.WorkflowMessage;
import de.pubflow.core.workflow.engine.WorkflowEngine;
import de.pubflow.core.workflow.engine.jbpm.JBPMEngine;

public class WFBroker {

	private static final String WFID = "WFID";
	private static final String PARAMS = "WFPARAMS";

	private static volatile WFBroker instance;
	private static final String WFCallChannel = "test-jms:WFBroker:out.queue";
	private static final String WFResponseChannel = "WFBroker:from:msg.queue";

	private Logger myLogger;

	private Hashtable<WFType, ArrayList<Class<? extends WorkflowEngine> >> registry;


	private WFBroker()
	{
		myLogger = LoggerFactory.getLogger(this.getClass());	
		myLogger.info("Starting WFBroker");
		registry = new Hashtable<WFType,ArrayList<Class<? extends WorkflowEngine> >>();

		ArrayList<Class<? extends WorkflowEngine> > bpmn2Engines = new ArrayList<Class<? extends WorkflowEngine> >(); 
		bpmn2Engines.add(JBPMEngine.class);

		registry.put(WFType.BPMN2, bpmn2Engines);

	}


	public static synchronized WFBroker getInstance()
	{
		if(instance == null)
		{
			instance = new WFBroker();
		}
		return instance;
	}


	@Consume(uri = "test-jms:wfbroker:in.queue")
	public void receiveWFCall(String msg)
	{
		//TODO implement rest
		myLogger.info("recived WF-Msg: " + msg);
		WorkflowMessage wm = MessageToolbox.loadFromString(msg, WorkflowMessage.class);
		if(!wm.isValid())
		{
			myLogger.error("Workflow NOT deployed >> Msg is not valid ");
			return;
		}
		myLogger.info("Loading WF with ID ("+wm.getWorkflowID()+") from WFRepo");
		WorkflowProvider provider = WorkflowProvider.getInstance();
		WorkflowEntity wfEntity = provider.getByWFID(wm.getWorkflowID());
		WFType type = wfEntity.getType();

		PubFlow myWF = null;
		if(type.equals(WFType.BPMN2)){
			myLogger.info("BPMN2.0 Workflow detected");
			myWF = new JBPMPubflow();
			myWF.setWFID(wfEntity.getWFID());
			myWF.setWfDef(wfEntity.getgBpmn());
			myLogger.info("Set WFDef: "+wfEntity.getgBpmn().toString());
			//TODO fill var
		}else if (type.equals(WFType.BPEL)) {
			myLogger.info("BPEL Workflow detected");
			//TODO
		}else{
			myLogger.error("Workflow NOT deployed >> Type could not be resolved");
			return;
		}


		WorkflowEngine engine = null;
		if(type!=null){
			ArrayList<Class<? extends WorkflowEngine> > engineList = registry.get(type);
			Class<? extends WorkflowEngine> clazz = engineList.get(0);
			try {
				myLogger.info("Creating new "+clazz.getCanonicalName());
				engine = clazz.newInstance();
				myLogger.info("Instance created! ");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//TODO: dead code??
		else{
			myLogger.error("Workflow NOT deployed >> Msg was malformed / No type provided");
			return;
		}

		try {
			myLogger.info("deploying WF");
			engine.deployWF(myWF);
			WFParamList params = wm.getWfparams();
			if (params!=null){
				myLogger.info("Parameter found ...");
				engine.setParams(wm.getWfparams());
			}else{
				myLogger.info("No Parameter found!");

			}
			myLogger.info("Starting wf ...");
			Thread wfEngineThread = new Thread(engine);
			wfEngineThread.start();
			myLogger.info("... engine up and running");
			sendWFResponse("WF Started");
		} catch (WFException e) {
			e.printStackTrace();
		}
	}



	private void sendWFResponse(String msg)
	{
		//TODO
	}
}
