package com.example.zeraki;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @Mock
//    private StudentRepository studentRepository;
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @Spy // We use @Spy for the service we want to test but also call its real methods
//    private FraudDetectionService fraudDetectionService;
//
//    @InjectMocks
//    private TransactionService transactionService;
//
//    private Student mockStudent;
//
//    private Account mockAccount;
//
//    @BeforeEach
//    void setUp() {
//        mockStudent = new Student();
//        mockStudent.setId(1);
//
//        mockAccount = new Account();
//        mockAccount.setStudent(mockStudent);
//        mockAccount.setCurrentBalance(BigDecimal.valueOf(1000));
//    }
//
//    @Test
//    @DisplayName("Should flag duplicate transactions within a 10-minute window")
//    void testDuplicateDetection() {
//        // 1. Arrange: Prepare a batch with two identical transactions 5 minutes apart
//        Instant now = Instant.now();
//        TransactionRequest req1 = new TransactionRequest(
//                1, new BigDecimal("5.00"), "MPESA", "REF1", now
//        );
//        TransactionRequest req2 = new TransactionRequest(
//                1, new BigDecimal("5.00"), "MPESA", "REF2", now.plus(Duration.ofMinutes(5))
//        );
//
//        // Mock the DB to return an empty list initially (no previous history)
//        when(transactionRepository.findByStudentIdInAndTimestampAfter(any(), any()))
//                .thenReturn(new ArrayList<>());
//
//        // Mock the student lookup for mapToEntity
//        when(studentRepository.getReferenceById(1)).thenReturn(mockStudent);
//        when(accountRepository.getReferenceById(1)).thenReturn(mockAccount);
//        when(transactionRepository.saveAll(any())).thenAnswer(i -> i.getArgument(0));
//        // 2. Act
//        List<Transaction> results = transactionService.processBatch(List.of(req1, req2));
//        System.out.println("Batch Results: " + results);
//        // 3. Assert
//        assertEquals(2, results.size());
//        assertEquals(PaymentStatus.COMPLETED, results.get(0).getStatus());
//        assertEquals(PaymentStatus.SUSPICIOUS, results.get(1).getStatus(),
//                "The second identical transaction should be marked as suspicious");
//    }
}
