package q.jv.demo.dtos;

import q.jv.demo.enums.AccountStatus;

public record UpdateStatusRequestDTO(String accountId, AccountStatus accountStatus) {
}
