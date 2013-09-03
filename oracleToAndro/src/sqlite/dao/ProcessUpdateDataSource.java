package sqlite.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sqlite.db.MyPanelSQLHelper;
import sqlite.db.MyProcessUpdateSQLHelper;
import sqlite.db.MyWorkOrderSQLHelper;
import sqlite.model.Panel;
import sqlite.model.ProcessUpdate;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProcessUpdateDataSource {

	// Database fields
	public SQLiteDatabase database;
	public MyProcessUpdateSQLHelper dbHelper;
	public String[] allColumns = { MyProcessUpdateSQLHelper.COLUMN_ID,
			MyProcessUpdateSQLHelper.COLUMN_USER_ID, MyProcessUpdateSQLHelper.COLUMN_PHONE_ID,
			MyProcessUpdateSQLHelper.COLUMN_UPDATE_DATE};

	public ProcessUpdateDataSource(Context context) {
		dbHelper = new MyProcessUpdateSQLHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		if(!onControlExistance(database, MyProcessUpdateSQLHelper.TABLE_PROCESS_UPDATE)){
			dbHelper.onCreate(database);
		}
		
	}

	public void close() {
		dbHelper.close();
	}

	public void insertProcessUpdate(ProcessUpdate processUpdate) {
		ContentValues values = new ContentValues();
		values.put(MyProcessUpdateSQLHelper.COLUMN_USER_ID, processUpdate.getUserId());
		values.put(MyProcessUpdateSQLHelper.COLUMN_PHONE_ID, processUpdate.getPhoneId());
		values.put(MyProcessUpdateSQLHelper.COLUMN_UPDATE_DATE, processUpdate.getUpdateDate());
		database.insert(MyProcessUpdateSQLHelper.TABLE_PROCESS_UPDATE, null,
				values);
		
	}
	
	//belirtilen saat içinde bu tabloda kayýt var mý?
	public boolean isRecordAvaliable(int hour) throws ParseException{
		//þuanki tarihten hour kadar saat geriye gidip parametre olarak vereceðiz
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.HOUR, -hour);
		Date date=calendar.getTime();
		DateFormat format=new SimpleDateFormat("yyyy/MM/DD HH:mm");
		String tarihim=format.format(date);
		String query="Select 1 from " + MyProcessUpdateSQLHelper.TABLE_PROCESS_UPDATE + " where updateDate>'"+tarihim+"'";
		Cursor cursor=database.rawQuery(query,new String[]{});
		if(cursor.getCount()>0)
		return true;
		return false;
	}

	public void deleteProcessUpdate(ProcessUpdate processUpdate) {
		long id = processUpdate.getId();
		database.delete(MyProcessUpdateSQLHelper.TABLE_PROCESS_UPDATE, MyProcessUpdateSQLHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	public void deleteAllProcessUpdate() {
		database.delete(MyProcessUpdateSQLHelper.TABLE_PROCESS_UPDATE,null, null);
	}

	public List<ProcessUpdate> getAllProcessUpdate() {
		List<ProcessUpdate> comments = new ArrayList<ProcessUpdate>();

		Cursor cursor = database.query(MyProcessUpdateSQLHelper.TABLE_PROCESS_UPDATE, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ProcessUpdate processUpdate = cursorToProcessUpdate(cursor);
			comments.add(processUpdate);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}

	public int getProcessUpdateCount() {

		Cursor cursor = database.query(MyProcessUpdateSQLHelper.TABLE_PROCESS_UPDATE, allColumns,
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
	
	
	public ProcessUpdate cursorToProcessUpdate(Cursor cursor){
		ProcessUpdate processUpdate=new ProcessUpdate();
		processUpdate.setId(cursor.getInt(0));
		processUpdate.setUserId(cursor.getString(1));
		processUpdate.setPhoneId(cursor.getString(2));
		processUpdate.setUpdateDate(cursor.getString(3));
		return processUpdate;
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
