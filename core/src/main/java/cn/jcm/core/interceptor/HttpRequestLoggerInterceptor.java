package cn.jcm.core.interceptor;

import cn.jcm.core.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

/**
 * RestTemplate 配置http请求日志
 *
 * @author changming.jiang
 */
@Log4j2
public class HttpRequestLoggerInterceptor implements ClientHttpRequestInterceptor {

	/**
	 * 实体内容转字符串最大长度限制，默认为 1024 个字。
	 */
	public static final int BODY_CONTENT_TO_STRING_MAX_LENGTH = 400;

	@Override
	public ClientHttpResponse intercept( HttpRequest request, byte[] requestBody, ClientHttpRequestExecution execution ) throws IOException {
		ClientHttpResponse clientHttpResponse = null;
		try {
			clientHttpResponse = execution.execute( request, requestBody );
		} catch ( IOException ex ) {
			if ( log.isErrorEnabled() )
				log.error( ex.getMessage(), ex );
		}
		if ( clientHttpResponse != null ) {
			this.doInterceptAfter( request, requestBody, clientHttpResponse );
		}
		return clientHttpResponse;
	}

	private void doInterceptAfter( HttpRequest request, byte[] requestBody, ClientHttpResponse clientHttpResponse ) throws IOException {
		URI        requestURI         = request.getURI();
		HttpMethod requestHttpMethod  = request.getMethod();
		HttpStatus responseHttpStatus = clientHttpResponse.getStatusCode();
		if ( log.isInfoEnabled() )
			log.info( "拦截http请求，请求 URI ：{}，请求方法：{}，请求实体长度：{}，请求实体：{}，响应状态：{}", requestURI, requestHttpMethod.name(), bodyContentToStringLength( requestBody ), bodyContentToString( requestBody, BODY_CONTENT_TO_STRING_MAX_LENGTH ), new StringBuilder().append( responseHttpStatus.name() ).append( "/" ).append( responseHttpStatus.value() ).toString() );
	}

	/**
	 * 读取 byte[] 数组转换为字符串，如果给定了指定读取长度，则截取指定长度字符串，如果数组为空或者长度为 0 ，则返回空对象。
	 *
	 * @param body      byte[] 数组
	 * @param maxLength 截取指定字符串长度
	 * @return 返回读取 byte[] 数组转换为字符串
	 */
	private String bodyContentToString( byte[] body, int maxLength ) {
		String bodyContentToString;
		String tempBodyContentToString = null;
		if ( bodyContentToStringLength( body ) > 0 ) {
			try {
				tempBodyContentToString = new String( body, "UTF-8" );
			} catch ( UnsupportedEncodingException e ) {
				tempBodyContentToString = new String( body );
			}
		}
		if ( maxLength > 0 && ( !StringUtils.isEmpty( tempBodyContentToString ) ) ) {
			bodyContentToString = tempBodyContentToString.substring( 0, Math.min( maxLength, tempBodyContentToString.length() ) );
		} else {
			bodyContentToString = tempBodyContentToString;
		}
		return bodyContentToString;
	}

	/**
	 * 读取 byte[] 数组长度，如果数组为空则返回 0
	 *
	 * @param body byte[] 数组
	 * @return 返回 byte[] 数组长度，如果数组为空则返回 0
	 */
	private int bodyContentToStringLength( byte[] body ) {
		int length = 0;
		if ( null != body ) {
			length = body.length;
		}
		return length;
	}
}
