package com.cherrypick.backend;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/hello")
@RestController
public class HelloController {

  @GetMapping
  @PreAuthorize("hasAnyRole('USER')")
  public String hello() {
    return "hello";
  }
}
