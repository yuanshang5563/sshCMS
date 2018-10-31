package org.ys.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.ys.constant.MenuType;
import org.ys.model.Menu;
import org.ys.services.MenuService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller("/system/menuAction")
public class MenuAction extends DispatchAction {
	private MenuService menuService;
	
	@Autowired
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public ActionForward getSubTreeJsonData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Map<String,Object> maps = new HashMap<String,Object>();
		Gson gson = new Gson();
		out.print(gson.toJson(maps));
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward getMenuList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String parentMenuId = request.getParameter("parentMenuId");
		Set<Menu> allSubMenus = new HashSet<Menu>();
		if(StringUtils.isNotEmpty(parentMenuId)){
			allSubMenus = menuService.findAllSubMensByParentId(parentMenuId);
		}
		Gson gson = new Gson();
		//System.out.println(" ---------- " + gson.toJson(allSubMenus));
		request.setAttribute("allSubMenus", gson.toJson(allSubMenus));
		return mapping.findForward("menuList");
		
	}
	
	public ActionForward addMenuSave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String msg = "增加菜单成功！";
		boolean success = true;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Menu menu = new Menu();
			menu.setMenuName(request.getParameter("menuName"));
			String menuType = request.getParameter("menuType");
			if(StringUtils.isNotEmpty(menuType)){
				menu.setMenuType(MenuType.valueOf(menuType));
			}
			menu.setMenuUrl(request.getParameter("menuUrl"));
			String parentMenuId = request.getParameter("parentMenuId");
			if(StringUtils.isNotEmpty(parentMenuId)){
				Menu parentMenu = menuService.findMenuByMenuId(parentMenuId);
				menu.setParentMenu(parentMenu);
			}
			menu.setIcon(request.getParameter("icon"));
			if(StringUtils.isNotEmpty(request.getParameter("orderNum"))){
				menu.setOrderNum(Integer.parseInt(request.getParameter("orderNum").trim()));
			}
			menuService.addMenu(menu);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,增加菜单失败！ " + e.getMessage();
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		Gson gson = new Gson();
		out.print(gson.toJson(map));
		out.flush();
		out.close();
		return null;
	} 
	
	public ActionForward editMenuSave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String msg = "修改菜单成功！";
		boolean success = true;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Menu menu = new Menu();
			menu.setMenuId(request.getParameter("menuId"));
			menu.setMenuName(request.getParameter("menuName"));
			String menuType = request.getParameter("menuType");
			if(StringUtils.isNotEmpty(menuType)){
				menu.setMenuType(MenuType.valueOf(menuType));
			}
			menu.setMenuUrl(request.getParameter("menuUrl"));
			String parentMenuId = request.getParameter("parentMenuId");
			if(StringUtils.isNotEmpty(parentMenuId)){
				Menu parentMenu = menuService.findMenuByMenuId(parentMenuId);
				menu.setParentMenu(parentMenu);
			}
			menu.setIcon(request.getParameter("icon"));
			if(StringUtils.isNotEmpty(request.getParameter("orderNum"))){
				menu.setOrderNum(Integer.parseInt(request.getParameter("orderNum").trim()));
			}
			menuService.updateMenu(menu);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,修改菜单失败！ " + e.getMessage();
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		Gson gson = new Gson();
		out.print(gson.toJson(map));
		out.flush();
		out.close();
		return null;
	} 
	
	
	public ActionForward deleteMenu(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String msg = "删除菜单成功！";
		boolean success = true;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String menuId = request.getParameter("menuId");
			menuService.deleteMenuById(menuId);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,删除菜单失败！ " + e.getMessage();
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		Gson gson = new Gson();
		out.print(gson.toJson(map));
		out.flush();
		out.close();
		return null;
	} 
	
	public ActionForward ajaxFindMenuById(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String msg = "查找菜单成功！";
		boolean success = true;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Menu menu = null;
		try {
			String menuId = request.getParameter("menuId");
			menu = menuService.findMenuByMenuId(menuId);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,查找菜单失败！ " + e.getMessage();
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(success){
			map.put("data", menu);
		}else{
			map.put("errorMessage", msg);
		}
		map.put("success", success);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		out.print(gson.toJson(map));
		out.flush();
		out.close();
		return null;
	} 
	
	public ActionForward getMenuListJsonData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		StringBuffer hql = new StringBuffer("from Menu where 1=1");
		if(StringUtils.isNotEmpty(request.getParameter("menuName"))){
			hql.append(" and menuName like '%").append(request.getParameter("menuName").trim()).append("%'");
		}
		if(StringUtils.isNotEmpty(request.getParameter("menuType"))){
			hql.append(" and menuType = '").append(request.getParameter("menuType").trim()).append("'");
		}
		List<Menu> menus = null;
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(limit)){
			menus = menuService.pageMenuList(hql.toString(), Integer.parseInt(start.trim()), Integer.parseInt(limit.trim()));
		}else{
			menus = new ArrayList<Menu>();
		}
		long count = menuService.pageMenuListCount("select count(*) " + hql.toString());
		PrintWriter out = response.getWriter();
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("count", count);
		maps.put("root", menus);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		out.print(gson.toJson(maps));
		out.flush();
		out.close();
		return null;
	}
}
