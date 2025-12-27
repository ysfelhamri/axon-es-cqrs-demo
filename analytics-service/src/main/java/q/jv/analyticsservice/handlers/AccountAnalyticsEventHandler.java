package q.jv.analyticsservice.handlers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import q.jv.analyticsservice.entities.AccountAnalytics;
import q.jv.analyticsservice.repositories.AccountAnalyticsRepository;
import q.jv.demo.events.AccountCreatedEvent;
import q.jv.demo.events.AccountCreditedEvent;
import q.jv.demo.events.AccountDebitedEvent;

@Component
@Slf4j
@AllArgsConstructor
public class AccountAnalyticsEventHandler {
    private AccountAnalyticsRepository accountAnalyticsRepo;
    private QueryUpdateEmitter queryUpdateEmitter;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("AccountCreatedEvent received");
        AccountAnalytics accountAnalytics=AccountAnalytics.builder()
                .accountId(event.getAccountId())
                .status(event.getStatus().toString())
                .balance(event.getInitialBalance())
                .totalDebit(0)
                .totalCredit(0)
                .numberDebitOperations(0)
                .numberCreditOperations(0)
                .build();
        accountAnalyticsRepo.save(accountAnalytics);
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("AccountCreatedEvent received");
        AccountAnalytics accountAnalytics=accountAnalyticsRepo.findByAccountId(event.getAccountId());
        accountAnalytics.setTotalCredit(accountAnalytics.getTotalCredit()+event.getAmount());
        accountAnalytics.setNumberCreditOperations(accountAnalytics.getNumberCreditOperations()+1);
        accountAnalytics.setBalance(accountAnalytics.getBalance()+event.getAmount());
        accountAnalyticsRepo.save(accountAnalytics);
    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("AccountDebitedEvent received");
        AccountAnalytics accountAnalytics=accountAnalyticsRepo.findByAccountId(event.getAccountId());
        accountAnalytics.setTotalDebit(accountAnalytics.getTotalDebit()+event.getAmount());
        accountAnalytics.setNumberDebitOperations(accountAnalytics.getNumberDebitOperations()+1);
        accountAnalytics.setBalance(accountAnalytics.getBalance()-event.getAmount());
        accountAnalyticsRepo.save(accountAnalytics);
    }


}
