package com.parma.genetics;

import static org.junit.Assert.*;
import org.junit.Test;

public class ParamIndividualTest {

  @Test
  public void test() {
    ParamIndividual p = new ParamIndividual();
    p.setLambda((float) 1.73973172);
    System.out.println(p.getLambda());
    System.out.println(System.getProperty("os.name"));
    
  }

}
