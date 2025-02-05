package org.example.eduapp.repository;

import jakarta.transaction.Transactional;
import org.example.eduapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "delete from user_roles as us where us.user_id=?1 and us.role_id=?2", nativeQuery = true)
    void deleteRoleFromUser(Long userId, Long roleId);

    @Query(value = "select u.* from users as u\n" +
            "inner join token as t on u.id = t.user_id\n" +
            "where t.token=?1", nativeQuery = true)
    Optional<User> getUserByToken(String token);

    @Query(value = "select u.* from users as u\n" +
            "inner join user_roles as ur on u.id = ur.user_id\n" +
            "inner join roles as r on ur.role_id = r.id\n" +
            "where r.name='STUDENT'", nativeQuery = true)
    Page<User> getAllStudents(Pageable pageable);

    @Query(value = "select u.* from users as u\n" +
            "inner join user_roles as ur on u.id = ur.user_id\n" +
            "inner join roles as r on ur.role_id = r.id\n" +
            "where r.name='TEACHER'", nativeQuery = true)
    Page<User> getAllTeacher(Pageable pageable);

    @Query(value = "select u.* from users as u\n" +
            "inner join user_roles as ur on u.id = ur.user_id\n" +
            "inner join roles as r on ur.role_id = r.id\n" +
            "where r.name='PARENTS'", nativeQuery = true)
    Page<User> getAllParents(Pageable pageable);

    @Query(value = "select u.* from student_group as sg \n" +
            "inner join users as u on u.id=sg.student_id\n" +
            "where sg.group_id=?1", nativeQuery = true)
    Page<User> getAllStudentByGroupId(Long groupId, Pageable pageable);

    @Query(value = "select u.* from users as u\n" +
            "inner join subject_teacher as st on st.teacher_id=u.id\n" +
            "where st.subject_id=?1", nativeQuery = true)
    Page<User> getAllTeacherBySubjectId(Long subjectId, Pageable pageable);
}
