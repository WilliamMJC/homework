package com.hzu.homework.sysback.comment.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.comment.vo.CommentModel;

@Repository
public class CommentH4Impl implements CommentDAO {
	
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
	public String create(CommentModel comment) {
		// TODO Auto-generated method stub
		getH4Session().save(comment);
		return "ret";
	}

	@Override
	public void update(CommentModel comment) {
		// TODO Auto-generated method stub
		getH4Session().update(comment);
	}

	@Override
	public void delete(CommentModel comment) {
		getH4Session().delete(comment);
		
	}

	@Override
	public List<CommentModel> getAll() {
		String hql = "select o from CommentModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);   
	    return query.list();
	}

	@Override
	public CommentModel getByUuid(String uuid) {
		String hql = "select o from CommentModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    List<CommentModel> list=query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }
	    return null;
	}

	@Override
	public List<CommentModel> getByHomeworkUuid(String homeworkUuid) {
		String hql = "select o from CommentModel o where delFlag='1' and homeworkUuid=:homeworkUuid";   
		hql+=" order by o.createTime desc";
	    Query query = getH4Session().createQuery(hql);
	    query.setString("homeworkUuid", homeworkUuid);
	    List<CommentModel> list=query.list();
	    if(list.size()>0) {
	    	return list;
	    }
	    return null;
	}
	

}
