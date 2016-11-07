package com.cooksys.ftd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.ftd.entity.Profile;

public interface ProfileRepo extends JpaRepository<Profile, Long> {

}
