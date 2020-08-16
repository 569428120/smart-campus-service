package com.xzp.smartcampus.portal.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/auth_routes")
public class AuthRoutesController {

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAuthoritys() {
        Map<String, Object> map = new HashMap<>();
        map.put("authority", Arrays.asList("admin", "user"));
        return ResponseEntity.ok(map);
    }
}
