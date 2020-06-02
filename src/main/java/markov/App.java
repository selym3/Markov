package markov;

import java.io.File;

public class App {

    public static class Event {
        private final String activity;

        public String getActivity() {
            return activity;
        }

        @Override
        public String toString() {
            return "Event [activity=" + activity + "]";
        }

        public Event(String activity) {
            this.activity = activity;
        }
    }

    public static void main(String[] args) {
        Markov<Event> schedule = new Sequence<>();

        for (int i = 0; i < args.length; ++i) {
            schedule.append(new Event(args[i]));
        }

        // SEQUENCE CAN STILL RETURN NULL BECAUSE CHAIN IS NOT CIRCULAR (has a terminating node)

        System.out.println(schedule.generate(10));
    }

    /*
     * public static void main(String[] args) { TextGenerator mk = new
     * TextGenerator("One two");
     * 
     * System.out.println(mk.generateText(10));
     * 
     * mk.append("Three four");
     * 
     * System.out.println(mk.generateText(10));
     * 
     * mk.append(new File("src/main/resources/test.txt"));
     * 
     * System.out.println(mk.generateText(10));
     * 
     * mk.append("Seven", "Eight", "Nine", "Ten");
     * 
     * System.out.println(mk.generate(10));
     * 
     * Markov<String> wg = new WordGenerator(3);
     * 
     * wg.append("chicky", "ribonucleic");
     * 
     * System.out.println(wg.generate(10));
     * 
     * Markov<String> fileWg = new WordGenerator(3, new
     * File("src/main/resources/test.txt"));
     * 
     * System.out.println(fileWg.generate(10));
     * 
     * Sequence<Integer> seq = new Sequence<>();
     * 
     * seq.append(1,2,3,4,5,6,7,8,9,10,11); System.out.println(seq.generate(10)); }
     */

    /*
     * public static void main(String[] args) throws FileNotFoundException,
     * IOException { // 'One two' is a problem because One => two but two => null
     * because it does not have // a key in the chain
     * 
     * TextGenerator mk = new TextGenerator("One two");
     * 
     * // if there a word found that does not point to any word, it will return null
     * // in this case it prints ? repeatedly because it is undefined
     * 
     * mk.append("Three four");
     * 
     * // because you using append command, `two` is linked to `three` such // to
     * preserve the chain
     * 
     * // four is still unlinked though. if a ? is observed that means there is a
     * problem in the // chaining. I could probably fix this by making it circular
     * (last => first) (<- worst case scenario)
     * 
     * System.out.println(mk.generate(10));
     * 
     * mk.append(new File("src/main/resources/test.txt"));
     * 
     * System.out.println(mk.generate(10)); }
     */

}
