package org.ys.services.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ys.dao.UserDAO;
import org.ys.model.User;
import org.ys.services.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	private UserDAO userDAO;

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void addUser(User user) throws Exception {
		if(null != user){
			userDAO.addUser(user);
		}
		
	}

	@Override
	public void deleteUserById(String userId) throws Exception {
		if(StringUtils.isNotEmpty(userId)){
			this.userDAO.deleteUserById(userId);
		}
	}

	@Override
	public void updateUser(User user) throws Exception {
		if(null != user){
			this.userDAO.updateUser(user);
		}
	}

	@Override
	public List<User> pageUserList(String hql, int startNum, int pageSize)throws Exception {
		if(StringUtils.isEmpty(hql)){
			return null;
		}
		return userDAO.pageUserList(hql, startNum, pageSize);
	}

	@Override
	public long pageUserListCount(String hql) throws Exception {
		if(StringUtils.isEmpty(hql)){
			return 0l;
		}
		return userDAO.pageUserListCount(hql);
	}

	@Override
	public User findUserById(String userId) throws Exception {
		if(StringUtils.isEmpty(userId)){
			return null;
		}
		return userDAO.findUserById(userId);
	}
}
