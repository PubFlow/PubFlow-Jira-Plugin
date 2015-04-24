package de.pubflow.server.common.repository.abstractRepository.adapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.pubflow.server.PubFlowSystem;
import de.pubflow.server.common.persistence.entities.ObjectEntity;
import de.pubflow.server.common.properties.PropLoader;

public class FSStorageAdapter extends StorageAdapter {

	/** DEFAULT PROPERTIES **/
	public static final String DEFAULT_FSSTORAGEPATH = "etc/";

	private static String storagePath = PubFlowSystem.getInstance().pubflowHome + PropLoader.getInstance().getProperty("FSSTORAGEPATH", FSStorageAdapter.class, DEFAULT_FSSTORAGEPATH);

	public static String getStoragePath() {
		return storagePath;
	}

	protected Object onRestore(long id) throws IOException {

		FileInputStream fin = new FileInputStream(storagePath + "FSStorageAdapter/" + id + ".pub");
		ObjectInputStream oos = new ObjectInputStream(fin);
		ObjectEntity oe;

		try {
			oe = (ObjectEntity) oos.readObject();

		} catch (ClassNotFoundException e) {
			throw new IOException(e);

		} finally {
			oos.close();
		}

		try {
			return oe.getObject();

		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
	}

	protected void onUpdate(ObjectEntity o) throws IOException {

		FileOutputStream fout = new FileOutputStream(storagePath + "FSStorageAdapter/" +  o.getId() + ".pub");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(o);
		oos.close();	
	}

	protected void onDelete(long id) throws IOException {
		File fin = new File(storagePath + "FSStorageAdapter/" + id + ".pub");
		fin.delete();
	}


}
