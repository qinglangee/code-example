package com.zhch.example.ehcache3;

import java.io.IOException;
import java.net.URL;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.CacheManagerBuilder;
import org.ehcache.config.Configuration;
import org.ehcache.config.xml.XmlConfiguration;
import org.xml.sax.SAXException;

public class ExampleInitUseXml {
	public void test()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SAXException, IOException {

		final URL myUrl = this.getClass().getResource("/ehcache3/ehcache3.xml");
		Configuration xmlConfig = new XmlConfiguration(myUrl);
		CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
		cacheManager.init();

		Cache<Long, String> foo = cacheManager.getCache("foo", Long.class, String.class);
		Cache<Long, String> foo2 = cacheManager.getCache("foo2", Long.class, String.class);

		foo.put(1L, "da one!");
		foo2.put(2L, "f002 value");
		System.out.println("value from foo is :" + foo.get(1L));
		System.out.println("value from foo is :" + foo.get(2L));
		System.out.println("value from foo2 is :" + foo2.get(2L));

		cacheManager.removeCache("preConfigured");

		cacheManager.close();
	}

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SAXException, IOException {
		ExampleInitUseXml t = new ExampleInitUseXml();
		t.test();
	}
}
