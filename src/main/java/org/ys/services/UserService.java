package org.ys.services;

import java.util.List;

import org.ys.model.User;

public interface UserService {
	public void addUser(User user) throws Exception;
	public void deleteUserById(String userId) throws Exception;
	public void updateUser(User user) throws Exception;
	public User findUserById(String userId) throws Exception;
	public List<User> pageUserList(String hql,int startNum,int pageSize) throws Exception;
	public long pageUserListCount(String hql) throws Exception;
}
