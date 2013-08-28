package sqlite.dao;

import java.util.ArrayList;
import java.util.List;

import sqlite.model.LocalProcess;
import sqlite.model.Panel;
import sqllite.db.MyLocalProcessSQLHelper;
import sqllite.db.MyPanelSQLHelper;
import sqllite.db.MyWorkOrderSQLHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LocalProcessDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MyLocalProcessSQLHelper dbHelper;
	private String[] allColumns = { MyLocalProcessSQLHelper.COLUMN_ID,
			MyLocalProcessSQLHelper.COLUMN_USER_ID, MyLocalProcessSQLHelper.COLUMN_DATE,
			MyLocalProcessSQLHelper.COLUMN_PROCESS };

	public LocalProcessDataSource(Context context) {
		dbHelper = new MyLocalProcessSQLHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		if(!onControlExistance(database, MyLocalProcessSQLHelper.TABLE_LOCAL_PROCESS)){
			dbHelper.onCreate(database);
		}
		
	}

	public void close() {
		dbHelper.close();
	}

	public void insertLocalProcess(LocalProcess localProcess) {
		ContentValues values = new ContentValues();
		values.put(MyLocalProcessSQLHelper.COLUMN_USER_ID, localProcess.getUserId());
		values.put(MyLocalProcessSQLHelper.COLUMN_DATE, localProcess.getDate());
		values.put(MyLocalProcessSQLHelper.COLUMN_PROCESS, localProcess.getProcess());
		database.insert(MyLocalProcessSQLHelper.TABLE_LOCAL_PROCESS, null,
				values);
		
	}

	public void deleteLocalProcess(LocalProcess localProcess) {
		long id = localProcess.getId();
		database.delete(MyLocalProcessSQLHelper.TABLE_LOCAL_PROCESS, MyLocalProcessSQLHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	public void deleteAllLocalProcess() {
		database.delete(MyLocalProcessSQLHelper.TABLE_LOCAL_PROCESS,null, null);
	}

	public List<LocalProcess> getAllLocalProcess() {
		List<LocalProcess> comments = new ArrayList<LocalProcess>();

		Cursor cursor = database.query(MyLocalProcessSQLHelper.TABLE_LOCAL_PROCESS, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			LocalProcess localProcess = cursorToLocalProcess(cursor);
			comments.add(localProcess);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}

	public int getLocalProcessCount() {

		Cursor cursor = database.query(MyLocalProcessSQLHelper.TABLE_LOCAL_PROCESS, allColumns,
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
	
	
	private LocalProcess cursorToLocalProcess(Cursor cursor){
		LocalProcess localProcess=new LocalProcess();
		localProcess.setId(cursor.getInt(0));
		localProcess.setUserId(cursor.getString(1));
		localProcess.setDate(cursor.getString(2));
		localProcess.setProcess(cursor.getString(3));
		return localProcess;
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
