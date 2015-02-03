package ch.fhnw.edu.rental.test.util;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import ch.fhnw.edu.rental.service.util.CacheAspect;

public class JdbcTransactionManager implements PlatformTransactionManager {
	
	private PlatformTransactionManager tm;
	public void setTransactionManager(PlatformTransactionManager tm) {
		this.tm = tm;
	}

	@Override
	public TransactionStatus getTransaction(TransactionDefinition definition)
			throws TransactionException {
		CacheAspect.open();
		TransactionStatus res = tm.getTransaction(definition);
//		System.err.printf("getTransaction: isNew:%b isRollback:%b isCompleted:%b hasSavepoint%b\n", res.isNewTransaction(), res.isRollbackOnly(), res.isCompleted(), res.hasSavepoint());
		return res;
	}

	@Override
	public void commit(TransactionStatus status) throws TransactionException {
//		System.err.printf("commit: isNew:%b isRollback:%b isCompleted:%b hasSavepoint%b\n", status.isNewTransaction(), status.isRollbackOnly(), status.isCompleted(), status.hasSavepoint());
		CacheAspect.close();
		tm.commit(status);
//		System.err.printf("commit: isNew:%b isRollback:%b isCompleted:%b hasSavepoint%b\n", status.isNewTransaction(), status.isRollbackOnly(), status.isCompleted(), status.hasSavepoint());
	}

	@Override
	public void rollback(TransactionStatus status) throws TransactionException {
//		System.err.printf("rollback: isNew:%b isRollback:%b isCompleted:%b hasSavepoint%b\n", status.isNewTransaction(), status.isRollbackOnly(), status.isCompleted(), status.hasSavepoint());
		CacheAspect.close();
		tm.rollback(status);
//		System.err.printf("rollback: isNew:%b isRollback:%b isCompleted:%b hasSavepoint%b\n", status.isNewTransaction(), status.isRollbackOnly(), status.isCompleted(), status.hasSavepoint());
	}

}
