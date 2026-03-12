package custom.cyd.EpgpHelperBackend.Repository;

import custom.cyd.EpgpHelperBackend.Entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, String> {
    Optional<Setting> findBySettingNameIgnoreCase(String name);
}
