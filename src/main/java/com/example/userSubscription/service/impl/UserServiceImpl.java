package com.example.userSubscription.service.impl;

import com.example.userSubscription.dto.request.SubscriptionRequest;
import com.example.userSubscription.dto.request.UserRequest;
import com.example.userSubscription.entity.Subscription;
import com.example.userSubscription.entity.User;
import com.example.userSubscription.exception.app.AlreadySubscribedException;
import com.example.userSubscription.exception.app.SubscriptionNotFoundException;
import com.example.userSubscription.exception.app.UserAlreadyExistsException;
import com.example.userSubscription.exception.app.UserNotFoundException;
import com.example.userSubscription.mapper.SubscriptionMapper;
import com.example.userSubscription.mapper.UserMapper;
import com.example.userSubscription.repository.SubscriptionRepository;
import com.example.userSubscription.repository.UserRepository;
import com.example.userSubscription.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserMapper userMapper;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public User save(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            String message = "User with email " + request.email() + " already exists.";
            log.warn(message);
            throw new UserAlreadyExistsException(message);
        }
        User user = userMapper.mapToEntity(request);
        User savedUser = userRepository.save(user);
        log.info("User with email {} has been successfully created", savedUser.getEmail());
        return savedUser;
    }

    @Override
    public User getUserById(Long id) {
        return findUserByIdOrThrow(id);
    }

    @Override
    public User updateUserById(Long id, UserRequest request) {
        User existingUser = findUserByIdOrThrow(id);
        userMapper.updateEntity(existingUser, request);
        User updatedUser = userRepository.save(existingUser);
        log.info("User with id {} has been successfully updated", updatedUser.getId());
        return updatedUser;
    }

    @Override
    public void deleteById(Long id) {
        User user = findUserByIdOrThrow(id);
        userRepository.deleteById(user.getId());
        log.info("User with id {} has been successfully deleted", id);
    }

    @Override
    @Transactional
    public User addSubscriptionToUser(Long userId, SubscriptionRequest request) {
        User user = findUserByIdOrThrow(userId);

        if (isAlreadySubscribed(user, request.platformName())) {
            log.warn("User with ID {} is already subscribed to platform: {}", userId, request.platformName());
            throw new AlreadySubscribedException("You are already subscribed to this platform");
        }

        Subscription subscription = subscriptionRepository.findByPlatformName(request.platformName())
                .orElseGet(() -> createAndSaveSubscription(request));

        user.getSubscriptions().add(subscription);

        User savedUser = userRepository.save(user);
        log.info("Subscription {} added to user {}", subscription.getPlatformName(), user.getEmail());
        return savedUser;
    }

    @Override
    public List<Subscription> getUserSubscriptions(Long userId) {
        List<Subscription> subscriptions = List.copyOf(findUserByIdOrThrow(userId).getSubscriptions());
        log.info("User with ID {} has {} subscriptions", userId, subscriptions.size());
        return subscriptions;
    }

    @Override
    public void deleteUserSubscription(Long userId, Long subscriptionId) {
        User user = findUserByIdOrThrow(userId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found"));

        boolean removed = user.getSubscriptions().remove(subscription);
        if (removed) {
            userRepository.save(user);
            log.info("Subscription with ID {} removed from user ID {}", subscriptionId, userId);
        } else {
            log.warn("Subscription with ID {} not found in user's subscription list", subscriptionId);
        }
    }

    private User findUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new UserNotFoundException(id);
                });
    }

    private Subscription createAndSaveSubscription(SubscriptionRequest request) {
        Subscription subscription = subscriptionMapper.mapToEntity(request);
        return subscriptionRepository.save(subscription);
    }

    private boolean isAlreadySubscribed(User user, String platformName) {
        return user.getSubscriptions().stream()
                .anyMatch(sub -> sub.getPlatformName().equalsIgnoreCase(platformName));
    }
}
