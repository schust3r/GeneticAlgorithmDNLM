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

    String filename = "001";
    int w = 5;
    int w_n = 5;
    int sigma_r = 310;

    Mat imagen = ih.leerImagenGrises("images/Recortadas/" + filename + ".png");
    Mat groundtruth = ih.leerImagenGrises("images/GT/" + filename + ".png");

    // apply filter
    DnlmFilter filter = new DnlmFilter();
   
    Mat res = filter.filter(imagen, w, w_n, sigma_r, 1.6);

    // cut unwanted borders
    int snip = w + w_n;
    res = res.submat(snip, res.rows() - snip - 2, snip, res.cols() - snip - 2);
    groundtruth = groundtruth.submat(snip, groundtruth.rows() - snip - 2, snip,
        groundtruth.cols() - snip - 2);
    
    //ih.guardarImagen("C:\\Users\\Eliot\\Documents\\GitHub\\GeneticAlgorithmDNLM\\images\\", filename + "_out_nootsu", "png", res);

    // apply binarization + Otsu thresholding
    Thresholder.applyOtsuThreshold(res);
    Thresholder.applyThreshold(groundtruth, 1);  
    

    // calculate dice similarity index
    double dice = Dice.calculateDice(res, groundtruth);

   // ih.guardarImagen("C:\\Users\\Eliot\\Documents\\GitHub\\GeneticAlgorithmDNLM\\images\\", filename + "_out", "png", res);
    
    System.out.println(dice);

  }

}
