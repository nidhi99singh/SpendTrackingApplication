package expense.track.application.repository;

import expense.track.application.entity.OrderProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends MongoRepository<OrderProduct, String> {

}
