/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Controller.MainController;
import Model.Area;
import java.awt.BorderLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

/**
 *
 * @author mattramo
 */
public class Welcome extends JFrame {
    
    private MainController mController;
    private JPanel mContentF;
    private JPanel mButtonsF;
    private JButton mButLoadPlan;
    private JButton mButLoadDelivery;
    private MultiGraph mGraph;
    //private JFileChooser mFcArea;
    //private JFileChooser mFcDeliveries;
    
    
  public Welcome (MainController pController) {
      
      this.setTitle("Bouton");
      this.setSize(300, 300);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLocationRelativeTo(null);
            //On définit le layout à utiliser sur le content pane
      this.setLayout(new BorderLayout());
      
      
      // On instancie les élèments de notre fenêtre.
      
      mController = pController;
      mContentF = new JPanel();
      mButtonsF = new JPanel();
      mButLoadDelivery = new JButton(" Charger livraisons");
      mButLoadDelivery.setEnabled(false);
      mButLoadPlan = new JButton("Charger Plan");
      
      
      this.getContentPane().add(mContentF, BorderLayout.CENTER);
      this.getContentPane().add(mButtonsF, BorderLayout.EAST);
      
      mButtonsF.add(mButLoadPlan);
      mButtonsF.add(mButLoadDelivery);
      
      
     final JFrame frame = this;
     mButLoadPlan.addActionListener(new ActionListener() {
          
          @Override
          public void actionPerformed(ActionEvent e) {
               JFileChooser mFcArea = new JFileChooser();
               
               int retval = mFcArea.showOpenDialog(frame);
               if (retval == JFileChooser.APPROVE_OPTION){
                   
                   mGraph = mController.loadPlan(mFcArea.getSelectedFile().getName());
                   Viewer v = new Viewer(mGraph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
                   View view = v.getDefaultView();
                   view.setMouseManager(new PlanMouseManager());
                   mContentF.add(view);
                   
                   mButLoadDelivery.setEnabled(true);
                   
                   }
          }
      });
      
      
      
      
      
      
      this.setVisible(true);
      
}

  

   
}
