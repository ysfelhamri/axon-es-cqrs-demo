package q.jv.demo.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import q.jv.demo.query.entities.Account;

public interface AccountRepository extends JpaRepository<Account,String> {
}
