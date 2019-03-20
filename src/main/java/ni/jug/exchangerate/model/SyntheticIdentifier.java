package ni.jug.exchangerate.model;

import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author aalaniz
 */
@MappedSuperclass
public abstract class SyntheticIdentifier<K> {

    protected K id;

    @Id
    @GeneratedValue(generator = "seq", strategy = GenerationType.SEQUENCE)
    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SyntheticIdentifier)) {
            return false;
        }
        final SyntheticIdentifier<?> other = (SyntheticIdentifier<?>) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
