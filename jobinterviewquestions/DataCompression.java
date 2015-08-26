package jobinterviewquestions;

/**
 * Created by Leon on 8/23/15.
 */
public class DataCompression {
    /*
    Question 1
    Ternary Huffman codes.
    Generalize the Huffman algorithm to codewords over the ternary alphabet (0, 1, and 2) instead of the binary alphabet.
    That is, given a bytestream, find a prefix-free ternary code that uses as few trits (0s, 1s, and 2s) as possible. Prove that it yields optimal prefix-free ternary code.
    */

    /*
    "Pick the smallest three frequencies, join them together and create
    a node with the frequency equal to the sum of the three. Repeat. However,
    notice that every contraction reduces the number of leaves by 2 – we remove
    3 nodes and add 1 back. So to make sure that we end up with just one node,
    we have to have an odd number of nodes to start with. If not, add a dummy
    node with 0 frequency to start with."
     */

    /*
    Question 2
    Uniquely decodable code.
    Identify an optimal uniquely-decodable code that is neither prefix free nor suffix tree.
    Identify two optimal prefix-free codes for the same input that have a different distribution of codeword lengths.
    */


    /*
    Question 3
    Move-to-front coding. Design an algorithm to implement move-to-front encoding so that each operation takes logarithmic time in the worst case.
    That is, maintain alphabet of symbols in a list.
    A symbol is encoded as the number of symbols that precede it in the list. After encoding a symbol, move it to the front of the list.
     */

    /*
    1. Logarithmic time operation: use RedBlackBST in algs4, use rank to write the encoding, use delete and min to move the symbol to the front of the tree
    2. Algorithm:
        initialize the symbol table;

        while ( not end-of-file ) {

            K = get character;

            output K’s position(P) in the symbol table; (use RedBlackBST.rank())

            move K to front of the symbol table. (delete K, then get min() and add back K, set K's new value less than min())

        }
     */


}
