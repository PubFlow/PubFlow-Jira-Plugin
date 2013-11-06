package de.pubflow.wfCompUntis.ocn.entity;

import javax.xml.bind.annotation.XmlRootElement;

import de.pubflow.wfCompUntis.ocn.entity.abstractClass.PubJect;



@XmlRootElement
public class Leg extends PubJect{

	public static final String NAME = "leg_name";
	public static final String EXPOCODE = "leg_expocode"; 
	public static final String ID = "leg_id";
	public static final String PARAMETERLIST = "parameterList";
	public static final String BOTTLELIST = "bottleList";
	public static final String LOGSTING = "logString";

	public static final String[] c_LEGTABLE = {NAME, EXPOCODE, ID};
}
