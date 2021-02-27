package com.nareshnj.lms.controller;

import com.nareshnj.lms.pojo.RegisterEntryRequest;
import com.nareshnj.lms.pojo.Response;
import com.nareshnj.lms.service.RegisterEntryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/register")
public class RegisterEntryController {

    private RegisterEntryService registerEntryService;

    public RegisterEntryController(RegisterEntryService registerEntryService) {
        this.registerEntryService = registerEntryService;
    }

    @GetMapping
    public String get() {
        return "success";
    }

    @PostMapping
    public Response createRegisterEntry(@RequestBody RegisterEntryRequest request) {
        return registerEntryService.createEntry(request);
    }

}
