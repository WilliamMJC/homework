package com.hzu.homework.sysback.matched.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.matched.vo.MatchedModel;


@Repository
public class MatchedH4Impl implements MatchedDAO {

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
	public String create(MatchedModel matched) {
		// TODO Auto-generated method stub
		return (String)getH4Session().save(matched);
	}

	@Override
	public String update(MatchedModel matched) {
		// TODO Auto-generated method stub
		getH4Session().update(matched);
		return "ret";
	}

	@Override
	public String delete(MatchedModel matched) {
		// TODO Auto-generated method stub
		getH4Session().delete(matched);
		return "ret";
	}

	@Override
	public MatchedModel getByUuid(String uuid) {
		String hql = "select o from MatchedModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    List<MatchedModel> list= query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }
	    return null;
	}

	@Override
	public List<MatchedModel> getAll() {
		String hql = "select o from MatchedModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);   
	    return query.list();
	}

	@Override
	public List<MatchedModel> getByUserUuid(String uuid) {
		String hql = "select o from MatchedModel o where delFlag='1' and (formUuid=:uuid or toUuid=:uuid) and belongUuid=:uuid ";  
		hql+=" order by createTime";
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    List<MatchedModel> list= query.list();
	    if(list.size()>0) {
	    	return list;
	    }
	    return null;
	}
	
	public boolean isMatched(String uuid,String toUuid){
		String hql = "select o from MatchedModel o where delFlag='1' and (formUuid=:toUuid or toUuid=:toUuid) and belongUuid=:uuid";  
		hql+=" order by createTime";
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    query.setString("toUuid", toUuid);
	    List<MatchedModel> list= query.list();
	    if(list.size()>0) {
	    	return true;
	    }
	    return false;
	}

}
