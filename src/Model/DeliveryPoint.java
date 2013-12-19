

package Model;

/**
 * Area where delivery can be made by a delivery person
 */
public class DeliveryPoint {
    
    /*
    * Id of the client to deliver
    */
   private final String mClientId;
   /*
    * the itinary to follow by the delivery personn
    */
   private final Itinary mItinary;

    /**
     * Create a delivery point with the client id and the itinary.
     * @param clientId the number which is the identification of a specific client
     * @param itinary  the list of the deliveries with a start and an end
     */
    public DeliveryPoint(String clientId, Itinary itinary){
       super();
       mClientId = clientId;
       mItinary = itinary;
   }
   
    /**
     * @return the id of a client of <code>this</code>
     */
    public final String getNclient() {
        return mClientId;
    }
    
    /**
     * @return the itinary of <code>this</code>
     */
    public final Itinary getItinary() {
        return mItinary;
    }

   
    
    
   
    
    
}
