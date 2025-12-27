package q.jv.demo.query.dtos;

import q.jv.demo.query.entities.Account;
import q.jv.demo.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(
        Account account, List<AccountOperation> accountOperations
) {
}
