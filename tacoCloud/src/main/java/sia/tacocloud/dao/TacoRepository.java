package sia.tacocloud.dao;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import sia.tacocloud.data.model.Taco;

import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "tacos", path = "tacos")
public interface TacoRepository extends PagingAndSortingRepository <Taco, Long> {

    /*Iterable <Taco> findAll(PageRequest page);*/
    Optional<Taco> findById(Long id);
}
