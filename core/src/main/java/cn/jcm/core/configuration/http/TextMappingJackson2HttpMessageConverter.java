package cn.jcm.core.configuration.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * text/html JSON 字符串转换集合类型子类的消息转换器，基于响应结果媒体类型：text/html;charset=utf-8 | text/html
 *
 * @author changming.jiang
 */
public class TextMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	/**
	 * 媒体类型字符串：text/html;charset=utf-8
	 */
	public static final String TEXT_HTML_UTF_8_VALUE = new StringBuilder( MediaType.TEXT_HTML_VALUE ).append( ";charset=utf-8" ).toString();

	/**
	 * 媒体类型：text/html;charset=utf-8
	 */
	public static final MediaType TEXT_HTML_UTF_8 = MediaType.parseMediaType( TEXT_HTML_UTF_8_VALUE );

	public TextMappingJackson2HttpMessageConverter() {
		this( Jackson2ObjectMapperBuilder.json().build() );
	}

	public TextMappingJackson2HttpMessageConverter( ObjectMapper objectMapper ) {
		super( objectMapper );
		List<MediaType> supportedMediaTypes       = this.getSupportedMediaTypes();
		List<MediaType> customSupportedMediaTypes = new ArrayList<MediaType>();
		customSupportedMediaTypes.addAll( supportedMediaTypes );
		customSupportedMediaTypes.add( MediaType.TEXT_PLAIN );
		customSupportedMediaTypes.add( TEXT_HTML_UTF_8 );
		this.setSupportedMediaTypes( customSupportedMediaTypes );
	}

	@Override
	public boolean canRead( Class<?> clazz, MediaType mediaType ) {
		boolean isCanRead = canRead( mediaType ); //判断类型支持，如果被支持则直接返回 true ，实现转换。
		if ( !isCanRead ) {
			return super.canRead( clazz, mediaType );
		}
		return true;
	}

}