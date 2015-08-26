package assignments;

/**
 * Created by Leon on 8/24/15.
 */
public class CircularSuffixArray {
    private int[] indices; //indices to store the order of sorted circular suffixes
    private String s;
    private int N;
    private static final int R = 256;
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        this.s = s;
        this.N = s.length();
        initializeIndex();
        sort(N);
    }
    //helper function to initialize circular suffixes indices
    private void initializeIndex() {
        indices = new int[N];
        for (int i = 0; i < N; i++) {
            indices[i] = i;
        }
    }
    //helper function, radix sort
    private void sort(int W) {
        int[] aux = new int[N];

        for (int d = W - 1; d >=0; d--) {
            int[] count = new int[R+1];

            for (int i = 0; i < N; i++) {
                count[s.charAt((d + indices[i]) % N) + 1]++;
            }

            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            for (int i = 0; i < N; i++) {
                aux[count[s.charAt((d + indices[i]) % N)]++] = indices[i];
            }

            for (int i = 0; i < N; i++)
                indices[i] = aux[i];
        }
    }

    // length of s
    public int length() {
        return N;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return indices[i];
    }

    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < csa.length(); i++) {
            System.out.println(csa.index(i));
        }
    }
}
