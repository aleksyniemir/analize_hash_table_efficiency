package pl.edu.pw.ee.services;

public interface HashTable <T extends Comparable<T>>{

    void add(T item);

    Object get(T item);

    void remove(T item);

}