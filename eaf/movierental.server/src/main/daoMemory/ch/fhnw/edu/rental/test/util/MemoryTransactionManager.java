package ch.fhnw.edu.rental.test.util;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

public class MemoryTransactionManager implements PlatformTransactionManager {
	
	private MemoryDbInitializer dbinit;
	public void setDbinit(MemoryDbInitializer dbinit) {
		this.dbinit = dbinit;
	}

	@Override
	public TransactionStatus getTransaction(TransactionDefinition definition)
			throws TransactionException {
		
		dbinit.initData();
		return new TransactionStatus() {

			@Override
			public Object createSavepoint() throws TransactionException {
				throw new UnsupportedOperationException();
			}

			@Override
			public void rollbackToSavepoint(Object savepoint)
					throws TransactionException {
				throw new UnsupportedOperationException();
			}

			@Override
			public void releaseSavepoint(Object savepoint)
					throws TransactionException {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isNewTransaction() {
				return false;
			}

			@Override
			public boolean hasSavepoint() {
				return false;
			}

			@Override
			public void setRollbackOnly() {
			}

			@Override
			public boolean isRollbackOnly() {
				return false;
			}

			@Override
			public void flush() {
			}

			@Override
			public boolean isCompleted() {
				return false;
			}
		};
	}

	@Override
	public void commit(TransactionStatus status) throws TransactionException {
	}

	@Override
	public void rollback(TransactionStatus status) throws TransactionException {
	}

}
