package com.example.kabsu.school;

import org.springframework.stereotype.Component;

@Component
public class SchoolMapper {

    public School toEntity(SchoolRequestDto dto) {
        return new School()
                .setName(dto.name())
                .setDescription(dto.description())
                .setType(dto.type());
    }

    public SchoolResponseDto toDto(School school) {
        return new SchoolResponseDto(
                school.getId(),
                school.getName(),
                school.getDescription(),
                school.getType(),
                school.getUpdatedAt(),
                school.getCreatedAt()
        );
    }

    public void updateEntity(School school, SchoolUpdateDto dto) {
        if (dto.name() != null) school.setName(dto.name());
        if (dto.description() != null) school.setDescription(dto.description());
        if (dto.type() != null) school.setType(dto.type());
    }

}
