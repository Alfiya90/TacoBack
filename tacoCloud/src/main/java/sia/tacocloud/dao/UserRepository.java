package sia.tacocloud.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import sia.tacocloud.service.UserDAO;

@Repository
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends CrudRepository <UserDAO, Long> {
    UserDAO findByUsername (String username);
}
