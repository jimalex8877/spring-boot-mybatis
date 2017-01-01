package cn.jcm.system.controller;

import cn.jcm.core.base.BaseController;
import cn.jcm.core.domain.CommonResult;
import cn.jcm.system.domain.User;
import cn.jcm.system.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

	@GetMapping("/show")
	public String show(ModelMap modelMap) {
		modelMap.put("users", userService.findAllUser());
		return "/user/show";
	}

	@GetMapping("/delUser/{id}")
	@ResponseBody
	public CommonResult delUser(@PathVariable("id") String id) {
		log.info("删除用户，ID：" + id);
		CommonResult result = new CommonResult("用户记录删除成功!");
		int i = userService.deleteUser(id);
		if (i <= 0) {
			result.setStatus(409);
			result.setMessage("用户记录删除失败!");
		}
		return result;
	}

	@GetMapping("/getAllUser")
	@ResponseBody
	public CommonResult getAllUser() {
		log.info("获取所有用户");
		CommonResult result = new CommonResult("获取所有用户记录成功!");
		result.setData(userService.findAllUser());
		return result;
	}

	@PostMapping("/saveUser")
	@ResponseBody
	public CommonResult saveUser(User user) {
		log.info("保存用户信息");
		CommonResult result = new CommonResult("保存用户信息成功!");
		int i = userService.saveUser(user);
		if (i <= 0) {
			result.setStatus(409);
			result.setMessage("保存用户信息失败!");
		}
		return result;
	}

	@PostMapping("/updateUser")
	@ResponseBody
	public CommonResult updateUser(User user) {
		log.info("修改用户信息");
		CommonResult result = new CommonResult("修改用户信息成功!");
		int i = userService.updateUser(user);
		if (i <= 0) {
			result.setStatus(409);
			result.setMessage("修改用户信息失败!");
		}
		return result;
	}
}
