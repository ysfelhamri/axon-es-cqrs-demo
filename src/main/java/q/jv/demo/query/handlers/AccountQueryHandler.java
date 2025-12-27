package q.jv.demo.query.handlers;

import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import q.jv.demo.query.dtos.AccountStatementResponseDTO;
import q.jv.demo.query.entities.Account;
import q.jv.demo.query.entities.AccountOperation;
import q.jv.demo.query.queries.GetAccountStatementQuery;
import q.jv.demo.query.queries.GetAllAccountsQuery;
import q.jv.demo.query.queries.WatchEventQuery;
import q.jv.demo.query.repository.AccountRepository;
import q.jv.demo.query.repository.OperationRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository, QueryUpdateEmitter queryUpdateEmitter) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query) {
        Account account = accountRepository.findById(query.getAccountId()).get();
        List<AccountOperation> accountOperations = operationRepository.findByAccountId(query.getAccountId());
        return new AccountStatementResponseDTO(account,accountOperations);
    }

    @QueryHandler
    public AccountOperation on(WatchEventQuery query) {
        return AccountOperation.builder().build();
    }
}
