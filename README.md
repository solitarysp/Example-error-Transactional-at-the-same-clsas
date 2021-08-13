Example này demo 1 số lỗi khi sử dụng Transactional và khi tại cùng 1 clss gọi các method khai báo Transactional thì sẽ có vấn đề như thế nào.
# Lưu ý:
- Trong demo có nói Spring sử dụng proxy để lan truyền transaction. Nhưng ở các tài liệu khác thì nó nói rằng @Transactional sử dụng AOP vậy thì tại sao ???
```
- Đúng là Spring sử dụng AOP để handler @Transactional để lan truyền transaction. Nhưng Spring lại sử dụng proxy để thực AOP. Vì vậy bản chất nó vẫn là proxy.
```
# Các demo bên trong
- Khong_Lan_truyen_Transaction: Demo một lỗi không lan truyền transaction
- Noi_Bo_khong_Tao_nhan_annotation_transaction: Demo khi gọi nội bộ có @Transactional thì không có tác dụng
- Bat_Dau_Transaction_Tu_Root: Demo thành công đúng flow, 
- Fix_goi_noi_bo_ok: Demo cách fix gọi method nội bộ transaction thông qua self inject