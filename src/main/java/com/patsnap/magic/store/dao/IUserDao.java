package com.patsnap.magic.store.dao;

import com.patsnap.magic.store.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, String> {
}
