package softuni.javaweb.melomy.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentAddBindingModel {

    private String message;

    @NotBlank
    public String getMessage() {
        return message;
    }

    public CommentAddBindingModel setMessage(String message) {
        this.message = message;
        return this;
    }
}
