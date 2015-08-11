package assignments;

import stdlib.Picture;

/**
 * Created by Leon on 8/9/15.
 */
public class PrintEnergy {
    public static void main(String[] args)
    {
        Picture inputImg = new Picture(args[0]);
        System.out.printf("image is %d pixels wide by %d pixels high.\n", inputImg.width(), inputImg.height());

        SeamCarver sc = new SeamCarver(inputImg);

        System.out.printf("Printing energy calculated for each pixel.\n");

        for (int j = 0; j < sc.height(); j++)
        {
            for (int i = 0; i < sc.width(); i++)
                System.out.printf("%9.0f ", sc.energy(i, j));

            System.out.println();
        }
    }
}
