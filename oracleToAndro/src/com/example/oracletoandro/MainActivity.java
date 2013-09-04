package com.example.oracletoandro;

import global.Variables;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dotnet.service.Service;
import sqlite.dao.LocalProcessDataSource;
import sqlite.dao.PanelDataSource;
import sqlite.dao.ProcessUpdateDataSource;
import sqlite.dao.UserDataSource;
import sqlite.dao.WorkOrderDataSource;
import sqlite.model.Panel;
import sqlite.model.ProcessUpdate;
import sqlite.model.User;
import sqlite.model.WorkOrder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.timer.service.Servisim;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	
	private PanelDataSource dataSourcePanel;
	private WorkOrderDataSource dataSourceWorkOrder;
	private UserDataSource dataSourceUser;
	private ProcessUpdateDataSource dataSourceProcessUpdate;
	private LocalProcessDataSource dataSourceLocalProcess;
	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.textView1);
		Button button = (Button) findViewById(R.id.button1);
		
		dataSourcePanel = new PanelDataSource(this);
		dataSourcePanel.open();
        dataSourceWorkOrder = new WorkOrderDataSource(this);
		dataSourceWorkOrder.open();
		dataSourceUser = new UserDataSource(this);
		dataSourceUser.open();
		dataSourceLocalProcess = new LocalProcessDataSource(this);
		dataSourceLocalProcess.open();
		dataSourceProcessUpdate = new ProcessUpdateDataSource(this);
		dataSourceProcessUpdate.open();
		//servisin çalýþma olayý
		Intent serviceIntent=new Intent(this,Servisim.class);
		serviceIntent.putExtra("connectionConvenience", isConnectionConvenient());
		try {
			serviceIntent.putExtra("updateRecordAvaliability", new isProcessUpdateRecordAvaliable().execute(10).get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//servisOlayi();
		stopService(new Intent(this,Servisim.class));	
		startService(new Intent(this,Servisim.class));
       
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
			}
			
		});
		     	
	}
	
	public void servisOlayi(){
		if(isServiceWorking()){
			stopService(new Intent(this,Servisim.class));	
			startService(new Intent(this,Servisim.class));
		}
		else{
			startService(new Intent(this,Servisim.class));
		}
	}
	
	public  boolean isConnectionConvenient(){
		//2.3 puan geliyorsa baðlantý sonucunda iþlemi yapmaya baþlayabiliriz
		if(NetworkUtil.getConnectivityStatus(getApplicationContext()) == 2.3)
			return true;
		    return false;
	}
	
	public boolean isServiceWorking(){
		ActivityManager activityManager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
		for(RunningServiceInfo servis : activityManager.getRunningServices(Integer.MAX_VALUE)){
			if(servis.service.getPackageName().equals(getApplicationContext().getPackageName())){
				return true;
			}
		}
		return false;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//inner classes
	
	private class insertAllPanelTask extends AsyncTask<String,String,String> {

		@Override
		protected String doInBackground(String... arg0) {
			List<Panel> panelList=new ArrayList<Panel>();
    		try {
    			panelList = Service.getIntance()
    					.LoadAndroidPanel();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    		for (int i = 0; i < panelList.size(); i++) {
    			dataSourcePanel.insertPanel(panelList.get(i));
    		}
    		
    		return null;
		}

		
	     
	 }
	
	private class insertAllWorkOrderTask extends AsyncTask<String,String,String> {

		@Override
		protected String doInBackground(String... arg0) {
			List<WorkOrder> workOrderList=new ArrayList<WorkOrder>();
    		try {
    			//kayýtlarý 10 ar bin þeklinde alýyoruz.
    			int start=1,limit=10000;
    			float forCount=(float) Math.ceil((float)Service.getIntance().GetWorkOrderCount()/10000);
    			for(int i=0;i<=forCount;i++){
    				workOrderList = Service.getIntance()
        					.LoadAndroidWorkOrder(start,limit);
    				start += 10000;
    				for (int j = 0; j < workOrderList.size(); j++) {
    	    			dataSourceWorkOrder.insertWorkOrder(workOrderList.get(j));
    	    		}
    			}
    			
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		
    		return null;
		}

		
	     
	 }
	
	private class insertAllUserTask extends AsyncTask<String,String,String> {

		@Override
		protected String doInBackground(String... arg0) {
			List<User> userList=new ArrayList<User>();
    		try {
    			userList = Service.getIntance()
    					.LoadAndroidUser();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    		for (int i = 0; i < userList.size(); i++) {
    			dataSourceUser.insertUser(userList.get(i));
    		}
    		
    		return null;
		}

		
	     
	 }
	
	private class insertProcessUpdateTask extends AsyncTask<String,String,String> {

		@Override
		protected String doInBackground(String... args) {
			String userId=null,phoneId=null,updateDate=null;
			ProcessUpdate processUpdate=new ProcessUpdate();
    		userId=args[0];
    		phoneId=args[1];
    		updateDate=args[2];
    		processUpdate.setUserId(userId);
    		processUpdate.setPhoneId(phoneId);
    		processUpdate.setUpdateDate(updateDate);
    		
    			dataSourceProcessUpdate.insertProcessUpdate(processUpdate);
    		
    		
    		return null;
		}

		
	     
	 }
	
	private class getAllPanelTask extends AsyncTask<String,String,List<Panel>> {

		@Override
		protected List<Panel> doInBackground(String... arg0) {
			List<Panel> panelList=new ArrayList<Panel>();
    		try {
    			panelList = dataSourcePanel.getAllPanel();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    		
    		return panelList;
		}

		
	     
	 }
	
	private class getAllWorkOrderTask extends AsyncTask<String,String,List<WorkOrder>> {

		@Override
		protected List<WorkOrder> doInBackground(String... arg0) {
			List<WorkOrder> workOrderList=new ArrayList<WorkOrder>();
    		try {
    			workOrderList = dataSourceWorkOrder.getAllWorkOrder();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    		
    		return workOrderList;
		}

		
	     
	 }
	
	private class getAllUserTask extends AsyncTask<String,String,List<User>> {

		@Override
		protected List<User> doInBackground(String... arg0) {
			List<User> userList=new ArrayList<User>();
    		try {
    			userList = dataSourceUser.getAllUser();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    		
    		return userList;
		}

		     
	 }
	
	private class getAllProcessUpdateTask extends AsyncTask<String,String,List<ProcessUpdate>> {

		@Override
		protected List<ProcessUpdate> doInBackground(String... arg0) {
			List<ProcessUpdate> processUpdateList=new ArrayList<ProcessUpdate>();
    		try {
    			processUpdateList = dataSourceProcessUpdate.getAllProcessUpdate();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    		
    		return processUpdateList;
		}

		     
	 }
	
	private class controlPanelTask extends AsyncTask<String,String,Boolean> {

		@Override
		protected Boolean doInBackground(String... args) {
			boolean kontrol=dataSourcePanel.controlPanel(args[0]);
			return kontrol;
		}

		
	     
	 }
	
	private class countPanelTask extends AsyncTask<String,String,Integer> {

		@Override
		protected Integer doInBackground(String... args) {
			int count=dataSourcePanel.getPanelCount();
			return count;
		}

	     
	 }
	
	private class countWorkOrderTask extends AsyncTask<String,String,Integer> {

		@Override
		protected Integer doInBackground(String... args) {
			int count=dataSourceWorkOrder.getWorkOrderCount();
			return count;
		}

	     
	 }
	
	private class countUserTask extends AsyncTask<String,String,Integer> {

		@Override
		protected Integer doInBackground(String... args) {
			int count=dataSourceUser.getUserCount();
			return count;
		}

	     
	 }
	
	private class countOracleUserTask extends AsyncTask<String,String,Integer> {

		@Override
		protected Integer doInBackground(String... args) {
			int count=Service.getIntance().GetUserCount();
			return count;
		}
     
	 }
	
	private class countOracleWorkOrderTask extends AsyncTask<String,String,Integer> {

		@Override
		protected Integer doInBackground(String... args) {
			int count=Service.getIntance().GetWorkOrderCount();
			return count;
		}

	     
	 }
	
	private class countOraclePanelTask extends AsyncTask<String,String,Integer> {

		@Override
		protected Integer doInBackground(String... args) {
			int count=Service.getIntance().GetPanelCount();
			return count;
		}

	     
	 }
	
	
	private class deleteAllPanelTask extends AsyncTask<String,String,Integer> {

		@Override
		protected Integer doInBackground(String... args) {
			dataSourcePanel.deleteAllPanel();;
			return 1;
		}
    
	 }
	
	private class deleteAllWorkOrderTask extends AsyncTask<String,String,Integer> {

		@Override
		protected Integer doInBackground(String... args) {
			dataSourceWorkOrder.deleteAllWorkOrder();
			return 1;
		}
    
	 }
	
	private class deleteAllUserTask extends AsyncTask<String,String,Integer> {

		@Override
		protected Integer doInBackground(String... args) {
			dataSourceUser.deleteAllUser();
			return 1;
		}
    
	 }
	private class deleteAllProcessUpdateTask extends AsyncTask<String,String,Integer> {

		@Override
		protected Integer doInBackground(String... args) {
			dataSourceProcessUpdate.deleteAllProcessUpdate();
			return 1;
		}
    
	 }
	
	private class isProcessUpdateRecordAvaliable extends AsyncTask<Integer,String,Boolean>{

		@Override
		protected Boolean doInBackground(Integer... params) {
			
			try {
				return dataSourceProcessUpdate.isRecordAvaliable(params[0]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
	}
		
	 public void startDay(){
		 
		 int oracleWorkOrderCount=0,oraclePanelCount=0,oracleUserCount=0;
		 int localWorkOrderCount=0,localPanelCount=0,localUserCount=0;
		 
		 //kontrol tablolarýmýzýn hepsini siliyoruz
		 	new deleteAllWorkOrderTask().execute();
		 	new deleteAllUserTask().execute();
		 	new deleteAllPanelTask().execute();
			
		 //bu tablolarýn oracledaki satýr sayýlarýný tutuyoruz. 
		 	
				try {
					oracleWorkOrderCount=new countOracleWorkOrderTask().execute().get();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		 	try {
				oraclePanelCount=new countOraclePanelTask().execute().get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		 	try {
				oracleUserCount=new countOracleUserTask().execute().get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		 	
		 //þimdi de oracle dan tablolarýmýza kayýtlarý atýyoruz
		    new insertAllPanelTask().execute();
		    new insertAllWorkOrderTask().execute();
			new insertAllUserTask().execute();
			
		 // kayýtlarý atttýktan sonra da elimizdeki tablolardaki kayýt sayýsýna bakýp doðru atýp atmadýðýný kontrol ediyoruz.s
			try {
				localWorkOrderCount=new countWorkOrderTask().execute().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				localPanelCount=new countPanelTask().execute().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				localUserCount=new countUserTask().execute().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 //eðer oracle ile elimizdeki kayýtlarýn sayýsý eþit ise kayýt iþlemi baþarý ile tamamlanmýþ demektir.
		 if(localPanelCount==oraclePanelCount && localUserCount==oracleUserCount && localWorkOrderCount==oracleWorkOrderCount){
			//baþarý ile tamamlandý ise processupdate tablosuna kayýt atýyoruz.
			 ProcessUpdate processUpdate=new ProcessUpdate();
			 processUpdate.setUserId("aa");
			 TelephonyManager   telephonyManager  =  ( TelephonyManager )getSystemService( Context.TELEPHONY_SERVICE );
             processUpdate.setPhoneId(telephonyManager.getDeviceId());
             processUpdate.setUpdateDate(Variables.tarihim);
			 dataSourceProcessUpdate.insertProcessUpdate(processUpdate);
		 }
			
	 }
	
}