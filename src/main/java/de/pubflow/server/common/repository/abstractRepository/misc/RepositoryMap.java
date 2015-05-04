package de.pubflow.server.common.repository.abstractRepository.misc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.pubflow.server.common.persistence.entities.ObjectEntity;


public class RepositoryMap {
	private List<ObjectEntity> entries = new ArrayList<ObjectEntity>();

	public List<Long> getAllIds(){
		List<Long> l = new LinkedList<Long>();
		for(ObjectEntity e : entries){
			l.add(e.getId());
		}
		return l;
	}
	
	public List<ObjectEntity> getEntries() {
		return entries;
	}

	public void setEntries(List<ObjectEntity> entries) {
		this.entries = entries;
	}

	public long add(Object o) throws IOException{
		long id = IDPool.getUniqueID();		
		entries.add(new ObjectEntity(id, o));
		return id;
	}

	public void add(Object o, long id) throws IOException{
		boolean success = false;
		
		for(ObjectEntity oe : entries){
			if(oe.getId() == id){
				oe.setObject(o);
				success = true;
				break;
			}
		}
		
		if(!success){
			throw new IOException("No entry with id " + id);
		}
	}

	public void remove(long l) throws IOException{		
		for(ObjectEntity entry : entries){
			if(entry.getId() == l){
				entries.remove(entry);
				break;
			}
		}
	}

	public Object get(long l) throws IOException, ClassNotFoundException{
		for(ObjectEntity entry : entries){
			if(entry.getId() == l){
				return entry.getObject();
			}
		}
		return null;
	}
}
