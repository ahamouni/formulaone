package com.formulaone.controller.errors;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.formulaone.controller.exceptions.UserNotFoundException;

/**
 * Controller advice to translate the server side exceptions into 
 * json structures.
 */
@ControllerAdvice
public class RestErrorHandler {
	
	//TODO

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final MessageSource messageSource;

	@Autowired
	public RestErrorHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public RestErrorInfo handleUserNotFound(UserNotFoundException ex) {
		log.info("Processing UserNotFoundException ...");
		String message = getLocalizedMessage(UserNotFoundException.CODE, new String[] { String.valueOf(ex.getId()) });
		return new RestErrorInfo(message);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ResponseEntity<RestErrorInfo> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
		log.debug("Processing HttpRequestMethodNotSupportedException ...");
		return new ResponseEntity<RestErrorInfo>(
				new RestErrorInfo(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, ex.getMessage()),
				HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ResponseEntity<RestErrorInfo> handleAccessDenied(AccessDeniedException ex) {

		log.debug("Processing AccessDeniedException ...");
		return new ResponseEntity<RestErrorInfo>(new RestErrorInfo(ErrorConstants.ERR_ACCESS_DENIED, ex.getMessage()),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<RestErrorInfo> processValidationError(MethodArgumentNotValidException ex) {
		log.debug("Processing error validation ...");
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		String message = getLocalizedMessage(fieldErrors.get(0));
		return new ResponseEntity<RestErrorInfo>(new RestErrorInfo(ErrorConstants.ERR_VALIDATION, message),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<RestErrorInfo> handleThrowableException(Throwable ex) {
		log.debug("Processing Throwable ...");
		return new ResponseEntity<RestErrorInfo>(new RestErrorInfo(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ConcurrencyFailureException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ResponseEntity<RestErrorInfo> processConcurencyError(ConcurrencyFailureException ex) {
		return new ResponseEntity<RestErrorInfo>(
				new RestErrorInfo(ErrorConstants.ERR_CONCURRENCY_FAILURE, ex.getMessage()), HttpStatus.CONFLICT);
	}

	private String getLocalizedMessage(FieldError fieldError) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
		return localizedErrorMessage;
	}

	private String getLocalizedMessage(String code, String[] args) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String localizedErrorMessage = messageSource.getMessage(code, args, currentLocale);
		return localizedErrorMessage;
	}

}
