/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import java.awt.event.MouseEvent;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Selector;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;

/**
 *
 * @author Aleks
 */
public class MapMouseManager extends DefaultMouseManager{

    public MapMouseManager() {
        super();
    }

    @Override
    public void mouseDragged(MouseEvent event) {}

    @Override
    protected void mouseButtonPress(MouseEvent event) {}

    @Override
    public void mouseClicked(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {}

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        
        GraphicElement element = view.findNodeOrSpriteAt(e.getX(), e.getY());
        
        if(curElement != null) {
            if(element == null) {
                curElement.addAttribute("ui.style", "text-mode: hidden;");
                curElement = null;
                return;
            } else if (curElement.equals(element)) {
                return;
            }
            curElement.addAttribute("ui.style", "text-mode: hidden;");
            curElement = null;
        } else if (element != null) {
            if(element.getSelectorType() == Selector.Type.NODE) {
                Object attribute = element.getAttribute("delivery");
                if(attribute != null) {
                    curElement = element;
                    curElement.addAttribute("ui.style", "text-mode: normal;");
                }
            }
        }
    }
    
}
