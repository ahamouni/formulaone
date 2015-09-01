package com.formulaone.controller.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transferring error message with a list of field errors.
 */
public class RestErrorInfo implements Serializable {

	private static final long serialVersionUID = -2472026446965712212L;

	private final String message;
    private final String description;
    private List<FieldErrorDTO> fieldErrors;
    
    
    RestErrorInfo(String message) {
        this(message, null);
    }

    RestErrorInfo(String message, String description) {
        this.message = message;
        this.description = description;
    }

    RestErrorInfo(String message, String description, List<FieldErrorDTO> fieldErrors) {
        this.message = message;
        this.description = description;
        this.fieldErrors = fieldErrors;
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDTO(objectName, field, message));
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }


}
