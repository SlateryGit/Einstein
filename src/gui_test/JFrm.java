package gui_test;
import java.awt.Container;
//import java.util.Scanner;
import java.awt.GridLayout;
import java.awt.event.*;
//import java.awt.event.ActionListener;

import javax.swing.*;

public class JFrm extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton buttons[][];
	private Container container;
	private GridLayout jfr1;
	//grid1 = new Grid
	public JFrm() {
		
		//JFrame jfr1 = new JFrame();
		
		setTitle("Einstein");
		setSize(360,240);
		setLocation(500, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfr1 = new GridLayout(5, 5, 1, 1);
		container=getContentPane();
		container.setLayout(jfr1);
		buttons = new JButton[5][5];
		char i,j;
		for(i=0;i<5;i++) {
			for(j=0;j<5;j++) {
				String s="0";
				;
				buttons[i][j]=new JButton(s);
				//buttons[i].addActionListener(this);
				container.add(buttons[i][j]);
			}
		}
		//Scanner s=new Scanner(System.);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//JFrm App1 = new JFrm();
		//App1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Matrix_test m=new Matrix_test();
		m.print();
		//new
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
