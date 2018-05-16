package com.hzu.homework.sysback.sysaccount.dao;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.sysaccount.vo.SysAccountModel;

@Repository
public class SysAccountDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return this.sessionFactory.openSession();
	}
	
	public void close(Session session){
		if(session != null)
			session.close();
	}
		
	public SysAccountModel findByUsernameAndPassword(String username,String password){
    	String hsql="from SysAccountModel u where u.loginName= :username and u.password= :password";
        Session session = getSession();
        Query query = session.createQuery(hsql);
        query.setParameter("username", username).setParameter("password", password);
        SysAccountModel user = (SysAccountModel) query.uniqueResult();
        close(session);
        return user;
    }

}
