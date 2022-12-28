package trees;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package dsatreeassignment3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author sehall
 */
public class GUITree extends JPanel implements ActionListener
{
   private final JButton addButton, removeButton, levelOrderTraverseButton,
           leftRotateButton, rightRotateButton,inOrderTraverse;

   private DrawPanel drawPanel;
   BinarySearchTree<String> tree;
   public static int PANEL_H = 500;
   public static int PANEL_W = 700;
   public Timer timer;

   public GUITree()
   {
      super(new BorderLayout());
      try
      {  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch(Exception e){}
      tree = new BinarySearchTree<>();
 
      timer = new Timer(100,this);
      super.setPreferredSize(new Dimension(PANEL_W,PANEL_H+30));
      JPanel buttonPanel = new JPanel();
      buttonPanel.setPreferredSize(new Dimension(PANEL_W,30));
      drawPanel = new DrawPanel();

      addButton = new JButton("Add Node");
      removeButton = new JButton("Remove Node");
      levelOrderTraverseButton = new JButton("Level Order Traverse");
      inOrderTraverse = new JButton("In Order Traverse");
      leftRotateButton = new JButton("Left Rotate");
      rightRotateButton = new JButton("Right Rotate");

      addButton.addActionListener((ActionListener)this);
      removeButton.addActionListener((ActionListener)this);
      levelOrderTraverseButton.addActionListener((ActionListener)this);
      leftRotateButton.addActionListener((ActionListener)this);
      rightRotateButton.addActionListener((ActionListener)this);
      inOrderTraverse.addActionListener((ActionListener)this);

      buttonPanel.add(addButton);
      buttonPanel.add(removeButton);
      buttonPanel.add(levelOrderTraverseButton);
      buttonPanel.add(inOrderTraverse);
      buttonPanel.add(leftRotateButton);
      buttonPanel.add(rightRotateButton);

      super.add(drawPanel,BorderLayout.CENTER);
      super.add(buttonPanel,BorderLayout.SOUTH);
      
      timer.start();
   }

   @Override
   public void actionPerformed(ActionEvent event)
   {
      Object source = event.getSource();

      if(source == timer)
      {
         drawPanel.repaint();
      }
      else if(source == addButton)
      {
         String input = JOptionPane.showInputDialog(this, "Enter a small String to add to the tree");
         boolean addS = tree.add(input);
         if(addS)
            drawPanel.repaint();
         else
            JOptionPane.showMessageDialog(this,"THAT ELEMENT IS ALREADY IN THE TREE!","HEY",JOptionPane.INFORMATION_MESSAGE);
      }
      else if(source == removeButton)
      {
         String input = JOptionPane.showInputDialog(this, "Enter a value to Remove from the tree");
         tree.remove(input);
        
         drawPanel.repaint();
        
         
      }
      else if(source == levelOrderTraverseButton && tree.size() > 0 )
      {  
         tree.levelOrderTraversal();
      }
      else if(source == inOrderTraverse && tree.size() > 0 )
      {
          tree.inOrderTraversal();
      }
      else if(source == leftRotateButton)
      {   String input = JOptionPane.showInputDialog(this, "Enter a Node to Rotate Left");
          if(input != null)tree.leftRotation(input);
      }
      else if(source == rightRotateButton)
      {   String input = JOptionPane.showInputDialog(this, "Enter a Node to Rotate Left");
          if(input != null)tree.rightRotation(input);
      }
      drawPanel.repaint();
   }

   
   private class DrawPanel extends JPanel
   {
      public DrawPanel()
      {
         super();
         super.setBackground(Color.WHITE);
         super.setPreferredSize(new Dimension(PANEL_W,PANEL_H));
      }

      @Override
      public void paintComponent(Graphics g)
      {
         super.paintComponent(g);
     
         if(tree.size() > 0)
             tree.drawTree(g, getWidth());
      }
   }

   public static void main(String[] args)
    {
       JFrame frame = new JFrame("Seth - GUI TREE");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.getContentPane().add(new GUITree());
       Toolkit toolkit = Toolkit.getDefaultToolkit();
       Dimension dimension = toolkit.getScreenSize();
       int screenHeight = dimension.height;
       int screenWidth = dimension.width;
       frame.pack();             //resize frame apropriately for its content
       //positions frame in center of screen
       frame.setLocation(new Point((screenWidth/2)-(frame.getWidth()/2),
         (screenHeight/2)-(frame.getHeight()/2)));
       frame.setVisible(true);
    }
}
