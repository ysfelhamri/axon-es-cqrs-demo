package q.jv.demo.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import q.jv.demo.commands.commands.AddAccountCommand;
import q.jv.demo.commands.commands.CreditAccountCommand;
import q.jv.demo.commands.commands.DebitAccountCommand;
import q.jv.demo.enums.AccountStatus;
import q.jv.demo.events.AccountActivatedEvent;
import q.jv.demo.events.AccountCreatedEvent;
import q.jv.demo.events.AccountCreditedEvent;
import q.jv.demo.events.AccountDebitedEvent;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;

    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("############ AddAccountCommand Received ############");
        if(command.getInitialBalance()<=0) throw new IllegalArgumentException("Initial balance must be positive");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));
        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("############ AccountCreatedEvent Occurred ############");

        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        log.info("############ AccountActivatedEvent Occurred ############");

        this.accountId = event.getAccountId();
        this.status = event.getStatus();
    }


    @CommandHandler
    public void handle(CreditAccountCommand command) {
        log.info("############ CreditAccountCommand Received ############");
        if(!status.equals(AccountStatus.ACTIVATED)) throw new RuntimeException("Account "+command.getId()+" must be activated");
        if(command.getAmount()<=0) throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        log.info("############ AccountCreditedEvent Occurred ############");

        this.accountId = event.getAccountId();
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        log.info("############ DebitAccountCommand Received ############");
        if(!status.equals(AccountStatus.ACTIVATED)) throw new RuntimeException("Account "+command.getId()+" must be activated");
        if(balance < command.getAmount()) throw new RuntimeException("Insufficient Balance for the debit operation");
        if(command.getAmount()<=0) throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        log.info("############ AccountDebitedEvent Occurred ############");

        this.accountId = event.getAccountId();
        this.balance -= event.getAmount();
    }
}
