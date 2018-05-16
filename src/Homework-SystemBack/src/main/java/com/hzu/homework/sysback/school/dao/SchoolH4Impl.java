package com.hzu.homework.sysback.school.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.school.vo.SchoolModel;
import com.hzu.homework.sysback.user.vo.UserModel;

@Repository
public class SchoolH4Impl implements SchoolDAO {
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
	public String create(SchoolModel user) {
		getH4Session().save(user);
		return "ret";
	}

	@Override
	public String update(SchoolModel user) {
		getH4Session().update(user);
		return "ret";
	}

	@Override
	public String delete(SchoolModel user) {
		getH4Session().delete(user);
		return "ret";
	}

	@Override
	public SchoolModel getByUuid(String uuid) {
		String hql = "select o from SchoolModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    List<SchoolModel> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }

	}

	@Override
	public List<SchoolModel> getAll() {
		String hql = "select o from SchoolModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);    
	    return query.list();
	}

	@Override
	public boolean checkSchoolName(String schoolName) {
		String hql = "select o from SchoolModel o where delFlag='1' and schoolName=:name";    
	    Query query = getH4Session().createQuery(hql);  
	    query.setString("name", schoolName);
	    if(query.list().size()>0) {
	    	return true;
	    }else {
	    	return false;
	    }    
	}

	@Override
	public String getUuidByName(String schoolName) {
		String hql = "select o.uuid from SchoolModel o where delFlag='1' and schoolName=:name";    
	    Query query = getH4Session().createQuery(hql);  
	    query.setString("name", schoolName);
	    List<String> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

}
