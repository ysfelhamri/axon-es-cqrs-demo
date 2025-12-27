package q.jv.demo.query.handlers;

import org.springframework.stereotype.Component;
import q.jv.demo.query.entities.Account;
import q.jv.demo.query.queries.GetAllAccountsQuery;
import q.jv.demo.query.repository.AccountRepository;
import q.jv.demo.query.repository.OperationRepository;

import java.util.List;

@Component
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    public List<Account> on(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }
}
