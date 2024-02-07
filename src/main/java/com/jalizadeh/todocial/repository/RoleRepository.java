package com.jalizadeh.todocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);

	@Override
	void delete(Role role);
}
