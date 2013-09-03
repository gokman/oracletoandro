package global;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Variables {
	//tarih çek
	public static Calendar calendar=Calendar.getInstance();
	public static Date date=calendar.getTime();
	public static final DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm");
	public static final String tarihim=format.format(date);
	
	

}
