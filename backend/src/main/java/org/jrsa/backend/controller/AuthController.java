//package org.jrsa.backend.controller;
//
//import org.jrsa.backend.dto.LoginRequest;
//import org.jrsa.backend.dto.RegisterRequest;
//import org.jrsa.backend.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/restapi/auth")
//public class AuthController {
//	
//	@Autowired
//	AuthService service;
//	
//	@PostMapping("/register")
//	public String register(@RequestBody RegisterRequest req){
//	
//		return service.register(req);
//	
//	}
//	
//	@PostMapping("/login")
//	public String login(@RequestBody LoginRequest req){
//	
//		return service.login(req);
//	
//	}
//
//}