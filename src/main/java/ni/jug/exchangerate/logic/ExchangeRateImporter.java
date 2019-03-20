package ni.jug.exchangerate.logic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ni.jug.cb.exchangerate.ExchangeRateCBClient;
import ni.jug.cb.exchangerate.ExchangeRateTrade;
import ni.jug.exchangerate.model.Bank;
import ni.jug.exchangerate.model.CentralBankExchangeRate;
import ni.jug.exchangerate.model.CommercialBankExchangeRate;
import ni.jug.exchangerate.model.Currency;
import ni.jug.ncb.exchangerate.ExchangeRateScraper;
import ni.jug.ncb.exchangerate.MonthlyExchangeRate;

/**
 *
 * @author aalaniz
 */
@Singleton
public class ExchangeRateImporter {

    private static final String DOLLAR_ISO_STRING_CODE = "USD";

    @PersistenceContext
    EntityManager em;

    @Inject
    CurrencyProvider currencyProvider;

    @Inject
    CommercialBankExchangeRateProvider commercialBankExchangeRateProvider;

    @Inject
    CentralBankExchangeRateProvider centralBankExchangeRateProvider;

    private Currency getDollar() {
        return currencyProvider.getCurrencyByIsoStringCode(DOLLAR_ISO_STRING_CODE);
    }

    @Schedule(hour = "*", minute = "*", second = "*/20", persistent = false)
    public void importCentralBankExchangeRate() {
        MonthlyExchangeRate currentMonthExchangeRate = new ExchangeRateScraper().getCurrentMonthExchangeRate();

        Currency dollar = getDollar();
        long dbRowCount = centralBankExchangeRateProvider.countByCurrentPeriodAndCurrency(dollar);
        if (dbRowCount == currentMonthExchangeRate.size()) {
            System.out.println("----> Los datos ya fueron importados");
            return;
        }

        System.out.println("----> Importando datos del sitio web del BCN");
        for (Map.Entry<LocalDate, BigDecimal> exchangeRateByDate : currentMonthExchangeRate.getMonthlyExchangeRate().entrySet()) {
            CentralBankExchangeRate exchangeRate = new CentralBankExchangeRate(dollar, exchangeRateByDate.getKey(),
                    exchangeRateByDate.getValue());
            em.merge(exchangeRate);
        }
    }

    @Schedule(hour = "*", minute = "*/10", persistent = false)
    public void importCommercialBankExchangeRate() {
        System.out.println("----> Leyendo compra/venta de los bancos comerciales");
        ExchangeRateCBClient client = ExchangeRateCBClient.scrapAndRepeatIfNecessary();
        for (ExchangeRateTrade trade : client.trades()) {
            Bank bank = Bank.valueOf(trade.bank());
            CommercialBankExchangeRate exchangeRate = commercialBankExchangeRateProvider.findByBankAndDate(bank, trade.date());

            if (exchangeRate == null) {
                System.out.println("----> Importando datos de [" + bank + "]");
                exchangeRate = new CommercialBankExchangeRate(getDollar(), bank, trade.date(), trade.sell(), trade.buy(),
                        trade.isBestSellPrice(), trade.isBestBuyPrice());
                em.persist(exchangeRate);
            }
        }
    }

}
