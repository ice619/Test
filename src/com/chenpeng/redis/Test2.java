package com.chenpeng.redis;

import redis.clients.jedis.Jedis;

public class Test2 {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1",6379);
		jedis.set("test01", "key01");
		String value = jedis.get("test01");
		System.out.println(value);
	}
}
