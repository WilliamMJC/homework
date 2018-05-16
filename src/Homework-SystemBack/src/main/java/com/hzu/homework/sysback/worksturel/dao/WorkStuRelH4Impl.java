package com.hzu.homework.sysback.worksturel.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.teacourserel.vo.TeacherCourseRelModel;
import com.hzu.homework.sysback.worksturel.vo.WorkStuRelModel;


@Repository
public class WorkStuRelH4Impl implements WorkStuRelDAO {
	
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
	public String create(WorkStuRelModel stuCourel) {
		getH4Session().save(stuCourel);
		return "ret";
	}

	@Override
	public String update(WorkStuRelModel stuCourel) {
		getH4Session().update(stuCourel);
		return "ret";
	}

	@Override
	public String delete(WorkStuRelModel stuCourel) {
		getH4Session().delete(stuCourel);
		return "ret";
	}

	@Override
	public WorkStuRelModel getByUuid(String uuid) {
		String hql = "select o from WorkStuRelModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    List<WorkStuRelModel> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public List<WorkStuRelModel> getAll() {
		String hql = "select o from WorkStuRelModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);    
	    return query.list();
	}

	@Override
	public WorkStuRelModel getByHomeworkUuid(String homeworkUuid) {
		String hql = "select o from WorkStuRelModel o where delFlag='1' and homeworkUuid=:homeworkUuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("homeworkUuid", homeworkUuid);
	    List<WorkStuRelModel> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public String getNoByuuid(String homeworkUuid) {
		String hql = "select o.studentNo from WorkStuRelModel o where delFlag='1' and homeworkUuid=:homeworkUuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("homeworkUuid", homeworkUuid);
	    List<String> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public boolean isSubmit(String homeworkUuid, String no) {
		String hql = "select o.uuid from WorkStuRelModel o where delFlag='1' and homeworkUuid=:homeworkUuid and studentNo=:no ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("homeworkUuid", homeworkUuid);
	    query.setString("no", no);
	    List<String> list = query.list();
	    if(list.size()>0) {
	    	return true;
	    }else {
	    	return false;
	    }
	}

}
