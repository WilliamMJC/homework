package com.hzu.homework.sysback.statistics.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.school.vo.SchoolModel;
import com.hzu.homework.sysback.statistics.vo.StatisticsModel;

@Repository
public class StatisticsH4Impl implements StatisticsDAO {
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
	public String create(StatisticsModel statistics) {
		// TODO Auto-generated method stub
		getH4Session().save(statistics);
		return "ret";
	}

	@Override
	public String update(StatisticsModel statistics) {
		// TODO Auto-generated method stub
		getH4Session().update(statistics);
		return "ret";
	}

	@Override
	public String delete(StatisticsModel statistics) {
		// TODO Auto-generated method stub
		getH4Session().delete(statistics);
		return "ret";
	}

	@Override
	public StatisticsModel getByUuid(String uuid) {
		String hql = "select o from StatisticsModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    List<StatisticsModel> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public List<StatisticsModel> getAll() {
		String hql = "select o from StatisticsModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);    
	    return query.list();
	}
	
	

}
