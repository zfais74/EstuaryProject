import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class TopTen {
    public static LinkedList<Player> scores = new LinkedList<>();


    public static void checkTopTen(Player p){
        Iterator<Player> x =scores.descendingIterator();
        int index =10;
        while (x.hasNext()) {
            if(p.getScore()>x.next().getScore()){
                index = scores.indexOf(x.next());

        }
        else{
                break;
            }

    }
        try {
            scores.add(index, p);
        }
        catch (Exception e){
            scores.add(p);
        }

    }

    public static void getTopTenList(){
        try
        {
            FileInputStream fis = new FileInputStream("scores.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            TopTen.scores = (LinkedList<Player>) ois.readObject();
            ois.close();



            // Clean up the file
            new File("scores.ser").delete();
            System.out.print("Loaded Scores");

        }
        catch (Exception ex)
        {
            System.out.print("No Scores Recorded. Creating new score file ");
            new File("scores.ser");
        }

    }

    public static void SaveTopTenList(){
        try
        {

            FileOutputStream fos = new FileOutputStream("scores.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(TopTen.scores);
            oos.close();
            System.out.println("Saved Scores!");
        }
        catch (Exception ex)
        {
            System.out.print("Exception thrown while saving high scores" + ex.toString());
        }
    }
}
