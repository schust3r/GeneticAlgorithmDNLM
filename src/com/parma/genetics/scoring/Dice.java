package com.parma.genetics.scoring;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import com.parma.images.ImageHandler;

/**
 * Clase para calcular la metrica de Sorensen-Dice. 2 * |A intersect B| / ( |A| + |B| )
 * 
 * @author Joel Barrantes
 *
 */
public class Dice {

  /**
   * Sobrecarga de metodo para calcular el indice de similitud de Dice entre dos imagenes Mat.
   * 
   * @param umbralized Mat con imagen umbralizada
   * @param groundtruth Mat con imagen groundtruth
   * @return resultado de Dice
   */
  public static double calculateDice(Mat umbralized, Mat groundtruth) {

    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    float intersection = 0;
    int cardinalityA = umbralized.rows() * umbralized.cols();
    int cardinalityB = groundtruth.rows() * groundtruth.cols();

    // DEBUG ONLY
    ImageHandler ih = new ImageHandler();
    ih.guardarImagen("images", "umb_temp", "png", umbralized);
    ih.guardarImagen("images", "grn_temp", "png", groundtruth);

    Size sizeGroundTruth = groundtruth.size();
    int sizeX = (int) sizeGroundTruth.width;
    int sizeY = (int) sizeGroundTruth.height;

    for (int x = 0; x < sizeX; x++) {
      for (int y = 0; y < sizeY; y++) {
        double[] pixelU = umbralized.get(y, x);
        double[] pixelG = groundtruth.get(y, x);
        if (pixelU[0] == pixelG[0]) {
          intersection++;
        }
      }
    }
    return (2.0 * intersection) / ((double) cardinalityA + (double) cardinalityB);
  }


  public static double calculateDice2(Mat umbralized, Mat groundtruth) {

    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    float intersection = 0;
    double cardinalityA = Core.sumElems(umbralized).val[0] / 255.0;
    double cardinalityB = Core.sumElems(groundtruth).val[0] / 255.0;

    Size sizeGroundTruth = groundtruth.size();
    int sizeX = (int) sizeGroundTruth.width;
    int sizeY = (int) sizeGroundTruth.height;

    for (int x = 0; x < sizeX; x++) {
      for (int y = 0; y < sizeY; y++) {
        double[] pixelU = umbralized.get(y, x);
        double[] pixelG = groundtruth.get(y, x);
        if (pixelU[0] == pixelG[0] && pixelU[0] != 0) {
          intersection++;
        }
      }
    }
    return (2.0 * intersection) / (cardinalityA + cardinalityB);
  }


}
