package com.hzu.homework.sysback.message.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzu.homework.sysback.message.vo.MessageModel;

@Repository
public class MessageH4Impl implements MessageDAO {
	
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
	public String create(MessageModel message) {
		// TODO Auto-generated method stub
		return (String)getH4Session().save(message);
	}

	@Override
	public String update(MessageModel message) {
		getH4Session().update(message);
		return "ret";
	}

	@Override
	public String delete(MessageModel message) {
		// TODO Auto-generated method stub
		getH4Session().delete(message);
		return "ret";
	}

	@Override
	public MessageModel getByUuid(String uuid) {
		String hql = "select o from MessageModel o where delFlag='1' and uuid=:uuid ";    
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("uuid", uuid);
	    List<MessageModel> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public List<MessageModel> getAll() {
		String hql = "select o from MessageModel o where delFlag='1' ";    
	    Query query = getH4Session().createQuery(hql);    
	    return query.list();
	}

	@Override
	public MessageModel getLastMessage(String belongUuid, String toUuid) {
		String hql = "select o from MessageModel o where delFlag='1' and belongUuid=:belongUuid and (formUuid=:toUuid or toUuid=:toUuid) "; 
		hql+=" order by o.createTime desc";
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("belongUuid", belongUuid);
	    query.setString("toUuid", toUuid); 
	    List<MessageModel> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public List<MessageModel> getListByBelongUuid(String belongUuid, String toUuid) {
		String hql = "select o from MessageModel o where delFlag='1' and belongUuid=:belongUuid and (formUuid=:toUuid or toUuid=:toUuid) "; 
		hql+=" order by o.createTime";
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("belongUuid", belongUuid);
	    query.setString("toUuid", toUuid); 
	    List<MessageModel> list = query.list();
	    if(list.size()>0) {
	    	return list;
	    }else {
	    	return null;
	    }
	}

	@Override
	public MessageModel getNewMessage(String belongUuid, String formUuid) {
		String hql = "select o from MessageModel o where delFlag='1' and belongUuid=:belongUuid and formUuid=:formUuid and toUuid=:belongUuid and state='0' "; 
		hql+=" order by o.createTime desc";
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("belongUuid", belongUuid);
	    query.setString("formUuid", formUuid); 
	    List<MessageModel> list = query.list();
	    if(list.size()>0) {
	    	return list.get(0);
	    }else {
	    	return null;
	    }
	}

	@Override
	public int getAllUnreadNum(String belongUuid) {
		String hql = "select count(*) from MessageModel o where delFlag='1' and belongUuid=:belongUuid and state='0'"; 
		hql+=" order by o.createTime desc";
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("belongUuid", belongUuid);
	    return ((Number) query.uniqueResult()).intValue();
	}

	@Override
	public int getUnreadNum(String belongUuid, String formUuid) {
		String hql = "select count(*) from MessageModel o where delFlag='1' and belongUuid=:belongUuid and toUuid=:belongUuid and formUuid=:formUuid and state='0' "; 
		hql+=" order by o.createTime desc";
	    Query query = getH4Session().createQuery(hql); 
	    query.setString("belongUuid", belongUuid);
	    query.setString("formUuid", formUuid);
	    return ((Number) query.uniqueResult()).intValue();
	}
	

}
