package et.artisan.cn.cps.dto;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import et.artisan.cn.cps.entity.Project;

public class ProjectsListDTO {

	ArrayList<Project> data;
	JQueryDataTableParamModel param;
	String sEcho;
	String draw = "1";
	int iTotalRecords; // total number of records (unfiltered)
	int iTotalDisplayRecords; // value will be set when code filters companies by keyword

	public ArrayList<Project> getData() {
		return data;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}
	public void setData(ArrayList<Project> data) {
		this.data = data;
	}

	public void setTotalNumberOfRecords(int totalRecords) {
		this.iTotalRecords = totalRecords;
	}

	public void setJQueryDataTableParamModel(JQueryDataTableParamModel param) {
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
			row.add(new JsonPrimitive(data.get(i).getCode()));
			row.add(new JsonPrimitive(data.get(i).getName().replace('"', ' ')));
			row.add(new JsonPrimitive(data.get(i).getAmharicName()));
			row.add(new JsonPrimitive(data.get(i).getClientName()));
			row.add(new JsonPrimitive(data.get(i).getRegisteredOn().toString()));
			items.add(row);
		}
		jsonResponse.add("aaData", items);
		return jsonResponse.toString();
	}
}
