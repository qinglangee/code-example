package com.zhch.example.hibernate.dao.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;



/**
 * 
 * Title: 基础DAO实现类
 * 
 * Description：该类是个抽象类，并继承了HibernateTemplateSupport获得hibernateTemplate
 * 使用hibernateTemplate来操作数据库
 * 
 * @Author 项国轩
 * @Date 2012-3-8
 * @param <T>
 */
public class BaseDaoSupport<T> extends HibernateTemplateSupport<T> implements BaseDao<T> {
	
	@Override
	public T getById(Class<T> entityClass, Serializable id)
			throws RuntimeException {
		return (T) getHiberanteTemplate().get(entityClass, id) ;
	}

	@Override
	public void insertOrUpdate(T entity) throws RuntimeException {
		getHiberanteTemplate().saveOrUpdate(entity) ;
	}
	@Override
	public T loadById(Class<T> entityClass, Serializable id)
			throws RuntimeException {
		return (T) getHiberanteTemplate().load(entityClass, id) ;
	}

	@Override
	public void saveObject(T entity) throws RuntimeException {
		getHiberanteTemplate().save(entity) ;
	}
	
	@Override
	public Serializable saveObjectRet(T entity) throws RuntimeException {
		return getHiberanteTemplate().save(entity);
	}

	@Override
	public void updateObject(T entity) throws RuntimeException {
		getHiberanteTemplate().update(entity) ;
	}
	
	@Override
	public void deleteObject(Class<T> entityClass, Serializable id)
	throws RuntimeException {
		getHiberanteTemplate().delete(loadById(entityClass,id)) ;
	}
	
	@Override
	public void deleteObject(T entity) throws RuntimeException {
		getHiberanteTemplate().delete(entity) ;
	}

