package com.cooksys.ftd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.ftd.projections.HashTagProjections;
import com.cooksys.ftd.repository.HashTagRepo;

@Service
public class HashTagService {
	
	HashTagRepo hashTagRepo;
	
	public HashTagService (HashTagRepo repo) {
		hashTagRepo = repo;
	}

	public List<HashTagProjections> getHashTags() {
		return hashTagRepo.findAllProjectedBy();
	}

	
}
