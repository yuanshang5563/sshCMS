package org.ys.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ys.constant.MenuType;
import org.ys.dao.MenuDAO;
import org.ys.model.Menu;

@Component("menuDAO")
public class MenuDAOImpl implements MenuDAO {
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getSession() throws Exception{
		Session session = sessionFactory.getCurrentSession();
		//Session session = null;
		if(null == session){
			session = sessionFactory.openSession();
		}
		return session;
	}

	@Override
	public void addMenu(Menu menu) throws Exception {
		Session session = getSession();
		session.save(menu);
	}

	@Override
	public void deleteMenuById(String menuId) throws Exception {
		String hql = "delete from Menu where menuId=?";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setString(0, menuId);
		query.executeUpdate();
	}

	@Override
	public void updateMenu(Menu menu) throws Exception {
		Session session = getSession();
		session.update(menu);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> pageMenuList(String hql, int startNum, int pageSize)throws Exception {
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(startNum);
		query.setMaxResults(pageSize);
		List<Menu> menus = query.list();
		return menus;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public long pageMenuListCount(String hql) throws Exception {
		Session session = getSession();
		Query query = session.createQuery(hql);
		List menuCount = query.list();
		return (Long) menuCount.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Menu findMenuByMenuId(String menuId) throws Exception {
		String hql = "from Menu where menuId=?";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setString(0, menuId);
		List<Menu> menus = (List<Menu>)query.list();
		if(null != menus && menus.size() > 0){
			return menus.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> findMensByParentId(String parentId) throws Exception {
		String hql = "from Menu where parentMenu.menuId=?";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setString(0, parentId);
		List<Menu> menus = query.list();
		return menus;
	}
	
	@Override
	public Set<Menu> findAllSubMensByParentId(String parentId,Set<Menu> allSubMenus) throws Exception {
		List<Menu> menus = findMensByParentId(parentId);
		if(null != menus && menus.size() > 0){
			if(null != allSubMenus){
				allSubMenus.addAll(menus);
			}
			for (Menu menu : menus) {
				if(MenuType.TREENODE.equals(menu.getMenuType())){
					findAllSubMensByParentId(menu.getMenuId(),allSubMenus);
				}
			}
		}
		return allSubMenus;
	}
}
