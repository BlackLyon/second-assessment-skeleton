package com.cooksys.ftd.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cooksys.ftd.entity.Users;
import com.cooksys.ftd.projections.UsersProjections;
import com.cooksys.ftd.repository.UsersRepo;

@Service
public class UsersService {
	private UsersRepo usersRepo;
	
	public UsersService(UsersRepo repo)
	{
		usersRepo = repo;
	}
	
	public Users get(Long id) {
		return usersRepo.findOne(id);
	}
	
	public Users findByusername(String name) {
		return usersRepo.findByusername(name);
	}
	
	public UsersProjections getUsername(String name, boolean status) {
		return usersRepo.findByusernameAndIsDeleted(name, status);
	}
	
	public List<UsersProjections> getUsers() {
		return usersRepo.findByisDeleted(false);
	}
	public Set<UsersProjections> getFollowers(String username) {
		return usersRepo.findFollowersByFollowing_UsernameAndIsDeleted(username, false);
	}
	
	public Set<UsersProjections> getFollowing(String username) {
		return usersRepo.findFollowingByFollowers_UsernameAndIsDeleted(username, false);
	}
	
	public void createUser(Users user) {
		usersRepo.saveAndFlush(user);
	}
	
	public void followUser(Users user) {
		usersRepo.saveAndFlush(user);
	}
	
	public void updateUser(Users user) {
		usersRepo.saveAndFlush(user);
	}
	
	public void toggleStatus(String name, boolean status) {
		Users user = usersRepo.findByusername(name);
		user.setDeleted(status);
		usersRepo.saveAndFlush(user);
	}
}
