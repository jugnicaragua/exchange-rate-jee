package ni.jug.exchangerate.model;

import java.time.LocalDateTime;
import javax.persistence.PrePersist;

/**
 *
 * @author aalaniz
 */
public class OnlyCreationTimeAndSythenticIdentifierListener {

    @PrePersist
    public void beforeInsert(OnlyCreationTimeAndSythenticIdentifier onlyCreationTime) {
        onlyCreationTime.setCreatedOn(LocalDateTime.now());
    }

}
