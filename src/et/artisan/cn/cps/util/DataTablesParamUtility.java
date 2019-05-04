package et.artisan.cn.cps.util;

import javax.servlet.http.HttpServletRequest;

import et.artisan.cn.cps.dto.JQueryDataTableParamModel;

public class DataTablesParamUtility {
	
	public static JQueryDataTableParamModel getParam(HttpServletRequest request, JQueryDataTableParamModel param)
	{
		if(request.getParameter("sEcho")!=null && request.getParameter("sEcho")!= "")
		{
			param.exact = request.getParameter("exact")==null?false:Boolean.parseBoolean(request.getParameter("exact"));
			param.sEcho = request.getParameter("sEcho");
			param.sSearch = request.getParameter("sSearch")!=null?request.getParameter("sSearch"):"";
			param.sColumns = request.getParameter("sColumns");
			param.iDisplayStart = Integer.parseInt( request.getParameter("iDisplayStart") );
			param.iDisplayLength = Integer.parseInt( request.getParameter("iDisplayLength") );
			param.iColumns = Integer.parseInt( request.getParameter("iColumns") );
			param.iSortingCols = request.getParameter("iSortingCols")==null?0:Integer.parseInt( request.getParameter("iSortingCols") );
			param.iSortColumnIndex = request.getParameter("iSortCol_0")==null?0:Integer.parseInt(request.getParameter("iSortCol_0"));
			param.sSortDirection = request.getParameter("sSortDir_0");
			return param;
		}else
			return null;
	}
	
}

