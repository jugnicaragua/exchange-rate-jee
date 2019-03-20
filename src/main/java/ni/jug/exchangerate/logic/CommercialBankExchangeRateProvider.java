package ni.jug.exchangerate.logic;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import ni.jug.exchangerate.dto.CommercialBankExchangeRateDTO;
import ni.jug.exchangerate.model.Bank;
import ni.jug.exchangerate.model.CommercialBankExchangeRate;

/**
 *
 * @author aalaniz
 */
@Stateless
public class CommercialBankExchangeRateProvider {

    @PersistenceContext
    EntityManager em;

    public CommercialBankExchangeRate findByBankAndDate(Bank bank, LocalDate date) {
        try {
            return em.createNamedQuery("findByBankAndDate", CommercialBankExchangeRate.class)
                    .setParameter("bank", bank)
                    .setParameter("date", date)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<CommercialBankExchangeRateDTO> findByDate(LocalDate date) {
        return em.createNamedQuery("findByDate", CommercialBankExchangeRate.class)
                .setParameter("date", date)
                .getResultList()
                .stream()
                .map(CommercialBankExchangeRateDTO::new)
                .collect(Collectors.toList());
    }

    public List<CommercialBankExchangeRateDTO> findByBankAndPeriod(Bank bank, LocalDate period) {
        LocalDate firstDay = period.withDayOfMonth(1);
        LocalDate lastDay = firstDay.plusMonths(1).minusDays(1);
        return em.createNamedQuery("findByBankAndRangeOfDates", CommercialBankExchangeRate.class)
                .setParameter("bank", bank)
                .setParameter("firstDay", firstDay)
                .setParameter("lastDay", lastDay)
                .getResultList()
                .stream()
                .map(CommercialBankExchangeRateDTO::new)
                .collect(Collectors.toList());
    }

    public List<CommercialBankExchangeRateDTO> findByPeriod(LocalDate period) {
        LocalDate firstDay = period.withDayOfMonth(1);
        LocalDate lastDay = firstDay.plusMonths(1).minusDays(1);
        return em.createNamedQuery("findByRangeOfDates", CommercialBankExchangeRate.class)
                .setParameter("firstDay", firstDay)
                .setParameter("lastDay", lastDay)
                .getResultList()
                .stream()
                .map(CommercialBankExchangeRateDTO::new)
                .collect(Collectors.toList());
    }

    public List<CommercialBankExchangeRateDTO> findByCurrentPeriod() {
        return findByPeriod(LocalDate.now());
    }

}
