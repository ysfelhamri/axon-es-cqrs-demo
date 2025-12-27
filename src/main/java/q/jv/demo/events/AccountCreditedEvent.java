package q.jv.demo.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import q.jv.demo.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountCreditedEvent {
    private String accountId;
    private double amount;
    private String currency;
}
