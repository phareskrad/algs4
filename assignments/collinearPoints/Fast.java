package assignments;

import algs4.Queue;
import stdlib.StdIn;

import java.util.Arrays;

/**
 * Created by Leon on 7/16/15.
 */
public class Fast {
    public static void main(String[] args) {
        int[] input = StdIn.readAllInts();

        Point[] p = new Point[input[0]];

        for (int i = 0; i < input[0]; i++) p[i] = new Point(input[i * 2 + 1], input[i * 2 + 2]);

        Queue<Double> slopes = new Queue<Double>();

        for (int i = 0; i < input[0] - 3; i++) {
            Arrays.sort(p, i, input[0], p[i].SLOPE_ORDER);
            System.out.println(p[i]);
            int start = i + 1;
            while (start < p.length - 2) {
                int count = 1;
                int end = start + 1;
                double slope = p[i].slopeTo(p[start]);
                while (end < p.length && p[i].slopeTo(p[end]) == slope) {
                    count++;
                    end++;
                }
                //System.out.println(count);
                if (count >= 3) {
                    boolean exist = false;
                    for (Double s : slopes) {
                        if (s == slope) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        while (start < end) {
                            System.out.print(p[start++] + "->");
                        }
                        System.out.print(p[i]);
                        System.out.println("");
                        slopes.enqueue(slope);
                    }
                }
                start = end;
            }
        }
    }
}
