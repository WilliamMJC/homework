package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hzu.feirty.entity.Construction;
import com.hzu.feirty.entity.HomeWork;

public class ConstructionDaoImpl extends BaseDaoImpl{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	/**
	 * 作业附件信息记录表
	 * @param homework
	 * @return
	 */
	public boolean inSert(Construction construction){
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("insert into construction(teacher_name,number,zipname,zipsize,time)values(?,?,?,?,?)");
			pstmt.setString(1, construction.getTeacher_name());
			pstmt.setLong(2, construction.getNumber());
			pstmt.setString(3, construction.getZipname());
			pstmt.setString(4, construction.getZipsize());
			pstmt.setDate(5, construction.getTime());
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
