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
import java.util.Random;


public class TextGenerator implements Markov<String> {

    private final static String[] FILTERS = { ".", ",", "?", "!", ":", ";" };

    private Map<String, List<String>> chain;

    private String last;

    public TextGenerator() {
        chain = new HashMap<String, List<String>>();
        last = null;
    }

    public TextGenerator(String input) {
        this();
        append(input);
    }

    public TextGenerator(File input) {
        this();
        append(input);
    }

    public TextGenerator(String... words) {
        this();
        append(words);
    }

    public TextGenerator append(File input) {
        if (!input.exists()) {
            return this;
        }

        try {
            BufferedReader fIn = new BufferedReader(new FileReader(input));

            String line;
            while ((line = fIn.readLine()) != null) {
                append(line);
            }

            fIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public TextGenerator append(String input) {
        String copy = new String(input);

        for (String filter : FILTERS) {
            copy = copy.replace(filter, " " + filter);
        }

        String[] words = copy.split(" ");

        if (last != null) {
            chain.putIfAbsent(last, new ArrayList<String>());
            chain.get(last).add(words[0]);
        }

        for (int i = 1; i < words.length; ++i) {
            chain.putIfAbsent(words[i - 1], new ArrayList<String>());
            chain.get(words[i - 1]).add(words[i]);
        }

        if (words.length != 0) {
            last = words[words.length - 1];
        }

        return this;
    }

    public String generateText(int len) {
        if (chain.size() == 0 || len == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        Object[] entries = chain.keySet().toArray();
        String first = (String) entries[RANDOM.nextInt(entries.length)];

        result.append(first);
        result.append(" ");

        for (int i = 1; i < len; ++i) {
            List<String> possiblities = chain.get(first);
            if (possiblities != null) {
                first = possiblities.get(RANDOM.nextInt(possiblities.size()));
            } else {
                first = "?";
            }

            result.append(first);
            result.append(" ");
        }

        return result.toString();
    }

    public String generateText(int len, int seed) {
        if (chain.size() == 0 || len == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        Object[] entries = chain.keySet().toArray();
        String first = (String) entries[new Random(seed).nextInt(entries.length)];

        result.append(first);
        result.append(" ");

        for (int i = 1; i < len; ++i) {
            List<String> possiblities = chain.get(first);
            if (possiblities != null) {
                first = possiblities.get(new Random(seed).nextInt(possiblities.size()));
            } else {
                first = "?";
            }

            result.append(first);
            result.append(" ");
        }

        return result.toString();
    }

    @Override
    public Queue<String> generate(int length) {
        if (chain.size() == 0 || length == 0) {
            return new LinkedList<String>();
        }

        Queue<String> out = new LinkedList<String>();

        Object[] entries = chain.keySet().toArray();
        String first = (String) entries[RANDOM.nextInt(entries.length)];

        out.add(first);

        for (int i = 1; i < length; ++i) {
            List<String> possiblities = chain.get(first);
            if (possiblities != null) {
                first = possiblities.get(RANDOM.nextInt(possiblities.size()));
            } else {
                first = "?";
            }

            out.add(first);
        }

        return out;
    }

    public Queue<String> generate(int length, int seed) {
        Queue<String> out = new LinkedList<String>();

        if (chain.size() == 0 || length == 0) {
            return new LinkedList<String>();
        }

        Object[] entries = chain.keySet().toArray();
        String first = (String) entries[new Random(seed).nextInt(entries.length)];

        out.add(first);

        for (int i = 1; i < length; ++i) {
            List<String> possiblities = chain.get(first);
            if (possiblities != null) {
                first = possiblities.get(new Random(seed).nextInt(possiblities.size()));
            } else {
                first = "?";
            }

            out.add(first);
        }

        return out;
    }
    
    @Override
    public TextGenerator append(String... words) {

        if (last != null) {
            chain.putIfAbsent(last, new ArrayList<String>());
            chain.get(last).add(words[0]);
        }

        for (int i = 1; i < words.length; ++i) {
            chain.putIfAbsent(words[i - 1], new ArrayList<String>());
            chain.get(words[i - 1]).add(words[i]);
        }

        if (words.length != 0) {
            last = words[words.length - 1];
        }

        return this;
    }

}