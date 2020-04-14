package com.product.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import redis.clients.util.Slowlog;

public class RadisPoolUtils {
	
	//缓存池对象
	private static JedisPool jedisPool = null;

	/***
	 * @Title 安全期间 使用了radis Synchronized
	 * @author wuyongchao
	 * @date 2018-10-25
	 * @return 返回缓存池对象实体 用来在调用方法时使用及返还给缓存池
	 */
	public static JedisPool getJedisPool() {
		if (jedisPool == null)
			synchronized (RadisPoolUtils.class) {
				if (jedisPool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxIdle(5);
					config.setMaxWaitMillis(100000L);
					config.setTestOnBorrow(true);
					jedisPool = new JedisPool(config,RadisConfig.RADIS_HOST,RadisConfig.RADIS_PORT);
				}
			}

		return jedisPool;
	}

	/***
	 * @Title 将缓存对象扔回到缓存池里
	 * @author wuyongchao
	 * @date 2018-10-25
	 * @param jedisPool
	 *            缓存池
	 * @param jedis
	 *            //缓存对象
	 */
	public static void returnResource(JedisPool jedisPool, Jedis jedis) {
		if (jedis != null)
			jedisPool.returnResource(jedis);
	}

	public static List<Map<String, String>> sentinelMasters() {
		List list = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			list = jedis.sentinelMasters();
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return list;
	}

	public static List<String> sentinelGetMasterAddrByName(String masterName) {
		List list = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			list = jedis.sentinelGetMasterAddrByName(masterName);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return list;
	}

	public static Long sentinelReset(String pattern) {
		Long r = Long.valueOf(0L);
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			r = jedis.sentinelReset(pattern);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return r;
	}

	public static List<Map<String, String>> sentinelSlaves(String masterName) {
		List pattern = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			pattern = jedis.sentinelSlaves(masterName);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return pattern;
	}

