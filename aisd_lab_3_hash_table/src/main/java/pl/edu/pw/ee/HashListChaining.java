package pl.edu.pw.ee;

import java.util.ArrayList;

import pl.edu.pw.ee.services.HashTable;

public class HashListChaining<T extends Comparable<T>> implements HashTable<T> {

    private final Elem nil = null;
    private ArrayList<Elem> hashElems;
    private int nElem = 0;

    private class Elem {
        private T value;
        private Elem next;

        Elem(T value, Elem nextElem) {
            this.value = value;
            this.next = nextElem;
        }
    }

    public HashListChaining(int size) {
        hashElems = new ArrayList<>();
        initializeHash(size);
    }

    @Override
    public void add(T value) {

        if (value == null) {
            throw new IllegalArgumentException("The argument cant be null");
        }
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);
        Elem oldElem = hashElems.get(hashId);
        while (oldElem != nil && !oldElem.value.equals(value)) {
            oldElem = oldElem.next;
        }
        if (oldElem != nil) {
            hashElems.remove(oldElem);
            hashElems.add(oldElem);
        } else  {
            Elem ellem = new Elem(value,  hashElems.get(hashId));
            hashElems.set(hashId, ellem);
            nElem++;
        }
    }

    @Override
    public void remove(T value) {
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem olderElem = hashElems.get(hashId);
        if (olderElem == null) {
        } else if (olderElem.value == value) {
            olderElem = olderElem.next;
            hashElems.set(hashId, olderElem);
        } else if (olderElem.next != nil) {
            Elem oldElem = olderElem.next;
            while (oldElem != nil && !oldElem.value.equals(value)) {
                olderElem = oldElem;
                oldElem = oldElem.next;
            }
            if (oldElem != nil) {
                if (oldElem.next != nil) {
                    olderElem.next = oldElem.next;
                } else if (oldElem.next == nil) {
                    olderElem.next = nil;
                }
            }
        }
    }

    @Override
    public T get(T value) {
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem elem = hashElems.get(hashId);

        while (elem != nil && !elem.value.equals(value)) {
            elem = elem.next;
        }

        return elem != nil ? elem.value : null;
    }

    public double countLoadFactor() {
        double size = hashElems.size();
        return nElem / size;
    }

    private void initializeHash(int size) {
        for (int i = 0; i < size; i++) {
            hashElems.add(nil);
        }
    }

    private int countHashId(int hashCode) {
        int n = hashElems.size();
        return Math.abs(hashCode) % n;
    }
}