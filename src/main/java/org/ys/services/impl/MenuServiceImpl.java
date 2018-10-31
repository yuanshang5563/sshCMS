package org.ys.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ys.dao.MenuDAO;
import org.ys.model.Menu;
import org.ys.services.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
	private MenuDAO menuDAO;

	@Autowired
	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	@Override
	public void addMenu(Menu menu) throws Exception {
		if(null != menu){
			menuDAO.addMenu(menu);
		}
	}

	@Override
	public void deleteMenuById(String menuId) throws Exception {
		if(StringUtils.isNotEmpty(menuId)){
			this.menuDAO.deleteMenuById(menuId.trim());
		}
	}

	@Override
	public void updateMenu(Menu menu) throws Exception {
		if(null != menu){
			this.menuDAO.updateMenu(menu);
		}
	}

	@Override
	public List<Menu> pageMenuList(String hql, int startNum, int pageSize)throws Exception {
		if(StringUtils.isEmpty(hql)){
			return null;
		}
		return menuDAO.pageMenuList(hql, startNum, pageSize);
	}

	@Override
	public long pageMenuListCount(String hql) throws Exception {
		if(StringUtils.isEmpty(hql)){
			return 0l;
		}
		return menuDAO.pageMenuListCount(hql);
	}

	@Override
	public Menu findMenuByMenuId(String menuId) throws Exception {
		if(StringUtils.isEmpty(menuId)){
			return null;
		}
		return menuDAO.findMenuByMenuId(menuId);
	}
	
	public List<Menu> findMensByParentId(String parentId) throws Exception{
		if(StringUtils.isNotEmpty(parentId)){
			return null;
		}
		return menuDAO.findMensByParentId(parentId);
	}

	@Override
	public Set<Menu> findAllSubMensByParentId(String parentId) throws Exception {
		if(StringUtils.isEmpty(parentId)){
			return null;
		}
		Set<Menu> allSubMenus = new HashSet<Menu>();
		return menuDAO.findAllSubMensByParentId(parentId, allSubMenus);
	}
}
