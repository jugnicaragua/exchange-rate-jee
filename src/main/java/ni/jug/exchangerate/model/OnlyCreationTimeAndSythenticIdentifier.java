package ni.jug.exchangerate.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author aalaniz
 */
@MappedSuperclass
@EntityListeners(OnlyCreationTimeAndSythenticIdentifierListener.class)
public abstract class OnlyCreationTimeAndSythenticIdentifier<K> extends SyntheticIdentifier<K> {

    protected LocalDateTime createdOn;

    @Column(name = "created_on", updatable = false)
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

}
