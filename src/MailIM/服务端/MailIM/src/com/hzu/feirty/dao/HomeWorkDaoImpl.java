package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.hzu.feirty.entity.HomeWork;

public class HomeWorkDaoImpl extends BaseDaoImpl{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	/**
	 * 作业信息记录表
	 * @param homework
	 * @return
	 * @throws SQLException 
	 */
	public boolean inSert(HomeWork homework) throws SQLException{
		conn = this.getConnection();
		if(isExist(homework.getStu_id())){
			try {
				pstmt = conn.prepareStatement("insert into homework(stu_id,file_name,file_size,file_time,file_number)values(?,?,?,?,?)");
				pstmt.setString(1, homework.getStu_id());
				pstmt.setString(2, homework.getFile_name());
				pstmt.setString(3, homework.getFile_size());
				pstmt.setString(4, homework.getFile_time());
				pstmt.setInt(5, homework.getFile_number());
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
		return false;
	}
	
	/*
	 * 查询学生数据是否存在
	 * 
	 */
	public boolean isExist(String stu_id) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from homework where stu_id='" + stu_id + "'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * 查询所有的作业数据
	 * @return list<HomeWork>
	 * 
	 */
	public List<HomeWork> QueryAll() throws SQLException{
		conn = this.getConnection();
		List<HomeWork> homeworklist = new ArrayList<HomeWork>();
		try {
			pstmt = conn.prepareStatement("select * from homework ");
			rs = pstmt.executeQuery();
			while(rs.next()){				
				HomeWork homework=new HomeWork();
				homework.setId(rs.getInt(1));
				homework.setStu_id(rs.getString(2));
				homework.setFile_name(rs.getString(3));
				homework.setFile_size(rs.getString(4));
				homework.setFile_time(rs.getString(5));
				homeworklist.add(homework);
				}
			
		} catch (Exception e) {
			homeworklist.add(null);
			return homeworklist;
		}finally {
			this.closeAll(null, pstmt, conn);
			return homeworklist;
		}			
	}	
}
