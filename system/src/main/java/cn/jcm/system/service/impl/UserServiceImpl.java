package cn.jcm.system.service.impl;

import cn.jcm.system.dao.UserDao;
import cn.jcm.system.domain.User;
import cn.jcm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;


	@Override
	public User findUserById(String id) {
		return userDao.findUserById(id);
	}

	@Override
	public List<User> findAllUser() {
		return userDao.findAllUser();
	}

	@Override
	public int saveUser(User user) {
		return userDao.saveUser(user);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public int deleteUser(String id) {
		return userDao.deleteUser(id);
	}
}
