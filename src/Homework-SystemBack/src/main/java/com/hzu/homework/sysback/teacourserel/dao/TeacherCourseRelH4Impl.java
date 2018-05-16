package com.hzu.homework.sysback.teacourserel.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.teacourserel.vo.TeacherCourseRelModel;


@Repository
public class TeacherCourseRelH4Impl implements TeacherCourseRelDAO {
	
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
	public String create(TeacherCourseRelModel stuCourel) {
		getH4Session().save(stuCourel);
		return "ret";
	}

	@Override
	public String update(TeacherCourseRelModel stuCourel) {
		getH4Session().update(stuCourel);
		return "ret";
	}

	@Override
	public String delete(TeacherCourseRelModel stuCourel) {
		getH4Session().delete(stuCourel);
		return "ret";
	}

	@Override
	public TeacherCourseRelModel getByUuid(String uuid) {
		String hql = "select o from TeacherCourseRelModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    List<TeacherCourseRelModel> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public List<TeacherCourseRelModel> getAll() {
		String hql = "select o from TeacherCourseRelModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);    
	    return query.list();
	}

	@Override
	public TeacherCourseRelModel getByCourseUuid(String courseUuid) {
		String hql = "select o from TeacherCourseRelModel o where delFlag='1' and courseUuid=:courseUuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("courseUuid", courseUuid);
	    List<TeacherCourseRelModel> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public String getTeacherUuidByCourseUuid(String courseUuid) {
		String hql = "select o.teacherUuid from TeacherCourseRelModel o where delFlag='1' and courseUuid=:courseUuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("courseUuid", courseUuid);
	    List<String> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

}
