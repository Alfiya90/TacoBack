package sia.tacocloud.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import sia.tacocloud.data.model.TacosOrder;

@Repository
@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends CrudRepository <TacosOrder, Long> {
    TacosOrder save(TacosOrder order);
}