	public static String sentinelFailover(String masterName) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sentinelFailover(masterName);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String sentinelMonitor(String masterName, String ip, int port, int quorum) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sentinelMonitor(masterName, ip, port, quorum);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String sentinelRemove(String masterName) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sentinelRemove(masterName);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String sentinelSet(String masterName, Map<String, String> parameterMap) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sentinelSet(masterName, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterNodes() {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterNodes();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterMeet(String ip, int port) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterMeet(ip, port);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterAddSlots(int[] slots) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterAddSlots(slots);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterDelSlots(int[] slots) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterDelSlots(slots);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterInfo() {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterInfo();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static List<String> clusterGetKeysInSlot(int slot, int count) {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterGetKeysInSlot(slot, count);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterSetSlotNode(int slot, String nodeId) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterSetSlotNode(slot, nodeId);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterSetSlotMigrating(int slot, String nodeId) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterSetSlotMigrating(slot, nodeId);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterSetSlotImporting(int slot, String nodeId) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterSetSlotImporting(slot, nodeId);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterSetSlotStable(int slot) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterSetSlotStable(slot);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterForget(String nodeId) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterForget(nodeId);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterFlushSlots() {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterFlushSlots();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long clusterKeySlot(String key) {
		Long value = Long.valueOf(0L);
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterKeySlot(key);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long clusterCountKeysInSlot(int slot) {
		Long value = Long.valueOf(0L);
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterCountKeysInSlot(slot);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterSaveConfig() {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterSaveConfig();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterReplicate(String nodeId) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterReplicate(nodeId);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static List<String> clusterSlaves(String nodeId) {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterSlaves(nodeId);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String clusterFailover() {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterFailover();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static List<Object> clusterSlots() {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterSlots();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	@SuppressWarnings("deprecation")
	public static String clusterReset(JedisCluster.Reset resetType) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.clusterReset(resetType);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String readonly() {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.readonly();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Object eval(String script, int keyCount, String[] params) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.eval(script, keyCount, params);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Object eval(String script, List<String> keys, List<String> args) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.eval(script, keys, args);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Object eval(String script) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.eval(script);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	@SuppressWarnings("deprecation")
	public static Object evalsha(String script) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.evalsha(script);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Object evalsha(String sha1, List<String> keys, List<String> args) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.evalsha(sha1, keys, args);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Object evalsha(String sha1, int keyCount, String[] params) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.evalsha(sha1, keyCount, params);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Boolean scriptExists(String sha1) {
		boolean value = false;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.scriptExists(sha1).booleanValue();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Boolean.valueOf(value);
	}

	public static List<Boolean> scriptExists(String[] sha1) {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.scriptExists(sha1);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String scriptLoad(String script) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.scriptLoad(value);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static List<String> configGet(String pattern) {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.configGet(pattern);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String configSet(String parameter, String value) {
		String value2 = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value2 = jedis.configSet(parameter, value);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value2;
	}

	public static List<Slowlog> slowlogGet() {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.slowlogGet();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static List<Slowlog> slowlogGet(long entries) {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.slowlogGet(entries);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long objectRefcount(String string) {
		Long value = Long.valueOf(0L);
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.objectRefcount(string);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String objectEncoding(String string) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.objectEncoding(string);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long objectIdletime(String string) {
		Long value = Long.valueOf(0L);
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.objectIdletime(string);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long del(String[] keys) {
		Long value = Long.valueOf(0L);
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long exists(String[] keys) {
		Long value = Long.valueOf(0L);
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.exists(keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static List<String> blpop(int timeout, String[] keys) {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.blpop(timeout, keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static List<String> brpop(int timeout, String[] keys) {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.brpop(timeout, keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static List<String> blpop(String[] args) {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.blpop(args);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static List<String> brpop(String[] args) {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.brpop(args);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Set<String> keys(String pattern) {
		Set value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.keys(pattern);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static List<String> mget(String[] keys) {
		List value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.mget(keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String mset(String[] keysvalues) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.mset(keysvalues);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long msetnx(String[] keysvalues) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.msetnx(keysvalues);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String rename(String oldkey, String newkey) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.rename(oldkey, newkey);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long renamenx(String oldkey, String newkey) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.renamenx(oldkey, newkey);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String rpoplpush(String srckey, String dstkey) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.rpoplpush(srckey, dstkey);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Set<String> sdiff(String[] keys) {
		Set value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sdiff(keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long sdiffstore(String dstkey, String[] keys) {
		Long value = Long.valueOf(0L);
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sdiffstore(dstkey, keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Set<String> sinter(String[] keys) {
		Set value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sinter(keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long sinterstore(String dstkey, String[] keys) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sinterstore(dstkey, keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long smove(String srckey, String dstkey, String member) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.smove(srckey, dstkey, member);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long sort(String key, SortingParams sortingParameters, String dstkey) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sort(key, sortingParameters, dstkey);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long sort(String key, String dstkey) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sort(key, dstkey);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Set<String> sunion(String[] keys) {
		Set value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sunion(keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long sunionstore(String dstkey, String[] keys) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.sunionstore(dstkey, keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String watch(String[] keys) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.watch(keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long zinterstore(String dstkey, String[] sets) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.zinterstore(dstkey, sets);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long zinterstore(String dstkey, ZParams params, String[] sets) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.zinterstore(dstkey, params, sets);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long zunionstore(String dstkey, String[] sets) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.zunionstore(dstkey, sets);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long zunionstore(String dstkey, ZParams params, String[] sets) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.zunionstore(dstkey, params, sets);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String brpoplpush(String source, String destination, int timeout) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.brpoplpush(source, destination, timeout);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long publish(String channel, String message) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.publish(channel, message);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static void subscribe(JedisPubSub jedisPubSub, String[] channels) {
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			jedis.subscribe(jedisPubSub, channels);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
	}

	public static void psubscribe(JedisPubSub jedisPubSub, String[] patterns) {
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			jedis.psubscribe(jedisPubSub, patterns);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
	}

	public static String randomKey() {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.randomKey();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long bitop(BitOP op, String destKey, String[] srcKeys) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.bitop(op, destKey, srcKeys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static ScanResult<String> scan(int cursor) {
		ScanResult value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.scan(cursor);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static ScanResult<String> scan(String cursor) {
		ScanResult value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.scan(cursor);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static ScanResult<String> scan(String cursor, ScanParams params) {
		ScanResult value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.scan(cursor, params);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String pfmerge(String destkey, String[] sourcekeys) {
		String value = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.pfmerge(destkey, sourcekeys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static long pfcount(String[] keys) {
		long value = 0L;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.pfcount(keys);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static String set(String key, String value) {
		String value1 = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static String set(String key, String value, String nxxx, String expx, long time) {
		String value1 = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.set(key, value, nxxx, expx, time);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static String set(String key, String value, String nxxx) {
		String value1 = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.set(key, value, nxxx);
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static String get(String key) {
		String value = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Boolean exists(String key) {
		boolean value = false;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.exists(key).booleanValue();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Boolean.valueOf(value);
	}

	public static Long persist(String key) {
		long value = 0L;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.persist(key).longValue();
		} catch (Exception e) {
			e.printStackTrace();

			jedisPool.returnBrokenResource(jedis);

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value);
	}

	public static String type(String key) {
		String value = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.type(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long expire(String key, int seconds) {
		Long value = Long.valueOf(0L);
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.expire(key, seconds);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long expireAt(String key, long unixTime) {
		Long value = Long.valueOf(0L);
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.expireAt(key, unixTime);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long pexpireAt(String key, long millisecondsTimestamp) {
		Long value = Long.valueOf(0L);
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.pexpireAt(key, millisecondsTimestamp);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long ttl(String key) {
		Long value = Long.valueOf(0L);
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.ttl(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Long pttl(String key) {
		Long value = Long.valueOf(0L);
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value = jedis.pttl(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	public static Boolean setbit(String key, long offset, boolean value) {
		boolean value1 = false;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.setbit(key, offset, value).booleanValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Boolean.valueOf(value1);
	}

	public static Boolean setbit(String key, long offset, String value) {
		boolean value1 = false;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.setbit(key, offset, value).booleanValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Boolean.valueOf(value1);
	}

	public static Boolean getbit(String key, long offset) {
		boolean value1 = false;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.getbit(key, offset).booleanValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Boolean.valueOf(value1);
	}

	public static Long setrange(String key, long offset, String value) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.setrange(key, offset, value).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static String getrange(String key, long startOffset, long endOffset) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.getrange(key, startOffset, endOffset);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static String getSet(String key, String value) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.getSet(key, value);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long setnx(String key, String value) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.setnx(key, value).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static String setex(String key, int seconds, String value) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.setex(key, seconds, value);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static String psetex(String key, long milliseconds, String value) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.psetex(key, milliseconds, value);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long decrBy(String key, long integer) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.decrBy(key, integer).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long decr(String key) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.decr(key).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long incrBy(String key, long integer) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.incrBy(key, integer).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Double incrByFloat(String key, double value) {
		double value1 = 0D;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.incrByFloat(key, value).doubleValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Double.valueOf(value1);
	}

	public static Long incr(String key) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.incr(key).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long append(String key, String value) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.append(key, value).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static String substr(String key, int start, int end) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.substr(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long hset(String key, String field, String value) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hset(key, field, value).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static String hget(String key, String field) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hget(key, field);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long hsetnx(String key, String field, String value) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hsetnx(key, field, value).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static String hmset(String key, Map<String, String> hash) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hmset(key, hash);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<String> hmget(String key, String[] fields) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hmget(key, fields);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long hincrBy(String key, String field, long value) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hincrBy(key, field, value).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Double hincrByFloat(String key, String field, double value) {
		double value1 = 0D;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hincrByFloat(key, field, value).doubleValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Double.valueOf(value1);
	}

	public static Boolean hexists(String key, String field) {
		boolean value1 = false;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hexists(key, field).booleanValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Boolean.valueOf(value1);
	}

	public static Long hdel(String key, String[] field) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hdel(key, field).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long hlen(String key) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hlen(key).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Set<String> hkeys(String key) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hkeys(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<String> hvals(String key) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hvals(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Map<String, String> hgetAll(String key) {
		Map value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hgetAll(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long rpush(String key, String[] string) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.rpush(key, string).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long lpush(String key, String[] string) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.lpush(key, string).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long llen(String key) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.llen(key).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static List<String> lrange(String key, long start, long end) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.lrange(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static String ltrim(String key, long start, long end) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.ltrim(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static String lindex(String key, long index) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.lindex(key, index);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static String lset(String key, long index, String value) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.lset(key, index, value);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long lrem(String key, long count, String value) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.lrem(key, count, value).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static String lpop(String key) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.lpop(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static String rpop(String key) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.rpop(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long sadd(String key, String[] member) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.sadd(key, member).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Set<String> smembers(String key) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.smembers(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long srem(String key, String[] member) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.srem(key, member).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static String spop(String key) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.spop(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> spop(String key, long count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.spop(key, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long scard(String key) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.scard(key).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Boolean sismember(String key, String member) {
		boolean value1 = false;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.sismember(key, member).booleanValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Boolean.valueOf(value1);
	}

	public static String srandmember(String key) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.srandmember(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<String> srandmember(String key, int count) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.srandmember(key, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long strlen(String key) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.strlen(key).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long zadd(String key, double score, String member) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zadd(key, score, member).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long zadd(String key, double score, String member, ZAddParams params) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zadd(key, score, member, params).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long zadd(String key, Map<String, Double> scoreMembers) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zadd(key, scoreMembers).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zadd(key, scoreMembers, params).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Set<String> zrange(String key, long start, long end) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrange(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long zrem(String key, String[] member) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrem(key, member).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Double zincrby(String key, double score, String member) {
		double value1 = 0D;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zincrby(key, score, member).doubleValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Double.valueOf(value1);
	}

	public static Double zincrby(String key, double score, String member, ZIncrByParams params) {
		double value1 = 0D;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zincrby(key, score, member, params).doubleValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Double.valueOf(value1);
	}

	public static Long zrank(String key, String member) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrank(key, member).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long zrevrank(String key, String member) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrank(key, member).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Set<String> zrevrange(String key, long start, long end) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrange(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<Tuple> zrangeWithScores(String key, long start, long end) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeWithScores(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long zcard(String key) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zcard(key).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Double zscore(String key, String member) {
		double value1 = 0D;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zscore(key, member).doubleValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Double.valueOf(value1);
	}

	public static List<String> sort(String key) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.sort(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<String> sort(String key, SortingParams sortingParameters) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.sort(key, sortingParameters);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long zcount(String key, double min, double max) {
		Long value1 = Long.valueOf(0L);
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zcount(key, min, max);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long zcount(String key, String min, String max) {
		Long value1 = Long.valueOf(0L);
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zcount(key, min, max);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrangeByScore(String key, double min, double max) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrangeByScore(String key, String min, String max) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrevrangeByScore(String key, double max, double min) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrevrangeByScore(String key, String max, String min) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long zremrangeByRank(String key, long start, long end) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zremrangeByRank(key, start, end).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long zremrangeByScore(String key, double start, double end) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zremrangeByScore(key, start, end).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long zremrangeByScore(String key, String start, String end) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zremrangeByScore(key, start, end).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long zlexcount(String key, String min, String max) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zlexcount(key, min, max).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Set<String> zrangeByLex(String key, String min, String max) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeByLex(key, min, max);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrangeByLex(key, min, max, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrevrangeByLex(String key, String max, String min) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeByLex(key, min, max);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
		Set value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zrevrangeByLex(key, max, min, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long zremrangeByLex(String key, String min, String max) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zremrangeByLex(key, min, max).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.linsert(key, where, pivot, value).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long lpushx(String key, String[] string) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.lpushx(key, string).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long rpushx(String key, String[] string) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.rpushx(key, string).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static List<String> blpop(String arg) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.blpop(arg);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<String> blpop(int timeout, String key) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.blpop(timeout, key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<String> brpop(String arg) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.brpop(arg);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<String> brpop(int timeout, String key) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.brpop(timeout, key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long del(String key) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.del(key).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static String echo(String string) {
		String value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.echo(string);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long move(String key, int dbIndex) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.move(key, dbIndex).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long bitcount(String key) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.bitcount(key).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long bitcount(String key, long start, long end) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.bitcount(key, start, end).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long bitpos(String key, boolean value) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.bitpos(key, value).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long bitpos(String key, boolean value, BitPosParams params) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.bitpos(key, value, params).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
		ScanResult value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hscan(key, cursor);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static ScanResult<String> sscan(String key, int cursor) {
		ScanResult value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.sscan(key, cursor);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static ScanResult<Tuple> zscan(String key, int cursor) {
		ScanResult value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zscan(key, cursor);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
		ScanResult value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hscan(key, cursor);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
		ScanResult value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.hscan(key, cursor, params);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static ScanResult<String> sscan(String key, String cursor) {
		ScanResult value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.sscan(key, cursor);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static ScanResult<String> sscan(String key, String cursor, ScanParams params) {
		ScanResult value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.sscan(key, cursor, params);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static ScanResult<Tuple> zscan(String key, String cursor) {
		ScanResult value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zscan(key, cursor);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
		ScanResult value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.zscan(key, cursor, params);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long pfadd(String key, String[] elements) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.pfadd(key, elements).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static long pfcount(String key) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.pfcount(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static Long geoadd(String key, double longitude, double latitude, String member) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.geoadd(key, longitude, latitude, member).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
		long value1 = 0L;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.geoadd(key, memberCoordinateMap).longValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Long.valueOf(value1);
	}

	public static Double geodist(String key, String member1, String member2) {
		double value1 = 0D;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.geodist(key, member1, member2).doubleValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Double.valueOf(value1);
	}

	public static Double geodist(String key, String member1, String member2, GeoUnit unit) {
		double value1 = 0D;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.geodist(key, member1, member2, unit).doubleValue();
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return Double.valueOf(value1);
	}

	public static List<String> geohash(String key, String[] members) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.geohash(key, members);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<GeoCoordinate> geopos(String key, String[] members) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.geopos(key, members);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,
			GeoUnit unit) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.georadius(key, longitude, latitude, radius, unit);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,
			GeoUnit unit, GeoRadiusParam param) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.georadius(key, longitude, latitude, radius, unit, param);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.georadiusByMember(key, member, radius, unit);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.georadiusByMember(key, member, radius, unit, param);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}

	public static List<Long> bitfield(String key, String[] arguments) {
		List value1 = null;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try {
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			value1 = jedis.bitfield(key, arguments);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();

			returnResource(jedisPool, jedis);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return value1;
	}
}