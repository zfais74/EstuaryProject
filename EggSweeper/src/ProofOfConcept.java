
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.Serializable;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProofOfConcept extends JFrame implements Serializable {
	
	JButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
	JButton butPlus, butMinus, clearAll;
	JTextField textResult;
	
	public void calcExample(JPanel thePanel) {
		GridBagConstraints gridConstraints = new GridBagConstraints();
		
		//The constraints are defined every time you add an element
		//Grid x and grid y are the positions the component starts at
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 1;
		//Grid width and grid height define how many spaces the component takes up
		gridConstraints.gridwidth = 1;
		gridConstraints.gridheight = 1;
		//Something about a hint on how the components can fit into place
		gridConstraints.weightx = 50;
		gridConstraints.weighty = 100;
		
		gridConstraints.insets = new Insets(5,5,5,5);
		gridConstraints.anchor = GridBagConstraints.CENTER;
		gridConstraints.fill = GridBagConstraints.BOTH;
		
		textResult = new JTextField("0",20);
		
		thePanel.add(clearAll,gridConstraints);
		gridConstraints.gridwidth = 20;
		gridConstraints.gridx = 5;
		thePanel.add(textResult,gridConstraints);
		gridConstraints.gridwidth = 1;
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 2;
		thePanel.add(button1,gridConstraints);
		gridConstraints.gridx = 5;
		thePanel.add(button2,gridConstraints);
		gridConstraints.gridx = 9;
		thePanel.add(button3,gridConstraints);
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 3;
		thePanel.add(button4,gridConstraints);
		gridConstraints.gridx = 5;
		thePanel.add(button5,gridConstraints);
		gridConstraints.gridx = 9;
		thePanel.add(button6,gridConstraints);
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 4;
		thePanel.add(button7,gridConstraints);
		gridConstraints.gridx = 5;
		thePanel.add(button8,gridConstraints);
		gridConstraints.gridx = 9;
		thePanel.add(button9,gridConstraints);
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 5;
		thePanel.add(butMinus,gridConstraints);
		gridConstraints.gridx = 5;
		thePanel.add(button0,gridConstraints);
		gridConstraints.gridx = 9;
		thePanel.add(butPlus,gridConstraints);
	}
	
	public void beachExample() {
		
		GridBagConstraints constraints = new GridBagConstraints();
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		constraints.gridx = 0;
		constraints.gridy = 0;
		//Grid width and grid height define how many spaces the component takes up
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		//Something about a hint on how the components can fit into place
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(0,0,0,0);
		constraints.fill = GridBagConstraints.BOTH;
		
		for(int y = 0; y < 20; y++) {
			for(int x = 0; x < 20; x++) {
				JButton btn = new JButton();
				btn.setText(String.format("[%02d,%02d]",x,y));
				constraints.gridx = x;
				constraints.gridy = y;
				panel.add(btn, constraints);
			}
		}
		this.add(panel);
		this.pack();
	}
	
	public void setup() {
		
		
		JPanel thePanel = new JPanel();
		button0 = new JButton("0");
		button1 = new JButton("1");
		button2 = new JButton("2");
		button3 = new JButton("3");
		button4 = new JButton("4");
		button5 = new JButton("5");
		button6 = new JButton("6");
		button7 = new JButton("7");
		button8 = new JButton("8");
		button9 = new JButton("9");
		butPlus = new JButton("+");
		butMinus = new JButton("-");
		clearAll = new JButton("C");
		//FlowLayout
//		thePanel.setLayout(new FlowLayout(FlowLayout.LEFT,30,20));
//		thePanel.add(button1);
//		thePanel.add(button2);
//		thePanel.add(button3);
//		thePanel.add(button4);
//		thePanel.add(button5);
		
		//BorderLayout
//		thePanel.setLayout(new BorderLayout());
//		thePanel.add(button1,BorderLayout.NORTH);
//		thePanel.add(button2,BorderLayout.SOUTH);
//		thePanel.add(button3,BorderLayout.WEST);
//		thePanel.add(button4,BorderLayout.EAST);
//		thePanel.add(button5,BorderLayout.CENTER);
		
		//BoxLayout
//		Box theBox = Box.createHorizontalBox();
//		theBox.add(button1);
//		theBox.add(Box.createHorizontalStrut(4));
//		theBox.add(button2);
//		theBox.add(Box.createRigidArea(new Dimension(30,20)));
//		theBox.add(button3);
		
		//GridLayout
		thePanel.setLayout(new GridLayout(0,3,2,2));
		
		//GridBagLayout
		thePanel.setLayout(new GridBagLayout());
		
		
		
		this.add(thePanel);
		this.setVisible(true);
	}
	public ProofOfConcept() {
		//this.setSize(400,400);
		this.setPreferredSize(new Dimension(1000,1000));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("My frame");
		beachExample();
		this.setVisible(true);
	}
	public static void main(String args[]) {
		new ProofOfConcept();
	}
}
