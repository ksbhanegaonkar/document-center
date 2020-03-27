package com.vamanos.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vamanos.entity.ContextMenuOptions;

public interface ContextMenuOptionRepository extends JpaRepository<ContextMenuOptions,Integer>{
	public ContextMenuOptions getOptionListByType(String type);
}
