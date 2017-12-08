package com.parma.filter;

import org.junit.Test;
import org.opencv.core.Mat;
import com.parma.genetics.scoring.Dice;
import com.parma.images.ImageHandler;
import com.parma.segmentation.Thresholder;

public class FilterResultTest {

  @Test
  public void testFilter() {

    ImageHandler ih = new ImageHandler();

    String filename = "002";
    int w = 3;
    int w_n = 5;
    int sigma_r = 300;

    Mat imagen = ih.leerImagenGrises("images/" + filename + ".png");
    Mat groundtruth = ih.leerImagenGrises("images/" + filename + "_gt.png");

    // apply filter
    Mat res = DnlmFilter.filter(imagen, w, w_n, sigma_r);

    // cut unwanted borders
    int snip = w + w_n;
    res = res.submat(snip, res.rows() - snip - 2, snip, res.cols() - snip - 2);
    groundtruth = groundtruth.submat(snip, groundtruth.rows() - snip - 2, snip,
        groundtruth.cols() - snip - 2);

    // apply binarization + Otsu thresholding
    Thresholder.applyOtsuThreshold(res);
    Thresholder.applyThreshold(groundtruth, 1);  

    // calculate dice similarity index
    double dice = Dice.calculateDice2(res, groundtruth);

    ih.guardarImagen("images/", filename + "_out", "png", res);
    
    System.out.println(dice);

  }

}
