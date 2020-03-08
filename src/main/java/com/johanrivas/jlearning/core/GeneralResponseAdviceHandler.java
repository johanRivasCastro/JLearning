package com.johanrivas.jlearning.core;

import java.util.Map;

import com.johanrivas.jlearning.Controllers.UploadsController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class GeneralResponseAdviceHandler implements ResponseBodyAdvice<Object> {

    private static final int HIGHEST_CODE_SUCCESSFUL = 399;
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralResponseAdviceHandler.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getContainingClass() != UploadsController.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {

        final WrapperResponse wrapper = new WrapperResponse();
        final ServletServerHttpResponse servletResponse = (ServletServerHttpResponse) response;

        if (servletResponse.getServletResponse().getStatus() <= HIGHEST_CODE_SUCCESSFUL) {
            wrapper.setSuccessful(true);
            wrapper.setData(body);
        } else {
            String message = "Try again later";

            if (body instanceof Map<?, ?>) {
                Map<String, Object> errorInfo = (Map<String, Object>) body;
                message = (String) errorInfo.get("message");
            }

            wrapper.setErrorMessage(message);
        }

        return wrapper;
    }
}