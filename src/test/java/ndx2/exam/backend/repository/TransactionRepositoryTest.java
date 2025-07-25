package ndx2.exam.backend.repository;

import ndx2.exam.backend.model.Transaction;
import ndx2.exam.backend.specifications.TransactionSpecifications;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void assertGetTransactionByTransactionTypeAndCustomername() {
        Specification<Transaction> spec = TransactionSpecifications.withTransactionTypeAndCustomerName(null,null);
        List<Transaction> transactions = transactionRepository.findAll(spec);

        Assertions.assertEquals(3, transactions.size());
    }
}
