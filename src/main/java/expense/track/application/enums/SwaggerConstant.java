package expense.track.application.enums;

public enum SwaggerConstant {
    JWT("JWT"),
    X_AUTHORIZATION("Authorization"),
    GLOBAL("global"),
    ACCESS_EVERYTHING("accessEverything"),
    HEADER("header");

    String value;

    SwaggerConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
