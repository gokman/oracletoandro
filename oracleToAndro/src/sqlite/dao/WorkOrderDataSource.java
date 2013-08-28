package sqlite.dao;

import java.util.ArrayList;
import java.util.List;

import sqlite.model.WorkOrder;
import sqllite.db.MyWorkOrderSQLHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WorkOrderDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MyWorkOrderSQLHelper dbHelper;
	private String[] allColumns = { MyWorkOrderSQLHelper.COLUMN_ID,
			MyWorkOrderSQLHelper.COLUMN_TESISAT_NO, MyWorkOrderSQLHelper.COLUMN_STATU,
			MyWorkOrderSQLHelper.COLUMN_WORKTYPE};

	public WorkOrderDataSource(Context context) {
		dbHelper = new MyWorkOrderSQLHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		if(!onControlExistance(database, MyWorkOrderSQLHelper.TABLE_WORKORDER)){
			dbHelper.onCreate(database);
		}
		
	}

	public void close() {
		dbHelper.close();
	}

	public void insertWorkOrder(WorkOrder workOrder) {
		ContentValues values = new ContentValues();
		values.put(MyWorkOrderSQLHelper.COLUMN_TESISAT_NO, workOrder.getTesisatNo());
		values.put(MyWorkOrderSQLHelper.COLUMN_STATU, workOrder.getStatu());
		values.put(MyWorkOrderSQLHelper.COLUMN_WORKTYPE, workOrder.getWorkType());
		database.insert(MyWorkOrderSQLHelper.TABLE_WORKORDER, null,
				values);
		
	}

	public void deleteWorkOrder(WorkOrder workOrder) {
		long id = workOrder.getId();
		database.delete(MyWorkOrderSQLHelper.TABLE_WORKORDER, MyWorkOrderSQLHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	public void deleteAllWorkOrder() {
		database.delete(MyWorkOrderSQLHelper.TABLE_WORKORDER,null, null);
	}

	public List<WorkOrder> getAllWorkOrder() {
		List<WorkOrder> comments = new ArrayList<WorkOrder>();

		Cursor cursor = database.query(MyWorkOrderSQLHelper.TABLE_WORKORDER, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			WorkOrder workOrder = cursorToWorkOrder(cursor);
			comments.add(workOrder);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}

	public int getWorkOrderCount() {

		Cursor cursor = database.query(MyWorkOrderSQLHelper.TABLE_WORKORDER, allColumns,
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
	
	public boolean controlWorkOrder(String panelId) {

		Cursor cursor = database.query(MyWorkOrderSQLHelper.TABLE_WORKORDER, allColumns,
				null, null, null, null, null);
		
		//Cursor cursor=database.rawQuery("select count(*) from "+MySQLiteHelper.TABLE_PANEL+" where "+MySQLiteHelper.COLUMN_PANEL_ID+"='"+panelId+"'", allColumns);
		//Cursor cursor=database.rawQuery("select * from "+MySQLiteHelper.TABLE_PANEL, allColumns);
		
		cursor.moveToFirst();
		for(int i=0;i<cursor.getCount();i++){
			WorkOrder workOrder=cursorToWorkOrder(cursor);
			System.out.println(workOrder);
			cursor.moveToNext();
		}
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
	
	private WorkOrder cursorToWorkOrder(Cursor cursor){
		WorkOrder workOrder=new WorkOrder();
		workOrder.setId(cursor.getInt(0));
		workOrder.setTesisatNo(cursor.getString(1));
		workOrder.setStatu(cursor.getString(2));
		workOrder.setWorkType(cursor.getString(3));
		return workOrder;
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
