package custom.cyd.EpgpHelperBackend.Repository;

import custom.cyd.EpgpHelperBackend.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
