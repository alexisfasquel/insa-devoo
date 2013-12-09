
package Tools.Reader;

import Model.Area;
import Model.DeliveryPoint;
import Model.Itinary;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 *
 * @author Aleks
 */
public class DeliveryLoader {
    
    private final DeliveryHandler mDeliveryHandler = new DeliveryHandler();
    private final File mFile;
    
    private Area mArea;
    
    public DeliveryLoader(String filePath, Area area) {
        mFile = new File(filePath);
        mArea = area;
    }
    
    public void process() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory fabrique = SAXParserFactory.newInstance();
        SAXParser parseur = fabrique.newSAXParser();
        parseur.parse(mFile, mDeliveryHandler);  
        
        mArea.setTour(mDeliveryHandler.wareHouseId, mDeliveryHandler.mTour);
    }
    
    
    public static class DeliveryHandler extends DefaultHandler{
        
        
        
        private String wareHouseId = null;
        private Itinary mItinary = null;
        private List<Itinary> mTour;
        
        private int index = 0;
        
        public DeliveryHandler(){
            super();
            mTour = new ArrayList<>();
        }
   
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            
            if (qName.equals("Entrepot")) {
                wareHouseId = attributes.getValue("adresse");
                
            } else if(qName.equals("Plage")) {
                Date start;
                Date end;

                //Exemple on how to use Date
                //Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                //calendar.setTime(date);   // assigns calendar to given date 
                //int hour = calendar.get(Calendar.HOUR);...
                
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    start = sdf.parse(attributes.getValue("heureDebut"));
                    end = sdf.parse(attributes.getValue("heureFin"));
                    mItinary = new Itinary(start, end, index);
                    index++;
                    
                } catch(ParseException e) {
                    //TODO Handle Exception
                    Logger.getGlobal().log(Level.SEVERE, e.getMessage());
                }
                
            } else if(qName.equals("Livraison")) {
                    String deliveryId = attributes.getValue("id");
                    String cliendId = attributes.getValue("client");
                    String deliveryAdress = attributes.getValue("adresse");
                    
                    mItinary.addDeliveryPoint(new DeliveryPoint(deliveryId, cliendId, deliveryAdress));
            }
        }
            
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            
            if (qName.equals("Plage")) {
                mTour.add(mItinary);
                mItinary = null;
            }
        }       
    }
}

