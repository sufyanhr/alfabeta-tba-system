package com.alfabeta.core.flow;

import java.util.function.Supplier;

public interface TransactionalExecutor {
    <T> T executeInTransaction(Supplier<T> work);

    // simple no-op implementation
    class NoOp implements TransactionalExecutor {
        public static final NoOp INSTANCE = new NoOp();
        @Override
        public <T> T executeInTransaction(Supplier<T> work) {
            return work.get();
        }
    }
}
