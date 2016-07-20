package de.pubflow.common.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class JiraAttachment {
	private String issueKey;
	private String type;
	private String filename;
	private byte[] data;

	public JiraAttachment(){
		
	}
	
	public JiraAttachment(String issueKey, String type, String filename, byte[] data) {
		super();
		this.issueKey = issueKey;
		this.type = type;
		this.filename = filename;
		this.data = data;
	}

	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}