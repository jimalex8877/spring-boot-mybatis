package cn.jcm.core.configuration;

import cn.jcm.core.util.PropertiesUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * RestTemplate 配置.
 *
 * @author changming.jiang
 */
@Configuration
public class ScheduledExecutorConfiguration {
	@Bean
	public ScheduledExecutorService globalScheduledExecutorService() {
		return Executors.newScheduledThreadPool( Integer.valueOf( PropertiesUtil.getPropertyValue( "taskScheduledThreadpool" ) ) );
	}
}
