package q.jv.analyticsservice.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;
import q.jv.analyticsservice.entities.AccountAnalytics;
import q.jv.analyticsservice.queries.GetAllAccountAnalytics;
import q.jv.analyticsservice.repositories.AccountAnalyticsRepository;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class AccountAnalyticsQueryHandler {
    private AccountAnalyticsRepository accountAnalyticsRepo;

    @QueryHandler
    public List<AccountAnalytics> on(GetAllAccountAnalytics query){
        return accountAnalyticsRepo.findAll();
    }
}
