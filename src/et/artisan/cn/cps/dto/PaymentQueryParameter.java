package et.artisan.cn.cps.dto;

import java.util.*;

import et.artisan.cn.cps.entity.*;
import et.artisan.cn.cps.util.Constants;

/**
 *
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 *
 */
public class PaymentQueryParameter extends JQueryDataTableParamModel {

	long documentId;
	long projectId;
	long branchId;
	ArrayList<String> statuses = new ArrayList<String>();
	public long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public long getBranchId() {
		return branchId;
	}
	public void setBranchId(long branchId) {
		this.branchId = branchId;
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
