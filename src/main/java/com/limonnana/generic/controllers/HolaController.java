package com.limonnana.generic.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import java.net.InetAddress;
import java.net.UnknownHostException;
 
@RestController
public final class HolaController {
 
	@RequestMapping(value = "/hola")
    public final String hola() throws UnknownHostException {
        return "Hola! Puedes encontrarme en " + InetAddress.getLocalHost().getHostAddress();
    }
}