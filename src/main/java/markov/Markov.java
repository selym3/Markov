package markov;

import java.util.Queue;
import java.util.Random;

// implement circular markov chain, basically do as much as possbile to prevent undefined behavior
// also implement Generic markov chain

// test edge cases

public interface Markov<T> {

    public final static Random RANDOM = new Random();

    // make this generic such that it doesnt have to return string (it could return
    // a linked list of `events`) or basically the datatype
    public Queue<T> generate(int length);

    public Queue<T> generate(int length, int seed);

    // is this safe varargs, the world may never know
    public Markov<T> append(T... input);

}