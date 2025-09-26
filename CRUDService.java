package edu.ccrm.service;

import java.util.List;

/**
 * Generic interface for basic CRUD operations
 * Demonstrates interface with generics and default methods
 */
public interface CRUDService<T, ID> {
    
    // Abstract methods that implementations must provide
    T create(T entity);
    T findById(ID id);
    List<T> findAll();
    T update(T entity);
    boolean delete(ID id);
    
    // Default method demonstrating interface evolution
    default boolean exists(ID id) {
        return findById(id) != null;
    }
    
    // Default method with implementation
    default long count() {
        return findAll().size();
    }
}