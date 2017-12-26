package com.patsnap.magic.store.exception;

import com.patsnap.magic.store.common.ServerResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleException(Exception e){
        LOGGER.error("global exception messages:{}", e);
        ServerResponse serverResponse;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            StringBuilder stringBuilder = new StringBuilder();
            List<ObjectError> ls = methodArgumentNotValidException.getBindingResult().getAllErrors();
            for (int i = 0; i < ls.size(); i++) {
                stringBuilder.append(ls.get(i).getDefaultMessage());
            }
            serverResponse = ServerResponse.createByErrorCodeMessage(HttpStatus.BAD_REQUEST.value(),stringBuilder.toString());
        } else {
            serverResponse = ServerResponse.createByErrorCodeMessage(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.toString());
         }
         return serverResponse;
    }
}
