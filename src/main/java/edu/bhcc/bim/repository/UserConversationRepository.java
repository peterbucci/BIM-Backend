package edu.bhcc.bim.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import edu.bhcc.bim.entity.UserConversation;
import edu.bhcc.bim.entity.UserConversationId;

public interface UserConversationRepository extends CrudRepository<UserConversation, UserConversationId> {
    List<UserConversation> findByUserId(Integer userId);

    List<UserConversation> findByConversationId(Integer conversationId);
}
