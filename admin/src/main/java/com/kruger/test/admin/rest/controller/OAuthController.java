package com.kruger.test.admin.rest.controller;

import java.time.LocalDateTime;
import java.util.Map;

import com.kruger.test.admin.config.security.OAuthJWTProvider;
import com.kruger.test.admin.rest.model.JwtResponse;
import com.kruger.test.admin.rest.model.Response;
import com.kruger.test.admin.rest.model.User;
import com.kruger.test.admin.rest.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    OAuthJWTProvider jwtProvider;

    @Value("${security.jwt.expiration}")
    private String expires;

    HttpStatus httpStatus = HttpStatus.OK;

    @PostMapping("/token")
    public ResponseEntity<Response<?>> login(@RequestBody Map<String, String> credentials,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Response<Object> response = new Response<Object>();
            response.setContent(null);
            response.setDateTime(LocalDateTime.now());
            response.setMessage("Credenciales mal configuradas");
            response.setOK(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtProvider.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = this.userService.getByUsername(userDetails.getUsername());
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setExpires(expires);
        jwtResponse.setToken_type("Bearer");
        jwtResponse.setToken(jwtToken);
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setRole(user.getRol());
        jwtResponse.setId(user.getId());
        return new ResponseEntity<>(new Response<>(jwtResponse), httpStatus);
    }
}