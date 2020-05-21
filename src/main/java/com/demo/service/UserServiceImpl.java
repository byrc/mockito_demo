package com.demo.service;

import com.demo.dao.JdbcQueryManager;
import com.demo.model.PageParam;
import com.demo.model.User;
import com.demo.model.UserDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl {

	private JdbcQueryManager jdbcQueryManager;;

	public User findById(Long id) {
		User user = new User();
		user.setId(id);
		return jdbcQueryManager.queryForObject(user, User.class).orElse(null);
	}

	public List<User> findByName(long pageNo, long pageSize, String name) {
		User user = new User();
		user.setName(name);
		PageParam page = PageParam.create(pageNo, pageSize);
		return jdbcQueryManager.queryForPageList(user, User.class, page);
	}

	public List<UserDTO> findByUser(User user) {
		return jdbcQueryManager.queryForList(user, User.class, User::convert);
	}

	public List<Map> findByNameAndIdCard(String name, String idCard) {
		Map map = new HashMap();
		map.put("name", name);
		map.put("idCard", idCard);
		return jdbcQueryManager.queryForMap(map);
	}

	public UserDTO testStatic(User user){
		return User.convert(user);
	}
}
