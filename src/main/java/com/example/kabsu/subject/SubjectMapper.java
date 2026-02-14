package com.example.kabsu.subject;

import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    public Subject toEntity(SubjectRequestDto dto) {
        return new Subject.SubjectBuilder()
                .name(dto.name())
                .code(dto.code())
                .description(dto.description())
                .units(dto.units())
                .type(dto.type())
                .build();
    }

    public void updateEntity(SubjectUpdateDto dto, Subject subject) {
        if (dto.name() != null) subject.setName(dto.name());
        if (dto.code() != null) subject.setCode(dto.code());
        if (dto.type() != null) subject.setType(dto.type());
        if (dto.type() != null) subject.setType(dto.type());
        if (dto.units() != null) subject.setUnits(dto.units());
        if (dto.description() != null) subject.setDescription(dto.description());
    }

    public SubjectResponseDto toDto(Subject subject) {
        return new SubjectResponseDto(
                subject.getId(),
                subject.getName(),
                subject.getCode(),
                subject.getDescription(),
                subject.getUnits(),
                subject.getType(),
                subject.getUpdatedAt(),
                subject.getCreatedAt()
        );
    }
}
