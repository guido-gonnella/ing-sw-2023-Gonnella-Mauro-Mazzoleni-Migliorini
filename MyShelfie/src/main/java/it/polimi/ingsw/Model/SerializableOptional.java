package it.polimi.ingsw.Model;

import javax.swing.text.html.Option;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Re-implementation of the {@link java.util.Optional Optional<T>} but serializable.<br>
 * If the value is ot null isEmpty return false, and the isPresent return true.<br><br>
 * The methods in the class are a copy of the original methods.<br>
 * The only method different from the original is of(T value), which if value is null return an empty SerializableOptional
 * otherwise returns a SerializableOptional with its value equals to the value passed.
 * <br><br>
 * There aren't present the method ifPresent, ifPresentOrElse, filter, map, flatMap, or, stream, orElseGet and orElseThrow.
 * @apiNote This class is used to implement an Optional which can be serialized and send the net, for client-server/RMI applications.
 * @param <T> the type of the value
 */
public class SerializableOptional<T> implements Serializable {
    /**
     * The value of the object. If null, there's no value.
     */
    private final T value;

    /**
     * Instance for empty.
     */
    private static final SerializableOptional<?> EMPTY = new SerializableOptional<>(null);

    /**
     * Constructors of the Serializable optional. Construct the instance with the passed value.
     * @param value
     */
    public SerializableOptional(T value) {
        this.value = value;
    }

    /**
     * Return an empty SerializableOptional instance.
     * @return empty SerializableOptional
     */
    public static<T> SerializableOptional<T> empty(){
        SerializableOptional<T> t = (SerializableOptional<T>) EMPTY;
        return t;
    }

    /**
     * Return the SerializableOptional describing the value passed as parameter.
     * @param value the value to describe
     * @return an instance of a SerializableOptional with the given value.
     */
    public static<T> SerializableOptional<T> of(T value){
        return new SerializableOptional<>(Objects.requireNonNull(value));
    }

    /**
     * Return a SerializableOptional Instance with the value set equal to the passed parameter.<br>
     * The parameter can be null, the method returns an empty SerializableOptional.
     * @param value the value to be described in the instance.
     * @return the instance eith the value, or an empty instance.
     */
    public static<T> SerializableOptional<T> ofNullable(T value){
        return value != null ? new SerializableOptional<>(value) : (SerializableOptional<T>) EMPTY;
    }

    /**
     *  Return the value of the instance, if the value is non-null, otherwise throws {@link NoSuchElementException}.
     * @return return the value of the instance.
     */
    public T get(){
        if(value == null) throw new NoSuchElementException("No value present");
        return value;
    }

    /**
     * Return true if the value is non-null, false otherwise.
     * @return true if the value is non-null, false otherwise.
     */
    public boolean isPresent() {return value != null;}

    /**
     * Return true if the value is null, false otherwise.
     * @return true if the value is null, false otherwise.
     */
    public boolean isEmpty() {return value == null;}

    /**
     * Return the value if the value is non-null, otherwise return the parameter.
     * @param other the value to return if the value of the instance is null.
     * @return return the instance's value if non-null, otherwise return the passed parameter.
     */
    public T orElse(T other) {return value != null ? value : other;}

    /**
     * Return true the if the instance is equal to the object passed.
     * @param o object passed
     * @return true if the object is equal to the instance, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        return o instanceof SerializableOptional<?> other &&
                Objects.equals(value, other.value);
    }

    /**
     * Return the representation of the instance in the form of a String.
     * @return return the representation of the value in the form of a String if the value is non-null, otherwise return "empty".
     */
    @Override
    public String toString() {
        return value != null ?"SerializableOptional{" +
                "value=" + value.toString() + '}'
                : "empty";
    }
}
