package org.jpajuelo.springsegurity6.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/auth")
@RestController
@PreAuthorize("denyAll()")
public class TestAuthController {

    @GetMapping("/one")
    @PreAuthorize("hasAuthority('READ')")
    public String test1() {
        return "test with Segurity 6";
    }
    @GetMapping("/two")
    public String test2() {
        return "test with Segurity 6";
    }
    @GetMapping("/three")
    public String test3() {
        return "You are admin";
    }
}
