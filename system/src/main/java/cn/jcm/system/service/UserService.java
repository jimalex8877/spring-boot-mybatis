package cn.jcm.system.service;

import cn.jcm.system.domain.User;

import java.util.List;

public interface UserService {
	User findUserById(String id);

	List<User> findAllUser();

	int saveUser(User user);

	int updateUser(User user);

	int deleteUser(String id);
}
