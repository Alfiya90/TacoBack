package sia.tacocloud.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sia.tacocloud.service.UserDAO;

@Repository
public interface UserRepository extends CrudRepository <UserDAO, Long> {
    UserDAO findByUsername (String username);
}
