/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * @author Aleks
 */


public class MapReader extends DefaultHandler{
    
    private PlanHandler mPlanHandler = new PlanHandler();
    private File mFile;
    
    public MapReader(String filePath) {
        mFile = new File(filePath);
    }
    
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

    
    public List<Node> getNodes() {
        return mPlanHandler.mNodes;
    }

    public List<Edge> getEdges() {
        return mPlanHandler.mEdges;
    }
    
     public static class PlanHandler extends DefaultHandler{

        private List<Node> mNodes = new ArrayList<Node>();
        private List<Edge> mEdges = new ArrayList<Edge>();
        private Node mNode;


        public PlanHandler() {
            super();
        }

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

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("Noeud")) {
               mNode = null;  
            }
        }
    }
    
    public static class Node {
            private final int mId, mX, mY;

            public Node(int id, int x, int y) {
                mId = id;
                mX = x;
                mY = y;
            }

            public int getId() {
                return mId;
            }

            public int getX() {
                return mX;
            }

            public int getY() {
                return mY;
            }

        }
        public static class Edge {
            private final int mNodeIdStart, mNodeIdFinish;
            private final double mSpeed, mLength;
            private final String mName;

            public Edge(int nodeIdStart, int nodeIdFinish, String name, double speed, double lenght) {
                mNodeIdStart = nodeIdStart;
                mNodeIdFinish = nodeIdFinish;
                mName = name;
                mSpeed = speed;
                mLength = lenght;
            }

            public String getName() {
                return mName;
            }

            public double getweight() {
                return mLength/mSpeed;
            }
            
            public int getNodeIdL() {
                return mNodeIdStart;
            }

            public int getNodeIdR() {
                return mNodeIdFinish;
            }        
        }
}
