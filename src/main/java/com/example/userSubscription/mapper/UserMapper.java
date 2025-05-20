package com.example.userSubscription.mapper;


import com.example.userSubscription.dto.request.UserRequest;
import com.example.userSubscription.dto.response.UserResponse;
import com.example.userSubscription.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract User mapToEntity(UserRequest request);

    public abstract UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntity(@MappingTarget User user, UserRequest request);
}
