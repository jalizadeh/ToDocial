package com.jalizadeh.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.springboot.web.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
