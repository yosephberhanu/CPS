package et.artisan.cn.cps.dto;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import et.artisan.cn.cps.entity.Branch;
import et.artisan.cn.cps.entity.Claim;
import et.artisan.cn.cps.entity.ClientRegion;
import et.artisan.cn.cps.entity.Payment;
import et.artisan.cn.cps.util.CommonTasks;

public class ClaimListDTO {

	
	ArrayList<Claim> data;
	JQueryDataTableParamModel param;
	String sEcho;
	String draw = "1";
	long iTotalRecords; // total number of records (unfiltered)
	long iTotalDisplayRecords; // value will be set when code filters companies by keyword

	public ArrayList<Claim> getData() {
		return data;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}
	public void setData(ArrayList<Claim> data) {
		this.data = data;
	}

	public void setTotalNumberOfRecords(long totalRecords) {
		this.iTotalRecords = totalRecords;
	}
	
	public long getTotalNumberOfRecords() {
		return this.iTotalRecords;
	}
	
	public void setJQueryDataTableParamModel(ClaimParamModel param) {
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
			row.add(new JsonPrimitive(data.get(i).getClaimNumber()));
			row.add(new JsonPrimitive(data.get(i).getClaimCNNumber()));
			row.add(new JsonPrimitive(data.get(i).getArrivalDate().toString()));
			row.add(new JsonPrimitive(data.get(i).getPayingBranchName()));
			row.add(new JsonPrimitive(CommonTasks.moneyFormat(data.get(i).getAmount()))+"/"+CommonTasks.moneyFormat(data.get(i).getRegisteredAmount()));
			row.add(new JsonPrimitive(data.get(i).getClientName()));
			items.add(row);
		}
		jsonResponse.add("aaData", items);
		return jsonResponse.toString();
	}
}
