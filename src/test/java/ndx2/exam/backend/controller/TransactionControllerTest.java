package ndx2.exam.backend.controller;

import ndx2.exam.backend.mappper.TransactionMapper;
import ndx2.exam.backend.model.Customer;
import ndx2.exam.backend.model.Transaction;
import ndx2.exam.backend.repository.TransactionRepository;
import ndx2.exam.backend.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionServiceImpl transactionService;

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
    public void getTransactionAPI() throws Exception {
        when(transactionService.getTransaction(any(), any())).thenReturn(transactions.stream().map(TransactionMapper.INSTANCE::toResponse).toList());
        mockMvc.perform(get("/api/transaction")).andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)));
    }
}
