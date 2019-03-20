package ni.jug.exchangerate.model;

import java.time.LocalDateTime;
import javax.persistence.PrePersist;

/**
 *
 * @author aalaniz
 */
public class OnlyCreationTimeListener {

    @PrePersist
    public void beforeInsert(OnlyCreationTime onlyCreationTime) {
        onlyCreationTime.setCreatedOn(LocalDateTime.now());
    }

}
