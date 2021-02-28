package com.nareshnj.lms.controller;

import com.nareshnj.lms.entity.RegisterEntry;
import com.nareshnj.lms.pojo.RegisterEntryRequest;
import com.nareshnj.lms.pojo.Response;
import com.nareshnj.lms.service.RegisterEntryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/register")
public class RegisterEntryController {

    private final RegisterEntryService registerEntryService;

    public RegisterEntryController(RegisterEntryService registerEntryService) {
        this.registerEntryService = registerEntryService;
    }

    @GetMapping
    public List<RegisterEntry> getRegisterEntry() {
        return registerEntryService.getAllRegisterEntries();
    }

    @PostMapping
    public Response createRegisterEntry(@RequestBody RegisterEntryRequest request) {
        return registerEntryService.createEntry(request);
    }

}
