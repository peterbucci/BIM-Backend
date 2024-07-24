package edu.bhcc.bim.repository;

import org.springframework.data.repository.CrudRepository;

import edu.bhcc.bim.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
