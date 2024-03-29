package com.jalizadeh.todocial.repository.user;

import java.util.Date;
import java.util.stream.Stream;

import com.jalizadeh.todocial.model.user.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jalizadeh.todocial.model.user.User;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {
	
	ActivationToken findByToken(String token);
    ActivationToken findByUser(User user);
    ActivationToken findByUserId(Long id);
    Stream<ActivationToken> findAllByExpiryDateLessThan(Date now);
    void deleteByExpiryDateLessThan(Date now);

    void deleteByUser(User user);

    @Transactional
    @Modifying
    @Query("delete from ActivationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
