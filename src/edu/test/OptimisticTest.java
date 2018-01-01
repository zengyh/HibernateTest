package edu.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import edu.po.Users;
import edu.utils.HibernateSessionFactory;

/**
 * Title: OptimisticTest.java
 * Description: Hibernate乐观锁测试
 * @author yh.zeng
 * @date 2017-6-27
 */
public class OptimisticTest {
	
	static SessionFactory  sessionFactory = HibernateSessionFactory.getSessionFactory();
	
	public static void main(String args[]) {
		
		
		Session session1 = sessionFactory.openSession();

		Session session2 = sessionFactory.openSession();
		
		try {
			
			Users user1 = (Users)session1.get(Users.class, 6);
			Users user2 = (Users)session2.get(Users.class, 6);
			
			System.out.println("user1的version="+user1.getVersion()+"，"+"user2的version="+user2.getVersion());
			
			Transaction transaction1 = session1.beginTransaction();
			Transaction transaction2 = session2.beginTransaction();
			
			user1.setPassword("tx");
			transaction1.commit();
			
			System.out.println("user1的version="+user1.getVersion()+"，"+"user2的version="+user2.getVersion());
			
			user2.setPassword("txs111");
			transaction2.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session1.close();
			session2.close();
		}
		
	}

}
