package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hzu.feirty.entity.HomeWork;


public class HomeWorkDaoImpl extends BaseDaoImpl{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	/**
	 * 作业信息记录表
	 * @param homework
	 * @return
	 */
	public boolean Insert(HomeWork homework ){
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("insert into homework(stu_id,file_name,file_size,file_time)values(?,?,?,?)");
			pstmt.setString(1, homework.getStu_id());
			pstmt.setString(2, homework.getFile_name());
			pstmt.setString(3, homework.getFile_size());
			pstmt.setString(4, homework.getFile_time());
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
