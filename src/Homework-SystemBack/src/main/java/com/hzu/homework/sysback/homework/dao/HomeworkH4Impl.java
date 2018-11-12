package com.hzu.homework.sysback.homework.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.homework.vo.HomeworkModel;

@Repository
public class HomeworkH4Impl implements HomeworkDAO {
	
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
	public String create(HomeworkModel homework) {
		// TODO Auto-generated method stub
		return (String)getH4Session().save(homework);
	}

	@Override
	public String update(HomeworkModel homework) {
		// TODO Auto-generated method stub
		getH4Session().update(homework);
		return "ret";
	}

	@Override
	public String delete(HomeworkModel homework) {
		// TODO Auto-generated method stub
		getH4Session().delete(homework);
		return "ret";
	}

	@Override
	public HomeworkModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		String hql = "select o from HomeworkModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    return (HomeworkModel)query.list().get(0);
	}

	@Override
	public List<HomeworkModel> getAll() {
		String hql = "select o from HomeworkModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);   
	    return query.list();
	}

	@Override
	public List<HomeworkModel> getByTeacherUuid(String teacherUuid) {
		String hql = "select o from HomeworkModel o where delFlag='1' and teacherUuid=:teacherUuid "; 
		hql+="order by o.createTime";
	    Query query = getH4Session().createQuery(hql);   
	    query.setString("teacherUuid", teacherUuid);
	    return query.list();
	}

	@Override
	public HomeworkModel getLastHomework(String teacherUuid, String courseUuid) {
		String hql = "select o from HomeworkModel o where delFlag='1' and teacherUuid=:teacherUuid and courseUuid=:courseUuid"; 
		hql +=" order by o.times desc";
	    Query query = getH4Session().createQuery(hql);   
	    query.setString("teacherUuid", teacherUuid);
	    query.setString("courseUuid", courseUuid);
	    List<HomeworkModel>  list=query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public int getCountByCourseUuid(String uuid) {
		String hql = "select count(*) from HomeworkModel o where delFlag='1' and courseUuid=:courseUuid "; 
		hql+=" order by o.createTime desc";
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("courseUuid", uuid);
	    return ((Number) query.uniqueResult()).intValue();
	}

}
