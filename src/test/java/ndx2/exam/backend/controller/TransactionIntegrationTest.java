package ndx2.exam.backend.controller;

import ndx2.exam.backend.model.Customer;
import ndx2.exam.backend.model.Transaction;
import ndx2.exam.backend.repository.CustomerRepository;
import ndx2.exam.backend.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransactionIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CustomerRepository customerRepository;

    List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();

        Customer customer = Customer.builder()
                .email("user1@gmail.com")
                .phone("0935310446")
                .name("Nguyen Dac Duc")
                .build();
        Customer customerSaved = customerRepository.save(customer);
        transactions = Arrays.asList(
                Transaction.builder()
                        .id(1)
                        .transactionId("MG-001")
                        .dt(50D)
                        .price(100000D)
                        .customer(customerSaved)
                        .transactionDate(LocalDate.now())
                        .transactionType(1)
                        .build(),
                Transaction.builder()
                        .id(2)
                        .transactionId("MG-002")
                        .dt(52D)
                        .price(200000D)
                        .customer(customerSaved)
                        .transactionDate(LocalDate.now())
                        .transactionType(1)
                        .build()
        );

        transactionRepository.saveAll(transactions);
    }

    @Test
    void getTransactionAPI_shouldReturnPass() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[1].transactionId").value("MG-002"));
    }
}
