package com.example.kabsu.school;

import com.example.kabsu.school.request.SchoolRequest;
import com.example.kabsu.school.request.SchoolUpdateRequest;
import com.example.kabsu.school.response.SchoolResponse;
import org.springframework.stereotype.Component;

@Component
public class SchoolMapper {

    public School toEntity(final SchoolRequest request) {
        return School
                .builder()
                .name(request.name())
                .description(request.description())
                .type(request.type())
                .build();
    }

    public SchoolResponse toSchoolResponse(School school) {
        return new SchoolResponse(
                school.getId(),
                school.getName(),
                school.getDescription(),
                school.getType(),
                school.getUpdatedAt(),
                school.getCreatedAt()
        );
    }

    public void updateEntity(School school, final SchoolUpdateRequest request) {
        if (request.name() != null) school.setName(request.name());
        if (request.description() != null) school.setDescription(request.description());
        if (request.type() != null) school.setType(request.type());
    }

}
