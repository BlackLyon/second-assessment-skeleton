package com.cooksys.ftd.controller;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.entity.Credentials;
import com.cooksys.ftd.entity.Users;
import com.cooksys.ftd.projections.UsersProjections;
import com.cooksys.ftd.service.UsersService;
import com.cooksys.ftd.service.ValidateService;
import com.google.gson.Gson;


@RestController
@RequestMapping("users")
public class UsersController {
	
	UsersService usersService;
	ValidateService validateService;
	
	public UsersController(UsersService uService, ValidateService vService) {
		usersService = uService;
		validateService = vService;
	}
	
	//Retrieves all active (non-deleted) users as an array.
	//return an array of users
	//done
	@GetMapping()
	public List<UsersProjections> getUsers() {
		return usersService.getUsers();
	}
	
	//Retrieves a user with the given username. If no such user exists or is deleted, an error should be sent in lieu of a response.
	//return an users instance
	//done
	@GetMapping("/@{username}")
	public UsersProjections getUsername(@PathVariable String username) throws Exception {
		if(validateService.checkName(username)) {
			return usersService.getUsername(username, true);
		}
		else {
			throw new Exception("User is no longer active or does not exist");
		}
	}
	
	//done
	@GetMapping("test/{id}")
	public Users getId(@PathVariable Long id) {
		return usersService.get(id);
	}
	
	//Retrieves all (non-deleted) tweets authored by the user with the given username, as well as all (non-deleted) tweets authored by users the given user is 
	//following. This includes simple tweets, reposts, and replies. The tweets should appear in reverse-chronological order. If no active user with that username 
	//exists (deleted or never created), an error should be sent in lieu of a response.
	@GetMapping("/@{username}/feed")
	public String feeds(@PathVariable String username) {
		return "I am working";
	}
	
	//Retrieves all (non-deleted) tweets authored by the user with the given username. This includes simple tweets, reposts, and replies. The tweets should appear 
	//in reverse-chronological order. If no active user with that username exists (deleted or never created), an error should be sent in lieu of a response.
	@GetMapping("/@{username}/tweets")
	public String getTweets(@PathVariable String username) {
		return "I am working";
	}
	
	//Retrieves all (non-deleted) tweets in which the user with the given username is mentioned. The tweets should appear in reverse-chronological order. 
	//If no active user with that username exists, an error should be sent in lieu of a response.
	//A user is considered "mentioned" by a tweet if the tweet has content and the user's username appears in that content following a @.
	//return an array of tweets
	@GetMapping("/@{username}/mentions")
	public String getMentions(@PathVariable String username) {
		return "I am working";
	}
	
	//Retrieves the followers of the user with the given username. Only active users should be included in the response. If no active user with the given 
	//username exists, an error should be sent in lieu of a respons
	//done
	@GetMapping("/@{username}/followers")
	public Set<UsersProjections> getFollowers(@PathVariable String username) throws Exception {
		
		if(!usersService.getFollowers(username).isEmpty()) {
			return usersService.getFollowers(username);
		}
		else
		{
			throw new Exception("No one is following you!");
		}
	}
	
	//Retrieves the users followed by the user with the given username. Only active users should be included in the response. If no active user with the given
	//username exists, an error should be sent in lieu of a response.
	//return an array of users
	//done
	@GetMapping("/@{username}/following")
	public Set<UsersProjections> getFollowing(@PathVariable String username) throws Exception {
		
		if(!usersService.getFollowing(username).isEmpty()) {
			return usersService.getFollowing(username);
		}
		else
		{
			throw new Exception("You arent following anyone!");
		}
	}
	
	
	//Creates a new user. If any required fields are missing or the username provided is already taken, an error should be sent in lieu of a response.
	//If the given credentials match a previously-deleted user, re-activate the deleted user instead of creating a new one.
	//return an user instance
	//done
	@Transactional
	@PostMapping()
	public UsersProjections createUser(@RequestBody Users user) throws Exception {
		Gson gson = new Gson();
		System.out.println(gson.toJson(user));
		
		if(validateService.checkName(user.getUsername())){
			
			user.setDeleted(usersService.findByusername(user.getUsername()).isDeleted());
			
			if(user.isDeleted() && validateService.checkPassword(user.getCredentials().getPassword())) {
				user.setId(usersService.findByusername(user.getUsername()).getId());
				user.setDeleted(false);
				usersService.toggleStatus(user.getUsername(), user.isDeleted());
				return usersService.getUsername(user.getUsername(), false);
			} 
			else {
				throw new Exception("Username is already taken!");
			}
		} 
		else 
		{
			usersService.createUser(user);
			return usersService.getUsername(user.getUsername(), false);
		}
	}
	
