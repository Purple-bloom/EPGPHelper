package custom.cyd.epgphelperbackend.Repository;


import custom.cyd.epgphelperbackend.Entity.Character;
import custom.cyd.epgphelperbackend.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    public List<Character> findByPlayer(Player player);
    public Optional<Character> findByNameIgnoreCase(String name);
}
