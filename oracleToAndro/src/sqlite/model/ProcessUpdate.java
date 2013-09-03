package sqlite.model;

import java.util.Date;

public class ProcessUpdate {
	
	private int id;
	private String userId;
	private String phoneId;
	private String updateDate;
	
	public ProcessUpdate(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
}