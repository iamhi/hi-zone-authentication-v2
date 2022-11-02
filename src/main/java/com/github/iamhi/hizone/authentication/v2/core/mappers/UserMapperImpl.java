package com.github.iamhi.hizone.authentication.v2.core.mappers;

import com.github.iamhi.hizone.authentication.v2.core.dto.UserDto;
import com.github.iamhi.hizone.authentication.v2.out.postgres.models.UserEntity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
record UserMapperImpl(
    Gson gson
) implements UserMapper {

    private static final String JSON_UUID_PROP = "uuid";
    private static final String JSON_USERNAME_PROP = "username";
    private static final String JSON_ROLES_PROP = "roles";
    private static final String JSON_ARRAY_DELIMITER_PROP = ",";

    @Override
    public UserDto fromEntity(UserEntity userEntity) {
        return new UserDto(
            userEntity.getUuid(),
            userEntity.getUsername(),
            userEntity.getEmail(),
            Arrays.stream(userEntity.getRoles().split(JSON_ARRAY_DELIMITER_PROP)).toList()
        );
    }

    @Override
    public UserEntity fromDto(UserDto userDto) {
        return new UserEntity(
            userDto.uuid(),
            userDto.username(),
            userDto.email(),
            String.join(JSON_ARRAY_DELIMITER_PROP, userDto.roles())
        );
    }

    @Override
    public String toString(UserDto userDto) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(JSON_UUID_PROP, userDto.uuid());
        jsonObject.addProperty(JSON_USERNAME_PROP, userDto.username());
        jsonObject.addProperty(JSON_ROLES_PROP, String.join(JSON_ARRAY_DELIMITER_PROP, userDto.roles()));

        return jsonObject.toString();
    }

    @Override
    public UserDto fromString(String userData) {
        JsonObject jsonObject = gson.fromJson(userData, JsonObject.class);

        return new UserDto(
            jsonObject.has(JSON_UUID_PROP) ? jsonObject.get(JSON_UUID_PROP).getAsString() : StringUtils.EMPTY,
            jsonObject.has(JSON_USERNAME_PROP) ? jsonObject.get(JSON_USERNAME_PROP).getAsString() : StringUtils.EMPTY,
            StringUtils.EMPTY,
            jsonObject.has(JSON_ROLES_PROP) ? Arrays.stream(jsonObject.get(JSON_ROLES_PROP).getAsString().split(JSON_ARRAY_DELIMITER_PROP)).toList() : new ArrayList<>()
        );
    }
}
