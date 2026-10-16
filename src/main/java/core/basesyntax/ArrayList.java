
package core.basesyntax;

import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROWTH_FACTOR = 2;

    private Object[] arraylist = new Object[DEFAULT_CAPACITY];
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayListIndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
        }

        ensureCapacity();

        System.arraycopy(arraylist, index, arraylist, index + 1,
                size - index);
        arraylist[index] = value;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        int initialSize = list.size();
        for (int i = 0; i < initialSize; i++) {
            add(list.get(i));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) arraylist[index];
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        arraylist[index] = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);

        T removedElement = (T) arraylist[index];
        int elementsToMove = size - index - 1;

        if (elementsToMove > 0) {
            System.arraycopy(arraylist, index + 1, arraylist, index,
                    elementsToMove);
        }

        arraylist[--size] = null;
        return removedElement;
    }

    @Override
    public T remove(T element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(arraylist[i], element)) {
                return remove(i);
            }
        }

        throw new NoSuchElementException(
                "Element not found: " + element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayListIndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
        }
    }

    private void ensureCapacity() {
        if (size == arraylist.length) {
            int newCapacity = arraylist.length
                    + arraylist.length / GROWTH_FACTOR;
            Object[] newArray = new Object[newCapacity];
            System.arraycopy(arraylist, 0, newArray, 0, size);
            arraylist = newArray;
        }
    }
}
