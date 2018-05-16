package com.hzu.homework.sysback.like.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.homework.vo.HomeworkModel;
import com.hzu.homework.sysback.like.vo.LikeModel;

@Repository
public class LikeH4Impl implements LikeDAO {
	
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
	public String create(LikeModel like) {
		// TODO Auto-generated method stub
		return (String)getH4Session().save(like);
	}

	@Override
	public String update(LikeModel like) {
		// TODO Auto-generated method stub
		getH4Session().update(like);
		return "";
	}

	@Override
	public String delete(LikeModel like) {
		// TODO Auto-generated method stub
		getH4Session().delete(like);
		return "";
	}

	@Override
	public LikeModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		String hql = "select o from LikeModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    return (LikeModel)query.list().get(0);
	}

	@Override
	public List<LikeModel> getAll() {
		String hql = "select o from LikeModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);   
	    return query.list();
	}

	@Override
	public boolean isLike(String homeworkUuid, String userUuid) {
		String hql = "select o from LikeModel o where delFlag='1' and homeworkUuid=:homeworkUuid and userUuid=:userUuid "; 
		Query query = getH4Session().createQuery(hql); 
		query.setString("homeworkUuid", homeworkUuid);
		query.setString("userUuid", userUuid);
		List<LikeModel> list = query.list();
		if(list.size()>0) {
			return true;
		}
		return false;
	}

	@Override
	public LikeModel getUpdateLike(String homeworkUuid, String userUuid) {
		String hql = "select o from LikeModel o where delFlag='1' and homeworkUuid=:homeworkUuid and userUuid=:userUuid "; 
		Query query = getH4Session().createQuery(hql); 
		query.setString("homeworkUuid", homeworkUuid);
		query.setString("userUuid", userUuid);
		List<LikeModel> list = query.list();
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int getCount(String homeworkUuid) {
		String hql = "select o from LikeModel o where o.delFlag='1' and o.homeworkUuid=:homeworkUuid"; 
		Query query = getH4Session().createQuery(hql);
		query.setString("homeworkUuid", homeworkUuid);
		List<LikeModel> list = query.list();
		if(list.size()>0) {
			return list.size();
		}
		return 0;
		//return ((Number) query.uniqueResult()).intValue();
	}

}
