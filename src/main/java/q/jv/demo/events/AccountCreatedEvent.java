package q.jv.demo.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import q.jv.demo.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus status;
    private String currency;
}
