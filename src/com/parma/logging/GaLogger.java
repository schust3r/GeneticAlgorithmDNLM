package com.parma.logging;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GaLogger {

  private Logger logger = Logger.getLogger(GaLogger.class.getName());
  private FileHandler fh = null;

  public GaLogger(String name) {
    try {
      fh = new FileHandler("logs/" + name + ".log");
    } catch (Exception e) {
      e.printStackTrace();
    }
    fh.setFormatter(new SimpleFormatter());
    logger.addHandler(fh);
    header();
  }
  
  public void header() {
    logger.info("generation,average_fitness,best_fitness,best_w,best_w_n,best_s_r");
  }
 
  public void log(int gen, double avgf, double bestf, int bestw, int bestwn, int bestsr) {
    logger.info(gen + "," + avgf + "," + bestf + "," + bestw + "," + bestwn + "," + bestsr);
  }
  
}