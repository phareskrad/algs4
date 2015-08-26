package jobinterviewquestions;

import algs4.Bag;
import algs4.Digraph;
import algs4.Stack;

/**
 * Created by Leon on 8/23/15.
 */
public class RegularExpressions {
    /*
    Question 1
    Challenging REs.
    Construct a regular expression for each of the following languages over the binary alphabet or prove that no such regular expression is possible:
    All strings except 11 or 111.
    Strings with 1 in every odd-number bit position.
    Strings with an equal number of 0s and 1s. //impossible
    Strings with at least two 0s and at most one 1.
    Strings that when interpreted as a binary integer are a multiple of 3.
    Strings with no two consecutive 1s.
    Strings that are palindromes (same forwards and backwards). //impossible
    Strings with an equal number of substrings of the form 01 and 10.
    */


    /*
    Question 2
    Exponential-size DFA. Design a regular expressions of length N such that any DFA that recognizes the same language has an exponential number of states.
    */

    /*
    Question 3
    Extensions to NFA. Add to NFA.java the ability to handle multiway or, wildcard, and the + closure operator.
     */

    /*
    make change to the constructor
     */

    class NFA {
        private Digraph G;         // digraph of epsilon transitions
        private String regexp;     // regular expression
        private int M;             // number of characters in regular expression

        // Create the algs4.NFA for the given RE
        public NFA(String regexp) {
            this.regexp = regexp;
            M = regexp.length();
            Stack<Integer> ops = new Stack<Integer>();
            G = new Digraph(M+1);
            for (int i = 0; i < M; i++) {
                int lp = i;
                if (regexp.charAt(i) == '(' || regexp.charAt(i) == '|')
                    ops.push(i);
                else if (regexp.charAt(i) == ')') {
                    int or = ops.pop();

                    // multiway or operator
                    if (regexp.charAt(or) == '|') {
                        Bag<Integer> ors = new Bag<Integer>();
                        while (ops.peek() == '|') {
                            ors.add(ops.pop());
                        }
                        lp = ops.pop();
                        for (int o : ors) {
                            G.addEdge(lp, o + 1);
                            G.addEdge(o, i);
                        }
                    }
                    else if (regexp.charAt(or) == '(')
                        lp = or;
                    else assert false;
                }

                // closure operator (uses 1-character lookahead)
                if (i < M-1 && regexp.charAt(i+1) == '*') {
                    G.addEdge(lp, i+1);
                    G.addEdge(i+1, lp);
                }

                // + operator (uses 1-character lookahead)
                if (i < M-1 && regexp.charAt(i+1) == '+') {
                    G.addEdge(i+1, lp);
                }

                if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')')
                    G.addEdge(i, i+1);
            }
        }
    }
}
