package com.nareshnj.lms.pojo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class RegisterEntryRequest {

    private long userId;
    private Set<Long> books;
    private String requestType;
    private LocalDateTime createdDate;
}
