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
@RequestMapping(value = "/api/Transactional/ok/Fix_goi_noi_bo_ok")
@ApiResponsesBase()
/**
 * Nếu chúng ta bắt buộc phải gọi nội bộ thì có thể tự inject chính bản thân của class này. TUy nhiên không khuyến khích sử dụng điều này, nên tách class ra
 * Lưu ý là chúng ta phải tiêm với Autowired hoặc 1 số cách khác nhé. Nếu chúng ta gắn = this thì không có tác dụng vì khi đó chúng ta sẽ gắn instance của class chứ k phải instance của proxy
 */
@Api(tags = "ok")
public class Fix_goi_noi_bo_ok {

    @Autowired
    UserRepo userRepo;
    @Autowired
    Fix_goi_noi_bo_ok fix_goi_noi_bo_ok;

    @GetMapping()
    public void root() {
        // khi gọi như này thì chúng ta đang sử dụng instance proxy nên sẽ lan truyền transaction được
        fix_goi_noi_bo_ok.child();
    }

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

    public void child4() {
        User user = new User();
        user.setFirstName("4");
        user.setLastName("4");
        userRepo.save(user);
        throw new NullPointerException();
    }
}
