package et.artisan.cn.cps.dto;

import et.artisan.cn.cps.entity.Document;
import et.artisan.cn.cps.util.Constants;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 *
 */
public class DocumentQueryParameter extends JQueryDataTableParamModel {

	String projectCode;
	String documentNo;
	String year;
	String documentId;
	String outgoingdocumentno;
	double amount;
	
	ArrayList<String> statuses = new ArrayList<String>();
	
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		try {
		String[] result = documentId.split("/");
		this.projectCode = result[0];
		this.documentNo = result[1];
		this.year = result[2];
		this.documentId = documentId;
		}catch(Exception e) {
			System.out.println("Invalid Document Code");
		}
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public String getYear() {
		return year;
	}
	public String getOutgoingdocumentno() {
		return outgoingdocumentno;
	}
	public void setOutgoingdocumentno(String outgoingdocumentno) {
		this.outgoingdocumentno = outgoingdocumentno;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void addStatus(String status) {
		statuses.add(status);
	}
	public String getStatus() {
		String returnValue = "";
		for (String status : statuses) {
			returnValue += " status = '" + status + "' OR ";
		}
		if(!returnValue.isEmpty()) {
			returnValue ="( " + returnValue.substring(0, returnValue.length()-3) + ")";
		}
		return returnValue;
	}

    

}
