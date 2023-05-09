package view;

import it.polimi.ingsw.View.Clidepecrated;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class clitest {
 Clidepecrated clidepecrated;

 @Before
 public void red() {
  clidepecrated = new Clidepecrated();
 }

 @Test
 public void nothing() {
  assertEquals(true, true);
 }
 @After
 public void destroy()
 {
  clidepecrated =null;
}


}
