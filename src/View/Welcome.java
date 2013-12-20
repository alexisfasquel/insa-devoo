
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

import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import Model.Area;
import Model.DeliveryPoint;
import Model.Itinary;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


/**
 * Welcome is main Frame of the application
 */
public class Welcome extends JFrame {
    
    private static boolean mAlreadyLoad;
    private Controller mController;
    private Area mArea;
    private View mMap;
    private JPanel mButtonsPanel;
    private JPanel mListPanel;
    private JPanel mItinaryPanel;
    private JButton mButLoadPlan;
    private JButton mButLoadDelivery;
    private JButton mButComputeItinary;
    private JButton mButUnDo;
    private JButton mButReDo;
    private JButton mButDeleteDel;
    private JButton mButAdd;
    private JTable mDelTable;
    private JTable mListItinary;
    private DefaultTableModel mTableModel;
    private MapMouseManager mMapListener;

    
    /**
     * Create the welcome Frame
     * @param controller
     * @param graph
     * @param pArea
     */
    public Welcome(Controller controller, SingleGraph graph, Area pArea ) {
        
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
        mItinaryPanel= new JPanel();
        
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
        mButAdd = new JButton("Ajouter Livraison");
        mButAdd.setEnabled(false);
                
                
       // Table
       String colNames[] = {"idClient", "adresse", "Début plage horaire", "Fin plage horaire"};
       String colNames2[] = {"dateDébut", "DateFin"};
     
        
       final DefaultTableModel mTableModel = new DefaultTableModel(colNames, 0);
       final JTable mDelTable = new JTable(mTableModel);
     
       
       
       final DefaultTableModel mItinaryModel = new DefaultTableModel(colNames2, 0);
       JTable mItinaryTable = new JTable(mItinaryModel); 
       
       JScrollPane scrollpane = new JScrollPane(mDelTable);
       scrollpane.setSize(20, 20);
       JScrollPane scrollpane2 = new JScrollPane(mItinaryTable);
      
       // mItinaryPanel.add(scrollpane2);
       mListPanel.setLayout(new BorderLayout());
       mListPanel.add(scrollpane,BorderLayout.NORTH);
       mItinaryPanel.setLayout(new BorderLayout());
       mListPanel.add(mItinaryPanel,BorderLayout.LINE_END);
       mItinaryPanel.add(scrollpane2,BorderLayout.LINE_START);
       mItinaryPanel.add(mButAdd,BorderLayout.SOUTH);
   
       mItinaryPanel.setVisible(false);
    
        
        //Initing and adding the graph viewer
        Viewer v = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        
        mMap = v.addDefaultView(false);
        mMapListener = new MapMouseManager(mController,mButDeleteDel,mItinaryPanel);
        mMap.setMouseManager(mMapListener);
        
        mButtonsPanel.add(mButLoadPlan);
        mButtonsPanel.add(mButLoadDelivery);
        mButtonsPanel.add(mButComputeItinary);
        mButtonsPanel.add(mButReDo);
        mButtonsPanel.add(mButUnDo);
        mButtonsPanel.add(mButDeleteDel);
        
        this.getContentPane().add(mButtonsPanel, BorderLayout.PAGE_END);
        
        this.getContentPane().add(mMap, BorderLayout.CENTER);
    
        this.getContentPane().add(mListPanel, BorderLayout.LINE_END);
        
      //  this.getContentPane().add( mItinaryPanel,FlowLayout.RIGHT);
      
        final JFrame frame = this;
        
        
        // Gerstion des action.
        mButLoadPlan.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mAlreadyLoad && !warning())
                    return; 
                JFileChooser mFcArea = new JFileChooser();

