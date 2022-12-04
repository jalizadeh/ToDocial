package com.jalizadeh.todocial.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.web.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);

	@Override
	void delete(Role role);
}
