import java.io.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TopTen {
    public static LinkedList<Score> scores = new LinkedList<>();
    public static LinkedList<Score> winners;


    public static boolean checkTopTen(Score p){
        winners = scores
                .stream()
                .limit(10)
                .collect(Collectors.toCollection(LinkedList::new));
        if(winners.size() <10 || winners.getLast().getScore()<p.getScore()){
            return true;
        }
        else{
            return false;
        }

    }

    public static void addTopTen( Score p){
        if(winners.size()<10){
            winners.addLast(p);
        }
        else{
            winners.removeLast();
            winners.addLast(p);

        }
        Collections.sort(winners);
//        Iterator<Score> x =scores.descendingIterator();
//        int index =10;
//        try {
//            //find index where score fits in
//            while (x.hasNext()) {
//                if (p.getScore() > x.next().getScore()) {
//                    index = scores.indexOf(x.next());
//
//                }
//            }
//        }
//        //if list empty, just add to empty list
//        catch(Exception e){
//            scores.add(p);
//
//        }
//
//        //add to index where it belongs
//        try {
//            scores.add(index, p);
//        }
//        catch (Exception e){
//            scores.add(p);
//        }
//        System.out.println("Score Recorded");

    }

    public static void getTopTenList(){
        try
        {
            FileInputStream fis = new FileInputStream("scores.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            TopTen.scores = (LinkedList<Score>) ois.readObject();
            ois.close();



            // Clean up the file
            new File("scores.ser").delete();
            System.out.print("Loaded Scores");

        }
        catch (Exception ex){
            System.out.print("No Scores Recorded. Creating new score file ");
            new File("scores.ser");
        }

    }

    public static void SaveTopTenList(){
        try
        {

            FileOutputStream fos = new FileOutputStream("scores.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(TopTen.winners);
            oos.close();
            System.out.println("Saved Scores!");
        }
        catch (Exception ex)
        {
            System.out.print("Exception thrown while saving high scores" + ex.toString());
        }
    }

}
