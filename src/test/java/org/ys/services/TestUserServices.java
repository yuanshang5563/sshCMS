package org.ys.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ys.model.User;

import com.google.gson.Gson;

public class TestUserServices {
	private ApplicationContext ctx;
	@Before
	public void setUp() throws Exception{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void testAddUser()throws Exception{
		UserService userService = (UserService) ctx.getBean("userService");
		User user = new User();
		user.setAge(25);
		user.setAddress("test 1231231231");
		user.setBirthday(new Date(System.currentTimeMillis()));
		user.setCommentContext("test comment");
		user.setEmail("test email");
		user.setPassword("test pass");
		user.setUserName("testUser");
		userService.addUser(user);
		System.out.println("---------");
	}
	
	@Test
	public void testPage()throws Exception{
		String hql = "from User where 1=1";
		UserService userService = (UserService) ctx.getBean("userService");
		List<User> users = userService.pageUserList(hql, 0, 50);
		if(null != users && users.size() > 0){
			for (User user : users) {
				System.out.println("--------- " + user.getUserName());
			}
			Map<String,List<User>> maps = new HashMap<String,List<User>>();
			maps.put("root", new ArrayList<User>());
			Gson gson = new Gson();
			System.out.println("------------gson-------- " + gson.toJson(users));
			System.out.println("------------gson2-------- " + gson.toJson(maps));
		}
	}
	
	@Test
	public void testPageCount()throws Exception{
		String hql = "select count(*) from User where 1=1";
		UserService userService = (UserService) ctx.getBean("userService");
		long count = userService.pageUserListCount(hql);
		System.out.println("---------------- " + count);
	}
}
