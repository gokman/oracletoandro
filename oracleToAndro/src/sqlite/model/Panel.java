package sqlite.model;

import org.ksoap2.serialization.SoapObject;

public class Panel {
	
	private  int id;
	private  String modemNo;
	private  String serialNo1;
	private  String serialNo2;
	private  String panelId;
	
	public Panel(){
		
	}
	
	public Panel(SoapObject obj) {
		this.panelId = GetValue(obj, "PanelId");
		this.modemNo=GetValue(obj, "ModemImeiNo");
		this.serialNo1=GetValue(obj, "SerialNo1");
		this.serialNo2=GetValue(obj, "SerialNo2");
		
	}
	
	public String GetValue(SoapObject object, String Field) {
		try {
			return object.getProperty(Field).toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModemNo() {
		return modemNo;
	}
	public void setModemNo(String modemNo) {
		this.modemNo = modemNo;
	}
	public String getSerialNo1() {
		return serialNo1;
	}
	public void setSerialNo1(String serialNo1) {
		this.serialNo1 = serialNo1;
	}
	public String getSerialNo2() {
		return serialNo2;
	}
	public void setSerialNo2(String serialNo2) {
		this.serialNo2 = serialNo2;
	}
	public String getPanelId() {
		return panelId;
	}
	public void setPanelId(String panelId) {
		this.panelId = panelId;
	}
	
	
}
