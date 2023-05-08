package view;

import it.polimi.ingsw.View.Cli;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class clitest {
 Cli cli;

 @Before
 public void red() {
  cli = new Cli();
 }

 @Test
 public void nothing() {
  assertEquals(true, true);
 }
 @After
 public void destroy()
 {
  cli=null;
}


}
