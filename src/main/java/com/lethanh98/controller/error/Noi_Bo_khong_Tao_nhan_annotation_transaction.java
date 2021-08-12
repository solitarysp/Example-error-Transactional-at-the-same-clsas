package com.lethanh98.controller.error;


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
@RequestMapping(value = "/api/Transactional/error/Noi_Bo_khong_Tao_nhan_annotation_transaction")
@ApiResponsesBase()
/**
 * Kịch bản demo:
 * - Tạo một Controller để được bean từ bên ngoài gọi đến. Method này sẽ được khai báo Transactional default
 * - Trong đấy sẽ gọi đến method child.
 * - Trong child sẽ gọi đến các method child sub có khai báo @Transactional(propagation = Propagation.REQUIRES_NEW) để yêu cầu 1 transaction mới riêng biệt
 * - trong các child sub sẽ gọi đến save qua instance để yêu cầu save dữ liệu.
 * - Tại child4 sẽ throw NullPointerException một lỗi Runtime để roolback
 */
/**
 * Kết quả : Không có data nào được lưu vào database cả.
 */
/**
 * Bởi vì : Khi chạy đến child 1 và 3, data vẫn chưa được commit, vì các method này được gọi trong nội bộ class.
 * Khi gọi nội bộ class thì sẽ không qua proxy vì vậy các @ Transactional sẽ không có tác dụng.
 * Vì vậy tất cả các child 1 đén 4 vẫn cùng 1 transaction.
 * Khi đén child 4 có một Exception xẩy ra, khi đó sẽ roolback tất cả từ child 1 đến 3, vì thế không có dữ liệu nào được lưu
 */
public class Noi_Bo_khong_Tao_nhan_annotation_transaction {

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void child1() {
        User user = new User();
        user.setFirstName("100");
        user.setLastName("100");
        userRepo.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void child2() {
        User user = new User();
        user.setFirstName("200");
        user.setLastName("200");
        userRepo.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void child3() {
        User user = new User();
        user.setFirstName("300");
        user.setLastName("300");
        userRepo.save(user);
    }


    private void child4() {
        throw new NullPointerException();
    }
}
