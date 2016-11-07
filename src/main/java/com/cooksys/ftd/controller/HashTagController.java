package com.cooksys.ftd.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.projections.HashTagProjections;
import com.cooksys.ftd.service.HashTagService;

@RestController
@RequestMapping("hashtag")
public class HashTagController {

	HashTagService hashTagService;
	
	public HashTagController(HashTagService service) {
		hashTagService = service;
	}
	
	//Retrieves all hashtags tracked by the database.
	//return an array of hashtags
	//done
	@GetMapping()
	public List<HashTagProjections> getHashTags() {
		return hashTagService.getHashTags();
	}
	
	//Retrieves all (non-deleted) tweets tagged with the given hashtag label. The tweets should appear in reverse-chronological order. 
	//If no hashtag with the given label exists, an error should be sent in lieu of a response.
	//A tweet is considered "tagged" by a hashtag if the tweet has content and the hashtag's label appears in that content following a #
	//return an array of tweets
	@GetMapping("/{label}")
	public String getTweetsTag(@PathVariable String label) {
		return "I am working";
	}
}
