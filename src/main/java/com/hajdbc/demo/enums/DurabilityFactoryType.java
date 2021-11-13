package com.hajdbc.demo.enums;

import net.sf.hajdbc.durability.DurabilityFactory;
import net.sf.hajdbc.durability.coarse.CoarseDurabilityFactory;
import net.sf.hajdbc.durability.fine.FineDurabilityFactory;
import net.sf.hajdbc.durability.none.NoDurabilityFactory;

public enum DurabilityFactoryType {

	NONE(new NoDurabilityFactory()), COARSE(new CoarseDurabilityFactory()), FINE(new FineDurabilityFactory());

	private DurabilityFactory durabilityFactory;

	private DurabilityFactoryType(DurabilityFactory durabilityFactory) {
		this.durabilityFactory = durabilityFactory;
	}

	public static DurabilityFactory byId(String id) {
		for (DurabilityFactoryType type : values()) {
			if (type.durabilityFactory.getId().equals(id)) {
				return type.durabilityFactory;
			}
		}
		return null;
	}
}
