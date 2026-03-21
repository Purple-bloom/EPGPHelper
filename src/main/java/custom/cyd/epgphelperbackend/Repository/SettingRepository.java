package custom.cyd.epgphelperbackend.Repository;

import custom.cyd.epgphelperbackend.Entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, String> {
    Optional<Setting> findBySettingNameIgnoreCase(String name);
}
