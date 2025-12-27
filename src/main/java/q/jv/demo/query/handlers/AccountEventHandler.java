package q.jv.demo.query.handlers;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import q.jv.demo.enums.OperationType;
import q.jv.demo.events.*;
import q.jv.demo.query.entities.Account;
import q.jv.demo.query.entities.AccountOperation;
import q.jv.demo.query.repository.AccountRepository;
import q.jv.demo.query.repository.OperationRepository;

@Component
@Slf4j
public class AccountEventHandler {
    private final QueryUpdateEmitter queryUpdateEmitter;
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository, QueryUpdateEmitter queryUpdateEmitter) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
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

    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage eventMessage){
        log.info("Query side : AccountDebitedEvent  Received");
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .operationType(OperationType.DEBIT)
                .currency(event.getCurrency())
                .account(account)
                .build();

        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance() - accountOperation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(e->true, accountOperation);
    }

    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage){
        log.info("Query side : AccountCreditedEvent  Received");
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .operationType(OperationType.CREDIT)
                .currency(event.getCurrency())
                .account(account)
                .build();

        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance() + accountOperation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(e->true, accountOperation);
    }
}
