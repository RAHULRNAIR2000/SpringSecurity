package telusko.SpringSecurtiyLearning.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import telusko.SpringSecurtiyLearning.model.Users;

public interface UserRepo extends JpaRepository<Users,Integer> {
    Users findByUsername(String username);
}
