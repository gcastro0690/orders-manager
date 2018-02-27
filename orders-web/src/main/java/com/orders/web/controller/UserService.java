package com.orders.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.persistence.model.mysql.entity.User;
import com.orders.persistence.model.mysql.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  UserRepository repo;

  public List<User> findAll() {
    return repo.findAll();
  }
}