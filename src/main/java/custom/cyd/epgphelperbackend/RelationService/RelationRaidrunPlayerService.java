package custom.cyd.epgphelperbackend.RelationService;

import custom.cyd.epgphelperbackend.Entity.Player;
import custom.cyd.epgphelperbackend.Entity.Raidrun;
import custom.cyd.epgphelperbackend.RelationRepository.RelationRaidrunPlayerRepository;
import custom.cyd.epgphelperbackend.Repository.PlayerRepository;
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
