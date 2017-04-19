package cn.jcm.system.controller;

import cn.jcm.core.base.BaseController;
import cn.jcm.core.domain.CommonResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
@RequestMapping( "/" )
public class IndexController extends BaseController {

	@GetMapping
	public String toIndex( ModelMap modelMap ) {
		modelMap.put( "result", "changming.jiang" );
		return "index";
	}

	@GetMapping( "/platform" )
	@ResponseBody
	public CommonResult platform( HttpServletRequest request ) {
		CommonResult result = new CommonResult();
		result.setMessage( "获取用户浏览设备成功!" );
		Device device = DeviceUtils.getCurrentDevice( request );
		if ( device.isMobile() )
			result.setData( "手机" );
		if ( device.isNormal() )
			result.setData( "台式" );
		if ( device.isTablet() )
			result.setData( "平板" );
		return result;
	}
}
