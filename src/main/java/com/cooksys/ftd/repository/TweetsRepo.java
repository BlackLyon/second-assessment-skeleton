package com.cooksys.ftd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.ftd.entity.Tweets;
import com.cooksys.ftd.projections.TweetsProjection;


public interface TweetsRepo  extends JpaRepository<Tweets, Long>{
	
	Tweets findBycontentAndIsDeleted(String content, boolean active);
	
	TweetsProjection findByIdAndIsDeleted(Long id, boolean active);
	
	List<TweetsProjection> findByisDeletedOrderByPostedDesc(boolean active);
	
	List<TweetsProjection> findByInReplyTo_IdAndIsDeleted(Long id, boolean active);
	
	List<TweetsProjection> findByRepostOf_IdAndIsDeleted(Long id, boolean active);
}
