/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.DeliveryPoint;
import Model.Itinary;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Selector;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;

/**
 *
 * @author Aleks
 */
public class MapMouseManager extends DefaultMouseManager {

    private Controller mController;
    private JButton mButDeleteDel;
    private JPanel mListPanel;

    private Node mSelected;
    
    public MapMouseManager(Controller controller, JButton ButDeleteDel, JPanel ListPanel) {
        super();
        mController = controller;
        mButDeleteDel = ButDeleteDel;
        mListPanel = ListPanel;
    }

    @Override
    public void mouseDragged(MouseEvent event) {
    }

    @Override
    protected void mouseButtonPress(MouseEvent event) {}

    public Node getSelected() {
        return mSelected;
    }
    @Override
    public void mouseClicked(MouseEvent event) {

        GraphicElement element = view.findNodeOrSpriteAt(event.getX(), event.getY());
        if (element != null) {
            if(mSelected != null) {
                    mSelected.removeAttribute("ui.selected");
            }
            if (element.getSelectorType() == Selector.Type.NODE) {
                
                mSelected = (Node)element;
                mSelected.addAttribute("ui.selected");
                //mListPanel.setVisible(false);
                //mButDeleteDel.setEnabled(false);
            }
            if (element.getAttribute("delivery") == null) {
                mListPanel.setVisible(true);
            } else {
                mButDeleteDel.setEnabled(true);
            }
        } 
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        GraphicElement element = view.findNodeOrSpriteAt(e.getX(), e.getY());

        if (curElement != null) {
            if (element == null) {
                curElement.addAttribute("ui.style", "text-mode: hidden;");
                curElement = null;
                return;
            } else if (curElement.equals(element)) {
                return;
            }
            curElement.addAttribute("ui.style", "text-mode: hidden;");
            curElement = null;
        } else if (element != null) {
            if (element.getSelectorType() == Selector.Type.NODE) {
                Object attribute = element.getAttribute("delivery");
                if (attribute != null) {
                    curElement = element;
                    curElement.addAttribute("ui.style", "text-mode: normal;");
                }
            }
        }
    }

}
