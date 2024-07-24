package edu.bhcc.bim.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import edu.bhcc.bim.entity.UserStatus;

public interface UserStatusRepository extends CrudRepository<UserStatus, Integer> {
    Optional<UserStatus> findByUserId(Integer userId);
}
