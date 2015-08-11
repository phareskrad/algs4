package assignments;

import stdlib.Picture;

import java.util.Arrays;

/**
 * Created by Leon on 8/9/15.
 */
public class ShowSeams {
    private static void showHorizontalSeam(SeamCarver sc)
    {
        Picture ep = SCUtility.toEnergyPicture(sc);
        int[] horizontalSeam = sc.findHorizontalSeam();
        Picture epOverlay = SCUtility.seamOverlay(ep, true, horizontalSeam);
        epOverlay.show();
    }


    private static void showVerticalSeam(SeamCarver sc)
    {
        Picture ep = SCUtility.toEnergyPicture(sc);
        int[] verticalSeam = sc.findVerticalSeam();
        Picture epOverlay = SCUtility.seamOverlay(ep, false, verticalSeam);
        epOverlay.show();
    }

    private static void printHorizontalSeam(SeamCarver sc) {
        int[] horizontalSeam = sc.findHorizontalSeam();
        System.out.println(Arrays.toString(horizontalSeam));
    }

    private static void printVerticalSeam(SeamCarver sc) {
        int[] verticalSeam = sc.findVerticalSeam();
        System.out.println(Arrays.toString(verticalSeam));
    }

    public static void main(String[] args)
    {
        Picture inputImg = new Picture("./resources/seamCarving/10x12.png");
        System.out.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
        //inputImg.show();
        SeamCarver sc = new SeamCarver(inputImg);

        SeamCarver.printEnergy(sc);

        System.out.println("Displaying horizontal seam calculated.\n");
        printHorizontalSeam(sc);

        System.out.println("Displaying vertical seam calculated.\n");
        printVerticalSeam(sc);

    }
}
