package q.jv.demo.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import q.jv.demo.query.entities.Account;
import q.jv.demo.query.entities.AccountOperation;

import java.util.List;

public interface OperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByAccountId(String id);
}
