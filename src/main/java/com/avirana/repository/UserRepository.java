package com.avirana.repository;

import com.avirana.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  UserEntity findByEmail(String email);

  UserEntity findByEmailAndOrganization(String email, String org);
}
