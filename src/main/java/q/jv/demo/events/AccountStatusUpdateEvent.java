package q.jv.demo.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import q.jv.demo.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountStatusUpdateEvent {
    private String accountId;
    private AccountStatus status;
}
