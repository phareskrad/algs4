package assignments;

import stdlib.StdIn;

/**
 * Created by Leon on 7/15/15.
 */
public class Brute {
    public static void main(String[] args) {
        int[] input = StdIn.readAllInts();

        Point[] p = new Point[input[0]];

        for (int i = 0; i < input[0]; i++) {
            p[i] = new Point(input[i*2 + 1], input[i*2 + 2]);
        }

//        for (int i = 0; i < p.length; i++) {
//            System.out.println(p[i]);
//        }

        for (int i = 0; i < input[0] - 3; i++) {
            for (int j = i + 1; j < input[0] - 2; j++) {
                for (int k = j + 1; k < input[0] - 1; k++) {
                    for (int l = k + 1; l < input[0]; l++) {
                        if (p[i].slopeTo(p[j]) == p[i].slopeTo(p[k]) && p[i].slopeTo(p[j]) == p[i].slopeTo(p[l]))
                            System.out.println(p[i].toString() + "->" + p[j].toString() + "->" + p[k].toString() + "->" + p[l].toString());
                    }
                }
            }
        }
    }
}
