import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

// The View

public class GUI{
	
	JFrame gui;
	
	
	GUI(){
		gui = buildGUI();
		//Display the window.
        gui.pack();
        gui.setVisible(true);
        addButtonTest();
	}
	
	public void addButtonTest() {
		JButton helloButton = new JButton("Click Me!!!");
		helloButton.setVisible(true);
		helloButton.setLocation(250, 275);
		helloButton.setSize(100, 50);
		gui.getContentPane().add(helloButton);
	}
	
	public JFrame buildGUI(){
		//Create and set up the window.
        JFrame frame = new JFrame("Egg Sweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent contentPane = new JLayeredPane();
        contentPane.setPreferredSize(new Dimension(600, 600));
        //pane.setOpaque(true); 
        // adding the pane to the window
        frame.setContentPane(contentPane);
        
        return frame;
	}
	
	
}