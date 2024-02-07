package com.jalizadeh.todocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.controller.admin.model.SettingsGeneral;

public interface SettingsGeneralReposiroty extends JpaRepository<SettingsGeneral, Long>{

}
