package com.cooksys.ftd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.ftd.entity.HashTag;
import com.cooksys.ftd.projections.HashTagProjections;

public interface HashTagRepo  extends JpaRepository<HashTag, Long>{
	
	HashTagProjections findBylabel(String label);
	
	List<HashTagProjections> findAllProjectedBy();

}
