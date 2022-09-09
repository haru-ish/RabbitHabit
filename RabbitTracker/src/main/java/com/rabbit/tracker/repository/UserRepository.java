package com.rabbit.tracker.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(new User("h.ono@frevo-works.co.jp", "おの"));
    }

}
