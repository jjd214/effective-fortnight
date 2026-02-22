package com.example.kabsu.subject;

import com.example.kabsu.subject.request.SubjectRequest;
import com.example.kabsu.subject.request.SubjectUpdateRequest;
import com.example.kabsu.subject.response.SubjectResponse;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    public Subject toEntity(final SubjectRequest request) {
        return Subject
                .builder()
                .name(request.name())
                .code(request.code())
                .description(request.description())
                .units(request.units())
                .type(request.type())
                .build();
    }

    public void updateEntity(final SubjectUpdateRequest request, Subject subject) {
        if (request.name() != null) subject.setName(request.name());
        if (request.code() != null) subject.setCode(request.code());
        if (request.type() != null) subject.setType(request.type());
        if (request.type() != null) subject.setType(request.type());
        if (request.units() != null) subject.setUnits(request.units());
        if (request.description() != null) subject.setDescription(request.description());
    }

    public SubjectResponse toSubjectResponse(Subject subject) {
        return new SubjectResponse(
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
