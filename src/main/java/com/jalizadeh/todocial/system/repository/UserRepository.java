package com.jalizadeh.todocial.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jalizadeh.todocial.web.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	User findByEmail(String email);
	
	//@Query("select u from User u where u.enabled=true")
	List<User> findByEnabled(Boolean flag);

	@Transactional
	@Modifying
	//@Query("delete from User u where u.id= ?1")
    void deleteById(Long id);
	
	List<String> findByFollowingsUsernameIn(List<String> names);

}
