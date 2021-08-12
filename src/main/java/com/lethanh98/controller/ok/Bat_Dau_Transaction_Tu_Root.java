package com.lethanh98.controller.ok;


import com.lethanh98.annotation.ApiResponsesBase;
import com.lethanh98.entity.User;
import com.lethanh98.repo.UserRepo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/api/Transactional/ok/Bat_Dau_Transaction_Tu_Root")
@ApiResponsesBase()
/**
 *  demo khai báo Transactional ở method cha được gọi từ bên ngoài vào/
 *  bởi vì method cha  khai báo Transactional
 *  Nên các  method con sẽ được lan truyền Transactional
 *  Chính vì vậy ở ví dụ này tại child40 có 1 lỗi, nên tất cả Transactional được rollback
 */
/**
 * Kịch bản demo:
 * - Tạo một Controller để được bean từ bên ngoài gọi đến. Method này sẽ được khai báo Transactional default
 * - Khi gọi các method nội bộ, Transaction này sẽ được làn truyền.
 * - tại child4 có throw NullPointerException là runtime Exception
 */
/**
 * Kết quả:
 *  Nếu để như hiện tại transaciton sẽ được rollback và không có gì được lưu cả.
 *  Nếu xóa throw NullPointerException là runtime Exception ở child4, sau khi kết thúc method root thì data sẽ được lưu vào database
 */
@Api(tags = "ok")
public class Bat_Dau_Transaction_Tu_Root {

    @Autowired
    UserRepo userRepo;

    @GetMapping()
    @Transactional
    public void root() {
        child();
    }

    public void child() {
        child1();
        child2();
        child3();
        child4();
    }


    private void child1() {
        User user = new User();
        user.setFirstName("10");
        user.setLastName("10");
        userRepo.save(user);
    }

    private void child2() {
        User user = new User();
        user.setFirstName("20");
        user.setLastName("20");
        userRepo.save(user);
    }

    private void child3() {
        User user = new User();
        user.setFirstName("30");
        user.setLastName("30");
        userRepo.save(user);
    }


    private void child4() {
        throw new NullPointerException();
    }
}
