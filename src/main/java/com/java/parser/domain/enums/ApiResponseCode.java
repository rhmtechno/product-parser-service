package com.java.parser.domain.enums;

import com.java.parser.domain.common.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ApiResponseCode {

    OPERATION_SUCCESSFUL("S100000"),
    RECORD_NOT_FOUND("E000101"),
    INVALID_REQUEST_DATA("E000102"),
    SERVICE_UNAVAILABLE("E000103"),
    UNIQUE_CONSTRAINT_VIOLATION("E000104"),
    VERIFICATION_FAILED("E000105"),
    REQUEST_PROCESSING_FAILED("E000106"),
    INVALID_TRANSACTION_PIN("E000107"),
    OTP_GENERATION_FAILED("E000108"),
    INACTIVE_ENTITY("E000109"),
    TFA_NOT_SUPPORTED("E000110"),
    INTER_SERVICE_COMMUNICATION_ERROR("E000111"),
    DB_OPERATION_FAILED("E000010"),
    BAD_OTP_ATTEMPTS_EXCEEDED("E000112"),
    ;

    private String responseCode;

    public static boolean isOperationSuccessful(ApiResponse apiResponse) {
        return Objects.nonNull(apiResponse) && apiResponse.getResponseCode().equals(ApiResponseCode.OPERATION_SUCCESSFUL.getResponseCode());
    }

    public static boolean isNotOperationSuccessful(ApiResponse apiResponse) {
        return !isOperationSuccessful(apiResponse);
    }

}
