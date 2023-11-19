package com.yelnar.ebook.repository;

import com.yelnar.ebook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    org.springframework.security.core.userdetails.User findByEmail(String email);
}
