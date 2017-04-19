package cn.jcm.core.util;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

@Log4j2
public class PropertiesUtil {
	private static final String MESSAGE_CONFIG = "message.properties";
	private static Properties properties = new Properties();
	private static long lastModifed = 0;// 文件最后修改时间
	private static long lastReadTime = 0;// 最后读取时间

	/**
	 * 需要定时同步properties文件内容时使用
	 *
	 * @return 设定文件
	 * @Title: getPropertyValueSync
	 * @Description: 读取之前先同步，此接口使用过多会有性能问题，确有必要才可使用
	 */
	public static String getPropertyValueSync( String key ) {
		initProperties();
		String value = StringUtils.trim( ( String ) properties.get( key ) );
		return value == null ? "" : value;
	}

	/**
	 * 不需要同步properties文件内容时使用
	 *
	 * @return 设定文件
	 * @Title: getPropertyValue
	 * @Description: 与Sync接口的区别在于，直接读取properties文件
	 */
	public static String getPropertyValue( String key ) {
		if ( lastReadTime == 0 ) {
			initProperties();
		}
		String value = StringUtils.trim( ( String ) properties.get( key ) );
		return value == null ? "" : value;
	}

	private static void initProperties() {
		if ( System.currentTimeMillis() - lastReadTime > 60000 ) {// 间隔60秒检查文件是否被修改(修改配置项最多60秒后生效)
			synchronized ( PropertiesUtil.class ) {
				if ( System.currentTimeMillis() - lastReadTime > 60000 ) {
					FileInputStream fis = null;
					try {
						ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
						File        file        = new File( classLoader.getResource( MESSAGE_CONFIG ).getPath() );
						if ( file.lastModified() > lastModifed ) {
							lastModifed = file.lastModified();
							fis = new FileInputStream( file );
							properties = new Properties();
							properties.load( fis ); // 从输入流中读取属性文件的内容
						}

						lastReadTime = System.currentTimeMillis();
					} catch ( FileNotFoundException e ) {
						log.error( "无法读取文件" + MESSAGE_CONFIG, e );
					} catch ( IOException e ) {
						log.error( "无法读取文件" + MESSAGE_CONFIG, e );
					} catch ( Exception e ) {
						log.error( "读取配置文件异常：" + MESSAGE_CONFIG, e );
					} finally {
						if ( fis != null )
							try {
								fis.close();
								fis = null;
							} catch ( IOException e ) {
								e.printStackTrace();
							}
					}
				}
			}
		}
	}

	public static String getPropertiesFileName() {
		return MESSAGE_CONFIG;
	}

	public static String formatMsg( String key, Object[] format ) {
		return MessageFormat.format( getPropertyValue( key ), format );
	}

	public static String formatMsgSync( String key, Object[] format ) {
		return MessageFormat.format( getPropertyValueSync( key ), format );
	}
}