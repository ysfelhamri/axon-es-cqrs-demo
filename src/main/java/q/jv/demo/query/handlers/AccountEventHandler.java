package q.jv.demo.query.handlers;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Component;
import q.jv.demo.events.AccountActivatedEvent;
import q.jv.demo.events.AccountCreatedEvent;
import q.jv.demo.events.AccountStatusUpdateEvent;
import q.jv.demo.query.entities.Account;
import q.jv.demo.query.repository.AccountRepository;
import q.jv.demo.query.repository.OperationRepository;

@Component
@Slf4j
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage){
        log.info("Query side : AccountCreatedEvent Received");
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .status(event.getStatus())
                .currency(event.getCurrency())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);

    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("Query side : AccountActivatedEvent Received");

        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());

        accountRepository.save(account);

    }

    @EventHandler
    public void on(AccountStatusUpdateEvent event){
        log.info("Query side : AccountStatusUpdateEvent Received");

        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());

        accountRepository.save(account);

    }
}
