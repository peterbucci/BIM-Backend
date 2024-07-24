package edu.bhcc.bim.repository;

import edu.bhcc.bim.entity.Conversation;

import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository extends CrudRepository<Conversation, Integer> {
}
