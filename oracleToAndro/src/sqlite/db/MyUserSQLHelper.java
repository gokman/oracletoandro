package sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyUserSQLHelper extends SQLiteOpenHelper {
	public static final String TABLE_USER = "user";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_USER_NAME = "userName";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_USER_ID = "userId";

	public static final String DATABASE_NAME = "sayacKontrol.db";
	public static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_USER
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_USER_ID + " text not null,"  
			+ COLUMN_USER_NAME + " text not null,"
			+ COLUMN_PASSWORD + " text not null" + ");";

	public MyUserSQLHelper(Context context) {
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		onCreate(db);
	}
	
    
}
