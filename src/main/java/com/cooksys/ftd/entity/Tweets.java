package com.cooksys.ftd.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tweets {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Users author;
	
	@Column(updatable = false, nullable = false)
	private Timestamp posted = new Timestamp(System.currentTimeMillis());
	
	@Column(nullable = false)
	private String content;
	private boolean isDeleted = false;

	@ManyToOne
	@JsonIgnore
	private Tweets inReplyTo;

	@ManyToOne
	@JsonIgnore
	private Tweets repostOf;

	@OneToMany(mappedBy = "inReplyTo")
	@JsonIgnore
	private List<Tweets> replies;

	@OneToMany(mappedBy = "repostOf")
	@JsonIgnore
	private List<Tweets> reposts;

	@ManyToMany
	@JsonIgnore
	@JoinTable
	private List<HashTag> hashTag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Users getAuthor() {
		return author;
	}

	public void setAuthor(Users author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Tweets getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Tweets inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Tweets getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweets repostOf) {
		this.repostOf = repostOf;
	}

	public List<HashTag> getHashTag() {
		return hashTag;
	}

	public void setHashTag(HashTag tag) {
		hashTag.add(tag);
	}

	@JsonIgnore
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<Tweets> getReplies() {
		return replies;
	}

	public void setReplies(Tweets tweet) {
		replies.add(tweet);
	}

	public List<Tweets> getReposts() {
		return reposts;
	}

	public void setReposts(List<Tweets> reposts) {
		this.reposts = reposts;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}
	
	
}
