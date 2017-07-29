package com.hzu.feirty.entity;

public class HomeWork {
	private int id;
	private String stu_id;
	private String file_name;
	private String file_size;
	private String file_time;
	
	public HomeWork(){
		super();
	}
	public HomeWork(String stu_id,String file_name,String file_size,String file_time){
		this.stu_id =stu_id;
		this.file_name =file_name;
		this.file_size =file_size;
		this.file_time = file_time;	
	}
	
	
	public String getFile_time() {
		return file_time;
	}
	public void setFile_time(String file_time) {
		this.file_time = file_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

}
