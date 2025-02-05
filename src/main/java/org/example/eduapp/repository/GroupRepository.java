package org.example.eduapp.repository;

import jakarta.transaction.Transactional;
import org.example.eduapp.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from student_group as sg where sg.student_id=?1 and sg.group_id=?2", nativeQuery = true)
    void deleteStudent(Long studentId, Long groupId);

    @Modifying
    @Transactional
    @Query(value = "delete from teacher_group as sg where sg.teacher_id=?1 and sg.group_id=?2", nativeQuery = true)
    void deleteTeacher(Long teacherId, Long groupId);

    @Query(value = "select g.* from groups as g\n" +
            "inner join teacher_group as tg on g.id=tg.group_id\n" +
            "where tg.teacher_id=?1", nativeQuery = true)
    Page<Group> getAllGroupByTeacherId(Long teacherId, Pageable pageable);
}
