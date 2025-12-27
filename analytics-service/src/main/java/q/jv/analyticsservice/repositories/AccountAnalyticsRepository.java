package q.jv.analyticsservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import q.jv.analyticsservice.entities.AccountAnalytics;

public interface AccountAnalyticsRepository extends JpaRepository<AccountAnalytics,Long> {
    AccountAnalytics findByAccountId(String accountId);
}
