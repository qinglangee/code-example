package com.zhch.example.hibernate.dao.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;



/**
 * 
 * Title:封装基本DAO接口
 *
 * Description：
 * 
 * @Author 项国轩
 * @Date 2012-3-8
 * @param <T>
 */
public interface BaseDao<T> {
	
	/**
	 * 持久化新对象
	 * 
	 * @param entity 持久化对象(PO)
	 * @throws RuntimeException
	 */
	public void saveObject(T entity) throws RuntimeException ;
	
	/**
	 * 持久化新对象
	 * 
	 * @param entity 持久化对象(PO)
	 * @return 返回该对象在表中的ID
	 * @throws RuntimeException
	 */
	public Serializable saveObjectRet(T entity) throws RuntimeException ;
	
	/**
	 * 删除对象
	 * 
	 * @param entity 需要删除的对象
	 * @throws RuntimeException
	 */
	public void deleteObject(T entity) throws RuntimeException ;

	/**
	 * 删除对象，根据ID
	 * 
	 * @param entityClass 需要删除的对象类
	 * @param id Serializable唯一标示
	 * @throws RuntimeException
	 */
	public void deleteObject(Class<T> entityClass,Serializable id) throws RuntimeException ;
	
	/**
	 * 删除集合中的所有对象
	 * 
	 * @param entityName 对象名称
	 * @param ids 要删除的ID集合
	 * @throws RuntimeException
	 */
	public void deleteAllObject(Class<T> entityName,Object[] ids) throws RuntimeException ;
	
	/**
	 * 更新已有对象
	 * 
	 * @param entity 需要更新的对象
	 * @throws RuntimeException
	 */
	public void updateObject(T entity) throws RuntimeException ;
	
	/**
	 * 添加或更新已有对象
	 * 
	 * @param entity
	 * @throws RuntimeException
	 */
	public void insertOrUpdate(T entity) throws RuntimeException ;
	
	/**
	 * 根据类和id加载对象(延迟加载)
	 * 
	 * @param entityClass entity类
	 * @param id 唯一标示
	 * @return entity对象
	 * @throws RuntimeException
	 */
	public T loadById(Class<T> entityClass,Serializable id) throws RuntimeException ;
	
	/**
	 * 根据类和ID加载对象(与之关联的对象一起加载)
	 * 
	 * @param entityClass entity类
	 * @param id 唯一标示
	 * @return entity对象
	 * @throws RuntimeException
	 */
	public T getById(Class<T> entityClass,Serializable id) throws RuntimeException ;
	
	/**
	 * 根据条件查询
	 * 
	 * @param hql
	 * @param whereHQL 查询条件
	 * @return 返回对象
	 * @throws RuntimeException
	 */
	public T findSingleEntity(String hql,String whereHQL,List<Object> param) throws RuntimeException ;
	
	/**
	 * 同时执行多个HQL语句
	 * 
	 * @param hql 一个完整的HQL(from o where o.id=1) 每个HQL的返回值为1条记录
	 * @return 多个提示类
	 * @throws RuntimeException
	 */
	public List<T> findSingleEntityByHqls(String[] hql) throws RuntimeException ;
	
	/**
	 * 使用Hibernate3提供的update和delete语句
	 * 
	 * @param hql 修改或删除语句
	 * @param param 条件参数
	 * @return
	 * @throws RuntimeException
	 */
	public Object executeHQL(String hql,String setHQL,String whereHQL,Object[] param) throws RuntimeException ;
	public Object executeHQL(String hql,String setHQL,String whereHQL,List<Object> param) throws RuntimeException ;
	
	/**
	 * 属性查询
	 * 
	 * @param property 属性，以逗号分隔
	 * @param clazz 查询的对象
	 * @param whereHQL 查询条件
	 * @param param 条件参数
	 * @return
	 * @throws RuntimeException
	 */
	public Object findSingleByProperty(String property,String clazz,String whereHQL,List<Object> param) throws RuntimeException ;
	public List<Object> findByProperty(String property,String clazz,String whereHQL,List<Object> param) throws RuntimeException ;
	public List<Object> findByProperty(String property,String clazz) throws RuntimeException ;
	
	/**
	 * 从数据库中查询相应列表(根据条件)
	 * 
	 * @param whereHQL 查询条件
	 * @param hql 查询语句
	 * @param param 条件参数
	 * @return List集合，元素entity对象
	 * @throws RuntimeException
	 */
	public List<T> find(String hql,String whereHQL,List<Object> param,int firstReult,int maxResult,LinkedHashMap<String , String> orderby) ;
	public List<T> find(String hql,String whereHQL,List<Object> param,LinkedHashMap<String , String> orderby) throws RuntimeException ;
	public List<T> find(String hql,String whereHQL,List<Object> param) throws RuntimeException ;
	public List<T> find(String hql,LinkedHashMap<String , String> orderby) throws RuntimeException ;
	public List<T> find(String hql) throws RuntimeException ;
	
	
	public List<Object> findBySQL(String sql,int firstResult,int maxResult) throws RuntimeException;
}






