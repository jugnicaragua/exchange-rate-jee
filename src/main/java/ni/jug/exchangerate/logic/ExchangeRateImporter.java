package ni.jug.exchangerate.logic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ni.jug.cb.exchangerate.ExchangeRateCBClient;
import ni.jug.cb.exchangerate.ExchangeRateTrade;
import ni.jug.exchangerate.model.Bank;
import ni.jug.exchangerate.model.CentralBankExchangeRate;
import ni.jug.exchangerate.model.CommercialBankExchangeRate;
import ni.jug.exchangerate.model.Currency;
import ni.jug.exchangerate.util.Dates;
import ni.jug.ncb.exchangerate.ExchangeRateBCNClient;
import ni.jug.ncb.exchangerate.ExchangeRateScraper;
import ni.jug.ncb.exchangerate.MonthlyExchangeRate;

/**
 *
 * @author aalaniz
 */
@Singleton
@Startup
public class ExchangeRateImporter {

    private static final String DOLLAR_ISO_STRING_CODE = "USD";

    @PersistenceContext
    EntityManager em;

    @Resource
    TimerService timerService;

    @Inject
    CurrencyProvider currencyProvider;

    @Inject
    CommercialBankExchangeRateProvider commercialBankExchangeRateProvider;

    @Inject
    CentralBankExchangeRateProvider centralBankExchangeRateProvider;

    private Currency getDollar() {
        return currencyProvider.getCurrencyByIsoStringCode(DOLLAR_ISO_STRING_CODE);
    }

    @PostConstruct
    public void onInit() {
        timerService.createSingleActionTimer(20000, new TimerConfig());
    }

    @Timeout
    public void importInitialCentralBankExchangeRate(Timer timer) {
        Currency dollar = getDollar();
        LocalDate processDate = LocalDate.of(ExchangeRateBCNClient.MINIMUM_YEAR, 1, 1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(1);

        while (processDate.isBefore(endDate)) {
            MonthlyExchangeRate monthlyExchangeRate = new ExchangeRateScraper().getMonthlyExchangeRate(processDate);
            for (Map.Entry<LocalDate, BigDecimal> exchangeRateByDate : monthlyExchangeRate.getMonthlyExchangeRate().entrySet()) {
                CentralBankExchangeRate exchangeRate = new CentralBankExchangeRate(dollar, exchangeRateByDate.getKey(),
                        exchangeRateByDate.getValue());
                em.merge(exchangeRate);
            }

            processDate = processDate.plusMonths(1);
        }
    }

    @Schedule(hour = "*", minute = "*/15", persistent = false)
    public void importCentralBankExchangeRate() {
        Currency dollar = getDollar();

        long dbRowCount = centralBankExchangeRateProvider.countByCurrentPeriodAndCurrency(dollar);
        if (dbRowCount == Dates.daysForCurrentPeriod()) {
            System.out.println("----> Los datos ya fueron importados");
            return;
        }

        System.out.println("----> Importando datos del sitio web del BCN");
        MonthlyExchangeRate currentMonthExchangeRate = new ExchangeRateScraper().getCurrentMonthExchangeRate();
        for (Map.Entry<LocalDate, BigDecimal> exchangeRateByDate : currentMonthExchangeRate.getMonthlyExchangeRate().entrySet()) {
            CentralBankExchangeRate exchangeRate = new CentralBankExchangeRate(dollar, exchangeRateByDate.getKey(),
                    exchangeRateByDate.getValue());
            em.merge(exchangeRate);
        }
    }

    @Schedule(hour = "*", minute = "*/25", persistent = false)
    public void importCommercialBankExchangeRate() {
        System.out.println("----> Leyendo compra/venta de los bancos comerciales");
        List<Bank> scrappedBanks = commercialBankExchangeRateProvider.findBankByCurrentDate();
        ExchangeRateCBClient client = ExchangeRateCBClient.scrapAndRepeatIfNecessary();
        for (ExchangeRateTrade trade : client.trades()) {
            Bank bank = Bank.valueOf(trade.bank());

            if (!scrappedBanks.contains(bank)) {
                System.out.println("----> Importando datos de [" + bank + "]");
                CommercialBankExchangeRate exchangeRate = new CommercialBankExchangeRate(getDollar(), bank, trade.date(), trade.sell(),
                        trade.buy(), trade.isBestSellPrice(), trade.isBestBuyPrice());
                em.persist(exchangeRate);
            }
        }
    }

}
