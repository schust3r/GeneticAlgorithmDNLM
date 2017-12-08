package com.parma.genetics.fitness;

import org.opencv.core.Mat;
import com.parma.filter.DnlmFilter;
import com.parma.genetics.ParamIndividual;
import com.parma.genetics.scoring.Dice;
import com.parma.genetics.settings.Fitness;
import com.parma.genetics.settings.Segmentation;
import com.parma.segmentation.Thresholder;

public class FitnessEval {

  private Fitness fitnessFunction;
  private Segmentation segTechnique;

  public FitnessEval(Fitness fitness, Segmentation seg) {
    this.fitnessFunction = fitness;
    this.segTechnique = seg;
  }

  public double evaluate(ParamIndividual p, Mat pOriginal, Mat pGroundtruth) {

    int w = p.getW();
    int w_n = p.getW_n();
    int sigma_r = p.getSigma_r();

    w = (w % 2 == 0) ? w++ : w;
    w_n = (w % 2 == 0) ? w_n++ : w_n;

    Mat original = new Mat();
    pOriginal.copyTo(original);

    // filter the image with DNLM-IDFT
    DnlmFilter filter = new DnlmFilter();
    Mat filteredImage = filter.filter(original, w, w_n, sigma_r);

    // cut black borders and apply same transformation to groundtruth
    int snipping = w + w_n;
    filteredImage = filteredImage.submat(snipping, filteredImage.rows() - snipping - 2, snipping,
        filteredImage.cols() - snipping - 2);
    pGroundtruth = pGroundtruth.submat(snipping, pGroundtruth.rows() - snipping - 2, snipping,
        pGroundtruth.cols() - snipping - 2);

    // segmentation of the filtered image
    filteredImage = applySegmentation(filteredImage);

    // calculate fitness with the specified similarity check function
    double fitness = getFitnessResult(filteredImage, pGroundtruth);

    original.release();
    filteredImage.release();

    return fitness;
  }


  private Mat applySegmentation(Mat image) {

    if (segTechnique == Segmentation.OTSU) {
      // apply the binary segmentation using OpenCV's Otsu
      Thresholder.applyOtsuThreshold(image);
      return image;
    }

    // for other segmentation techniques to be implemented
    else {
      return new Mat();
    }

  }


  private double getFitnessResult(Mat image, Mat groundtruth) {
    switch (fitnessFunction) {
      case DICE:
        return Dice.calculateDice(image, groundtruth);
      /*
       * More methods to be added ...
       */
      default:
        return 0;
    }
  }


}
