package it.polimi.ingsw.Model;

import java.io.Serial;
import java.io.Serializable;
import java.nio.file.OpenOption;
import java.util.NoSuchElementException;

public class SerializableOptional<T> implements Serializable {
    private final T value;
    private static final SerializableOptional<?> EMPTY = new SerializableOptional<>(null);

    public SerializableOptional(T value) {
        this.value = value;
    }

    public static<T> SerializableOptional<T> empty(){
        SerializableOptional<T> t = (SerializableOptional<T>) EMPTY;
        return t;
    }

    public static<T> SerializableOptional<T> of(T value){
        if(value != null) return new SerializableOptional<T>(value);
        else return empty();
    }

    public T get(){
        if(value == null) throw new NoSuchElementException("No value present");
        return value;
    }

    public boolean isPresent() {return value != null;}

    public boolean isEmpty() {return value == null;}

    public T orElse(T other) {return value != null ? value : other;}
}
