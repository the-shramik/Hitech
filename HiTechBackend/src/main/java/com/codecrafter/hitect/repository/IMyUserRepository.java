package com.codecrafter.hitect.repository;

import com.codecrafter.hitect.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMyUserRepository extends JpaRepository<MyUser,Long> {
}
