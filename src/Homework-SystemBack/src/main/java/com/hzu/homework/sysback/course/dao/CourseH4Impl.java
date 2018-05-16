package com.hzu.homework.sysback.course.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.course.vo.CourseModel;
import com.hzu.homework.sysback.user.vo.UserModel;

@Repository
public class CourseH4Impl implements CourseDAO {
	
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
	public String create(CourseModel course) {
		getH4Session().save(course);
		return "ret";
	}

	@Override
	public void update(CourseModel course) {
		getH4Session().update(course);
		
	}

	@Override
	public void delete(CourseModel course) {
		// TODO Auto-generated method stub
		getH4Session().delete(course);
	}

	@Override
	public List<CourseModel> getAll() {
		String hql = "select o from CourseModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);   
	    return query.list();
	}

	@Override
	public CourseModel getByUuid(String uuid) {
		String hql = "select o from CourseModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    return (CourseModel)query.list().get(0);
	}

	@Override
	public List<CourseModel> getByTeacherUuid(String teacherUuid) {
		String hql = "select o from CourseModel o where delFlag='1' and teacherUuid=:teacherUuid";    
	    Query query = getH4Session().createQuery(hql);   
	    query.setString("teacherUuid", teacherUuid);
	    return query.list();
	}

	@Override
	public String getUuidByTAndCN(String teacherUuid, String courseName) {
		String hql = "select o.uuid from CourseModel o where delFlag='1' and teacherUuid=:teacherUuid and courseName=:courseName";    
	    Query query = getH4Session().createQuery(hql);   
	    query.setString("teacherUuid", teacherUuid);
	    query.setString("courseName", courseName);
	    List<String> list=query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return "";
	    }
	    
	}

}
