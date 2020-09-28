package org.moloshnikov.userdataservice.web;


import org.moloshnikov.userdataservice.util.ValidationUtil;
import org.moloshnikov.userdataservice.util.exception.IllegalRequestDataException;
import org.moloshnikov.userdataservice.util.exception.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(IllegalRequestDataException.class)
    public Info illegalError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(e);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Info bindValidationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();
        String[] details = result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toArray(String[]::new);

        return logAndGetErrorInfo(e, details);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Info handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(e);
    }

    private Info logAndGetErrorInfo(Exception e, String... details) {
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, e);
        return new Info(false,
                details.length != 0 ? details : new String[]{ValidationUtil.getMessage(rootCause)});
    }
}