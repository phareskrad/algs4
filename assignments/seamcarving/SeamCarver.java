package assignments;

import stdlib.Picture;

import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Created by Leon on 8/6/15.
 */
public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    private int[][] rgb;
    private double[][] energy;

    private double[] seamDist;
    private int[] seamPath;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = picture;
        width = picture.width();
        height = picture.height();

        setRGB(picture);
        initEnergy();

    }

    private void initEnergy() {
        energy = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setEnergy(i, j);
            }
        }
    }

    private void setRGB(Picture picture) {
        rgb = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgb[i][j] = picture.get(j, i).getRGB();
            }
        }
    }

    private void setEnergy(int i, int j) {
        if (i == 0 || j == 0 || i == height - 1 || j == width - 1)
            energy[i][j] = 1000000;
        else
            energy[i][j] = deltaX(j, i) + deltaY(j, i);
    }


    // current picture
    public Picture picture() {
        Picture p = new Picture(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                    p.set(j, i, new Color(rgb[i][j]));
            }
        }
        picture = p;
        setRGB(picture);
        initEnergy();
        return picture;
    }
    // width of current picture
    public     int width() {
        return width;
    }
    // height of current picture
    public     int height() {
        return height;
    }
    // energy of pixel at column x and row y
    public  double energy(int x, int y) {
        return energy[y][x];
    }
    //rgb number of col x, row y
    private int getRed(int x, int y) {
        return (rgb[y][x] >> 16) & 0xFF;
    }

    private int getGreen(int x, int y) {
        return (rgb[y][x] >> 8) & 0xFF;
    }

    private int getBlue(int x, int y) {
        return (rgb[y][x] >> 0) & 0xFF;
    }
    //delta value of col x, row y
    private double deltaX(int x, int y) {
        int reddiff = getRed(x + 1, y) - getRed(x - 1, y);
        int greendiff = getGreen(x + 1, y) - getGreen(x - 1, y);
        int bluediff = getBlue(x + 1, y) - getBlue(x - 1, y);
        return reddiff*reddiff + greendiff*greendiff + bluediff*bluediff;
    }

    private double deltaY(int x, int y) {
        int reddiff = getRed(x, y + 1) - getRed(x, y - 1);
        int greendiff = getGreen(x, y + 1) - getGreen(x, y - 1);
        int bluediff = getBlue(x, y + 1) - getBlue(x, y - 1);
        return reddiff*reddiff + greendiff*greendiff + bluediff*bluediff;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        findSP(height, width, false);
        int[] result = new int[width];
        for (int v = seamPath[width*height + 1]; v > 0; v = seamPath[v])
            result[(v - 1) / height] = (v - 1) % height;
        return result;
    }
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        findSP(width, height, true);
        int[] result = new int[height];
        for (int v = seamPath[width*height + 1]; v > 0; v = seamPath[v])
            result[(v - 1) / width] = (v - 1) % width;
        return result;
    }

    private void findSP(int width, int height, boolean vertical) {
        int n = width*height;
        seamDist = new double[n + 2];
        seamPath = new int[n + 2];

        for (int i = 1; i < n + 2; i++) {
            if (i <= width) seamDist[i] = 0;
            else seamDist[i] = Double.POSITIVE_INFINITY;
        }
        seamDist[0] = 0;

        for (int i = width + 1; i < n + 1; i++) {
            relax(i, width, vertical);
        }

        for (int i = n - width + 1; i < n + 1; i++) {
            relax(n + 1, i, 1000000);
        }
    }

    private void relax(int i, int width, boolean vertical) {
        if (vertical) relaxV(i, width);
        else relaxH(i, width);
    }

    private void relaxV(int i, int width) {
        int x = (i - 1) % width;
        int y = (i - 1) / width;

        if (x > 0) relax(i, i - width - 1, energy(x - 1, y - 1));
        if (x < width - 1) relax(i, i - width + 1, energy(x + 1, y - 1));
        relax(i, i - width, energy(x, y - 1));
    }

    private void relaxH(int i, int width) {
        int x = (i - 1) / width;
        int y = (i - 1) % width;

        if (y % width > 0) relax(i, i - width - 1, energy(x - 1, y - 1));
        if (y % width < width - 1) relax(i, i - width + 1, energy(x - 1, y + 1));
        relax(i, i - width, energy(x - 1, y));
    }

    private void relax(int to, int from, double e) {
        if (seamDist[to] > seamDist[from] + e) {
            seamDist[to] = seamDist[from] + e;
            seamPath[to] = from;
        }
    }

    // remove horizontal seam from current picture
    public    void removeHorizontalSeam(int[] seam) {
        height--;
        for (int i = 0; i < width; i++) {
            int rm = seam[i];
            int j = 0;
            while (j < height) {
                if (j >= rm) {
                    rgb[j][i] = rgb[j + 1][i];
                    energy[j][i] = energy[j + 1][i];
                }
                j++;
            }
        }

        for (int i = 0; i < width; i++) {
            int rm = seam[i];
            setEnergy(rm, i);
            if (i > 0) setEnergy(rm, i - 1);
            if (i < width - 1) setEnergy(rm, i + 1);
            if (rm > 0) setEnergy(rm - 1, i);
            if (rm < height - 1) setEnergy(rm + 1, i);
        }
    }



    // remove vertical seam from current picture
    public    void removeVerticalSeam(int[] seam) {
        width--;
        for (int i = 0; i < height; i++) {
            int rm = seam[i];
            int j = 0;
            while (j < width) {
                if (j >= rm) {
                    rgb[i][j] = rgb[i][j + 1];
                    energy[i][j] = energy[i][j + 1];
                }

                j++;
            }
        }

        for (int i = 0; i < height; i++) {
            int rm = seam[i];
            setEnergy(i, rm);
            if (i > 0) setEnergy(i - 1, rm);
            if (i < width - 1) setEnergy(i + 1, rm);
            if (rm > 0) setEnergy(i, rm - 1);
            if (rm < height - 1) setEnergy(i, rm + 1);
        }
    }

    public static void printEnergy(SeamCarver sc) {
        System.out.printf("Printing energy calculated for each pixel.\n");

        for (int j = 0; j < sc.height(); j++)
        {
            for (int i = 0; i < sc.width(); i++)
                System.out.printf("%9.0f ", sc.energy(i, j));

            System.out.println();
        }
    }
}
