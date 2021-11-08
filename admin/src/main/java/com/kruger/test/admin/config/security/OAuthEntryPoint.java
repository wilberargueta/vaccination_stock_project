package com.kruger.test.admin.config.security;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kruger.test.admin.config.MapperCustomConfig;
import com.kruger.test.admin.rest.model.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class OAuthEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MapperCustomConfig mapper;

    public OAuthEntryPoint(MapperCustomConfig mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // String header = request.getHeader("Authorization");
        // logger.error("Authorization=" + header);
        logger.error("USER_FORBIDDEN = " + authException.getMessage());
        Response<Object> responseObject = new Response<>();

        responseObject.setContent(null);
        responseObject.setCode("403");
        responseObject.setMessage("Usuario  no esta autorizado.");
        responseObject.setDateTime(LocalDateTime.now());
        responseObject.setOK(false);
        response.setHeader("Content-Type", "application/json");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        mapper.objectMapper().writeValue((OutputStream) servletOutputStream, responseObject);
        servletOutputStream.flush();
    }

}
