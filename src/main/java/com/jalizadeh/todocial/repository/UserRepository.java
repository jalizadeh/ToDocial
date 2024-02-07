package com.jalizadeh.todocial.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jalizadeh.todocial.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select u from User u where u.id=:#{principal.id}")
	User findLoggedinUser();

	User findByUsername(String username);
	Optional<User> findOptionalByUsername(String username);

	User findByEmail(String email);
	
	//@Query("select u from User u where u.enabled=true")
	List<User> findByEnabled(Boolean flag);

	@Transactional
	@Modifying
	//@Query("delete from User u where u.id= ?1")
    void deleteById(Long id);
	
	List<String> findByFollowingsUsernameIn(List<String> names);

}
