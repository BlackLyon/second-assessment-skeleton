package com.cooksys.ftd.service;

import org.springframework.stereotype.Service;

import com.cooksys.ftd.repository.CredentialsRepo;
import com.cooksys.ftd.repository.HashTagRepo;
import com.cooksys.ftd.repository.TweetsRepo;
import com.cooksys.ftd.repository.UsersRepo;

@Service
public class ValidateService {
	
	HashTagRepo hashTagRepo;
	UsersRepo usersRepo;
	CredentialsRepo credentialsRepo;
	TweetsRepo tweetsRepo;
	
	public ValidateService(HashTagRepo hRepo, UsersRepo uRepo, CredentialsRepo cRepo, TweetsRepo tRepo) {
		hashTagRepo = hRepo;
		usersRepo = uRepo;
		credentialsRepo = cRepo;
		tweetsRepo = tRepo;
	}
	
	public boolean checkName(String username) {
		if(usersRepo.findByusername(username) != null)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public boolean checkPassword(String password) {
		if(credentialsRepo.findBypassword(password) != null)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public boolean availableName(String username) {
		if(usersRepo.findByusername(username) == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkTweet(Long id) {
		if(tweetsRepo.findByIdAndIsDeleted(id, false) != null)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public boolean checkTag(String label) {
		System.out.println(label);
		
		if(hashTagRepo.findBylabel(label) != null){
			return true;
		}
		else
		{
			return false;
		}
	}

}
