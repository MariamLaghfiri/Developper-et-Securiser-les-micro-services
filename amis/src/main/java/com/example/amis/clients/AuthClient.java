package com.example.amis.clients;

import com.javatechie.entity.UserCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "IDENTITY-SERVICE", path = "http://localhost:8089")
public interface AuthClient {

    @GetMapping("auth/getUserByName")
    ResponseEntity<UserCredential> getUserByName(@RequestHeader("username") String username);
}
