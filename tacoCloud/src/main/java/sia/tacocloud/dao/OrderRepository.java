package sia.tacocloud.dao;

import org.springframework.stereotype.Component;
import sia.tacocloud.data.model.TacosOrder;

@Component
public interface OrderRepository {
    TacosOrder save(TacosOrder order);
}
