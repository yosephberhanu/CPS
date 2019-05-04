package et.artisan.cn.cps.dto;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import et.artisan.cn.cps.entity.Document;
import et.artisan.cn.cps.entity.Payment;
import et.artisan.cn.cps.util.CommonTasks;

public class DocumentListDTO {

	
	ArrayList<Document> data;
	DocumentQueryParameter param;
	String sEcho;
	String draw = "1";
	int iTotalRecords; // total number of records (unfiltered)
	int iTotalDisplayRecords; // value will be set when code filters companies by keyword

	public ArrayList<Document> getData() {
		return data;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}
	public void setData(ArrayList<Document> data) {
		this.data = data;
	}

	public void setTotalNumberOfRecords(int totalRecords) {
		this.iTotalRecords = totalRecords;
	}

	public void setDocumentQueryParameter(DocumentQueryParameter param) {
		this.param = param;
	}

	public void setEcho(String echo) {
		this.sEcho = echo;
	}

	public String toJSON() {
		JsonObject jsonResponse = new JsonObject();
		JsonArray items = new JsonArray();
		jsonResponse.addProperty("draw",draw);
		jsonResponse.addProperty("sEcho", sEcho);
		jsonResponse.addProperty("iTotalRecords", iTotalRecords);
		jsonResponse.addProperty("iTotalDisplayRecords", iTotalRecords);

		for (int i = 0; i < data.size(); i++) {
			JsonArray row = new JsonArray();
			row.add(new JsonPrimitive(data.get(i).getId()));
			row.add(new JsonPrimitive(data.get(i).getDocumentNo()));
			row.add(new JsonPrimitive(data.get(i).getProjectName()));
			row.add(new JsonPrimitive(data.get(i).getClientName()));
			row.add(new JsonPrimitive(data.get(i).getClientRegionName()));
			row.add(new JsonPrimitive(CommonTasks.moneyFormat(data.get(i).getTotalAmount())+""+CommonTasks.moneyFormat(data.get(i).getRegisteredAmount())+"/"+CommonTasks.moneyFormat(data.get(i).getPaidAmount())));
			row.add(new JsonPrimitive(data.get(i).getStatus()));
			items.add(row);
		}
		jsonResponse.add("aaData", items);
		return jsonResponse.toString();
	}
}
