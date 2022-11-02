package com.github.iamhi.hizone.authentication.v2.core.mappers;

import com.github.iamhi.hizone.authentication.v2.core.dto.UserDto;
import com.github.iamhi.hizone.authentication.v2.out.postgres.models.UserEntity;

public interface UserMapper {

    UserDto fromEntity(UserEntity userEntity);

    UserEntity fromDto(UserDto userDto);

    String toString(UserDto userDto);

    UserDto fromString(String userData);
}
