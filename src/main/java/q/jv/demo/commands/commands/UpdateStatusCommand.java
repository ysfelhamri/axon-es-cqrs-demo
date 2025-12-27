package q.jv.demo.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import q.jv.demo.enums.AccountStatus;

@Getter @AllArgsConstructor
public class UpdateStatusCommand {
    @TargetAggregateIdentifier
    private String id;
    private AccountStatus accountStatus;
}
