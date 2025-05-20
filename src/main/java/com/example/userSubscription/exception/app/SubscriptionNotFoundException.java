package com.example.userSubscription.exception.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Subscription not found")
public class SubscriptionNotFoundException extends RuntimeException {

	public SubscriptionNotFoundException(String message) {
		super(format(message));
	}
}
