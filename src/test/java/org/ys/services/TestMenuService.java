package org.ys.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ys.constant.MenuType;
import org.ys.model.Menu;
import org.ys.model.User;

import com.google.gson.Gson;

public class TestMenuService {
	private ApplicationContext ctx;
	private MenuService menuService;
	@Before
	public void setUp() throws Exception{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		menuService = (MenuService) ctx.getBean("menuService");
	}
	
	@Test
	public void testAddMenu()throws Exception{
		Menu queryMenu = menuService.findMenuByMenuId("ff8080814bab35ed014bab35eefd0001");
		Menu menu = new Menu();
		menu.setMenuName("用户列表");
		menu.setMenuUrl("userAction.do?method=getUserList");
		menu.setMenuType(MenuType.TREELEAF);
		//menu.setMenuType(MenuType.TREENODE);
		menu.setParentMenu(queryMenu);
		menuService.addMenu(menu);
		System.out.println("---------");
	}
	
	@Test
	public void testFindMenu() throws Exception{
		String parentId = "ff8080814bab3360014bab3362590001";
		Set<Menu> menus = new HashSet<Menu>();
		Set<Menu> allMenus = menuService.findAllSubMensByParentId(parentId);
		if(null != allMenus && allMenus.size() > 0){
			for (Menu menu : allMenus) {
				System.out.println("---------- " + menu.getMenuName());
			}
		}
	}
}
