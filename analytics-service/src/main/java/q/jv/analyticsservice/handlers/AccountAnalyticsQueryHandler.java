package q.jv.analyticsservice.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import q.jv.analyticsservice.entities.AccountAnalytics;
import q.jv.analyticsservice.queries.GetAccountAnalyticsByAccountId;
import q.jv.analyticsservice.queries.GetAllAccountAnalytics;
import q.jv.analyticsservice.repositories.AccountAnalyticsRepository;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class AccountAnalyticsQueryHandler {
    private AccountAnalyticsRepository accountAnalyticsRepo;
    private QueryGateway queryGateway;

    @QueryHandler
    public List<AccountAnalytics> on(GetAllAccountAnalytics query){
        return accountAnalyticsRepo.findAll();
    }

    @QueryHandler
    public AccountAnalytics on(GetAccountAnalyticsByAccountId query){
        return accountAnalyticsRepo.findByAccountId(query.getAccountId());
    }

    @GetMapping(value = "/accountAnalytics/{accountId}/watch", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountAnalytics> watchAccountAccount(@PathVariable String accountId){
        SubscriptionQueryResult<AccountAnalytics, AccountAnalytics> result = queryGateway.subscriptionQuery(new GetAccountAnalyticsByAccountId(accountId),
                ResponseTypes.instanceOf(AccountAnalytics.class), ResponseTypes.instanceOf(AccountAnalytics.class));
        return result.initialResult().concatWith(result.updates());
    }
}
