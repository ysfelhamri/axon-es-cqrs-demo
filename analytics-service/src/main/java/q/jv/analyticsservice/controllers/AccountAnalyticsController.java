package q.jv.analyticsservice.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q.jv.analyticsservice.entities.AccountAnalytics;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/analytics")
@AllArgsConstructor
@Slf4j
public class AccountAnalyticsController {
    private QueryGateway queryGateway;
    @GetMapping("/accountAnalytics")
    public CompletableFuture<List<AccountAnalytics>> accountAnalytics(){
        return queryGateway.query(new GetAllAccountAnalytics(), ResponseTypes.multipleInstancesOf(AccountAnalytics.class));
    }
}