	@Override
	public void deleteAllObject(final Class<T> entityName, final Object[] ids)
			throws RuntimeException {
		this.getHiberanteTemplate().execute(new HibernateCallback<Object>() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				if(ids.length > 0) {
					for(int i=0; i<ids.length; i++) {
						session.delete(loadById(entityName,ids[i].toString())) ;
						if(i%20==0) {
							session.flush() ;
							session.clear() ;
						}
					}
				}
				return null;
			}
		}) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findByProperty(final String property, final String clazz, final String whereHQL,final List<Object> param) throws RuntimeException {
		
		return (List<Object>) this.getHiberanteTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				
				String hql = "select " + buildProperty(property) + " from " + clazz + buildWhereHQL(whereHQL);
				Query query = session.createQuery(hql) ;
				
				setQueryParams(query, param) ;
				return query.list() ;
			}
		}) ;
	}

	@Override
	public Object findSingleByProperty(final String property,final String clazz,
			final String whereHQL,final List<Object> param) throws RuntimeException {
		return this.getHiberanteTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				
				String hql = "select " + buildProperty(property) + " from " + clazz + buildWhereHQL(whereHQL);
				Query query = session.createQuery(hql) ;
				setQueryParams(query, param) ;
				return query.uniqueResult();
			}
		}) ;
	}
	
	/**
	 * 不根据条件查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findByProperty(final String property, final String clazz) throws RuntimeException {
		
		return (List<Object>) this.getHiberanteTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				
				String hql = "select " + buildProperty(property) + " from " + clazz ;
				Query query = session.createQuery(hql) ;
				
				return query.list() ;
			}
		}) ;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public T findSingleEntity(final String hql,final String whereHQL,final List<Object> param) throws RuntimeException {
		
		return (T) getHiberanteTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				
				Query query = session.createQuery(hql + buildWhereHQL(whereHQL)) ;
				setQueryParams(query, param) ;
				
				return query.uniqueResult() ;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findSingleEntityByHqls(final String[] hql) throws RuntimeException {
		return (List<T>)getHiberanteTemplate().execute(new HibernateCallback<Object>() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				List<T> list = new ArrayList<T>() ;
				for(int i = 0; i< hql.length ;i ++){
					Query query = session.createQuery(hql[i]);
					list.add((T)query.uniqueResult());
					if(i % 20 == 0){
						session.flush() ;
						session.clear() ;
					}
				}

				return list;
			}
		});
	}

	@Override
	public Object executeHQL(final String hql,final String setHQL,final String whereHQL,final List<Object> param) throws RuntimeException {
		
		return this.getHiberanteTemplate().execute(new HibernateCallback<Object>() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				
				System.out.println(hql + buildSetHQL(setHQL) + buildWhereHQL(whereHQL));
				
				Query query = session.createQuery(hql + buildSetHQL(setHQL) + buildWhereHQL(whereHQL)) ;
				setQueryParams(query, param) ;
				
				return query.executeUpdate();
			}
		}) ;
	}
	
@Override
public Object executeHQL(final String hql,final String setHQL,final String whereHQL,final Object[] param) throws RuntimeException {
		
		return this.getHiberanteTemplate().execute(new HibernateCallback<Object>() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				
				System.out.println(hql + buildSetHQL(setHQL) + buildWhereHQL(whereHQL));
				
				Query query = session.createQuery(hql + buildSetHQL(setHQL) + buildWhereHQL(whereHQL)) ;
				setQueryParams(query, param) ;
				
				return query.executeUpdate();
			}
		}) ;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> find(final String hql,final String whereHQL,final List<Object> param,final LinkedHashMap<String , String> orderby) throws RuntimeException {
		
		return (List<T>)this.getHiberanteTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				//System.out.println(hql + buildWhereHQL(whereHQL) + buildOrderBy(orderby));
				Query query = session.createQuery(hql + buildWhereHQL(whereHQL) + buildOrderBy(orderby)) ;
				
				setQueryParams(query, param) ;
				
				return query.list();
			}
		}) ;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> find(final String hql,final String whereHQL,final List<Object> param,final int firstReult,final int maxResult,final LinkedHashMap<String , String> orderby) throws RuntimeException {
		
		return (List<T>)this.getHiberanteTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				//System.out.println(hql + buildWhereHQL(whereHQL) + buildOrderBy(orderby));
				Query query = session.createQuery(hql + buildWhereHQL(whereHQL) + buildOrderBy(orderby)) ;
				
				setQueryParams(query, param) ;
				query.setFirstResult(firstReult);
				query.setMaxResults(maxResult);
				return query.list();
			}
		}) ;
	}
	
	/**
	 * 根据条件查询，但不排序
	 */
	@Override
	public List<T> find(final String hql,final String whereHQL,final List<Object> param) throws RuntimeException {
		return this.find(hql, whereHQL, param, null) ;
	}
	
	/**
	 * 不根据条件查询，但排序
	 */
	@Override
	public List<T> find(final String hql,final LinkedHashMap<String , String> orderby) throws RuntimeException {
		return this.find(hql, null, null, orderby) ;
	}
	
	/**
	 * 不排序和条件查询
	 */
	@Override
	public List<T> find(final String hql) throws RuntimeException {
		return this.find(hql, null, null, null) ;
	}

	
	

	/**
	 * 获得查询条件语句
	 * 
	 * @param whereHQL
	 * @return
	 */
	protected static String buildWhereHQL(String whereHQL) {
		return (null == whereHQL || "".equals(whereHQL.trim()) ? "" :BaseQuery.WHERE+whereHQL) ;
	}
	
	/**
	 * 获得修改条件语句
	 * 
	 * @param buildSetHQL
	 * @return
	 */
	protected static String buildSetHQL(String setHQL) {
		return (null == setHQL || "".equals(""+setHQL.trim()) ? "" : BaseQuery.SET + setHQL) ;
	}
	
	/**
	 * 设置参数值
	 * 
	 * @param query
	 * @param param<List>
	 */
	protected static void setQueryParams(Query query,List<Object> param) {
		if(null != param && param.size() > 0) {
			for(int i=0; i<param.size(); i++) {
				query.setParameter(i,param.get(i)) ;
			}
		}
	}
	
	/**
	 * 设置参数值
	 * 
	 * @param query
	 * @param param<Object>
	 */
	protected static void setQueryParams(Query query,Object[] param) {
		if(null != param && param.length > 0) {
			for(int i=0; i<param.length; i++) {
				query.setParameter(i,param[i]) ;
			}
		}
	}
	
	/**
	 * 组装order by语句
	 * 
	 * @param orderby
	 * @return
	 */
	protected static String buildOrderBy(LinkedHashMap<String, String> orderby) {
		StringBuffer sb = new StringBuffer() ;
		
		if(null != orderby && orderby.size() > 0) {
			sb.append(BaseQuery.ORDERBY) ;
			for(String key : orderby.keySet()) {
				sb.append(key).append(" ").append(orderby.get(key)).append(",") ;
			}
			sb.deleteCharAt(sb.length() - 1) ;
		}
		return sb.toString() ;
	}

	/**
	 * 组装查询属性
	 * 
	 * @param property
	 * @return
	 */
	public static String buildProperty(String property) {
		return (null != property && !"".equals(property.trim())? property.trim() : "") ;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object> findBySQL(final String sql,final int firstResult,final int maxResult) {
		return ((List<Object>) this.getHiberanteTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				
				Query query = session.createSQLQuery(sql);
				query.setFirstResult(firstResult).setMaxResults(maxResult);
				return query.list() ;
			}
		})) ;
	}


}
