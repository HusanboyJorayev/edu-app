package org.example.eduapp.mapper;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.aws.S3Service;
import org.example.eduapp.dto.AttachmentDto;
import org.example.eduapp.entity.Attachment;
import org.example.eduapp.exceptions.BadRequestException;
import org.example.eduapp.repository.AttachmentRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AttachmentMapper {
    private final AttachmentRepository attachmentRepository;
    private final S3Service s3Service;

    public Attachment toEntity(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File can not be null");
        }
        try {
            String cloudPath = this.s3Service.uploadFile(file.getBytes());

            Attachment attachment = new Attachment();
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());
            attachment.setOriginName(file.getOriginalFilename());
            attachment.setCreatedDate(LocalDateTime.now());
            attachment.setCloudPath(cloudPath);
            return this.attachmentRepository.save(attachment);

        } catch (Exception e) {
            throw new BadRequestException("SOMETHING ERROR TO UPLOAD IMAGE");
        }
    }

    public AttachmentDto toDto(Attachment attachment) {
        return AttachmentDto.builder()
                .id(attachment.getId())
                .cloudPath(attachment.getCloudPath())
                .size(attachment.getSize())
                .contentType(attachment.getContentType())
                .type(attachment.getType())
                .originName(attachment.getOriginName())
                .createdDate(attachment.getCreatedDate())
                .updatedDate(attachment.getUpdatedDate())
                .build();
    }

    public List<AttachmentDto> dtoList(List<Attachment> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }
}
