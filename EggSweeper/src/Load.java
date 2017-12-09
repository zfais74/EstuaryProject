import java.io.*;

/**
 * This class was meant to control the serialization of the game, but has not been fully implemented.
 * @author Chima Akparanta
 *
 */
public class Load {
    /**
     * Save the game status to savedata.ser using Serializable
     * 
     * @param cont
     */
    public static void SaveGame(Controller cont){//everything but buffered image
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
    /**
     * Load the game from savedata.ser using Serializable
     */
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
