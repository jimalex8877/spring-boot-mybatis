package cn.jcm.core.interceptor;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Property editor for <code>java.sql.Timestamp</code>,<br>
 * supporting a custom <code>java.text.DateFormat</code>.
 *
 * @author changming.jiang
 * @version 1.0
 * @ClassName: CustomTimestampEditor
 * @date 2015年5月18日 下午1:50:26
 * @see org.springframework.beans.propertyeditors.CustomDateEditor
 */
public class CustomTimestampEditor extends PropertyEditorSupport {

	private final SimpleDateFormat dateFormat;

	private final boolean allowEmpty;

	private final int exactDateLength;

	public CustomTimestampEditor( SimpleDateFormat dateFormat, boolean allowEmpty ) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = -1;
	}

	public CustomTimestampEditor( SimpleDateFormat dateFormat, boolean allowEmpty, int exactDateLength ) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = exactDateLength;
	}

	public String getAsText() {
		Timestamp value = ( Timestamp ) getValue();
		return ( ( value != null ) ? this.dateFormat.format( value ) : "" );
	}

	public void setAsText( String text ) throws IllegalArgumentException {
		if ( this.allowEmpty && StringUtils.isEmpty( text ) ) {
			setValue( null );
		} else {
			if ( ( text != null ) && ( this.exactDateLength >= 0 ) && ( text.length() != this.exactDateLength ) ) {
				throw new IllegalArgumentException( "Could not parse date: it is not exactly" + this.exactDateLength + "characters long" );
			}
			try {
				if ( text != null )
					setValue( new Timestamp( this.dateFormat.parse( text ).getTime() ) );
			} catch ( ParseException ex ) {
				throw new IllegalArgumentException( "Could not parse date: " + ex.getMessage(), ex );
			}
		}
	}
}
