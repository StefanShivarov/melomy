package softuni.javaweb.melomy.model.validation;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ApiError {

    private final HttpStatus status;
    private List<String> fieldsWithErrors = new ArrayList<>();

    public ApiError(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void addFieldWithError(String error){
        this.fieldsWithErrors.add(error);
    }

    public List<String> getFieldsWithErrors() {
        return fieldsWithErrors;
    }

    public ApiError setFieldWithErrors(List<String> fieldsWithErrors) {
        this.fieldsWithErrors = fieldsWithErrors;
        return this;
    }
}
