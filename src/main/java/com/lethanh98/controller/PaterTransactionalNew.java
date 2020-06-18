package com.lethanh98.controller;


import com.lethanh98.annotation.ApiResponsesBase;
import com.lethanh98.entity.User;
import com.lethanh98.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/Transactional/Pater_Transactional_new")
@ApiResponsesBase()
/**
 *  demo khai báo Transactional ở method cha được gọi từ bên ngoài vào/
 *  bởi vì method cha  khai báo Transactional
 *  mặc dù các method con đều khai báo Transactional Propagation.REQUIRES_NEW, tuy nhiên bởi vì đang gọi nội bộ the same trong class.
 *  CHo nên mặc dù khai báo REQUIRES_NEW nhưng spring vẫn sẽ sử dụng lại transaction cũ.
 *  CHính vì sử dụng lại transaction cũ cho nên khi child400 lỗi thì tất cả đều bị rollback
 */
public class PaterTransactionalNew {

    @Autowired
    UserRepo userRepo;

    @GetMapping()
    @Transactional
    public void pater() {
        child();
    }

    public void child() {
        child100();
        child200();
        child300();
        child400();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void child100() {
        User user = new User();
        user.setFirstName("100");
        user.setLastName("100");
        userRepo.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void child200() {
        User user = new User();
        user.setFirstName("200");
        user.setLastName("200");
        userRepo.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void child300() {
        User user = new User();
        user.setFirstName("300");
        user.setLastName("300");
        userRepo.save(user);
    }


    private void child400() {
        throw new NullPointerException();
    }
}
