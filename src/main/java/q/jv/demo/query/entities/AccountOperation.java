package q.jv.demo.query.entities;

import jakarta.persistence.*;
import q.jv.demo.enums.OperationType;

import java.util.Date;
@Entity
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private String currency;
    @ManyToOne
    private Account account;


}
