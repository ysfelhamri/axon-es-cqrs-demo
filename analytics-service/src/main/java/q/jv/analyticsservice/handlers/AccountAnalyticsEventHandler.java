package q.jv.analyticsservice.handlers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import q.jv.analyticsservice.entities.AccountAnalytics;
import q.jv.analyticsservice.repositories.AccountAnalyticsRepository;
import q.jv.demo.events.AccountCreatedEvent;

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



}
