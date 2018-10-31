package org.ys.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.ys.action.form.UserForm;
import org.ys.constant.SexType;
import org.ys.model.User;
import org.ys.services.UserService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller("/userAction")
public class UserAction extends DispatchAction  {
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ActionForward addUser(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		return mapping.findForward("addUser");
	}
	
	public ActionForward editUser(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		return mapping.findForward("editUser");
	}
	
	public ActionForward getUserList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		return mapping.findForward("userList");
	}
	
	public ActionForward addUserSave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String msg = "增加用户成功！";
		boolean success = true;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			UserForm userForm = (UserForm) form;
			User user = new User();
			user.setAddress(userForm.getAddress());
			user.setAge(userForm.getAge());
			user.setBirthday(userForm.getBirthday());
			user.setCommentContext(userForm.getCommentContext());
			user.setEmail(userForm.getEmail());
			user.setPassword(userForm.getPassword());
			user.setUserName(userForm.getUserName());
			if(StringUtils.isNotEmpty(userForm.getSex())){
				user.setSex(SexType.valueOf(userForm.getSex()));
			}
			userService.addUser(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,增加用户失败！ " + e.getMessage();
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
	
	public ActionForward editUserSave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String msg = "修改用户成功！";
		boolean success = true;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			UserForm userForm = (UserForm) form;
			User user = new User();
			user.setUserId(userForm.getUserId());
			user.setAddress(userForm.getAddress());
			user.setAge(userForm.getAge());
			user.setBirthday(userForm.getBirthday());
			user.setCommentContext(userForm.getCommentContext());
			user.setEmail(userForm.getEmail());
			user.setPassword(userForm.getPassword());
			user.setUserName(userForm.getUserName());
			if(StringUtils.isNotEmpty(userForm.getSex())){
				user.setSex(SexType.valueOf(userForm.getSex()));
			}
			userService.updateUser(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,修改用户失败！ " + e.getMessage();
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
	
	
	public ActionForward deleteUser(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String msg = "删除用户成功！";
		boolean success = true;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String userId = request.getParameter("userId");
			userService.deleteUserById(userId);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,删除用户失败！ " + e.getMessage();
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
	
	public ActionForward ajaxFindUserById(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String msg = "查找用户成功！";
		boolean success = true;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		User user = null;
		try {
			String userId = request.getParameter("userId");
			user = userService.findUserById(userId);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,查找用户失败！ " + e.getMessage();
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(success){
			map.put("data", user);
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
	
	public ActionForward getUserListJsonData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		StringBuffer hql = new StringBuffer("from User where 1=1");
		if(StringUtils.isNotEmpty(request.getParameter("userName"))){
			hql.append(" and userName like '%");
			hql.append(request.getParameter("userName").trim());
			hql.append("%'");
		}
		List<User> users = null;
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(limit)){
			users = userService.pageUserList(hql.toString(), Integer.parseInt(start.trim()), Integer.parseInt(limit.trim()));
		}else{
			users = new ArrayList<User>();
		}
		long count = userService.pageUserListCount("select count(*) " + hql.toString());
		PrintWriter out = response.getWriter();
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("count", count);
		maps.put("root", users);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//System.out.println("- " + gson.toJson(maps));
		out.print(gson.toJson(maps));
		out.flush();
		out.close();
		return null;
	}
}
