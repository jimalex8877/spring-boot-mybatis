package cn.jcm.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * spring boot 启动类
 *
 * @author changming.jiang
 */
@SpringBootApplication
@ComponentScan("cn.jcm.*")
public class JcmSystemApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JcmSystemApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(JcmSystemApplication.class, args);
	}
}
