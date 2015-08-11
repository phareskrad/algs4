package assignments;

import stdlib.In;
import stdlib.StdOut;

/**
 * Created by Leon on 8/3/15.
 */
public class Outcast {
    private WordNet wordnet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int d = 0;
        String outcast = nouns[0];

        for (int i = 0; i < nouns.length; i++) {
            int tmp = distance(nouns[i], nouns);
            if (tmp > d) {
                d = tmp;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    private int distance(String noun, String[] nouns) {
        int result = 0;

        for (int i = 0; i < nouns.length; i++) {
            result += wordnet.distance(noun, nouns[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        String[] outcasts = new String[3];
        outcasts[0] = "./resources/wordnet/outcast5.txt";
        outcasts[1] = "./resources/wordnet/outcast8.txt";
        outcasts[2] = "./resources/wordnet/outcast11.txt";
        WordNet wordnet = new WordNet("./resources/wordnet/synsets.txt", "./resources/wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        for (int t = 0; t < outcasts.length; t++) {
            In in = new In(outcasts[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(outcasts[t] + ": " + outcast.outcast(nouns));
        }
    }
}
