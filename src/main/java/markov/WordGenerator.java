package markov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// it is undefinded when the size of the word is longer
// than any given input word

// each word is separate so when append is called i do not have to keep track
// of the last thign i did was

// adaptation of code by @samb (sam belliveau) 
public class WordGenerator implements Markov<String> {

    private Map<String, List<Character>> chain;

    // size of the keys in chain
    private int size;

    private static final char TERMINATOR = '_';

    public WordGenerator(int size) {
        chain = new HashMap<String, List<Character>>();
        this.size = size;
    }

    public WordGenerator(int size, String... words) {
        this(size);
        append(words);
    }

    public WordGenerator(int size, File input) {
        this(size);
        append(input);
    }

    public WordGenerator(int size, String input) {
	this(size);
        append(input);
    }

    public WordGenerator append(File file) {

        if (!file.exists()) {
            return this;
        }

        BufferedReader fIn;
        try {

            fIn = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fIn.readLine()) != null) {
                String[] words = line.split(" ");
                append(words);
            }

            fIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public Queue<String> generate(int length) {
        if (chain.size() == 0 || length == 0) {
            return new LinkedList<String>();
        }

        Queue<String> out = new LinkedList<String>();

        Object[] prefixes = chain.keySet().toArray();

        for (int i = 1; i < length; i++) {

            StringBuilder res = new StringBuilder();

            String key = (String) prefixes[RANDOM.nextInt(prefixes.length)];
            List<Character> characters = chain.get(key);

            char next = characters.get(RANDOM.nextInt(characters.size()));
            res.append(key);
            if (next != TERMINATOR) {
                res.append(next);
            }

            System.out.println();
            System.out.println(key + " => " + next);

            while (next != TERMINATOR) {
                // shift key over
                key += next;
                key = key.substring(1);
                characters = chain.get(key);

                if (characters == null) {
                    // for now this ends the character with unknown character
                    // next = UNKNOWN;

                    // System.out.println(key + " => " + next);

                    break;
                } else {
                    next = characters.get(RANDOM.nextInt(characters.size()));
                    // System.out.println(key + " => " + next);

                    // res.append(key);
                    if (next != TERMINATOR) {
                        res.append(next);
                    }
                }
            }

            out.add(res.toString());
        }

        return out;
    }

    @Override
    public Queue<String> generate(int length, int seed) {
	// implement seeded generate function
        return generate(length);
    }

    @Override
    public WordGenerator append(String... input) {
        // nothing to ensure that it is chain yet

        for (String res : input) {
            // adding the + 1 will allow you to get (usually) ONE
            // substring that points to terminating character (TERMINATOR)
            for (int i = 0; i < res.length() - size + 1; ++i) {
                String key = res.substring(i, i + size);

                chain.putIfAbsent(key, new ArrayList<Character>());
                try {
                    chain.get(key).add(res.charAt(i + size));
                } catch (IndexOutOfBoundsException e) {
                    chain.get(key).add(TERMINATOR);
                }
            }
        }

        // System.out.println(chain);

        return this;
    }

}
