package org.ys.services;

import java.util.List;
import java.util.Set;

import org.ys.model.Menu;

public interface MenuService {
	public void addMenu(Menu menu) throws Exception;
	public void deleteMenuById(String menuId) throws Exception;
	public void updateMenu(Menu menu) throws Exception;
	public List<Menu> pageMenuList(String hql,int startNum,int pageSize) throws Exception;
	public long pageMenuListCount(String hql) throws Exception;
	public Menu findMenuByMenuId(String menuId)throws Exception;
	public Set<Menu> findAllSubMensByParentId(String parentId) throws Exception;
}
