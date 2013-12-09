/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;


/**
 *
 * @author Aleks
 */
public class DeliveryPoint {
   private final String mClientId;
   private final Itinary mItinary;
   
   public DeliveryPoint(String clientId, Itinary itinary){
       super();
       mClientId = clientId;
       mItinary = itinary;
   }
   
    public final String getNclient() {
        return mClientId;
    }
    
    public final Itinary getItinary() {
        return mItinary;
    }

   
    
    
   
    
    
}
