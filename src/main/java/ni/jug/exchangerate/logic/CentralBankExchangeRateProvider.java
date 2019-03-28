package ni.jug.exchangerate.logic;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import ni.jug.exchangerate.dto.CentralBankExchangeRateDTO;
import ni.jug.exchangerate.model.CentralBankExchangeRate;
import ni.jug.exchangerate.model.Currency;

/**
 *
 * @author aalaniz
 */
@Stateless
public class CentralBankExchangeRateProvider {

    @PersistenceContext
    EntityManager em;

    public long countByPeriodAndCurrency(LocalDate period, Currency currency) {
        LocalDate firstDay = period.withDayOfMonth(1);
        LocalDate lastDay = firstDay.plusMonths(1).minusDays(1);
        try {
            return em.createNamedQuery("countByCurrentPeriodAndCurrency", Long.class)
                    .setParameter("firstDay", firstDay)
                    .setParameter("lastDay", lastDay)
                    .setParameter("currency", currency)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return 0l;
        }
    }

    public long countByCurrentPeriodAndCurrency(Currency currency) {
        return countByPeriodAndCurrency(LocalDate.now(), currency);
    }

    public List<CentralBankExchangeRateDTO> findByPeriodAndCurrency(LocalDate period, Currency currency) {
        LocalDate firstDay = period.withDayOfMonth(1);
        LocalDate lastDay = firstDay.plusMonths(1).minusDays(1);
        return em.createNamedQuery("findByPeriodAndCurrency", CentralBankExchangeRate.class)
                .setParameter("firstDay", firstDay)
                .setParameter("lastDay", lastDay)
                .setParameter("currency", currency)
                .getResultList()
                .stream()
                .map(CentralBankExchangeRateDTO::new)
                .collect(Collectors.toList());
    }

    public List<CentralBankExchangeRateDTO> findByCurrentPeriodAndCurrency(Currency currency) {
        return findByPeriodAndCurrency(LocalDate.now(), currency);
    }

}
