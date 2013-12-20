
package View;

import Controller.Controller;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Selector;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;

/**
 *
 * @Description of the class
 */
public class MapMouseManager extends DefaultMouseManager {

    private Controller mController;
    private JButton mButDeleteDel;
    private JPanel mListPanel;
    private Welcome w;

    private Node mSelected;
    
    /**
     *
     * @param controller
     * @param ButDeleteDel
     * @param ListPanel
     */
    public MapMouseManager(Controller controller, JButton ButDeleteDel, JPanel ListPanel, Welcome w) {
        super();
        this.w = w;
        mController = controller;
        mButDeleteDel = ButDeleteDel;
        mListPanel = ListPanel;
    }

    /**
     *
     * @param event
     */
    @Override
    public void mouseDragged(MouseEvent event) {
    }

    /**
     *
     * @param event
     */
    @Override
    protected void mouseButtonPress(MouseEvent event) {}

    /**
     * @return th node selected of <code>this</code>
     */
    public Node getSelected() {
        return mSelected;
    }

    /**
     * 
     */
    public void deselect() {
        mSelected.removeAttribute("ui.selected");
        mSelected = null;
    }

    /**
     *
     * @param event
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        if(!w.isLoaded()) {
            return;
        }
        GraphicElement element = view.findNodeOrSpriteAt(event.getX(), event.getY());
        if(mSelected != null) {
            mSelected.removeAttribute("ui.selected");
        }
        if (element != null) {
            if(element.equals(mSelected)) {
                mSelected.removeAttribute("ui.selected");
                mSelected = null;
                mButDeleteDel.setEnabled(false);
                mListPanel.setVisible(false);
            } else {
                if (element.getSelectorType() == Selector.Type.NODE) {
                
                    mSelected = (Node)element;
                    mSelected.addAttribute("ui.selected");
                    //mButDeleteDel.setEnabled(false);
                }
                if (element.getAttribute("delivery") == null) {
                    mListPanel.setVisible(true);
                    mButDeleteDel.setEnabled(false);
                } else {
                    //w.select(mSelected.getId());
                    mButDeleteDel.setEnabled(true);
                    mListPanel.setVisible(false);
                }
            }
        } else {
            mButDeleteDel.setEnabled(false);
            mListPanel.setVisible(false);
        }
        
    }

    /**
     *
     * @param event
     */
    @Override
    public void mouseReleased(MouseEvent event) {
    }

    /**
     *
     * @param event
     */
    @Override
    public void mousePressed(MouseEvent event) {
    }

    /**
     *
     * @param e
     */
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
