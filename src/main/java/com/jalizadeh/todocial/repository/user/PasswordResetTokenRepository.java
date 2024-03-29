package com.jalizadeh.todocial.repository.user;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jalizadeh.todocial.model.user.PasswordResetToken;
import com.jalizadeh.todocial.model.user.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	
	PasswordResetToken findByToken(String token);

	PasswordResetToken findByUser(User user);
    
	PasswordResetToken findByUserId(Long id);

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Transactional
    @Modifying
    @Query("delete from PasswordResetToken p where p.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
    
    @Transactional
    @Modifying
    @Query("delete from PasswordResetToken p where p.token = ?1")
    void deleteByToken(String token);
}
