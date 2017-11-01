package com.chenpeng.redis;

import redis.clients.jedis.Jedis;

public class Test1 {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("----" + jedis.ping());//查看服务是否运行
	}
}
