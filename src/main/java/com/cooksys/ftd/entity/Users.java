package com.cooksys.ftd.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	private boolean isDeleted = false;

	@OneToOne()
	@Cascade({ CascadeType.ALL })
	private Profile profile;

	@OneToOne
	@Cascade({ CascadeType.ALL })
	private Credentials credentials;

	@ManyToMany
	@JoinTable(name = "followers", joinColumns = @JoinColumn(name = "followers"), inverseJoinColumns = @JoinColumn(name = "following"))
	@JsonIgnore
	private Set<Users> followers;

	@ManyToMany(mappedBy = "followers")
	@JsonIgnore
	private Set<Users> following;

	@OneToMany(mappedBy = "author")
	@JsonIgnore
	private List<Tweets> tweets;

	@Column(updatable = false)
	private Timestamp joined = new Timestamp(System.currentTimeMillis());

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Users> getFollowers() {
		return followers;
	}

	public void setFollowers(Users user) {
		followers.add(user);
	}

	public Set<Users> getFollowing() {
		return following;
	}

	public void setFollowing(Users user) {
		following.add(user);
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public List<Tweets> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweets> tweets) {
		this.tweets = tweets;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Timestamp getJoined() {
		return joined;
	}

	public void setJoined(Timestamp joined) {
		this.joined = joined;
	}
	
	
}
