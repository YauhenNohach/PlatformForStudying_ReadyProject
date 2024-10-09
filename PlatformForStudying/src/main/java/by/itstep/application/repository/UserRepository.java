package by.itstep.application.repository;

import by.itstep.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends
        JpaRepository<User, Long>,
        QuerydslPredicateExecutor<User>
{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
           "SET a.enabled = TRUE WHERE a.email = ?1")
    void enableUser(String email);

    boolean existsByEmail(String email);

//    @Query("SELECT u FROM User u WHERE CONCAT(u.firstname, ' ', u.lastname) = :createdBy")
//    Optional<User> findUserByCreatedBy(@Param("createdBy") String createdBy);
}
