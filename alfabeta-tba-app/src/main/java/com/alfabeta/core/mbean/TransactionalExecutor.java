package com.alfabeta.core.mbean;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.function.Supplier;

/**
 * Executes code blocks inside a Spring-managed transaction safely.
 */
@Component
public class TransactionalExecutor {

    private final PlatformTransactionManager transactionManager;

    public TransactionalExecutor(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public <T> T executeInTransaction(Supplier<T> supplier) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            T result = supplier.get();
            transactionManager.commit(status);
            return result;
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException("Unexpected exception during transaction", e);
        }
    }
}
