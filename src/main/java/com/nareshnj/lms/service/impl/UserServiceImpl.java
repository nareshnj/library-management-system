package com.nareshnj.lms.service.impl;

import com.nareshnj.lms.entity.User;
import com.nareshnj.lms.repository.UserRepository;
import com.nareshnj.lms.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Order.asc("lastName"), Sort.Order.asc("firstName")));
    }
}
