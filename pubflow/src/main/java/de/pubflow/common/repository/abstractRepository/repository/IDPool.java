package de.pubflow.common.repository.abstractRepository.repository;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pubflow.common.PropLoader;
import de.pubflow.common.exception.PropNotSetException;

public class IDPool {
	private static long l = 100;
	private static Logger myLogger;

	static{
		myLogger = LoggerFactory.getLogger(IDPool.class);	
		l = Long.parseLong(PropLoader.getInstance().getProperty("ID", IDPool.class.toString(), l + ""));
		myLogger.debug("Setting ID to IDPool value " + l);
	}

	public synchronized static long getUniqueID() throws IOException{

		l++;
		try {
			PropLoader.getInstance().updateProperty("ID", IDPool.class.toString(), l + "");
			myLogger.debug("ID increased to " + l);

		} catch (PropNotSetException e) {
			e.printStackTrace();
		}
		return l;
	}
}

