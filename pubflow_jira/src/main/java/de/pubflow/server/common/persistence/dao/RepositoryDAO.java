package de.pubflow.server.common.persistence.dao;

import de.pubflow.server.common.persistence.entities.ObjectEntity;


public class RepositoryDAO extends BasicDAO<ObjectEntity>{

	public RepositoryDAO() {
		super(ObjectEntity.class, "ObjectEntity");
		// TODO Auto-generated constructor stub
	}

	public ObjectEntity getObject(long l){
		logger.info("Get");

		p.startTransaction();
		ObjectEntity result = p.<ObjectEntity>getObject(clazz, l);
		p.stopTransaction();
		return result;
	}

	public void remove(long l){
		logger.info("Remove");

		p.startTransaction();
		ObjectEntity o = p.getObject(clazz, l);
		p.remove(o);
		p.stopTransaction();
	}
}