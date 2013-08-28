package sqllite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyPanelSQLHelper extends SQLiteOpenHelper {
	public static final String TABLE_PANEL = "panel";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MODEM_NO = "modemNo";
	public static final String COLUMN_SERIAL_NO_1 = "serialNo1";
	public static final String COLUMN_SERIAL_NO_2 = "serialNo2";
	public static final String COLUMN_PANEL_ID = "panelId";

	public static final String DATABASE_NAME = "sayacKontrol.db";
	public static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_PANEL
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_MODEM_NO + " text not null," + COLUMN_SERIAL_NO_1
			+ " text not null," + COLUMN_SERIAL_NO_2 + " text not null,"
			+ COLUMN_PANEL_ID + " text not null" + ");";

	public MyPanelSQLHelper(Context context) {
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANEL);
		onCreate(db);
	}
	
    
}
