package ni.jug.exchangerate.logic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ni.jug.exchangerate.model.Currency;

/**
 *
 * @author aalaniz
 */
@Stateless
public class CurrencyProvider {

    @PersistenceContext
    EntityManager em;

    public Currency getCurrencyByIsoStringCode(String isoStringCode) {
        return em.createNamedQuery("byIsoStringCode", Currency.class)
                .setParameter("isoStringCode", isoStringCode)
                .getSingleResult();
    }

}
