package sqlite.dao;

import java.util.ArrayList;
import java.util.List;

import sqlite.model.Panel;
import sqllite.db.MyPanelSQLHelper;
import sqllite.db.MyWorkOrderSQLHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PanelDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MyPanelSQLHelper dbHelper;
	private String[] allColumns = { MyPanelSQLHelper.COLUMN_ID,
			MyPanelSQLHelper.COLUMN_MODEM_NO, MyPanelSQLHelper.COLUMN_SERIAL_NO_1,
			MyPanelSQLHelper.COLUMN_SERIAL_NO_2, MyPanelSQLHelper.COLUMN_PANEL_ID };

	public PanelDataSource(Context context) {
		dbHelper = new MyPanelSQLHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		if(!onControlExistance(database, MyPanelSQLHelper.TABLE_PANEL)){
			dbHelper.onCreate(database);
		}
		
	}

	public void close() {
		dbHelper.close();
	}

	public void insertPanel(Panel panel) {
		ContentValues values = new ContentValues();
		values.put(MyPanelSQLHelper.COLUMN_MODEM_NO, panel.getModemNo());
		values.put(MyPanelSQLHelper.COLUMN_SERIAL_NO_1, panel.getSerialNo1());
		values.put(MyPanelSQLHelper.COLUMN_SERIAL_NO_2, panel.getSerialNo2());
		values.put(MyPanelSQLHelper.COLUMN_PANEL_ID, panel.getPanelId());
		database.insert(MyPanelSQLHelper.TABLE_PANEL, null,
				values);
		
	}

	public void deletePanel(Panel panel) {
		long id = panel.getId();
		database.delete(MyPanelSQLHelper.TABLE_PANEL, MyPanelSQLHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	public void deleteAllPanel() {
		database.delete(MyPanelSQLHelper.TABLE_PANEL,null, null);
	}

	public List<Panel> getAllPanel() {
		List<Panel> comments = new ArrayList<Panel>();

		Cursor cursor = database.query(MyPanelSQLHelper.TABLE_PANEL, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Panel panel = cursorToPanel(cursor);
			comments.add(panel);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}

	public int getPanelCount() {

		Cursor cursor = database.query(MyPanelSQLHelper.TABLE_PANEL, allColumns,
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
	
	public boolean controlPanel(String panelId) {

		//Cursor cursor = database.query(MyPanelSQLHelper.TABLE_PANEL, allColumns,
		//		null, null, null, null, null);
		
		Cursor cursor=database.rawQuery("select count(*) from "+MyPanelSQLHelper.TABLE_PANEL+" where "+MyPanelSQLHelper.COLUMN_PANEL_ID+"='"+panelId+"'", allColumns);
		
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
	
	private Panel cursorToPanel(Cursor cursor){
		Panel panel=new Panel();
		panel.setId(cursor.getInt(0));
		panel.setModemNo(cursor.getString(1));
		panel.setSerialNo1(cursor.getString(2));
		panel.setSerialNo2(cursor.getString(3));
		panel.setPanelId(cursor.getString(4));
		return panel;
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
