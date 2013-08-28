package com.example.oracletoandro;

import java.util.ArrayList;
import java.util.List;

import service.Service;
import sqlite.dao.PanelDataSource;
import sqlite.dao.UserDataSource;
import sqlite.dao.WorkOrderDataSource;
import sqlite.model.Panel;
import sqlite.model.User;
import sqlite.model.WorkOrder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	
	private PanelDataSource dataSourcePanel;
	private WorkOrderDataSource dataSourceWorkOrder;
	private UserDataSource dataSourceUser;
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
		text.setText("DDD");
	  //  text.setText(dataSource.getPanelCount());

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				List<WorkOrder> a=new ArrayList<WorkOrder>();
				List<User> b=new ArrayList<User>();
				//new getAllPanelTask().execute();
				try {
				 	//a=new getAllWorkOrderTask().execute().get();
					b=new getAllUserTask().execute().get();
					
				    //startDay();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
		});
		     
		
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
    			int start=0,limit=10000;
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
		
	 private void startDay(){
		 	//new deleteAllWorkOrderTask().execute();
			//new insertAllWorkOrderTask().execute();
			//new deleteAllPanelTask().execute();
			//new insertAllPanelTask().execute();
		     new deleteAllUserTask().execute();
			 new insertAllUserTask().execute();
				
	 }
	
}