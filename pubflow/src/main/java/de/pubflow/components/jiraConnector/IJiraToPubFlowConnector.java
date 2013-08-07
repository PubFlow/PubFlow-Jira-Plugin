package de.pubflow.components.jiraConnector;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.pubflow.common.entity.PubFlowMessage;


@WebService(targetNamespace = "pubflow.de")
@SOAPBinding(style = Style.RPC)
public interface IJiraToPubFlowConnector {

	@javax.jws.WebMethod
	public void eventNotification(@javax.jws.WebParam(name = "jiraMessage") PubFlowMessage message);

}