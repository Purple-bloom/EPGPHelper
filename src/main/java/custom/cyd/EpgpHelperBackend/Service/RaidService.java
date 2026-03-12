package custom.cyd.EpgpHelperBackend.Service;

import custom.cyd.EpgpHelperBackend.Entity.Raid;
import custom.cyd.EpgpHelperBackend.Repository.RaidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RaidService {

    @Autowired
    private RaidRepository raidRepository;

    public List<Raid> getAllRaids(){
        return raidRepository.findAll();
    }

    public Optional<Raid> getRaid(Long id){
        return raidRepository.findById(id);
    }

    public Raid createRaid(Raid raid){
        return raidRepository.save(raid);
    }
}
