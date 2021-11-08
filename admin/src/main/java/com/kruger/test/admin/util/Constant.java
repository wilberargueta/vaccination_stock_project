package com.kruger.test.admin.util;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

public class Constant {

    public static final Contact DEFAULT_CONTACT = new Contact("<por definir>", "https://kruger.com/",
            "info@kruger.com");

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("REST VACCUNATION ADMIN Service",
            "REST VACCUNATION Service,  Endpoint para administracion de empleados vacunados ", "1.0.0",
            "https://kruger.com/", DEFAULT_CONTACT, "Kruger Endpoint Definition 1.0", "https://kruger.com/",
            new ArrayList<>());

    public static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList(new String[] { "application/json" }));

}