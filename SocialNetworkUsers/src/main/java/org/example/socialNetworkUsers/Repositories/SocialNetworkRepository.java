package org.example.socialNetworkUsers.Repositories;

import org.example.socialNetworkUsers.Entity.SocialNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialNetworkRepository extends JpaRepository<SocialNetwork, Integer> {
}
