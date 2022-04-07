package expense.track.application.response.generics;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class ValidationErrorResponse {  private Set<String> errorMessages = new HashSet<>();

    public Set<String> getErrorMessages() {
        return errorMessages;
    }

    public void addErrorMessage(String errorMessage) {
        this.errorMessages.add(errorMessage);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("errorMessages = " + errorMessages)
                .toString();
    }

}
