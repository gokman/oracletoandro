package sqlite.db;

import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyLocalProcessSQLHelper extends SQLiteOpenHelper {
	public static final String TABLE_LOCAL_PROCESS = "localprocess";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_USER_ID="userId";
	public static final String COLUMN_DATE="date";
	public static final String COLUMN_PROCESS="process";

	public static final String DATABASE_NAME = "sayacKontrol.db";
	public static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_LOCAL_PROCESS
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_USER_ID + " text not null," + COLUMN_DATE
			+ " text not null," + COLUMN_PROCESS + " text not null" + ");";

	public MyLocalProcessSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCAL_PROCESS);
		onCreate(db);
	}
	
    
}
