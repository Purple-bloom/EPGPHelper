package custom.cyd.GuildHelper.Repository;


import custom.cyd.GuildHelper.Entity.Character;
import custom.cyd.GuildHelper.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    public List<Character> findByPlayer(Player player);
    public Optional<Character> findByNameIgnoreCase(String name);
}
