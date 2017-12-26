package com.patsnap.magic.store.dao;

import com.patsnap.magic.store.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, String> {

    User findByUserName(String userName);

    User findByEmail(String email);

    User findByUserNameAndPassword(String userName, String password);
}
