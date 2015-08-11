package assignments;

import stdlib.StdIn;
import stdlib.StdRandom;

/**
 * Created by Leon on 7/7/15.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[1]);
        int count = 0;
        RandomizedQueue<String> input = new RandomizedQueue<String>();
        while (StdIn.hasNextChar()) {
            count++;
            if (input.size() <= k) {
                input.enqueue(StdIn.readString());
            }
            else {
                int r = StdRandom.uniform(count);
                if (r < k) {
                    input.dequeue();
                    input.enqueue(StdIn.readString());
                }
            }
        }

    }
}
