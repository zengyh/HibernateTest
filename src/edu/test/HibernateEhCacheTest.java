package edu.test;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.po.Users;
import edu.utils.HibernateSessionFactory;

/**
 * Title: HibernateEhCacheTest.java
 * Description: Hibernate二级缓存测试
 * @author yh.zeng
 * @date 2017-7-4
 */
public class HibernateEhCacheTest {

	static SessionFactory  sessionFactory = HibernateSessionFactory.getSessionFactory();
	
    public static void main(String args[]) {
    	
		//Session.get()方法支持二级缓存
    	System.out.println("###############session.get()###############");
		Session session1 = sessionFactory.openSession();
		session1.beginTransaction();
		Users user1 = (Users)session1.get(Users.class, 6);
		System.out.println("用户名：" + user1.getUsername());
		session1.getTransaction().commit();
		session1.close();

		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		Users user2 = (Users)session2.get(Users.class, 6);
		System.out.println("用户名：" + user2.getUsername());
		session2.getTransaction().commit();
		session2.close();
		
		//Query.list()方法支持查询缓存
    	System.out.println("###############Query.list()###############");
		Session session3 = sessionFactory.openSession();
		session3.beginTransaction();
		Query query = session3.createQuery("from Users where id = :id");
		query.setParameter("id", 6);
		query.setCacheable(true); //启用查询缓存
		Users user3 = (Users)query.uniqueResult();
		System.out.println("用户名：" + user3.getUsername());
		session3.getTransaction().commit();
		session3.close();
		
		
		Session session4 = sessionFactory.openSession();
		session4.beginTransaction();
		Query query2 = session4.createQuery("from Users where id = :id");
		query2.setParameter("id", 6);
		query2.setCacheable(true); //启用查询缓存
		Users user4 = (Users)query2.uniqueResult();
		System.out.println("用户名：" + user4.getUsername());
		session4.getTransaction().commit();
		session4.close();
		
		//Query.iterate()方法不支持查询缓存
    	System.out.println("###############Query.iterate()###############");
		Session session5 = sessionFactory.openSession();
		session5.beginTransaction();
		Query query3 = session5.createQuery("from Users where id = :id");
		query3.setParameter("id", 6);
		query3.setCacheable(true); //启用查询缓存
		Users user5 = (Users)query3.iterate().next();
		System.out.println("用户名：" + user5.getUsername());
		session5.getTransaction().commit();
		session5.close();
		
		
		Session session6 = sessionFactory.openSession();
		session6.beginTransaction();
		Query query4 = session6.createQuery("from Users where id = :id");
		query4.setParameter("id", 6);
		query4.setCacheable(true); //启用查询缓存
		Users user6 = (Users)query4.iterate().next();
		System.out.println("用户名：" + user6.getUsername());
		session6.getTransaction().commit();
		session6.close();
		
		
	}
}
