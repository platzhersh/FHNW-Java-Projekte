package ch.fhnw.edu.rental.daos.impl;

import org.springframework.transaction.annotation.Transactional;

/*
 * This is a marker interface. DAOs which are marked with this
 * interface document that they run in a managed environment,
 * i.e. changes on objects returned by such a DAO are persisted
 * automatically.
 */
@Transactional
public interface ManagedDAO<T> {
	T manage(T instance);
}
