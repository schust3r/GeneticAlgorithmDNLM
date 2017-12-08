package com.parma;

import org.opencv.core.Mat;
import com.parma.genetics.GaCalibration;
import com.parma.genetics.settings.Crossover;
import com.parma.genetics.settings.Fitness;
import com.parma.genetics.settings.GaSettings;
import com.parma.genetics.settings.Mutation;
import com.parma.genetics.settings.Segmentation;
import com.parma.images.ImageHandler;

public class Main {

  public static void main(String args[]) {

    try {
      GaSettings settings = new GaSettings();

      settings.setCrossoverType(Crossover.CLUSTER);
      settings.setFitnessFunction(Fitness.DICE);
      settings.setLowerW(1);
      settings.setUpperW(9);
      settings.setLowerWn(3);
      settings.setUpperWn(7);
      settings.setLowerSigmaR(1);
      settings.setUpperSigmaR(500);

      settings.setMaxGenerations(25);
      settings.setMaxIndividuals(100);
      settings.setMutationPerc((float) 0.05);
      settings.setMutationType(Mutation.RANDOM_BIT);
      settings.setSegmentationTechnique(Segmentation.OTSU);
      settings.setSelectionThreshold((float) 0.6);

      ImageHandler imageHandler = new ImageHandler();

      String absdir = ""; // "/home/scalderon/DnlmPortable/";

      Mat imagen1 = imageHandler.leerImagenGrises(absdir + "images/001.png");
      Mat imagengd1 = imageHandler.leerImagenGrises(absdir + "images/001_gt.png");

      settings.addToOriginalImages(imagen1);
      settings.addToGroundtruthImages(imagengd1);

      Mat imagen2 = imageHandler.leerImagenGrises(absdir + "images/002.png");
      Mat imagengd2 = imageHandler.leerImagenGrises(absdir + "images/002_gt.png");

      settings.addToOriginalImages(imagen2);
      settings.addToGroundtruthImages(imagengd2);

      Mat imagen3 = imageHandler.leerImagenGrises(absdir + "images/003.png");
      Mat imagengd3 = imageHandler.leerImagenGrises(absdir + "images/003_gt.png");

      settings.addToOriginalImages(imagen3);
      settings.addToGroundtruthImages(imagengd3);

      GaCalibration calibration = new GaCalibration(settings);

      calibration.runCalibration();

    } catch (Exception ex) {
      System.err.println(ex.getCause() + " - " + ex.getMessage() + " - " + ex.getStackTrace());
    }

  }

}
