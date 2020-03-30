package com.jalizadeh.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.springboot.web.controller.admin.model.SettingsGeneral;

public interface SettingsGeneralReposiroty extends JpaRepository<SettingsGeneral, Long>{
	

}
