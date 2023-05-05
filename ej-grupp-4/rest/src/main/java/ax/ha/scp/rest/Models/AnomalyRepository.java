package ax.ha.scp.rest.Models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalyRepository extends CrudRepository<Anomaly, String> {
}
