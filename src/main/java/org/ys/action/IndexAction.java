package org.ys.action;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.ys.model.Menu;
import org.ys.services.MenuService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller("/system/indexAction")
public class IndexAction extends DispatchAction {
	private MenuService menuService;
	
	@Autowired
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	public ActionForward index(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		//String parentMenuId = request.getParameter("parentMenuId");
		String parentMenuId = "ff8080814bab3360014bab3362590001";
		Set<Menu> allSubMenus = new HashSet<Menu>();
		if(StringUtils.isNotEmpty(parentMenuId)){
			allSubMenus = menuService.findAllSubMensByParentId(parentMenuId);
		}
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		//System.out.println(" ---------- " + gson.toJson(allSubMenus));
		request.setAttribute("allSubMenus", gson.toJson(allSubMenus));
		return mapping.findForward("index");
		
	}
}
