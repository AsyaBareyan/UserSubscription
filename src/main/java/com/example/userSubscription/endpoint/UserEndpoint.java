package com.example.userSubscription.endpoint;

import com.example.userSubscription.dto.request.SubscriptionRequest;
import com.example.userSubscription.dto.request.UserRequest;
import com.example.userSubscription.dto.response.SubscriptionResponse;
import com.example.userSubscription.dto.response.UserResponse;
import com.example.userSubscription.mapper.SubscriptionMapper;
import com.example.userSubscription.mapper.UserMapper;
import com.example.userSubscription.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;
    private final UserMapper userMapper;
    private final SubscriptionMapper subscriptionMapper;


    @PostMapping
    public UserResponse save(@Valid @RequestBody UserRequest request) {
        return userMapper.toResponse(userService.save(request));
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userMapper.toResponse(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public UserResponse updateUserById(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        var updatedUser = userService.updateUserById(id, userRequest);
        return userMapper.toResponse(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PostMapping("/{id}/subscriptions")
    public UserResponse addSubscriptionToUser(@PathVariable Long id, @Valid @RequestBody SubscriptionRequest subscription) {
        var user = userService.addSubscriptionToUser(id, subscription);
        return userMapper.toResponse(user);
    }

    @GetMapping("/{id}/subscriptions")
    public List<SubscriptionResponse> getUserSubscriptions(@PathVariable Long id) {
        var userSubscriptions = userService.getUserSubscriptions(id);
        return subscriptionMapper.toDtoList(userSubscriptions);
    }

    @DeleteMapping("/{id}/subscriptions/{subscriptionId}")
    public void deleteUserSubscription(@PathVariable Long id, @PathVariable Long subscriptionId) {
        userService.deleteUserSubscription(id, subscriptionId);
    }

}
