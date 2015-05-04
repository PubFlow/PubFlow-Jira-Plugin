/***************************************************************************
 * Copyright 2012 by
 *  + Christian-Albrechts-University of Kiel
 *    + Department of Computer Science
 *      + Software Engineering Group 
 *  and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
/**
 * @author arl
 *
 */

package de.pubflow.server.common.repository.abstractRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.pubflow.server.common.repository.abstractRepository.adapters.StorageAdapter;
import de.pubflow.server.common.repository.abstractRepository.misc.ERepositoryName;

public abstract class BasicProvider<T> implements IProvider<T> {

	protected BasicRepository br;

	protected BasicProvider(ERepositoryName repositoryName, StorageAdapter storageAdapter){
		try {
			br = new BasicRepository(repositoryName, storageAdapter);
		} catch (IOException e) {
			System.out.println("Cannot create BasicProvider.");
			e.printStackTrace();
		}
	}

	
	
	public void clear() {
		br.removeAll();
	}

	public long addEntry(T o) {
		return br.add(o);
	}

	@SuppressWarnings("unchecked")
	public T getEntry(long id) {
		return (T)br.get(id);

	}


	@SuppressWarnings("unchecked")
	public List<T> getAllEntries() {
		List<Long> allIds = br.getAllIds();
		List<T> result = new ArrayList<T>();

		for(Long l : allIds){
			result.add((T) br.get(l));
		}

		return result;
	}
}
