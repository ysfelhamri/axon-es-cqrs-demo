package q.jv.demo.commands.controllers;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;
import q.jv.demo.commands.commands.AddAccountCommand;
import q.jv.demo.commands.commands.CreditAccountCommand;
import q.jv.demo.commands.commands.DebitAccountCommand;
import q.jv.demo.commands.commands.UpdateStatusCommand;
import q.jv.demo.dtos.AddNewAccountRequestDTO;
import q.jv.demo.dtos.CreditAccountRequestDTO;
import q.jv.demo.dtos.DebitAccountRequestDTO;
import q.jv.demo.dtos.UpdateStatusRequestDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private CommandGateway commandGateway;
    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request){
        CompletableFuture<String> response =  commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return response;
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> response =  commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> response =  commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }

    @PutMapping("/updateStatus")
    public CompletableFuture<String> updateStatus(@RequestBody UpdateStatusRequestDTO request){
        CompletableFuture<String> response =  commandGateway.send(new UpdateStatusCommand(
                request.accountId(),
                request.accountStatus()
        ));
        return response;
    }


    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception){
        return exception.getMessage();
    }
}
