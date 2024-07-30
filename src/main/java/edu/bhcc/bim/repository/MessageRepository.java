package edu.bhcc.bim.repository;

import edu.bhcc.bim.entity.Conversation;
import edu.bhcc.bim.entity.Message;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findByConversation(Conversation conversation);

    List<Message> findByConversationOrderBySentAtAsc(Conversation conversation);
}
