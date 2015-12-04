package com.zhch.example.ehcache3;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.CacheManagerBuilder;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.CacheConfigurationBuilder;

public class Ehcache3Basic {

	public void testConfigInJava() {

		// 创建一个默认配置， 初始化 cacheManager
		CacheConfiguration<Long, String> defaultConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder()
				.buildConfig(Long.class, String.class);
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured", defaultConfig).build(false);
		// 初始化 cacheManager, 如果 build()调用 时参数传的是 true, 就不需要调用 init()方法了。
		cacheManager.init();

		Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);

		Cache<Long, String> myCache = cacheManager.createCache("myCache", defaultConfig);

		myCache.put(1L, "da one!");
		String value = myCache.get(1L);
		System.out.println("value is :" + value);

		cacheManager.removeCache("preConfigured");

		cacheManager.close();
	}

	public static void main(String[] args) {
		Ehcache3Basic t = new Ehcache3Basic();
		t.testConfigInJava();
	}
}
