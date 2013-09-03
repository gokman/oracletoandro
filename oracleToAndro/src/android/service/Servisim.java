package android.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.example.oracletoandro.MainActivity;
import com.example.oracletoandro.NetworkUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

public class Servisim extends Service {
	
	Timer timer;
	Handler handler;
	//çalýþma aralýðý
	final static long ZAMAN=100000;
	//gece 00:00 dan kaça kadar çalýþacaðýný tutan saat
	final static int EXECUTION_TIME_LIMIT=23;
	boolean connectionConvenience;
	boolean updateRecordAvaliability;
	

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		  // TODO Auto-generated method stub 
		  super.onStart(intent, startId); 
		  connectionConvenience = intent.getExtras().getBoolean("connectionConvenience");  
		  updateRecordAvaliability=intent.getExtras().getBoolean("updateRecordAvaliability");  
	}
	
	public void onCreate(){
		super.onCreate();
		
		timer=new Timer();
		handler=new Handler(Looper.getMainLooper());
		
		
		
		//zamanlayýcý oluþtur
		timer.scheduleAtFixedRate(new TimerTask() {	
			
			@Override
			public void run() {
				handler.post(new Runnable(){
					
					//tarih çek
					Calendar calendar=Calendar.getInstance();
					Date date=calendar.getTime();
					DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm");
					String tarihim=format.format(date);

					@Override
					public void run() {
						//gece 0 ile EXECUTION_TIME_LIMIT deðeri arasýnda bir saatte ise çalýþtýr
						//if(Integer.parseInt(tarihim.substring(11,13)) < EXECUTION_TIME_LIMIT){
						//yapýlacak ana iþlem burada olacak
						//}
						/*servisin çalýþmasý için 3 kural var
						 * 1 að ýn iyi olmasý (3g)
						 * 2 son güncellemenin yapýlmamýþ olmasý
						 * 3 saatin gece 0-6 arasýnda olmasý
						 */
						if(connectionConvenience==true && updateRecordAvaliability==false && Integer.parseInt(tarihim.substring(11,13)) < EXECUTION_TIME_LIMIT){
							//günü baþlattýk
						    new MainActivity().startDay();
						
						}
					}
					
				});
				
			}
		}, 0, ZAMAN);
		
		
	}
	
	public void onDestroy(){
		timer.cancel();
		super.onDestroy();
	}

}
