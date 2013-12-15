/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Controller.Controller;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import Model.Area;
import Model.DeliveryPoint;
import Model.Itinary;
import java.util.List;
import org.graphstream.graph.Node;


/**
 *
 * @author mattramo
 */
public class Welcome extends JFrame {
    
    private boolean alreadyLoad;
    private Controller mController;
    private Area mArea;
    private View mMap;
    private JPanel mButtonsPanel;
    private JPanel mListPanel;
    private JButton mButLoadPlan;
    private JButton mButLoadDelivery;
    private JButton mButComputeItinary;
    private JButton mButUnDo;
    private JButton mButReDo;
    private JButton mButDeleteDel;
    private JTable mDelTable;
    private DefaultTableModel mTableModel;
    
    
    public void fillTable(DefaultTableModel pTableModel) {
    
    List<Itinary> currentTour = mArea.getTour();
    
    
     //for each itinary.
        for (int i = 0; i < mArea.getTour().size(); i++) {
            
            Itinary currentItinary = currentTour.get(i);
            
            String startTime = currentItinary.getStart().toString();
            String endTime = currentItinary.getEnd().toString();
            
            
            
            //for each delivery within current itinary.
            for (int j = 0; j < currentItinary.getDeliveryNb(); j++) {
                
               Node address = currentItinary.getDeliveries().get(j);
               
               
               String deliveryAddress = address.getId();
               
               DeliveryPoint delPt = address.getAttribute("delivery");
               String idClient = delPt.getNclient();
               
               String[] tempRow = {idClient, deliveryAddress, startTime, endTime};
               pTableModel.addRow(tempRow);
     
               
                    
            }
        }
    }
    


        
        public Welcome(Controller controller, MultiGraph graph, Area pArea ) {
        
        mController = controller;
        mArea = pArea;
        
        this.setTitle("Welcome");
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //On définit le layout à utiliser sur le content pane
        this.setLayout(new BorderLayout());
      
      
        // On instancie les élèments de notre fenêtre.
        mButtonsPanel = new JPanel();
        mListPanel = new JPanel();
        
        mButLoadDelivery = new JButton(" Charger livraisons");
        mButLoadDelivery.setEnabled(false);
        mButLoadPlan = new JButton("Charger Plan");
        mButComputeItinary = new JButton("Calculer la tournée");
        mButComputeItinary.setEnabled(false);
        mButUnDo = new JButton("UnDo");
        mButUnDo.setEnabled(false);
        mButReDo = new JButton("Redo");
        mButReDo.setEnabled(false);
        mButDeleteDel = new JButton("Supprimer Livraison");
        mButDeleteDel.setEnabled(false);
                
                
       // Table
       String colNames[] = {"idClient", "adresse", "Début plage horaire", "Fin plage horaire"};
     
        
       final DefaultTableModel mTableModel = new DefaultTableModel(colNames, 0);
       JTable mDelTable = new JTable(mTableModel);
       
       
       JScrollPane scrollpane = new JScrollPane(mDelTable);
       
       mListPanel.add(scrollpane);
       
       
       
    
        
        //Initing and adding the graph viewer
        Viewer v = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        
        mMap = v.addDefaultView(false);
        
        mMap.setMouseManager(new MapMouseManager());
        
        mButtonsPanel.add(mButLoadPlan);
        mButtonsPanel.add(mButLoadDelivery);
        mButtonsPanel.add(mButComputeItinary);
        mButtonsPanel.add(mButReDo);
        mButtonsPanel.add(mButUnDo);
        mButtonsPanel.add(mButDeleteDel);
        
        this.getContentPane().add(mButtonsPanel, BorderLayout.PAGE_END);
        
        this.getContentPane().add(mMap, BorderLayout.CENTER);
        
        this.getContentPane().add(mListPanel, BorderLayout.LINE_END);
      
        final JFrame frame = this;
        
        
        // Gerstion des action.
        
        mButLoadPlan.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser mFcArea = new JFileChooser();

                int retval = mFcArea.showOpenDialog(frame);
                if (retval == JFileChooser.APPROVE_OPTION){

                    mController.loadPlan(mFcArea.getSelectedFile().getName());
                    mButLoadDelivery.setEnabled(true);

                }
             }
        });
        
        mButLoadDelivery.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser mFcArea = new JFileChooser();

                int retval = mFcArea.showOpenDialog(frame);
                if (retval == JFileChooser.APPROVE_OPTION){
                    
                    if ( alreadyLoad ) {
                    mController.loadDeliveries(mFcArea.getSelectedFile().getName());  
                    mButComputeItinary.setEnabled(true);
                    
                    mTableModel.setRowCount(0);
                    
                    }
                    else {
                        mController.loadDeliveries(mFcArea.getSelectedFile().getName());  
                        mButComputeItinary.setEnabled(true);
                    }
                    
                    fillTable(mTableModel);
                   
            
                }
            }
        });
        
        
        
        mButComputeItinary.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                    
                mController.computeRoadMap();
            }
        });
        
        mButReDo.addActionListener(new ActionListener() { 
            
            @Override
            public void actionPerformed(ActionEvent e) {
            
               mController.reDo(); 
                
            }
        });
      
        
        mButUnDo.addActionListener(new ActionListener() { 
            
            @Override
            public void actionPerformed(ActionEvent e) {
            
               mController.unDo(); 
                
            }
        });
        
        
        
        this.setVisible(true);
        }
    
    
    
   
}

   
    

