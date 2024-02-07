package com.jalizadeh.todocial.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.model.user.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String name);

}
