package expense.track.application.response.utils;
import expense.track.application.enums.ApiResponseCode;
import expense.track.application.locale.MessageByLocale;
import expense.track.application.response.ApiResponseDTO;
import expense.track.application.response.generics.ResponseCode;
import expense.track.application.response.generics.ResponseDTO;
import expense.track.application.response.generics.ValidationErrorDTO;
import expense.track.application.response.generics.ValidationErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public final class ResponseUtil {
    private static final Map data = new HashMap();
    @Autowired
    private MessageByLocale messageByLocale;

    public Locale getLocale(String locale) {
        return locale != null ? new Locale(locale) : Locale.UK;
    }


    public ResponseDTO ok() {
        String message = messageByLocale.getMessage(String.valueOf(ApiResponseCode.SUCCESS.getCode()));
        return new ApiResponseDTO(message, data);
    }

    public ResponseDTO ok(Object data) {
        String message = messageByLocale.getMessage(String.valueOf(ApiResponseCode.SUCCESS.getCode()));
        return new ApiResponseDTO(message, data);
    }

    public ResponseDTO ok(ResponseCode responseCode) {
        String message = messageByLocale.getMessage(String.valueOf(responseCode.getCode()));
        return new ApiResponseDTO(message, data);
    }


    public ResponseDTO ok(Object data, ResponseCode responseCode) {
        String message = messageByLocale.getMessage(String.valueOf(responseCode.getCode()));
        return new ApiResponseDTO(message, data);
    }


    public ResponseDTO ok(Object data, ResponseCode responseCode, String locale) {
        String message = messageByLocale.getMessage(String.valueOf(responseCode.getCode()));
        return new ApiResponseDTO(message, data);
    }

    public ResponseDTO ok(ResponseCode responseCode, String locale) {
        String message = messageByLocale.getMessage(String.valueOf(responseCode.getCode()));
        return new ApiResponseDTO(message, data);
    }

    /**
     * This method sets the values in code, message and the data.
     *
     * @param code it's a error code which is of integer type.
     * @return the anonymous object with parameter code,message and data.
     */
    public ResponseDTO exception(int code) {
        String message = messageByLocale.getMessage(String.valueOf(code));
        return new ApiResponseDTO(code, message, data);
    }

    /**
     * This method sets the values in code, message and the data.
     *
     * @param code    it's a error code which is of integer type.
     * @param message it's a error message which is of String type.
     * @return the anonymous object with parameter code,message and data.
     */
    public ResponseDTO exception(int code, String message) {
        return new ApiResponseDTO(code, message, data);
    }

    /**
     * This method sets the values in code, message and the data.
     *
     * @param responseCode it's a object which helps in setting up the value of message and the code.
     * @return the anonymous object with parameter code,message and data.
     */
    public ResponseDTO exception(ResponseCode responseCode) {
        String message = messageByLocale.getMessage(String.valueOf(responseCode.getCode()));
        return new ApiResponseDTO(responseCode.getCode(), message, data);
    }

    /**
     * This method sets the values in code, message and the data.
     *
     * @param code    it's a error code which is of integer type.
     * @param message it's a error message which is of String type.
     * @return the anonymous object with parameter code,message and data.
     */
    public ResponseDTO validationFailed(int code, String message) {
        return new ApiResponseDTO(code, message, data);
    }

    /**
     * This method sets the values in code, message and the data.
     *
     * @param code               it's a error code which is of integer type.
     * @param validationErrorDTO it's a object which helps in setting up the error message.
     * @return the anonymous object with parameter code,message and data.
     */
    public ResponseDTO validationFailed(int code, ValidationErrorDTO validationErrorDTO) {
        return new ApiResponseDTO(code, "Missing / Invalid Parameter(s)", validationErrorDTO.getFieldErrors());
    }

    /**
     * This method sets the values in code, message and the data.
     *
     * @param code          it's a error code which is of integer type.
     * @param errorResponse it's a object which helps in setting up the error message.
     * @return the anonymous object with parameter code,message and data.
     */
    public ResponseDTO validationFailed(int code, ValidationErrorResponse errorResponse) {
        return new ApiResponseDTO(code, "Missing / Invalid Parameter(s)", errorResponse.getErrorMessages());
    }

}
