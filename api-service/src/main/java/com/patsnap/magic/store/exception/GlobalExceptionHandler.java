package com.patsnap.magic.store.exception;

import com.patsnap.magic.store.common.Constant;
import com.patsnap.magic.store.common.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleException(Exception e) {
        LOGGER.error("global exception messages:{}", e);
        ServerResponse serverResponse;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            StringBuilder stringBuilder = new StringBuilder();
            List<ObjectError> ls = methodArgumentNotValidException.getBindingResult().getAllErrors();
            for (int i = 0; i < ls.size(); i++) {
                stringBuilder.append(ls.get(i).getDefaultMessage());
            }
            serverResponse = ServerResponse.createByErrorCodeMessage(HttpStatus.BAD_REQUEST.value(), stringBuilder.toString());
        } else if (e instanceof UsernameNotFoundException) {
            serverResponse = ServerResponse.createByErrorCodeMessage(HttpStatus.BAD_REQUEST.value(), messageSource.getMessage(Constant.PERMISSION_DENIED, null, Locale.getDefault()));
        } else {
            serverResponse = ServerResponse.createByErrorCodeMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return serverResponse;
    }
}
