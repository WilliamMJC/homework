package com.hzu.homework.sysback.user.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.sysaccount.vo.SysAccountModel;
import com.hzu.homework.sysback.user.vo.UserModel;

@Repository
public class UserH4Impl implements UserDAO{
	
	@Autowired
	private SessionFactory sessionFactory = null;
	
	protected Session getH4Session()
	{
	    Session s = null;
	    try {    	
	      s = this.sessionFactory.getCurrentSession();
	    } catch (Exception err) {    	
	      s = this.sessionFactory.openSession();
	    }
	    return s;
	}
	
	public void close(Session session){
		if(session != null)
			session.close();
	}

	@Override
	public String create(UserModel user) {
		getH4Session().save(user);
		return "ret";
						
	}

	@Override
	public String update(UserModel user) {
		getH4Session().update(user);
		return "ret";
	}

	@Override
	public String delete(UserModel user) {
		getH4Session().delete(user);
		return "ret";
	}

	@Override
	public String getUserType(String uuid) {
		String hql = "select o.userType from UserModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql);  
	    query.setString("uuid", uuid);
	    String type= (String)query.list().get(0);
	    return type;	
	}

	@Override
	public UserModel getByUuid(String uuid) {
		String hql = "select o from UserModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    return (UserModel)query.list().get(0);
	}

	@Override
	public List<UserModel> getAll() {
		String hql = "select o from UserModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);    
	    return query.list();
	}

	@Override
	public boolean checkUserName(String userName) {
		String hql = "select o from UserModel o where delFlag='1' and loginName=:userName ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("userName", userName);
	    if(query.list().size()>0) {
	    	return true;
	    }
		return false;
	}

	@Override
	public boolean checkEmail(String email) {
		String hql = "select o from UserModel o where delFlag='1' and personEmail=:email ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("email", email);
	    if(query.list().size()>0) {
	    	return true;
	    }
		return false;
	}

	@Override
	public String checkUserType(String uuid) {
		String hql = "select o.userType from UserModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    return (String)query.list().get(0);
	}

	@Override
	public boolean checkLogin(String loginName, String password) {
		String hql = "select o from UserModel o where delFlag='1' and loginName=:loginName and loginPwd=:loginPwd ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("loginName", loginName);
	    query.setString("loginPwd", password);
	    if(query.list().size()>0) {
	    	return true;
	    }
		return false;
	}

	@Override
	public UserModel getLoginModel(String loginName, String password) {
		String hql = "select o from UserModel o where delFlag='1' and loginName=:loginName and loginPwd=:loginPwd ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("loginName", loginName);
	    query.setString("loginPwd", password);
	    return (UserModel)query.list().get(0);
	}

	@Override
	public List<UserModel> getListByType(String type) {
		String hql = "select o from UserModel o where delFlag='1' and userType=:userType order by createTime";    
	    Query query = getH4Session().createQuery(hql);    
	    query.setString("userType", type);
	    return query.list();
	}

	@Override
	public String getStudentByNo(String no) {
		String hql = "select o.loginName from UserModel o where delFlag='1' and userType=:userType and studentNo=:studentNo order by studentNo";    
	    Query query = getH4Session().createQuery(hql);    
	    query.setString("userType", "1");
	    query.setString("studentNo", no);
	    List<String> list=query.list();
	    if(list.size()>0) {
	    	return (String)list.get(0);
	    }
		return null;
	}

}
