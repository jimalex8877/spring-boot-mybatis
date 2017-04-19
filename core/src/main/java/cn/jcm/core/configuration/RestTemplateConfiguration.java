package cn.jcm.core.configuration;

import cn.jcm.core.configuration.http.CustomClientHttpRequestFactory;
import cn.jcm.core.configuration.http.TextMappingJackson2HttpMessageConverter;
import cn.jcm.core.interceptor.HttpRequestLoggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RestTemplate 配置.
 *
 * @author changming.jiang
 */
@Configuration
public class RestTemplateConfiguration {

	private static final int DEFULT_TIMEOUT = 60 * 1000;

	private ClientHttpRequestFactory globalClientHttpRequestFactory() {
		CustomClientHttpRequestFactory simpleClientHttpRequestFactory = new CustomClientHttpRequestFactory();
		simpleClientHttpRequestFactory.setReadTimeout( DEFULT_TIMEOUT );
		simpleClientHttpRequestFactory.setConnectTimeout( DEFULT_TIMEOUT );
		return simpleClientHttpRequestFactory;
	}

	@Bean
	public RestTemplate globalRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory( globalClientHttpRequestFactory() );

		List<HttpMessageConverter<?>> defaultHttpMessageConverters = restTemplate.getMessageConverters();
		List<HttpMessageConverter<?>> newHttpMessageConverters = new ArrayList<HttpMessageConverter<?>>();
		newHttpMessageConverters.addAll( defaultHttpMessageConverters );
		newHttpMessageConverters.add( new TextMappingJackson2HttpMessageConverter() );
		restTemplate.setMessageConverters( newHttpMessageConverters );

		List<ClientHttpRequestInterceptor> clientHttpRequestInterceptors = new ArrayList<ClientHttpRequestInterceptor>();
		clientHttpRequestInterceptors.add( new HttpRequestLoggerInterceptor() );
		restTemplate.setInterceptors( clientHttpRequestInterceptors );

		restTemplate.setErrorHandler( new ResponseErrorHandler() {
			@Override
			public boolean hasError( ClientHttpResponse response ) throws IOException {
				return new DefaultResponseErrorHandler().hasError( response );
			}

			@Override
			public void handleError( ClientHttpResponse response ) throws IOException {
				if ( response.getStatusCode() != HttpStatus.NOT_FOUND ) {
					new DefaultResponseErrorHandler().handleError( response );
				}
			}
		} );
		return restTemplate;
	}


}
