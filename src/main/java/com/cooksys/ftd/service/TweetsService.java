package com.cooksys.ftd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.ftd.entity.Tweets;
import com.cooksys.ftd.projections.TweetsProjection;
import com.cooksys.ftd.repository.TweetsRepo;

@Service
public class TweetsService {
	
	TweetsRepo tweetsRepo;
	
	public TweetsService(TweetsRepo repo)
	{
		tweetsRepo = repo;
	}
	
	public List<TweetsProjection> getTweets() {
		return tweetsRepo.findByisDeletedOrderByPostedDesc(false);
	}
	
	public TweetsProjection findByid(Long id) {
		return tweetsRepo.findByIdAndIsDeleted(id, false);
	}
	
	public Tweets getId(Long id) {
		return tweetsRepo.getOne(id);
	}
	
	public List<TweetsProjection> getReplies(Long id) {
		return tweetsRepo.findByInReplyTo_IdAndIsDeleted(id, false);
	}
	
	public List<TweetsProjection> getReposts(Long id) {
		return tweetsRepo.findByRepostOf_IdAndIsDeleted(id, false);
	}
	
	public void postSimpleTweet(Tweets tweet) {
		tweetsRepo.saveAndFlush(tweet);
	}
	
	public void ToggleStatus(Tweets tweet, boolean status) {
		tweet.setDeleted(status);
		tweetsRepo.saveAndFlush(tweet);
	}
}
