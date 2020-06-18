package com.lethanh98.controller;

import com.lethanh98.annotation.ApiResponsesBase;
import com.lethanh98.entity.User;
import com.lethanh98.repo.UserRepo;
import com.lethanh98.reponse.ResponseBase;
import com.lethanh98.reponse.UsersRP;
import com.lethanh98.reponse.dto.UsersDtoRp;
import com.lethanh98.request.PostUserRQ;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/Transactional/child_Transactional")
@ApiResponsesBase()
/**
 *  demo khai báo Transactional ở method con được method cha đầu tiền gọi vào/
 * method cha sẽ được gọi vào từ bên ngoài
 *  bởi vì method cha không khai báo Transactional
 *  nên các method trong cùng một class được method cha gọi khai báo Transactional sẽ không thể khai báo.
 *  Vì không thể tạo mới Transactional nên không thể lan truyền Transactional được
 *  Khi gọi method save của JPA thì trong method save có Transactional, vì vậy mỗi một lần save là một Transactional riêng
 *  vì vậy 1 2 3  vẫn sẽ đc lưu mà không bị roolbank
 */
public class Child_Transactional {

  @Autowired
  UserRepo userRepo;

  @GetMapping()
  public void pater() {
    child();
  }

  @Transactional
  public void child() {
    child1();
    child2();
    child3();
    child4();
  }


  private void child1() {
    User user = new User();
    user.setFirstName("1");
    user.setLastName("1");
    userRepo.save(user);
  }

  private void child2() {
    User user = new User();
    user.setFirstName("2");
    user.setLastName("2");
    userRepo.save(user);
  }

  private void child3() {
    User user = new User();
    user.setFirstName("3");
    user.setLastName("3");
    userRepo.save(user);
  }


  private void child4() {
    throw new NullPointerException();
  }

}
