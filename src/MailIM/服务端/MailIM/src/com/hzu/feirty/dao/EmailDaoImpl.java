package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hzu.feirty.entity.Email;

public class EmailDaoImpl extends BaseDaoImpl{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	/**
	 * 信息记录表
	 * @param homework
	 * @return
	 */
	public boolean Insert(Email email){
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("insert into email(from,subject,sentdata,content)values(?,?,?,?)");
			pstmt.setString(1,email.getFrom());
			pstmt.setString(2,email.getSubject());
			pstmt.setString(3,email.getSentdata());
			pstmt.setString(4,email.getContent());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			this.closeAll(null, pstmt, conn);
		}	
	}	
	

}
