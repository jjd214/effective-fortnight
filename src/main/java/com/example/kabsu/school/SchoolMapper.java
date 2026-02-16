package com.example.kabsu.school;

import com.example.kabsu.school.request.SchoolRequestDto;
import com.example.kabsu.school.request.SchoolUpdateDto;
import com.example.kabsu.school.response.SchoolResponseDto;
import org.springframework.stereotype.Component;

@Component
public class SchoolMapper {

    public School toEntity(SchoolRequestDto dto) {
        return School
                .builder()
                .name(dto.name())
                .description(dto.description())
                .type(dto.type())
                .build();
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
