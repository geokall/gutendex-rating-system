package com.morotech.javachallenge.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morotech.javachallenge.dto.GutendexExceptionDTO;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static com.morotech.javachallenge.utils.MoroConstant.FEIGN_BAD_REQUEST;
import static com.morotech.javachallenge.utils.MoroConstant.FEIGN_NOT_FOUND;

public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignErrorDecoder.class);

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        GutendexExceptionDTO message;

        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, GutendexExceptionDTO.class);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            return new Exception(e.getMessage());
        }

        switch (response.status()) {
            case 400:
                return new MoroBadRequestException(message.getDetail() != null
                        ? message.getDetail() : FEIGN_BAD_REQUEST);
            case 404:
                return new MoroNotFoundException(message.getDetail() != null
                        ? message.getDetail() : FEIGN_NOT_FOUND);
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }
}
