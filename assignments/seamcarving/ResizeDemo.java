package assignments;

import algs4.Stopwatch;
import stdlib.Picture;

/**
 * Created by Leon on 8/9/15.
 */
public class ResizeDemo {
    public static void main(String[] args)
    {
        /*if (args.length != 3)
        {
            System.out.println("Usage:\njava ResizeDemo [image filename] [num cols to remove] [num rows to remove]");
            return;
        }
        */

        Picture inputImg = new Picture("./resources/seamCarving/chameleon.png");
        int removeColumns = 100;
        int removeRows = 50;

        System.out.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
        SeamCarver sc = new SeamCarver(inputImg);

        Stopwatch sw = new Stopwatch();

        for (int i = 0; i < removeRows; i++) {
            int[] horizontalSeam = sc.findHorizontalSeam();
            sc.removeHorizontalSeam(horizontalSeam);
        }

        for (int i = 0; i < removeColumns; i++) {
            int[] verticalSeam = sc.findVerticalSeam();
            sc.removeVerticalSeam(verticalSeam);
        }
        Picture outputImg = sc.picture();

        System.out.printf("new image size is %d columns by %d rows\n", sc.width(), sc.height());

        System.out.println("Resizing time: " + sw.elapsedTime() + " seconds.");

        inputImg = new SeamCarver(inputImg).picture();
        inputImg.show();
        outputImg.show();

        //SeamCarver inputSC = new SeamCarver(inputImg);

        //SeamCarver.printEnergy(inputSC);
        //SeamCarver.printEnergy(sc);
    }
}
