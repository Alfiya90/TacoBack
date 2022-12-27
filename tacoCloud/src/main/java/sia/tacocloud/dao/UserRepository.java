package sia.tacocloud.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sia.tacocloud.service.User;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {
    User findByUsername (String username);
}
