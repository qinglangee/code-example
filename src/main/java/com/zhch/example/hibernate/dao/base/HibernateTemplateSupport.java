package com.zhch.example.hibernate.dao.base;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;


public class HibernateTemplateSupport<T> {

	private HibernateTemplate hibernateTemplate ;
	
	public HibernateTemplate getHiberanteTemplate() {
		return hibernateTemplate;
	}
	
	/**
	 * 通过注解的@Resource来获得一个HibernateTemplate的实例
	 * 通过配置文件来注入一个SessionFactory
	 * @param hibernateTemplate
	 */
	@Resource
	public void sethibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
