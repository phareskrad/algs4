package assignments;

import stdlib.Picture;

/**
 * Created by Leon on 8/9/15.
 */
public class ShowEnergy {
    public static void main(String[] args)
    {
        Picture inputImg = new Picture("./resources/seamCarving/7x3.png");
        System.out.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
        //inputImg.show();
        SeamCarver sc = new SeamCarver(inputImg);

        System.out.printf("Displaying energy calculated for each pixel.\n");
        SeamCarver.printEnergy(sc);
    }
}
