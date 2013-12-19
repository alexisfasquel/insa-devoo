
package Tools.Reader;

import Model.Area;
import Model.LoadingException;
import Model.Itinary;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    private final DeliveryHandler mDeliveryHandler;
    private final File mFile;
    
    
    public DeliveryLoader(String filePath, Area area) {
        mFile = new File(filePath);
        mDeliveryHandler = new DeliveryHandler(area);
    }
    
    public void process() throws LoadingException {
        try {
            SAXParserFactory fabrique = SAXParserFactory.newInstance();
            SAXParser parseur = fabrique.newSAXParser();  
            parseur.parse(mFile, mDeliveryHandler);
            
        } catch (ParserConfigurationException ex) {
            throw new LoadingException("Error with the xml parser :" + System.getProperty("line.separator") + ex.getMessage());
        } catch (SAXException ex) {
            throw new LoadingException("Error while parsing the file :" + System.getProperty("line.separator") + ex.getMessage());
        } catch (IOException ex) {
            throw new LoadingException("Error with the file:" + System.getProperty("line.separator") + ex.getMessage());
        }
    }
    
    
    public static class DeliveryHandler extends DefaultHandler{
        
        private Area mArea;
        
        private Itinary mItinary;
        
        public DeliveryHandler(Area area){
            super();
            mArea = area;
        }
   
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            
            if (qName.equals("Entrepot")) {
                mArea.setWareHouse(attributes.getValue("adresse"));
            } else if(qName.equals("Plage")) {
                try {
                    Date start;
                    Date end;
                    
                    //Exemple on how to use Date
                    //Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                    //calendar.setTime(date);   // assigns calendar to given date
                    //int hour = calendar.get(Calendar.HOUR);...
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    start = sdf.parse(attributes.getValue("heureDebut"));
                    end = sdf.parse(attributes.getValue("heureFin"));
                    mItinary = mArea.addItinary(start, end);

                } catch (ParseException ex) {
                    throw new SAXException("Issue while parsing dates from itinary :" + ex.getMessage() );
                }
                    
                
            } else if(qName.equals("Livraison")) {
                    String cliendId = attributes.getValue("client");
                    String deliveryAdress = attributes.getValue("adresse");
             
                    mArea.addDelivery(mItinary, cliendId, deliveryAdress);
            }
        }
    }
    
    /*private static class Delivery {
        private final String mAdress;
        private final String mIdClient;

        public Delivery(String adress, String idClient) {
            mAdress = adress;
            mIdClient = idClient;
        }
        
        public String getAdress() {
            return mAdress;
        }

        public String getIdClient() {
            return mIdClient;
        }
        
        
    }*/
}

