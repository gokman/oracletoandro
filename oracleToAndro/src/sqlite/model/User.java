package sqlite.model;

import org.ksoap2.serialization.SoapObject;

public class User {
	
	private  int id;
	private String userId;
	private  String userName;
	private  String password;
	
	public User(){
		
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public User(SoapObject obj) {
		this.userId = GetValue(obj, "UserId");
		this.userName = GetValue(obj, "UserName");
		this.password=GetValue(obj, "Password");
		
	}
	
	public String GetValue(SoapObject object, String Field) {
		try {
			return object.getProperty(Field).toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}