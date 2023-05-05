package ax.ha.scp.rest.Models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends CrudRepository<Observation, Long> {
    List<Observation> findByAnomaly(Anomaly anomaly);
}
