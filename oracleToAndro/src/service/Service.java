package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import sqlite.model.Panel;
import sqlite.model.User;
import sqlite.model.WorkOrder;
import android.app.Activity;
import android.content.Context;

public class Service
{
	private static Service _service = null;
	final String NAMESPACE = "http://tempuri.org/";
	//final String URL = "http://194.54.65.141:8090/ws.asmx";
	String URL = "http://www.aktekasos.com/ws.asmx";
	String SOAP_ACTION ;
	String METHOD_NAME ;
	
	public static Service getIntance() 
	{
		if (_service == null)
			return _service = new Service();
		return _service;
	}
	public List<Panel> LoadAndroidPanel()throws Exception
	{
		SOAP_ACTION = "http://tempuri.org/LoadAndroidPanel";
		METHOD_NAME = "LoadAndroidPanel";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		SoapObject ListPanel=null;
		
		List<Panel> panelList = new ArrayList<Panel>();
		try 
		{
			ListPanel = ((SoapObject) HttpTransport(SOAP_ACTION,
						METHOD_NAME, NAMESPACE, URL, Parameters));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(ListPanel==null)
			return panelList;
		
		for (int i = 0; i < ListPanel.getPropertyCount(); i++) 
		{
			Object property = ListPanel.getProperty(i);
			if (property instanceof SoapObject) 
			{
				SoapObject countryObj = (SoapObject) property;

				panelList.add(new Panel(countryObj));
			}
		}
		return panelList;
	}
	
	public List<WorkOrder> LoadAndroidWorkOrder(int start,int limit)throws Exception
	{
		SOAP_ACTION = "http://tempuri.org/LoadAndroidWorkOrder";
		METHOD_NAME = "LoadAndroidWorkOrder";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("start", start);
		Parameters.put("limit", limit);
		SoapObject ListWorkOrder=null;
		
		List<WorkOrder> workOrderList = new ArrayList<WorkOrder>();
		try 
		{
			ListWorkOrder = ((SoapObject) HttpTransport(SOAP_ACTION,
						METHOD_NAME, NAMESPACE, URL, Parameters));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(ListWorkOrder==null)
			return workOrderList;
		
		for (int i = 0; i < ListWorkOrder.getPropertyCount(); i++) 
		{
			Object property = ListWorkOrder.getProperty(i);
			if (property instanceof SoapObject) 
			{
				SoapObject countryObj = (SoapObject) property;

				workOrderList.add(new WorkOrder(countryObj));
			}
		}
		return workOrderList;
	}
	
	public int GetWorkOrderCount()throws Exception
	{
		SOAP_ACTION = "http://tempuri.org/GetWorkOrderCount";
		METHOD_NAME = "GetWorkOrderCount";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		String countObject=null;
		
		try 
		{
			countObject = ( HttpTransport(SOAP_ACTION,
						METHOD_NAME, NAMESPACE, URL, Parameters)).toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Integer.parseInt(countObject);
		
	}
	
	public List<User> LoadAndroidUser()throws Exception
	{
		SOAP_ACTION = "http://tempuri.org/LoadAndroidUser";
		METHOD_NAME = "LoadAndroidUser";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		SoapObject ListUser=null;
		
		List<User> userList = new ArrayList<User>();
		try 
		{
			ListUser = ((SoapObject) HttpTransport(SOAP_ACTION,
						METHOD_NAME, NAMESPACE, URL, Parameters));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(ListUser==null)
			return userList;
		
		for (int i = 0; i < ListUser.getPropertyCount(); i++) 
		{
			Object property = ListUser.getProperty(i);
			if (property instanceof SoapObject) 
			{
				SoapObject countryObj = (SoapObject) property;

				userList.add(new User(countryObj));
			}
		}
		return userList;
	}
	
	private Object HttpTransport(String SOAP_ACTION, String METHOD_NAME,
			String NAMESPACE, String URL, Map<String, Object> Parameters) throws Exception {
		try 
		{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			PropertyInfo pi = null;
			Iterator it = Parameters.entrySet().iterator();

			while (it.hasNext()) 
			{
				pi = new PropertyInfo();
				Map.Entry pairs = (Map.Entry) it.next();
				pi.setName(pairs.getKey().toString());
				pi.setValue(pairs.getValue());
				pi.setType(pairs.getValue().getClass());
				request.addProperty(pi);
			}

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;

			envelope.bodyOut = request;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransport = new HttpTransportSE(URL);
			Object response = null;

			try 
			{
				httpTransport.call(SOAP_ACTION, envelope);
				response = envelope.getResponse();

			} 
			catch (Exception exception) 
			{
				throw exception;				
			}
			return response;
		} catch (Exception e) 
		{
			throw e;
		}
		
	}
	public  void ToastMessage(Context ctx,String Message)
	{
			
	}

	
}
