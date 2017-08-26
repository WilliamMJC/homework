package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hzu.feirty.entity.Construction;
import com.hzu.feirty.entity.Course;

public class CourseDaoImpl extends BaseDaoImpl{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	public boolean inSert(Course course) throws SQLException{
		conn = this.getConnection();
		if(!find(course.getName())){
			try {
				pstmt = conn.prepareStatement("insert into course(name,stu_number,tea_name)values(?,?,?)");
				pstmt.setString(1, course.getName());
				pstmt.setInt(2,course.getStu_number());
				pstmt.setString(3,course.getTea_name());
				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}finally {
				this.closeAll(null, pstmt, conn);
			}				
		}else{
			return false;
		}		
	}
	
	
	
	//查找
		public boolean find(String name) throws SQLException{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select * from course where name='" + name + "'");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}else {
				return false;
			}
		}	

}
