package markov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class Sequence<T> implements Markov<T> {

    private Map<T, List<T>> chain;
    private T last;

    public Sequence() {
        chain = new HashMap<T, List<T>>();
        last = null;
    }

    public Sequence(T... input) {
        this();
        append(input);
    }

    @Override
    public Queue<T> generate(int length) {
        if (chain.size() == 0 || length == 0) {
            return new LinkedList<T>();
        }

        Queue<T> out = new LinkedList<T>();

        Object[] entries = chain.keySet().toArray();
        T first = (T) entries[RANDOM.nextInt(entries.length)];

        out.add(first);

        for (int i = 1; i < length; ++i) {
            
            List<T> possiblities = chain.get(first);
            if (possiblities != null) {
                first = possiblities.get(RANDOM.nextInt(possiblities.size()));
            } else {
                first = null;
            }

            out.add(first);
        }

        return out;
    }

    @Override
    public Queue<T> generate(int length, int seed) {
        if (chain.size() == 0 || length == 0) {
            return new LinkedList<T>();
        }

        Random rn = new Random(seed);

        Queue<T> out = new LinkedList<T>();

        Object[] entries = chain.keySet().toArray();
        T first = (T) entries[rn.nextInt(entries.length)];

        out.add(first);

        for (int i = 1; i < length; ++i) {
            List<T> possiblities = chain.get(first);
            if (possiblities != null) {
                first = possiblities.get(rn.nextInt(possiblities.size()));
            } else {
                first = null;
            }

            out.add(first);
        }

        return out;
    }

    // links first element to last element inputted
    @Override
    public Sequence<T> append(T... input) {

        if (last != null) {
            chain.putIfAbsent(last, new ArrayList<T>());
            chain.get(last).add(input[0]);
        }

        for (int i=1; i < input.length;++i) {
            chain.putIfAbsent(input[i-1], new ArrayList<>());
            chain.get(input[i-1]).add(input[i]);
        }

        if (input.length != 0) {
            last = input[input.length - 1];
        }

        return this;
    }

}