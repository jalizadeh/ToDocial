package com.jalizadeh.todocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String name);

}
