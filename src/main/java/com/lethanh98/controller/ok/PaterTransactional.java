package com.lethanh98.controller.ok;


import com.lethanh98.annotation.ApiResponsesBase;
import com.lethanh98.entity.User;
import com.lethanh98.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/api/Transactional/Pater_Transactional")
@ApiResponsesBase()
/**
 *  demo khai báo Transactional ở method cha được gọi từ bên ngoài vào/
 *  bởi vì method cha  khai báo Transactional
 *  Nên các  method con sẽ được lan truyền Transactional
 *  Chính vì vậy ở ví dụ này tại child40 có 1 lỗi, nên tất cả Transactional được rollback
 */
public class PaterTransactional {

    @Autowired
    UserRepo userRepo;

    @GetMapping()
    @Transactional
    public void pater() {
        child();
    }

    public void child() {
        child10();
        child20();
        child30();
        child40();
    }


    private void child10() {
        User user = new User();
        user.setFirstName("10");
        user.setLastName("10");
        userRepo.save(user);
    }

    private void child20() {
        User user = new User();
        user.setFirstName("20");
        user.setLastName("20");
        userRepo.save(user);
    }

    private void child30() {
        User user = new User();
        user.setFirstName("30");
        user.setLastName("30");
        userRepo.save(user);
    }


    private void child40() {
        throw new NullPointerException();
    }
}
