package com.formulaone.controller.errors;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.formulaone.controller.exceptions.EntityNotFoundException;
import com.formulaone.controller.exceptions.MerchantNotFoundException;
import com.formulaone.controller.exceptions.UserNotFoundException;

/**
 * Controller advice to translate the server side exceptions into json
 * structures.
 */
@ControllerAdvice
public class RestErrorHandler {

	// TODO

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final MessageSource messageSource;

	@Autowired
	public RestErrorHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public RestErrorInfo handleEntityNotFound(EntityNotFoundException ex) {
		log.info("Processing EntityNotFoundException ...");
		return new RestErrorInfo("", ErrorConstants.ERR_ENTITY_NOT_FOUND,
				ex.getErrorMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public RestErrorInfo handleMethodNotAllowed(
			HttpRequestMethodNotSupportedException ex) {
		log.debug("Processing HttpRequestMethodNotSupportedException ...");
		return new RestErrorInfo("", ErrorConstants.ERR_METHOD_NOT_SUPPORTED,
				ex.getMessage());

	}

	@ExceptionHandler(ClientAbortException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public RestErrorInfo handleNotAuthorized(ClientAbortException ex) {
		log.debug("Processing ClientAbortException ...");
		return new RestErrorInfo("", ErrorConstants.ERR_ACCESS_DENIED,
				"Credentials not correct.");

	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RestErrorInfo handleConstraintViolation(
			ConstraintViolationException ex) {
		log.debug("Processing ConstraintViolationException ...");

		ConstraintViolation<?> next = ex.getConstraintViolations().iterator()
				.next();
		StringBuilder sb = new StringBuilder();
		sb.append("Field: ").append(next.getPropertyPath()).append(", ");
		sb.append("Value: ").append(next.getInvalidValue()).append(", ");
		sb.append("Message: ").append(next.getMessage());
		return new RestErrorInfo("", ErrorConstants.ERR_VALIDATION,
				sb.toString());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public RestErrorInfo handleAccessDenied(AccessDeniedException ex) {

		log.debug("Processing AccessDeniedException ...");
		return new RestErrorInfo("", ErrorConstants.ERR_ACCESS_DENIED,
				ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RestErrorInfo handleValidationError(
			MethodArgumentNotValidException ex) {

		List<String> errors = new ArrayList<String>(
				ex.getBindingResult().getAllErrors().size());
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		StringBuilder sb;

		for (FieldError fieldError : fieldErrors) {
			sb = new StringBuilder();
			sb.append("Field: ").append(fieldError.getField()).append(", ");
			sb.append("Value: ").append(fieldError.getRejectedValue())
					.append(", ");
			sb.append("Message: ").append(fieldError.getDefaultMessage());
			errors.add(sb.toString());
		}

		List<ObjectError> globalErrors = ex.getBindingResult()
				.getGlobalErrors();

		for (ObjectError objectError : globalErrors) {
			sb = new StringBuilder();
			sb.append("Object: ").append(objectError.getObjectName())
					.append(", ");
			sb.append("Code: ").append(objectError.getCode()).append(", ");
			sb.append("Message: ").append(objectError.getDefaultMessage());
			errors.add(sb.toString());
		}

		return new RestErrorInfo("", ErrorConstants.ERR_VALIDATION, errors);
	}

	@ExceptionHandler(ConcurrencyFailureException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public RestErrorInfo processConcurencyError(
			ConcurrencyFailureException ex) {
		return new RestErrorInfo("", ErrorConstants.ERR_CONCURRENCY_FAILURE,
				ex.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public RestErrorInfo handleThrowableException(Throwable ex) {
		log.debug("Processing Throwable ..." + ex.getClass().getSimpleName());
		return new RestErrorInfo("", ErrorConstants.ERR_INTERNAL_ERR,
				ex.getMessage());
	}

}
