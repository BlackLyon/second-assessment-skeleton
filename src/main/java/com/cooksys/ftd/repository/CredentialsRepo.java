package com.cooksys.ftd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.ftd.entity.Credentials;

public interface CredentialsRepo extends JpaRepository<Credentials, Long> {

	Credentials findBypassword(String password);
}
