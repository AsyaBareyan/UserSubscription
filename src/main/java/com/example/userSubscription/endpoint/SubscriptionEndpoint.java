package com.example.userSubscription.endpoint;

import com.example.userSubscription.dto.response.SubscriptionResponse;
import com.example.userSubscription.mapper.SubscriptionMapper;
import com.example.userSubscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionEndpoint {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    @GetMapping("/top")
    public List<SubscriptionResponse> getTopSubscriptions() {
        return subscriptionMapper.toDtoList(subscriptionService.getTopSubscriptions());
    }

}
