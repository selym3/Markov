package markov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;


public class TextGenerator extends Sequence<String> {

    private final static String[] FILTERS = { ".", ",", "?", "!", ":", ";" };

    public TextGenerator(String input) {
        super();
        append(input);
    }

    public TextGenerator(File input) {
        super();
        append(input);
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

        append(words);
        
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
    public TextGenerator append(String... input) {
        super.append(input);
        return this;
    }
}