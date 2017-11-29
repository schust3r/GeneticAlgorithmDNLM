package com.parma.filter;

import static org.junit.Assert.*;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import com.parma.images.ImageHandler;

public class DnlmFilterTest {

  @Test
  public void test() {

    ImageHandler ih = new ImageHandler();

    String filename = "001.jpg";

    Mat imagen = ih.leerImagenGrises("images/" + filename);
    DnlmFilter filter = new DnlmFilter();
    double mils = System.currentTimeMillis();

    Mat res = null;
    res = filter.filter(imagen, 11, 3, 69);

    System.out.println("Time of processing: " + (System.currentTimeMillis() - mils));
    ih.guardarImagen("images/", filename + "_out", "jpg", res);
    
    double checksum = Core.sumElems(res).val[0];
    System.out.printf("Checksum: %f\n", checksum);

    assertTrue("Resultado inaceptable.", Math.abs(checksum - 7909846.59852662) < 1750);

  }

}
