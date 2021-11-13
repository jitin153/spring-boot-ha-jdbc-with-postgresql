package com.hajdbc.demo.enums;

import net.sf.hajdbc.state.StateManagerFactory;
import net.sf.hajdbc.state.simple.SimpleStateManagerFactory;

public enum StateManagerFactoryType {
	SIMPLE(new SimpleStateManagerFactory()); // A non-persistent state manager that stores cluster state in memory.
	/*
	 * In case of below State Manager Factories getting error hence commented out.
	 * Below are all persistent state managers. They use database hence might need configuration for that too. Need to check.
	 * Check below URL for more information.
	 * http://ha-jdbc.org/doc.html
	 */
	//BERKELEYDB(new BerkeleyDBStateManagerFactory()),
	//SQLITE(new SQLiteStateManagerFactory()),
	//SQL(new SQLStateManagerFactory());

	private StateManagerFactory stateManagerFactory;

	private StateManagerFactoryType(StateManagerFactory stateManagerFactory) {
		this.stateManagerFactory = stateManagerFactory;
	}

	public static StateManagerFactory byId(String id) {
		for (StateManagerFactoryType type : values()) {
			if (type.stateManagerFactory.getId().equals(id)) {
				return type.stateManagerFactory;
			}
		}
		return null;
	}

}