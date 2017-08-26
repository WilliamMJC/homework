package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.hzu.feirty.entity.Student;
import com.hzu.feirty.entity.User;

public class StudentDaoImpl extends BaseDaoImpl {
	List<Student> list = new ArrayList<Student>();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	
   public StudentDaoImpl(){
		super();
	}
	/**
	 * 添加用户学号数据
	 * 
	 * @param news
	 * @throws SQLException 
	 */
	public boolean Save(Student user) throws SQLException {
		conn = this.getConnection();
		if(!find(user)){
		try {
			pstmt = conn.prepareStatement("insert into student(number,course)values(?,?)");
			//pstmt.setString(1, user.getName());
			pstmt.setString(1, user.getNumber());
			pstmt.setString(2, user.getCourse());
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
	//添加 老师name、邮箱
	public boolean update(Student user) throws SQLException {
		conn = this.getConnection();
		if(find(user)){
		try {
			pstmt = conn.prepareStatement("update student set name=?,teacher=?,mail=?,school=? where number='"
							+ user.getNumber() + "'");
			//pstmt.setString(1, user.getName());
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getTeacher());
			pstmt.setString(3, user.getMail());
			pstmt.setString(4, user.getSchool());
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
	//查找
	public boolean find(Student student) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from student where number='" + student.getNumber() + "',teacher='" + student.getTeacher() + "',course='" + student.getCourse() + "'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}	
	
	/**
	 * 查询某用户是否存在，根据usename
	 *
	 * @return
	 */
	public Student Search(String name) {
		conn = this.getConnection();
		Student content = null;
		try {
			pstmt = conn.prepareStatement("select * from student where name='"
					+ name + "'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				content = new Student();
				content.setId(rs.getInt("id"));
				content.setName(rs.getString("name"));
				content.setTeacher(rs.getString("teacher"));
				return content;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return null;

	}
	/**
	 * 查询某用户是否存在，根据teacher
	 *
	 * @return
	 */
	public boolean isNumber(String teacher,String number) {
		conn = this.getConnection();
		//Student content = null;
		try {
			pstmt = conn.prepareStatement("select * from student where teacher='"+ teacher + "'and number='"+number+"'");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				/*content = new Student();
				content.setId(rs.getInt("id"));
				content.setName(rs.getString("name"));
				content.setTeacher(rs.getString("teacher"));*/
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return false;
	}
	public boolean isNumber2(String number) {
		conn = this.getConnection();
		//Student content = null;
		try {
			pstmt = conn.prepareStatement("select * from student where number='"+number+"'");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				/*content = new Student();
				content.setId(rs.getInt("id"));
				content.setName(rs.getString("name"));
				content.setTeacher(rs.getString("teacher"));*/
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return false;

	}
}
