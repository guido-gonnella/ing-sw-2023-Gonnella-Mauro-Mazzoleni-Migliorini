package view;

import it.polimi.ingsw.Model.Enumeration.Type;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.View.Clidepecrated;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class clitest {
 ArrayList<Tile> hand;
 PrintStream out;

 @Before
 public void red() {
  out=System.out;
  hand=new ArrayList<>();
  hand.add(new Tile(Type.CAT));
  hand.add(new Tile(Type.TROPHY));
  hand.add(new Tile(Type.PLANT));
 }

 @Test
 public void nothing() {
  out.println("0 is the tile placed at the bottom of the column");
  for(int i=0;i<hand.size(); i++){
   out.print(" "+i+" ");
  }
  out.print("\n");
  for (Tile tile : hand) {
   switch (tile.getType()) {
    case TROPHY -> out.print("\u001B[36m" + "[T]" + "\u001B[0m");
    case FRAME -> out.print("\u001B[34m" + "[F]" + "\u001B[0m");
    case PLANT -> out.print("\u001B[35m" + "[P]" + "\u001B[0m");
    case GAME -> out.print("\u001B[33m" + "[G]" + "\u001B[0m");
    case BOOK -> out.print("\u001B[37m" + "[B]" + "\u001B[0m");
    case CAT -> out.print("\u001B[32m" + "[C]" + "\u001B[0m");
    default -> out.print("\u001B[30m" + "[■]" + "\u001B[0m");
   }
  }
  assertEquals(true, true);
 }
 @After
 public void destroy()
 {
  hand=null;
 }


}
