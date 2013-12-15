/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Controller.Controller;
import java.awt.event.MouseEvent;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;

/**
 *
 * @author Aleks
 */
public class MapMouseManager extends DefaultMouseManager{
    private Controller mcontroler;

    public MapMouseManager( Controller controler) {
        super();
        mcontroler = controler;
    }

    @Override
    protected void elementMoving(GraphicElement element, MouseEvent event) {
    
    }

    @Override
    protected void mouseButtonPressOnElement(GraphicElement element, MouseEvent event) {
        super.mouseButtonPressOnElement(element, event); //To change body of generated methods, choose Tools | Templates.
        mcontroler.addDelivery();
    }
    
    
}
