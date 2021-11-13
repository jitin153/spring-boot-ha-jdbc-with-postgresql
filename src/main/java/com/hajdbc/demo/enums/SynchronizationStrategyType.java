package com.hajdbc.demo.enums;

import java.util.HashMap;
import java.util.Map;

import net.sf.hajdbc.SynchronizationStrategy;
import net.sf.hajdbc.sync.DifferentialSynchronizationStrategy;
import net.sf.hajdbc.sync.DumpRestoreSynchronizationStrategy;
import net.sf.hajdbc.sync.FastDifferentialSynchronizationStrategy;
import net.sf.hajdbc.sync.FullSynchronizationStrategy;
import net.sf.hajdbc.sync.PassiveSynchronizationStrategy;
import net.sf.hajdbc.sync.PerTableSynchronizationStrategy;

public enum SynchronizationStrategyType {
	FULL(new FullSynchronizationStrategy()), DUMP_RESTORE(new DumpRestoreSynchronizationStrategy()),
	DIFF(new DifferentialSynchronizationStrategy()), FASTDIFF(new FastDifferentialSynchronizationStrategy()),
	PER_TABLE_FULL(new PerTableSynchronizationStrategy(new FullSynchronizationStrategy())),
	PER_TABLE_DIFF(new PerTableSynchronizationStrategy(new DifferentialSynchronizationStrategy())),
	PASSIVE(new PassiveSynchronizationStrategy());

	private SynchronizationStrategy synchronizationStrategy;

	private SynchronizationStrategyType(SynchronizationStrategy synchronizationStrategy) {
		this.synchronizationStrategy = synchronizationStrategy;
	}

	public static SynchronizationStrategy byId(String id) {
		for (SynchronizationStrategyType type : values()) {
			if (type.synchronizationStrategy.getId().equals(id)) {
				return type.synchronizationStrategy;
			}
		}
		return null;
	}

	public static Map<String, SynchronizationStrategy> map() {
		Map<String, SynchronizationStrategy> syncStrategyMap = new HashMap<>();
		for (SynchronizationStrategyType type : values()) {
			syncStrategyMap.put(type.synchronizationStrategy.getId(), type.synchronizationStrategy);
		}
		return syncStrategyMap;
	}
}