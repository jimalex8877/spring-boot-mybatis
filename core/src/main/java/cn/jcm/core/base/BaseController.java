package cn.jcm.core.base;

import cn.jcm.core.interceptor.CustomBigDecimalEditor;
import cn.jcm.core.interceptor.CustomStringEscapeEditor;
import cn.jcm.core.interceptor.CustomTimestampEditor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ControllerAdvice 配置.
 *
 * @author changming.jiang
 */
@ControllerAdvice
@Log4j2
public abstract class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Timestamp.class, new CustomTimestampEditor
                (dateTimeFormat, true));
        binder.registerCustomEditor(BigDecimal.class, new CustomBigDecimalEditor());

        binder.registerCustomEditor(String.class, new CustomStringEscapeEditor(true));// html转义
    }

    @ExceptionHandler(Exception.class)
    public void processException(Exception e, ModelMap modelMap) {
        log.error(e.getMessage(), e);
    }
}
