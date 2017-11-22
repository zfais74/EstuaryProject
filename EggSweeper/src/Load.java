import java.io.*;

public class Load {
    public static void SaveGame(Controller cont){
        try
        {

            FileOutputStream fos = new FileOutputStream("savedata.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cont);
            oos.close();
        }
        catch (Exception ex)
        {
            System.out.print("Exception thrown while saving game" + ex.toString());
        }
    }
    public static void LoadGame(){
        try
        {
            FileInputStream fis = new FileInputStream("savedata.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Controller cont = (Controller) ois.readObject();
            ois.close();


            // Clean up the file
            new File("savedata.ser").delete();
        }
        catch (Exception ex)
        {
            System.out.print("Exception thrown during test: " + ex.toString());
        }

    }
}
