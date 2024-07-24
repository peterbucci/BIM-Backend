package edu.bhcc.bim.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import edu.bhcc.bim.entity.Friendship;
import edu.bhcc.bim.entity.FriendshipId;

public interface FriendshipRepository extends CrudRepository<Friendship, FriendshipId> {
    List<Friendship> findByUserId1OrUserId2(Integer userId1, Integer userId2);
}
