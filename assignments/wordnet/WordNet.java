package assignments;

import algs4.*;
import stdlib.In;
import stdlib.StdIn;

import java.util.HashMap;

/**
 * Created by Leon on 7/30/15.
 */
public class WordNet {
    private final HashMap<Integer, Bag<String>> idmap;
    private final HashMap<String, Bag<Integer>> wordmap;
    private final Digraph G;
    private final SAP sap;
    private int V;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        idmap = new HashMap<Integer, Bag<String>>();
        wordmap = new HashMap<String, Bag<Integer>>();
        readSynsets(synsets);

        G = new Digraph(V);
        readHypernyms(hypernyms);
        sap = new SAP(G);

        checkCycle();
        checkRoot();


    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return wordmap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        return wordmap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        Iterable<Integer> A = wordmap.get(nounA);
        Iterable<Integer> B = wordmap.get(nounB);

        return sap.length(A, B);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        Iterable<Integer> A = wordmap.get(nounA);
        Iterable<Integer> B = wordmap.get(nounB);

        int ancestor = sap.ancestor(A, B);
        Bag<String> result = idmap.get(ancestor);
        return concat(result);
    }
    //helper function for read in file for synsets
    private void readSynsets(String synsets) {
        In SynsetsIn = new In(synsets);
        V = 0;

        while (SynsetsIn.hasNextLine()) {
            String line = SynsetsIn.readLine();
            String[] tokens = line.split(",");
            Integer id = Integer.parseInt(tokens[0]);
            String[] nouns = tokens[1].split(" ");
            Bag<String> words = new Bag<String>();

            for (int i = 0; i < nouns.length; i++) {
                words.add(nouns[i]);
                Bag<Integer> ids = new Bag<Integer>();
                if (this.wordmap.containsKey(nouns[i]))
                    ids = this.wordmap.get(nouns[i]);
                ids.add(id);
                this.wordmap.put(nouns[i], ids);
            }

            idmap.put(id, words);

            if (id > this.V) this.V = id;
        }
        this.V = this.V + 1;
    }
    //helper function for read in file for hypernyms
    private void readHypernyms(String hypernyms) {
        In HypernymsIn = new In(hypernyms);

        while (HypernymsIn.hasNextLine()) {
            String line = HypernymsIn.readLine();
            String[] token = line.split(",");
            int v = Integer.parseInt(token[0]);
            for (int i = 1; i < token.length; i++) {
                int w = Integer.parseInt(token[i]);
                this.G.addEdge(v, w);
            }
        }
    }
    //helper function for checking whether digraph has cycle
    private void checkCycle() {
        DirectedCycle cycle = new DirectedCycle(this.G);
        if (cycle.hasCycle()) throw new IllegalArgumentException("Digraph has cycle!");
    }
    //helper function for checking whether digraph is rooted
    private void checkRoot() {
        Digraph Gr = this.G.reverse();
        for (int v = 0; v < V; v++) {
            if (this.G.outdegree(v) == 0) {
                int count = 1;
                boolean[] marked = new boolean[V];
                marked[v] = true;
                Queue<Integer> visited = new Queue<Integer>();
                visited.enqueue(v);
                while (!visited.isEmpty()) {
                    int w = visited.dequeue();
                    for (int i : Gr.adj(w)) {
                        if (!marked[i]) {
                            marked[i] = true;
                            count++;
                            visited.enqueue(i);
                        }
                    }
                }
                if (count != V) throw new IllegalArgumentException("Digraph not rooted!");
            }
        }
    }
    //heloer function for concat nouns
    private String concat(Bag<String> nouns) {
        String result = "";
        for (String noun : nouns) {
            result = result + noun;
        }
        return result;
    }

    // do unit testing of this class
    public static void main(String[] args){
        //In SynsetsIn = new In("./resources/wordnet/synsets15.txt");
        //In HypernymsIn = new In("./resources/wordnet/hypernyms6TwoAncestors.txt");

        WordNet wordNet = new WordNet("./resources/wordnet/synsets8.txt", "./resources/wordnet/hypernyms8ManyAncestors.txt");
        System.out.println(wordNet.distance("a", "c"));
        System.out.println(wordNet.sap("a", "c"));

    }
}
