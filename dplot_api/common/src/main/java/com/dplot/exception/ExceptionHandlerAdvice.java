package com.dplot.exception;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.dplot.common.Response;
import com.dplot.common.Status;

@ControllerAdvice
public class ExceptionHandlerAdvice {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
	
	@ExceptionHandler(DataAccessException.class)
	@ResponseBody
    public Response dataAccessException(Exception e) {
		logger.debug("DataAccessException Called");
		logger.error("", e);
		return new Response(Status.FAIL);
	}
	
	@ExceptionHandler(SQLException.class)
	@ResponseBody
    public Response sqlException(Exception e) {
		logger.debug("sqlException Called");
		logger.error("", e);
		return new Response(Status.FAIL);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
    public Response noHandlerFoundException(Exception e) {
		logger.debug("NoHandlerFoundException Called");
		logger.error("", e);
		return new Response(Status.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(BizException.class)
	@ResponseBody
    public Response bizException(Exception e) {
		logger.debug("BizException Called");
		logger.debug("", e);
		return new Response(Status.PROC_FAIL, e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
    public Response handleException(Exception e) {
		logger.debug("Exception Called");
		logger.error("", e);
		return new Response(Status.FAIL);
	}
	
	@ExceptionHandler(CustomException.class)
	@ResponseBody
    public Response customException(CustomException e) {
		logger.debug("CustomException Called");
		logger.error("", e);
		return new Response(Status.FAIL, e.getMessage());
	}
	
}
