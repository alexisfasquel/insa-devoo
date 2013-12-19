
package Tools.Reader;
import Model.LoadingException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * Description of the class
 */


public class MapReader extends DefaultHandler{
    
    private PlanHandler mPlanHandler = new PlanHandler();
    private File mFile;
    
    /**
     *
     * @param filePath
     */
    public MapReader(String filePath) {
        mFile = new File(filePath);
    }
    
    /**
     *
     * @throws LoadingException
     */
    public void process() throws LoadingException {
        try {
            SAXParserFactory fabrique = SAXParserFactory.newInstance();
            SAXParser parseur = fabrique.newSAXParser();
            parseur.parse(mFile, mPlanHandler);
        } catch (SAXException ex) {
            throw new LoadingException("Error while parsing the file :" + System.getProperty("line.separator") + ex.getMessage());
        } catch (IOException ex) {
            throw new LoadingException("Error with the file:" + System.getProperty("line.separator") + ex.getMessage());
        } catch (ParserConfigurationException ex) {
            throw new LoadingException("Error with the xml parser :" + System.getProperty("line.separator") + ex.getMessage());
        }
              
    }

    /**
     * @return a list of nodes  of <code>this</code>
     */
    public List<Node> getNodes() {
        return mPlanHandler.mNodes;
    }

    /**
     * @return a list of edges  of <code>this</code>
     */
    public List<Edge> getEdges() {
        return mPlanHandler.mEdges;
    }

    /**
     *
     */
    public static class PlanHandler extends DefaultHandler{

        private List<Node> mNodes = new ArrayList<Node>();
        private List<Edge> mEdges = new ArrayList<Edge>();
        private Node mNode;

        /**
         *
         */
        public PlanHandler() {
            super();
        }

        /**
         *
         * @param uri
         * @param localName
         * @param qName
         * @param attributes
         * @throws SAXException
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
           if (qName.equals("Noeud")) {
               int id = Integer.parseInt(attributes.getValue("id"));
               int x = Integer.parseInt(attributes.getValue("x"));
               int y = Integer.parseInt(attributes.getValue("y"));
               
               mNode = new Node(id, x, y); 
               mNodes.add(id, mNode);

           } else if(qName.equals("TronconSortant")) {
    
                String name = attributes.getValue("nomRue");
                double speed = Float.parseFloat(attributes.getValue("vitesse").replace(",", "."));
                double lenght = Float.parseFloat(attributes.getValue("longueur").replace(",", "."));
                int destination = Integer.parseInt(attributes.getValue("destination"));

                Edge edge = new Edge(mNode.getId(), destination, name, speed, lenght);
                mEdges.add(edge);  
            
           } else if(!qName.equals("Reseau")){
               throw new SAXException("File not containings the correct format for a map");
           }
        }

        /**
         *
         * @param uri
         * @param localName
         * @param qName
         * @throws SAXException
         */
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("Noeud")) {
               mNode = null;  
            }
        }
    }
    
    /**
     *
     */
    public static class Node {
            private final int mId, mX, mY;

        /**
         * Create a Node with its geographical coordinates and its identification
         * @param id  the number of the identification of a specif node
         * @param x   geographical coordinate
         * @param y   geographical coordinate
         */
        public Node(int id, int x, int y) {
                mId = id;
                mX = x;
                mY = y;
            }

        /**
         * @return the id of <code>this</code>
         */
        public int getId() {
                return mId;
            }

        /**
         * @return the geographical coordinate X of  <code>this</code>
         */
        public int getX() {
                return mX;
            }

        /**
         * @return the geographical coordinate Y of  <code>this</code>
         */
        public int getY() {
                return mY;
            }

        }

    /**
     *
     */
    public static class Edge {
            private final int mNodeIdStart, mNodeIdFinish;
            private final double mSpeed, mLength;
            private final String mName;

        /**
         *
         * @param nodeIdStart
         * @param nodeIdFinish
         * @param name
         * @param speed
         * @param lenght
         */
        public Edge(int nodeIdStart, int nodeIdFinish, String name, double speed, double lenght) {
                mNodeIdStart = nodeIdStart;
                mNodeIdFinish = nodeIdFinish;
                mName = name;
                mSpeed = speed;
                mLength = lenght;
            }

        /**
         * @return the name of <code>this</code>
         */
        public String getName() {
                return mName;
            }

        /**
         * @return the weight of <code>this</code>
         */
        public double getweight() {
                return mLength/mSpeed;
            }

        /**
         * @return the id of the start of <code>this</code>
         */
        public int getNodeIdL() {
                return mNodeIdStart;
            }

        /**
         * @return the id of the start of <code>this</code>
         */
        public int getNodeIdR() {
                return mNodeIdFinish;
            }        
        }
}
