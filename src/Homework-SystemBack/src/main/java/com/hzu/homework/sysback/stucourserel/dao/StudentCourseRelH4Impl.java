package com.hzu.homework.sysback.stucourserel.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.school.vo.SchoolModel;
import com.hzu.homework.sysback.stucourserel.vo.StudentCourseRelModel;

@Repository
public class StudentCourseRelH4Impl implements StudentCourseRelDAO {
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
	public String create(StudentCourseRelModel stuCourel) {
		// TODO Auto-generated method stub
		getH4Session().save(stuCourel);
		return "ret";
	}

	@Override
	public String update(StudentCourseRelModel stuCourel) {
		// TODO Auto-generated method stub
		getH4Session().update(stuCourel);
		return "ret";
	}

	@Override
	public String delete(StudentCourseRelModel stuCourel) {
		// TODO Auto-generated method stub
		getH4Session().delete(stuCourel);
		return "ret";
	}

	@Override
	public StudentCourseRelModel getByUuid(String uuid) {
		String hql = "select o from StudentCourseRelModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    List<StudentCourseRelModel> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public List<StudentCourseRelModel> getAll() {
		String hql = "select o from StudentCourseRelModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);    
	    return query.list();
	}

	@Override
	public List<String> getCourseByNo(String no) {
		String hql = "select o.courseUuid from StudentCourseRelModel o where delFlag='1' and studentNo=:No ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("No", no);
	    List<String> list = query.list();
	    if(list.size()>0) {
	    	return list;
	    }else {
	    	return null;
	    }
	}

	@Override
	public List<String> getStudentNoByCourseUuid(String courseUuid) {
		String hql = "select o.studentNo from StudentCourseRelModel o where delFlag='1' and courseUuid=:courseUuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("courseUuid", courseUuid);
	    List<String> list = query.list();
	    if(list.size()>0) {
	    	return list;
	    }else {
	    	return null;
	    }
	}

	@Override
	public int getStudentCountByCourseUuid(String courseUuid) {
		String hql = "select count(*) from StudentCourseRelModel o where delFlag='1' and courseUuid=:courseUuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("courseUuid", courseUuid);
		return ((Number) query.uniqueResult()).intValue();
	}
}
