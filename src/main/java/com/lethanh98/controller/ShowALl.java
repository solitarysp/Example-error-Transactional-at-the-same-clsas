package com.lethanh98.controller;

import com.lethanh98.entity.User;
import com.lethanh98.repo.UserRepo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/Transactional/")
public class ShowALl {

    @Autowired
    UserRepo userRepo;

    @GetMapping()
    public List<User> root() {
        return (List<User>) userRepo.findAll();
    }
}
