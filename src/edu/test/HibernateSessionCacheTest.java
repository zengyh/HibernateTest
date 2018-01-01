package edu.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import edu.po.Users;
import edu.utils.HibernateSessionFactory;

/**
 * Title: HibernateEhCacheTest.java
 * Description: Hibernate一级缓存测试
 * @author yh.zeng
 * @date 2017-7-4
 */
public class HibernateSessionCacheTest {

	static SessionFactory  sessionFactory = HibernateSessionFactory.getSessionFactory();
	
    public static void main(String args[]) {
    	
		Session session1 = sessionFactory.openSession();
	    session1.beginTransaction();
		Users user1 = (Users)session1.get(Users.class, 6);  //查询出来的数据会放到一级缓存中
		System.out.println("用户名：" + user1.getUsername());
		Users user2 = (Users)session1.get(Users.class, 6);  //从一级缓存中取
		System.out.println("用户名：" + user2.getUsername());
		session1.getTransaction().commit();
		
		
		//由于一级缓存是session级别的缓存，所以session2是获取到session1中的缓存数据
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		Users user3= (Users)session2.get(Users.class, 6);  //查询出来的数据会放到一级缓存中
		System.out.println("用户名：" + user3.getUsername());
		session2.getTransaction().commit();
		
		session1.close(); 
		session2.close(); 
		
	}
}
