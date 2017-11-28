import java.io.*;

public class Load {
    public static void SaveGame(Board cont){//everything but buffered image
        try
        {

            FileOutputStream fos = new FileOutputStream("savedata.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cont);
            oos.close();
            System.out.println("Saved");
        }
        catch (Exception ex)
        {
            System.out.print("Exception thrown while saving game" + ex.toString());
        }
    }
    public static Board LoadGame(){
        Board cont = null;
        try
        {
            FileInputStream fis = new FileInputStream("savedata.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            cont = (Board) ois.readObject();
            ois.close();



            // Clean up the file
            new File("savedata.ser").delete();
            System.out.print("Loaded");

        }
        catch (Exception ex)
        {
            System.out.print("Exception thrown during test: " + ex.toString());
        }

        return cont;
    }

}
