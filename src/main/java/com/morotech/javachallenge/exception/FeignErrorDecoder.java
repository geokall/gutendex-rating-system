package com.morotech.javachallenge.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import static com.morotech.javachallenge.utils.MoroConstant.*;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.is5xxServerError()) {
            return new MoroBadRequestException(FEIGN_5XX_ERROR);
        } else if (responseStatus.is4xxClientError()) {
            return new MoroNotFoundException(FEIGN_4XX_ERROR);
        } else {
            return new Exception(FEIGN_GENERIC_EXCEPTION);
        }
    }
}
