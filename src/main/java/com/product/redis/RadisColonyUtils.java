package com.product.redis;

import java.util.LinkedHashSet;
import java.util.Set;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/***
 * @Title Radis集群时需要使用
 * @author wuyongchao
 * @date 2018-10-22  
 * */
public class RadisColonyUtils {
	
	private static JedisCluster jedisCluster;

	static {
		Set hostAndPorts = new LinkedHashSet();
		hostAndPorts.add(new HostAndPort("192.168.21.133", 7001));
		hostAndPorts.add(new HostAndPort("192.168.21.133", 7002));
		hostAndPorts.add(new HostAndPort("192.168.21.133", 7003));
		hostAndPorts.add(new HostAndPort("192.168.21.133", 7004));
		hostAndPorts.add(new HostAndPort("192.168.21.133", 7005));
		hostAndPorts.add(new HostAndPort("192.168.21.133", 7006));

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

		jedisPoolConfig.setMaxIdle(100);

		jedisPoolConfig.setMaxTotal(500);

		jedisPoolConfig.setMinIdle(0);

		jedisPoolConfig.setMaxWaitMillis(2000L);

		jedisPoolConfig.setTestOnBorrow(true);
		jedisCluster = new JedisCluster(hostAndPorts, jedisPoolConfig);
	}

	public static JedisCluster getJedisCluster() {
		return jedisCluster;
	}
}
