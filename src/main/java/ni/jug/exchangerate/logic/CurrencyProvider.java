package ni.jug.exchangerate.logic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import ni.jug.exchangerate.model.Currency;

/**
 *
 * @author aalaniz
 */
@Stateless
public class CurrencyProvider {

    private static final String DOLLAR_ISO_STRING_CODE = "USD";

    @PersistenceContext
    EntityManager em;

    public Currency getCurrencyByIsoStringCode(String isoStringCode) {
        try {
            return em.createNamedQuery("byIsoStringCode", Currency.class)
                    .setParameter("isoStringCode", isoStringCode)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public Currency getDollar() {
        return getCurrencyByIsoStringCode(DOLLAR_ISO_STRING_CODE);
    }

}
