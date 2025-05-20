package com.example.userSubscription.repository;

import com.example.userSubscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value =
            """
                    SELECT s.* FROM subscriptions s JOIN user_subscription us ON s.id = us.subscription_id 
                    GROUP BY s.id ORDER BY COUNT(us.user_id) DESC LIMIT 3
                    """,
            nativeQuery = true)
    List<Subscription> findTop3Subscriptions();

    Optional<Subscription> findByPlatformName(String platformName);
}
