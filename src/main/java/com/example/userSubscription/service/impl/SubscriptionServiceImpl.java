package com.example.userSubscription.service.impl;

import com.example.userSubscription.entity.Subscription;
import com.example.userSubscription.repository.SubscriptionRepository;
import com.example.userSubscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> getTopSubscriptions() {
        return subscriptionRepository.findTop3Subscriptions();
    }
}
