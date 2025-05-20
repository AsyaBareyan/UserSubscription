package com.example.userSubscription.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ExceptionMessage {
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
	private String trace;

	public static ExceptionMessage exceptionMessage(Exception exception, int httpStatus, HttpServletRequest request) {
		return ExceptionMessage.builder()
				.timestamp(LocalDateTime.now())
				.status(httpStatus)
				.message(exception.getMessage())
				.path(request.getRequestURI())
				.build();
	}
}
