package sqlite.model;

import org.ksoap2.serialization.SoapObject;

public class WorkOrder {
	
	private int id;
	private String tesisatNo;
	private String statu;
	private String workType;
	
	public WorkOrder(){
		
	}

	public WorkOrder(SoapObject obj) {
		this.tesisatNo = GetValue(obj, "TesisatNo");
		this.statu=GetValue(obj, "Statu");
		this.workType=GetValue(obj, "WorkType");
		
	}
	
	public String GetValue(SoapObject object, String Field) {
		try {
			return object.getProperty(Field).toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTesisatNo() {
		return tesisatNo;
	}
	public void setTesisatNo(String tesisatNo) {
		this.tesisatNo = tesisatNo;
	}
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}

}
