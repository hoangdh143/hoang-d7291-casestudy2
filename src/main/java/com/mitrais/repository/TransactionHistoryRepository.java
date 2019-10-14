package com.mitrais.repository;

import com.mitrais.model.TransactionHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, String> {
    @Query(value = "Select t from TransactionHistory t where t.account.accountNumber = ?1 order by t.createdAt desc")
    List<TransactionHistory> findAllByAccountNumber(String accountNumber, Pageable pageable);

    @Query(value = "SELECT * FROM transaction_history t WHERE t.account_id = :accNumber AND DATE(t.created_at) >= :fromDate AND DATE(t.created_at) <= :toDate ORDER BY t.created_at DESC", nativeQuery = true)
    List<TransactionHistory> findAllTransactionByDate(@Param("accNumber") String accountNumber,
                                                      @Param("fromDate") LocalDate fromDate,
                                                      @Param("toDate") LocalDate toDate,
                                                      Pageable pageable);
}
