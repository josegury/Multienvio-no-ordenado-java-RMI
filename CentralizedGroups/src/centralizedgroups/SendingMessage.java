/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package centralizedgroups;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class SendingMessage extends Thread{
    int id;
    GroupMessage mensaje;
    ClientInterface cliente;
    ObjectGroup grupo;
   // LinkedList groupList;
   // private ClientInterface stub;
    
    SendingMessage(GroupMessage mensaje,  
            ObjectGroup grupo) throws RemoteException{
        

        this.mensaje = new GroupMessage(mensaje.emisor, mensaje.mensaje);
        this.grupo = grupo;
      
    }
    
    @Override
    public void run(){
        //grupo.Sending();
        Registry registry = null;
        //registry = LocateRegistry.getRegistry(1099);

        
                     try {        
            registry = LocateRegistry.getRegistry(1099);
        } catch (RemoteException ex) {
            Logger.getLogger(SendingMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
                     
            //A continuación envio el mensaje a los miembros del grupo, menos al emisor
            for (int i = 0; i < grupo.members.size(); i++) {
           
                //Compruebo que no sea el emisor
            if(grupo.members.get(i) != mensaje.emisor){                  
                
            try {
                    //
                    cliente = (ClientInterface) registry.lookup( grupo.members.get(i).alias);
                    //cliente = (ClientInterface) registry.lookup( grupo.members.get(i).alias);
                try {
    
                         Thread.sleep((int)(Math.random()*30+30)*1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SendingMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
                     cliente.DepositMessage(mensaje);
                     }catch ( RemoteException | NotBoundException  ex) {
                    Logger.getLogger(SendingMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
                } 
                
            }
           
        
       
        grupo.EndSending(mensaje.emisor);
    }
    
}
