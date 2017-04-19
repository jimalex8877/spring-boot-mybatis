package cn.jcm.core.interceptor;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * 类名称：StringEscapeEditor<br>
 * 类描述：字符串转义<br>
 * 创建人：changming.jiang<br>
 * 创建时间：2016年12月14日 下午13:39:02<br>
 *
 * @version v1.0
 */
public class CustomStringEscapeEditor extends PropertyEditorSupport {
	private boolean escape;

	/**
	 * 创建一个新的实例 StringEscapeEditor.
	 */
	public CustomStringEscapeEditor() {
		super();
	}

	/**
	 * 创建一个新的实例 StringEscapeEditor.
	 */
	public CustomStringEscapeEditor( boolean escape ) {
		super();
		this.escape = escape;
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return value != null ? value.toString() : "";
	}

	@Override
	public void setAsText( String text ) {
		if ( StringUtils.isEmpty( text ) ) {
			setValue( text );
		} else {
			String value = text;
			if ( escape ) {
				value = StringEscapeUtils.escapeHtml4( value );
			}
			setValue( value );
		}
	}
}
