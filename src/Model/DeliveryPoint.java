/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import org.graphstream.graph.Path;

/**
 *
 * @author Aleks
 */
public class DeliveryPoint {
   private String mId;
   private String mClientId;
   private String mAdress;
   
   private Path mDirection;
   
   public DeliveryPoint(String id, String clientId, String adress){
       super();
       mId = id;
       mClientId = clientId;
       mAdress = adress;
   }
   
   public String getId(){
       return mId;
   }

    public String getNclient() {
        return mClientId;
    }

    public String getAdress() {
        return mAdress;
    }

    public Path getDirection() {
        return mDirection;
    }

    public void setDirection(Path direction) {
        mDirection = direction;
    }
    
    
   
    
    
}
