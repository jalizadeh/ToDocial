package com.jalizadeh.todocial.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneral;

public interface SettingsGeneralReposiroty extends JpaRepository<SettingsGeneral, Long>{

}
