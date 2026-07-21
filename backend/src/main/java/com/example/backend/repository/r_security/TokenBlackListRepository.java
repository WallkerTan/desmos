package com.example.backend.repository.r_security;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.backend.model.e_security.TokenBlackList;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList,Long> {
    
    // Kiểm tra token có trong blacklist không
    boolean existsByToken(String token);

    // Kiểm tra user có token bị thu hồi không
    boolean existsByUserId(Long userId);

    // Xóa toàn bộ token của user (revokeToken)
    void deleteAllByUserId(Long userId);

    // Xóa token đã hết hạn (chạy định kỳ)
    void deleteAllByExpiredAtBefore(LocalDateTime now);
    
}