	//Subscribes the user whose credentials are provided by the request body to the user whose username is given in the url. If there is already a following 
	//relationship between the two users, no such followable user exists (deleted or never created), or the credentials provided do not match an active user 
	//in the database, an error should be sent as a response. If successful, no data is sent.
	//no return type
	//done
	@Transactional
	@PostMapping("@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody Credentials credentials) throws Exception {
		
		if(validateService.checkName(username) && validateService.checkPassword(credentials.getPassword())){
			Users follower = usersService.findByusername(credentials.getUsername());
			Users user =  usersService.findByusername(username);
			
			if(!user.getFollowers().contains(follower) && !user.isDeleted()) {
			user.setFollowers(follower);
			usersService.followUser(user);
			}
			else
			{
				throw new Exception("Could not follow!");
			}
		}
		else 
		{
			throw new Exception("User does not exist or password is wrong!");
		}
	}
	
	//Unsubscribes the user whose credentials are provided by the request body from the user whose username is given in the url. If there is no preexisting 
	//following relationship between the two users, no such followable user exists (deleted or never created), or the credentials provided do not match an 
	//active user in the database, an error should be sent as a response. If successful, no data is sent.
	//no return type
	//done
	@Transactional
	@PostMapping("/@{username}/unfollow")
	public void unfollowUser(@PathVariable String username, @RequestBody Credentials credentials) throws Exception {
		
		if(validateService.checkName(username) && validateService.checkPassword(credentials.getPassword())){
			Users follower = usersService.findByusername(credentials.getUsername());
			Users user =  usersService.findByusername(username);
			
			if(user.getFollowers().contains(follower) && !user.isDeleted()) {
				user.getFollowers().remove(follower);
				usersService.followUser(user);
			}
			else
			{
				throw new Exception("You are not following this user!");
			}
		}
		else 
		{
			throw new Exception("User does not exist or password is wrong!");
		}
	}
	
	//Updates the profile of a user with the given username. If no such user exists, the user is deleted, or the provided credentials do not match the user, 
	//an error should be sent in lieu of a response. In the case of a successful update, the returned user should contain the updated data.
	//return an users instance
	//mostly done
	@Transactional
	@PatchMapping("/@{username}")
	public UsersProjections updateUser(@PathVariable String username, @RequestBody Users user) throws Exception {
		user.setId(usersService.findByusername(username).getId());
		user.getProfile().setId(usersService.findByusername(username).getProfile().getId());
		
		if(!validateService.checkName(username) || user.isDeleted()) {
			throw new Exception("Could not update");
		}
		else 
		{
			usersService.updateUser(user);
			return usersService.getUsername(user.getUsername(), false);
		}
	}
	
	//"Deletes" a user with the given username. If no such user exists or the provided credentials do not match the user, an error should be sent in lieu of a 
	//response. If a user is successfully "deleted", the response should contain the user data prior to deletion.
	//IMPORTANT: This action should not actually drop any records from the database! Instead, develop a way to keep track of "deleted" users so that if a user 
	//is re-activated, all of their tweets and information are restored.
	//return an users instance
	//done
	@Transactional
	@DeleteMapping("/@{username}")
	public UsersProjections deleteUser(@PathVariable String username, @RequestBody Credentials credentials) throws Exception {
		Users user = usersService.findByusername(credentials.getUsername());
		if(validateService.checkName(user.getUsername()) && validateService.checkPassword(credentials.getPassword())) {
			user.setDeleted(true);
			usersService.toggleStatus(user.getUsername(), user.isDeleted());
			return usersService.getUsername(user.getUsername(), true);
		}
		else 
		{
			throw new Exception("User could not be deleted");
		}
		
	}
}
