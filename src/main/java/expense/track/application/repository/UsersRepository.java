package expense.track.application.repository;

import expense.track.application.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    Users findUserByEmailId(String email);

    @Query("from Users where emailId=:emailId")
    public Users setUser(@Param("emailId") String emailId);

    Users findUserByEmailIdAndPassword(String email, String password);

    @Query("from Users where userStatus=:userStatus")
    public List<Users> getUsersList(@Param("userStatus") String userStatus);

    Optional<Users> findByUserId(String userId);
}
