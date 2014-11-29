/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package centralizedgroups;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class GroupMessage {
    
    ArrayList<Byte> mensaje;
    GroupMember emisor;
    
    public GroupMessage(GroupMember emisor, ArrayList<Byte> mensaje){
        
        this.emisor = new GroupMember(emisor.alias, emisor.hostname, emisor.idMember, emisor.idGroup, emisor.puerto);
        this.mensaje = mensaje;
        
    }
           
}
