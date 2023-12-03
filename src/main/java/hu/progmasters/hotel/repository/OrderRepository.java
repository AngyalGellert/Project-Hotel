package hu.progmasters.hotel.repository;

import hu.progmasters.hotel.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {


    PurchaseOrder findByUniqueId(String uniqueId);

}
