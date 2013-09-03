package sqlite.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyWorkOrderSQLHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_WORKORDER = "workorder";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TESISAT_NO = "tesisatNo";
	public static final String COLUMN_STATU = "statu";
	public static final String COLUMN_WORKTYPE = "workType";

	public static final String DATABASE_NAME = "sayacKontrol.db";
	public static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_WORKORDER
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_TESISAT_NO + " text not null," + COLUMN_STATU
			+ " text not null," + COLUMN_WORKTYPE + " text not null" + ");";

	public MyWorkOrderSQLHelper(Context context) {
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKORDER);
		onCreate(db);
	}
	
}
