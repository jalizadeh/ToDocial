package com.jalizadeh.todocial.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.web.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String name);

}