                int retval = mFcArea.showOpenDialog(frame);
                if (retval == JFileChooser.APPROVE_OPTION){

                    
                    mController.loadPlan(mFcArea.getSelectedFile().getPath());
                    mButLoadDelivery.setEnabled(true);

                }
             }
        });
        
        mButLoadDelivery.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mAlreadyLoad && !warning())
                    return;
                JFileChooser mFcArea = new JFileChooser();

                int retval = mFcArea.showOpenDialog(frame);
                if (retval == JFileChooser.APPROVE_OPTION){
                    
                    if ( mAlreadyLoad ) {
                        mTableModel.setRowCount(0);
                        //mItinaryModel.setRowCount(0);
                    }
                    mController.loadDeliveries(mFcArea.getSelectedFile().getPath());  
                    mButComputeItinary.setEnabled(true);
                    
                    fillTable(mTableModel);
                    //fillItinaryTable(mItinaryModel);
            
                }
            }
        });
        
        mItinaryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                mButAdd.setEnabled(true);
            }
        });
        
        final JTable table = mItinaryTable;
        mButAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                mController.addDelivery(mMapListener.getSelected(), selectedRow);
                mMapListener.deselect();
                if(mController.CheckUndo()){
                    mButUnDo.setEnabled(true);
                    mButAdd.setEnabled(false);
                    mItinaryPanel.setVisible(false);
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
                if(!mController.CheckRedo()){
                mButReDo.setEnabled(false);
            }
                 if(mController.CheckUndo()){
                mButUnDo.setEnabled(true);
            }
                
            }
        });
      
        
        mButUnDo.addActionListener(new ActionListener() { 
            
            @Override
            public void actionPerformed(ActionEvent e) {
            
               mController.unDo(); 
               if(mController.CheckRedo()){
                mButReDo.setEnabled(true);
            }
                if(!mController.CheckUndo()){
                mButUnDo.setEnabled(false);
            }
                
            }
        });
        
        mButDeleteDel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mController.DeleteDelivery(mMapListener.getSelected());   
                mMapListener.deselect();
                if(mController.CheckUndo()){
                    mButUnDo.setEnabled(true);
                }
            }
 
            
        });
        
        mItinaryPanel.addComponentListener ( new ComponentAdapter ()
        {
            public void componentShown ( ComponentEvent e )
            {
                /////Mettre le noeud en avant(Grossir)
                    //Node node= mController.getCurrentNode();
                    mItinaryModel.setRowCount(0);
                    fillItinaryTable(mItinaryModel);
            }

            public void componentHidden ( ComponentEvent e )
            {
                System.out.println ( "Component hidden" );
            }
        } );
        
        this.setVisible(true);
        }
    
    
    /**
     * Display a popup to warn the user about an error
     * @param title
     * @param message
     */
    public void displayPopup(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public boolean warning() {
        int showConfirmDialog = JOptionPane.showConfirmDialog(this, "Attention, vous aller supprimer la tournée en cours\nEtes vous sur ?", "Attention !", JOptionPane.YES_NO_OPTION);
        if(showConfirmDialog == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Fill the mTableModel with the informations about the deliveryPoint
     * @param pTableModel
     */
    public void fillTable(DefaultTableModel pTableModel) {
    mAlreadyLoad = true;
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

    /**
     * Fill the mTableModel with the informations about the itinary
     * @param pTableModel
     */
    public void fillItinaryTable(DefaultTableModel pTableModel) {
    
    List<Itinary> currentTour = mArea.getTour();
    
    
     //for each itinary.
        for (int i = 0; i < mArea.getTour().size(); i++) {
            
            Itinary currentItinary = currentTour.get(i);
            String startTime = currentItinary.getStart().toString();
            String endTime = currentItinary.getEnd().toString();
            String[] tempRow = {startTime, endTime};
            pTableModel.addRow(tempRow);
     
        }
    }
    
    /**
     * Select a node in the graph
     * @param node
     */
    public void selectionnerNode(Node node){
        DeliveryPoint dp = node.getAttribute("delivery");
        for(int i =0;i<mTableModel.getRowCount();i++){
            if(dp.getNclient()==mTableModel.getValueAt(1, i)){
                mTableModel.setColumnCount(i);
            }else{
                i++;
            }
        }
    }
    
    
   
}

   
    

