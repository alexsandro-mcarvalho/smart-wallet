package com.project.smart_wallet.mapper;

import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.dto.response.RegisterResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegisterMapper {

    public static RegisterResponse toResponse(User user) {
        return new RegisterResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }
}
