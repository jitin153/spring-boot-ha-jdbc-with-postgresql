package com.hajdbc.demo.enums;

import net.sf.hajdbc.balancer.BalancerFactory;
import net.sf.hajdbc.balancer.random.RandomBalancerFactory;
import net.sf.hajdbc.balancer.roundrobin.RoundRobinBalancerFactory;
import net.sf.hajdbc.balancer.simple.SimpleBalancerFactory;

public enum BalancerFactoryType {
	ROUND_ROBIN(new RoundRobinBalancerFactory()), RANDOM(new RandomBalancerFactory()),
	SIMPLE(new SimpleBalancerFactory());

	private BalancerFactory balancerFactory;

	private BalancerFactoryType(BalancerFactory balancerFactory) {
		this.balancerFactory = balancerFactory;
	}

	public static BalancerFactory byId(String id) {
		for (BalancerFactoryType type : values()) {
			if (type.balancerFactory.getId().equals(id)) {
				return type.balancerFactory;
			}
		}
		return null;
	}
}