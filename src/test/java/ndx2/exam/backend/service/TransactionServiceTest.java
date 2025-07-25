package ndx2.exam.backend.service;

import ndx2.exam.backend.dto.response.TransactionResponse;
import ndx2.exam.backend.model.Customer;
import ndx2.exam.backend.model.Transaction;
import ndx2.exam.backend.repository.TransactionRepository;
import ndx2.exam.backend.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {
    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    TransactionRepository transactionRepository;

    List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .email("user1@gmail.com")
                .phone("0935310446")
                .name("Nguyen Dac Duc")
                .build();
        transactions = Arrays.asList(
                Transaction.builder()
                        .id(1)
                        .transactionId("MG-001")
                        .dt(50D)
                        .price(100000D)
                        .customer(customer)
                        .build(),
                Transaction.builder()
                        .id(2)
                        .transactionId("MG-002")
                        .dt(52D)
                        .price(200000D)
                        .customer(customer)
                        .build()
        );
    }

    @Test
    public void getTransactions() {
        when(transactionRepository.findAll(any(Specification.class))).thenReturn(transactions);

        List<TransactionResponse> mockTransactions = transactionService.getTransaction(null, null);

        Assertions.assertEquals(transactions.size(), mockTransactions.size());
    }

}
