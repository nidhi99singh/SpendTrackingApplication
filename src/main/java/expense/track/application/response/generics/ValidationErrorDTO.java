package expense.track.application.response.generics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ValidationErrorDTO {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public ValidationErrorDTO() {
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    public ValidationErrorDTO setFieldErrors(List<FieldErrorDTO> fieldErrors) {
        this.fieldErrors = fieldErrors;
        return this;
    }

    public void addError(FieldErrorDTO errorDTO) {
        this.fieldErrors.add(errorDTO);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("fieldErrors = " + fieldErrors)
                .toString();
    }

    public String toJSON() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException ignored) {
        }
        return "";
    }


    public static class FieldErrorDTO {
        private String field;
        private String message;

        public FieldErrorDTO() {
        }

        public FieldErrorDTO(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public FieldErrorDTO setField(String field) {
            this.field = field;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public FieldErrorDTO setMessage(String message) {
            this.message = message;
            return this;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", "" + "{", "}")
                    .add("field = " + field)
                    .add("message = " + message)
                    .toString();
        }
    }
}
