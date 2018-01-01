package edu.test;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import edu.po.Users;
import edu.utils.HibernateSessionFactory;

/**
 * Title: PessimisticLockTest.java
 * Description: 悲观锁测试
 * @author yh.zeng
 * @date 2017-6-27
 */
public class PessimisticLockTest {
	
	static SessionFactory  sessionFactory = HibernateSessionFactory.getSessionFactory();
	
	public static void main(String args[]){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			
			//方式一，使用Session.load()给数据加悲观锁
			Users user = (Users) session.load(Users.class, 6, LockMode.UPGRADE);
			System.out.println("用户名：" + user.getUsername() + "，密码：" + user.getPassword());
		/*
	     *  //方式二，使用Session.lock()给对象加悲观锁
			Users user = (Users) session.load(Users.class, 6);
			session.lock(user, LockMode.UPGRADE);
			*/
			
		/*	
		 *  //方式三，使用Query.setLockMode()给数据加悲观锁
		 *  String hql = "from Users u  where u.id = :id";  
			Query query = session.createQuery(hql);
			query.setParameter("id", 6);
			query.setLockMode("u", LockMode.UPGRADE);
			List<Users> userList = query.list();  
		    for(Users user : userList){
				System.out.println("用户名：" + user.getUsername() + "，密码：" + user.getPassword());
		    }*/
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
		
	}

}
