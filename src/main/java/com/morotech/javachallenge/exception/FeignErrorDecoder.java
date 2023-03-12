package com.morotech.javachallenge.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import static com.morotech.javachallenge.utils.MoroConstant.FEIGN_GENERIC_EXCEPTION;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.is5xxServerError()) {
            return new MoroBadRequestException("Something went wrong with gutendex");
        } else if (responseStatus.is4xxClientError()) {
            return new MoroNotFoundException("Failed to retrieve data from gutendex");
        } else {
            return new Exception("Generic exception");
        }
    }
}
