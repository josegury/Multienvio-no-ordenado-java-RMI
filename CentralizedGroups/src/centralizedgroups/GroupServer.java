/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizedgroups;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class GroupServer extends UnicastRemoteObject implements GroupServerInterface{
    LinkedList<ObjectGroup> GroupList;
    int idGrupos;
    
    public GroupServer() throws RemoteException{
        super();
        GroupList=new LinkedList();
        idGrupos=-1;
    
    }
    
    
    public static void main(String[] args) throws MalformedURLException {
        
        try {
            GroupServer Server;
            
            System.setProperty("java.security.policy", "C:\\Users\\Valen\\NetbeansProjects\\seguridad.policy");
            if(System.getSecurityManager()==null)
            System.setSecurityManager(new SecurityManager());
            int numPuerto=1099;
                LocateRegistry.createRegistry(numPuerto);
                Server=new GroupServer();
            try {
                Naming.rebind("//localhost"+":"+numPuerto+"/HolaServer", Server);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\t--------------------SERVIDOR LANZADO--------------------");
        
        
        
        
    }
    
    @Override
    public int numgrup() {
        return this.GroupList.size();
    }
    
    @Override
     public String MemberList(int grupid) {
         String solve="";
         
             for(int j=0;j<this.GroupList.get(grupid).members.size();j++){
             solve+="Alias: "+this.GroupList.get(grupid).members.get(j).alias+" Hostname: "+this.GroupList.get(grupid).members.get(j).hostname+"\n";
             }
         
         
        return solve;
    }

    @Override
    public int createGroup(String galias, String oalias, String ohostname, int puerto) {
        if(findGroup(galias)==-1){
            idGrupos=idGrupos+1;
            GroupList.add(new ObjectGroup(galias, idGrupos, oalias, ohostname, puerto));
            System.out.println("Grupo creado, ahora hay "+GroupList.size());
            return idGrupos;
        }
        else{
            return -1;
        }
    }

    @Override
    public int findGroup(String galias) {
        for(int i=0; i<this.GroupList.size();i++){
            if(GroupList.get(i).groupName.equals(galias)){
                return GroupList.get(i).idGroup;
            }
        }
        return -1;
    }

    @Override
    public boolean removeGroup(String galias, String oalias) {
        for(int i=0; i<this.GroupList.size();i++){
            if(GroupList.get(i).groupName.equals(galias) && GroupList.get(i).propietario.alias.equals(oalias)){
                this.GroupList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public GroupMember addMember(int gid, String alias, String hostname, int puerto) {
        for(int i=0; i<this.GroupList.size();i++){
            if(GroupList.get(i).idGroup==gid){
                if(GroupList.get(i).isMember(alias)==null){
                    try {
                        return GroupList.get(i).addMember(alias, hostname, puerto);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    return null;
                }
            }
        }
        return null;
    }
    
    @Override
    public boolean removeMember(int gid, String alias) {
        for(int i=0; i<this.GroupList.size();i++){
            if(GroupList.get(i).idGroup==gid){
                if(GroupList.get(i).isMember(alias)!=null){
                    try {
                        GroupList.get(i).removeMember(alias);
                        return true;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public GroupMember isMember(int gid, String alias) {
         for(int i=0; i<this.GroupList.size();i++){
              if(GroupList.get(i).idGroup==gid){
                  return GroupList.get(i).isMember(alias);
              }
         }
         return null;
    }
    
/*
    @Override
    public boolean StopMembers(int gid) {
        for(int i=0; i<this.GroupList.size();i++){
              if(GroupList.get(i).idGroup==gid){
                  GroupList.get(i).StopMembers();
                  return true;
              }
         }
        return false;
    }

    @Override
    public boolean AllowMembers(int gid) {
        for(int i=0; i<this.GroupList.size();i++){
              if(GroupList.get(i).idGroup==gid){
                  GroupList.get(i).AllowMembers();
                  return true;
              }
         }
        return false;
    }
    */

    @Override
    public boolean sendGroupMessage(GroupMember gm, ArrayList<Byte> msg) throws RemoteException {
        GroupMessage mensaje;
        for (int i = 0; i < GroupList.size(); i++) {
            if(GroupList.get(i).isMember(gm.alias) != null){
                
            mensaje = new GroupMessage(gm,msg);
            GroupList.get(i).Sending();
            SendingMessage hilo = new SendingMessage(mensaje, GroupList.get(i));
            hilo.start();
            
            //Se termina de enviar los mensajes de grupo
            return true;
            }
        }
        return false;
    }
    
    
}
