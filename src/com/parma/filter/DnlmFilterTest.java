package com.parma.filter;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.opencv.core.Mat;
import com.parma.genetics.scoring.Dice;
import com.parma.images.ImageHandler;
import com.parma.segmentation.Thresholder;

public class DnlmFilterTest {

  @Test
  public void testFilter() {

    ImageHandler ih = new ImageHandler();
    //3,8,66,28.36
    String filename = "001";
    int w = 3;
    int w_n = 9;
    int sigma_r = 66;
    double lambda = 28.36;

    List<Mat> imagenes = new ArrayList<>();
    List<Mat> gd = new ArrayList<Mat>();
    
    Mat imagen1 = ih.leerImagenGrises("images/Recortadas/" + "001" + ".png");
    Mat groundtruth1 = ih.leerImagenGrises("images/GT/" + "001"+ ".png");

    Mat imagen2 = ih.leerImagenGrises("images/Recortadas/" + "255" + ".png");
    Mat groundtruth2 = ih.leerImagenGrises("images/GT/" + "255" + ".png");

    Mat imagen3 = ih.leerImagenGrises("images/Recortadas/" + "577" + ".png");
    Mat groundtruth3 = ih.leerImagenGrises("images/GT/" + "577" + ".png");
    
    imagenes.add(imagen1);
    imagenes.add(imagen2);
    imagenes.add(imagen3);
    gd.add(groundtruth1);
    gd.add(groundtruth2);
    gd.add(groundtruth3);
    

   
    
    // apply filter
    DnlmFilter filter = new DnlmFilter();
    int i=0;
    double total=0;
    while (i<3) {
      System.out.println("processing...");
        Mat res = filter.filter(imagenes.get(i), w, w_n, sigma_r, lambda);
      
  
      // cut unwanted borders
      int snip = w + w_n;
      res = res.submat(snip, res.rows() - snip - 2, snip, res.cols() - snip - 2);
      Mat groundtruth = gd.get(i);
      groundtruth = groundtruth.submat(snip, groundtruth.rows() - snip - 2, snip,
      groundtruth.cols() - snip - 2);
      
      //ih.guardarImagen("C:\\Users\\Eliot\\Documents\\GitHub\\GeneticAlgorithmDNLM\\images\\", filename + "_out_nootsu", "png", res);
  
      // apply binarization + Otsu thresholding
      Thresholder.applyOtsuThreshold(res);
      Thresholder.applyThreshold(groundtruth, 1);  
      
  
      // calculate dice similarity index
      total += Dice.calculateDice(res, groundtruth);
      //System.out.println(total);
      i++;
    }
   // ih.guardarImagen("C:\\Users\\Eliot\\Documents\\GitHub\\GeneticAlgorithmDNLM\\images\\", filename + "_out", "png", res);
    
    System.out.println(total/3);

  }

}
