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
@RequestMapping(value = "/api/Transactional/Khong_Lan_truyen_Transaction")
@ApiResponsesBase()
/**
 * Kịch bản demo:
 * - Tạo một Controller để được bean từ bên ngoài gọi đến.
 * - Trong đấy sẽ gọi đến method child, nơi khai báo tạo transaction để lan truyền
 * - sau đó gọi các method bên trong có gọi đến entity để lưu data đến database
 * - Tại child4 sẽ throw NullPointerException một lỗi Runtime để roolback
 */
/**
 * Kết quả : Khi chạy đến child 4, tuy có throw một Exception nhưng các transaction ở child1 đến child 3 không được roolback, dữ liệu vẫn được lưu vào database.
 */
/**
 * Vì sao : ?
 * - Bởi vì Spring lan truyền transaction thông qua cơ chế proxy, mà để sử dụng proxy thì sẽ cần gọi method thông qua instance.
 * - Tuy nhiên ở method root tức method sẽ được gọi từ bên ngoài thông qua instance lại không khai báo Transactional, nên sẽ không tạo Transactional.
 * - Ở child có khai báo Transactional nhưng ở trường hợp demo thì đang được root gọi nội bộ, vì vậy không thể tạo Transactional, vì gọi nội bộ không qua proxy
 * - Nếu child được gọi từ bên ngoài thông qua instance thì sẽ đợc tạo transaciton và lan truyền
 */
/**
 * Vì sao child4 vẫn được lưu ?
 * - Bởi vì khi gọi method save chúng ta đang gọi đến method qua một instacne  khác, method đó có khai báo Transactional default
 * - Vì không có Transactional trước đó nên nó sẽ tạo 1 transaciton mới, sau khi thoát method thì transaciton đã được commit rồi.
 */
public class Khong_Lan_truyen_Transaction {

  @Autowired
  UserRepo userRepo;

  @GetMapping()
  public void root() {
    child();
  }

  // Tạo transaction tại method được gọi từ một method khác trong cùng class
  // Tuy nhiên demo đang gọi nội bộ nên không qua proxy nên không tạo được
  // Nếu không gọi nội bộ thì sẽ tạo thành công
  @Transactional
  public void child() {
    child1();
    child2();
    child3();
    child4();
  }

  public void child1() {
    User user = new User();
    user.setFirstName("1");
    user.setLastName("1");
    userRepo.save(user);
  }

  public void child2() {
    User user = new User();
    user.setFirstName("2");
    user.setLastName("2");
    userRepo.save(user);
  }

  public void child3() {
    User user = new User();
    user.setFirstName("3");
    user.setLastName("3");
    userRepo.save(user);
  }

  public void child4() {User user = new User();
    user.setFirstName("4");
    user.setLastName("4");
    userRepo.save(user);
    throw new NullPointerException();
  }

}
