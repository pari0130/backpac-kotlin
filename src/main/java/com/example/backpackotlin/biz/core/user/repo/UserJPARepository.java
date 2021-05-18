package com.example.backpackotlin.biz.core.user.repo;

import com.example.backpackotlin.biz.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<User, Long> {
}
