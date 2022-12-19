package sia.tacocloud.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import sia.tacocloud.data.model.TacosOrder;

@Component
public interface OrderRepository extends CrudRepository <TacosOrder, Long> {
    TacosOrder save(TacosOrder order);
}
