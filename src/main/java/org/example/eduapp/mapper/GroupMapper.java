package org.example.eduapp.mapper;

import org.example.eduapp.dto.GroupDto;
import org.example.eduapp.entity.Group;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupMapper {
    public Group toEntity(GroupDto.CreateGroup dto) {
        return Group.builder()
                .name(dto.getName())
                .build();
    }

    public GroupDto toDto(Group group) {
        return GroupDto.builder()
                .id(group.getId())
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .name(group.getName())
                .build();
    }

    public List<GroupDto> dtoList(List<Group> groups) {
        if (groups != null && !groups.isEmpty()) {
            return groups.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Group group, GroupDto.CreateGroup dto) {
        if (dto == null) {
            return;
        }
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            group.setName(dto.getName());
        }
    }
}
