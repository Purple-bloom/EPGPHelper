package custom.cyd.GuildHelper.RelationService;

import custom.cyd.GuildHelper.Entity.Player;
import custom.cyd.GuildHelper.Entity.Raidrun;
import custom.cyd.GuildHelper.RelationRepository.RelationRaidrunPlayerRepository;
import custom.cyd.GuildHelper.Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RelationRaidrunPlayerService {
    Logger logger = Logger.getLogger(RelationRaidrunPlayerService.class.getName());

    @Autowired
    private RelationRaidrunPlayerRepository relationRaidrunPlayerRepository;
    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getPlayerForRaidrun(Raidrun raidrun){
        return null;
    }

    
}
