package sqlite.dao;

import java.util.ArrayList;
import java.util.List;

import sqlite.db.MyUserSQLHelper;
import sqlite.model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MyUserSQLHelper dbHelper;
	private String[] allColumns = { MyUserSQLHelper.COLUMN_ID,
			MyUserSQLHelper.COLUMN_USER_ID,MyUserSQLHelper.COLUMN_USER_NAME, MyUserSQLHelper.COLUMN_PASSWORD };

	public UserDataSource(Context context) {
		dbHelper = new MyUserSQLHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		if(!onControlExistance(database, MyUserSQLHelper.TABLE_USER)){
			dbHelper.onCreate(database);
		}
		
	}

	public void close() {
		dbHelper.close();
	}

	public void insertUser(User user) {
		ContentValues values = new ContentValues();
		values.put(MyUserSQLHelper.COLUMN_USER_ID, user.getUserId());
		values.put(MyUserSQLHelper.COLUMN_USER_NAME, user.getUserName());
		values.put(MyUserSQLHelper.COLUMN_PASSWORD, user.getPassword());
		database.insert(MyUserSQLHelper.TABLE_USER, null,
				values);
		
	}

	public void deleteUser(User user) {
		long id = user.getId();
		database.delete(MyUserSQLHelper.TABLE_USER, MyUserSQLHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	public void deleteAllUser() {
		database.delete(MyUserSQLHelper.TABLE_USER,null, null);
	}

	public List<User> getAllUser() {
		List<User> comments = new ArrayList<User>();

		Cursor cursor = database.query(MyUserSQLHelper.TABLE_USER, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = cursorToUser(cursor);
			comments.add(user);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}

	public int getUserCount() {

		Cursor cursor = database.query(MyUserSQLHelper.TABLE_USER, allColumns,
				null, null, null, null, null);
		int a=0;
        try{
		a = cursor.getCount();
        }catch(Exception e){
        	e.printStackTrace();
        }

		// Make sure to close the cursor
		cursor.close();
		return a;
	}
	
	public boolean controlUser(String userId) {

		//Cursor cursor = database.query(MyUserSQLHelper.TABLE_User, allColumns,
		//		null, null, null, null, null);
		
		Cursor cursor=database.rawQuery("select count(*) from "+MyUserSQLHelper.TABLE_USER+" where "+MyUserSQLHelper.COLUMN_ID+"='"+userId+"'", allColumns);
		
		cursor.moveToFirst();
		int a=0;
        try{
		a = cursor.getCount();
        }catch(Exception e){
        	e.printStackTrace();
        }
		// Make sure to close the cursor
		cursor.close();
		if(a==1)
			return true;
			return false;
	}
	
	private User cursorToUser(Cursor cursor){
		User user=new User();
		user.setId(cursor.getInt(0));
		user.setUserId(cursor.getString(1));
		user.setUserName(cursor.getString(2));
		user.setPassword(cursor.getString(3));
		return user;
	}
	
    public boolean onControlExistance(SQLiteDatabase db,String tableName) {
		

	    Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
	    if(cursor!=null) {
	        if(cursor.getCount()>0) {
	                            cursor.close();
	            return true;
	        }
	                    cursor.close();
	    }
	    return false;
	}
}
