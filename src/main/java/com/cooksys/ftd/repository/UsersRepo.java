package com.cooksys.ftd.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.ftd.entity.Users;
import com.cooksys.ftd.projections.UsersProjections;

public interface UsersRepo extends JpaRepository<Users, Long>{
	
	Users findByusername(String name);
	
	UsersProjections findByusernameAndIsDeleted(String name, boolean active);
	
	List<UsersProjections> findByisDeleted(boolean active);
	
	Set<UsersProjections> findFollowersByFollowing_UsernameAndIsDeleted(String username, boolean active);
	Set<UsersProjections> findFollowingByFollowers_UsernameAndIsDeleted(String username, boolean active);
}
