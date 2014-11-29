/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package centralizedgroups;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Client extends UnicastRemoteObject implements ClientInterface{

    public Client() throws RemoteException{
        super();
    }
    
    final ReentrantLock lock = new ReentrantLock(true);
    final Condition condicion = lock.newCondition();
    LinkedList<GroupMessage> cola = new LinkedList<>();
    static GroupServerInterface stub;
    
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException{
        
        Scanner sc = new Scanner(System.in);
        ClientInterface client;
       
         String alias;
         String hostname;
         String aliasgrupo;
         String aliascliente;
         int num1,num2;
         
        System.setProperty("java.security.policy", "C:/Users/usuario/Desktop/nbworkspace/CentralizedGroups/server.policy");
        
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        
        client=new Client();
         System.out.printf("Inserte alias de cliente: ");
          aliascliente=sc.nextLine();  
         Naming.rebind("//localhost/" + aliascliente, (Remote) client);
            
        Registry registry = LocateRegistry.getRegistry(1099);
        
            stub = (GroupServerInterface) Naming.lookup("rmi://localhost:1099/HolaServer");
       // Naming.rebind("//localhost"+":"+1099+aliascliente, client);
         
         do{
         System.out.println("Elija opción:");
         System.out.println("1. Crear grupo");
         System.out.println("2. Eliminar grupo");
         System.out.println("3. Añadir miembro al grupo");
         System.out.println("4. Eliminar miembro del grupo");
         System.out.println("5. Bloquear altas/bajas");
         System.out.println("6. Desbloquear altas/bajas");
         System.out.println("7. Número de grupos en servidor");
         System.out.println("8. Miembros en grupo a indicar");
         System.out.println("9. Enviar mensaje");
         System.out.println("10. Recibir mensaje");
         System.out.println("11. Terminar ejecución");
         sc = new Scanner(System.in);
         num1=sc.nextInt();
          switch (num1) {
            case 1:  
                System.out.println("\t--------------------CREAR GRUPO--------------------");
                System.out.printf("Inserte alias de grupo: ");
                sc = new Scanner(System.in);
                aliasgrupo=sc.next();                              
                        System.out.printf("Inserte hostname de propietario: ");
                        sc = new Scanner(System.in);
                        hostname=sc.next();
                        System.out.println("Inserte un número de puerto");
                        sc = new Scanner(System.in);
                        int entero = sc.nextInt();
                    System.out.println(stub.createGroup(aliasgrupo, aliascliente, hostname, entero));
                    System.out.println("Grupo creado");
                    System.out.println("\t---------------------------------------------------");
                     break;
            case 2: System.out.println("\t--------------------ELIMINACION DE GRUPO--------------------");
                System.out.print("\nInserte alias de grupo: ");
                sc = new Scanner(System.in);
                        aliasgrupo=sc.next();
                        System.out.print("\nInserte alias de propietario: ");
                        sc = new Scanner(System.in);
                        alias=sc.next(); 
                    if(!(stub.removeGroup(aliasgrupo, alias))){
                        System.out.println("No se pudo borrar el grupo seleccionado");
                    };
                    System.out.println("\t---------------------------------------------------");
                     break;
            case 3: System.out.println("\t--------------------NUEVO MIEMBRO--------------------");
                System.out.print("\nInserte alias de miembro nuevo: ");
                sc = new Scanner(System.in);
                        alias=sc.next();
                        System.out.print("\nInserte hostname: ");
                        sc = new Scanner(System.in);
                        hostname=sc.next(); 
                        System.out.print("\nInserte id de grupo: ");
                        sc = new Scanner(System.in);
                        num2=sc.nextInt();
                        System.out.println("Inserte un número de puerto");
                        sc = new Scanner(System.in);
                        int entero2 = sc.nextInt();
                    if(stub.addMember(num2, alias, hostname, entero2) ==null){
                        System.out.println("No se pudo añadir miembro");
                    }
                    else{
                         System.out.println("Miembro insertado");
                    }
                    System.out.println("\t---------------------------------------------------");
                     break;
            case 4: System.out.println("\t--------------------BORRAR MIEMBRO--------------------");
                System.out.print("\nInserte alias de miembro nuevo: ");
                sc = new Scanner(System.in);
                        alias=sc.next();
//                        System.out.print("\nInserte hostname: ");
//                        sc = new Scanner(System.in);
//                        hostname=sc.next(); 
                        System.out.print("\nInserte id de grupo: ");
                        sc = new Scanner(System.in);
                        num2=sc.nextInt();
                    if(!(stub.removeMember(num2, alias))){
                        System.out.println("No se pudo eliminar miembro");
                    }
                    else{
                        System.out.println("Miembro eliminado");
                    };
                    System.out.println("\t---------------------------------------------------");
                     break;
            case 5:  /*System.out.println("\t--------------------BLOQUEO DE ALTAS/BAJAS--------------------");
                System.out.print("\nInserte id de grupo: ");
                b=sc.nextInt();
                    if(stub.StopMembers(b)){
                        System.out.println("Altas/bajas bloqueadas en "+b);
                    }
                    else{
                        System.out.println("No se pudieron bloquear altas/bajas en "+b);
                    }*/
                System.out.println("Opcion deshabilitada");
                    System.out.println("\t---------------------------------------------------");
                     break;
            case 6: /* System.out.println("\t--------------------DESBLOQUEO DE ALTAS/BAJAS--------------------");
                System.out.print("\nInserte id de grupo: ");
                b=sc.nextInt();
                    if(stub.AllowMembers(b)){
                        System.out.println("Altas/bajas bloqueadas en "+b);
                    }
                    else{
                        System.out.println("No se pudieron bloquear altas/bajas en "+b);
                    }*/
                System.out.println("Opcion deshabilitada");
                    System.out.println("\t---------------------------------------------------");
                     break;
                
                case 7: System.out.println("\t------------------NUMERO DE GRUPOS EN SERVER------------------");
                    System.out.println(stub.numgrup());
                    System.out.println("\t---------------------------------------------------");
                     break;
                    
            case 8: System.out.println("\t--------------------MIEMBROS DEL GRUPO--------------------");
                System.out.print("\nInserte id de grupo: ");
                sc = new Scanner(System.in);
                num2=sc.nextInt(); 
                System.out.println(stub.MemberList(num2));
                System.out.println("\t---------------------------------------------------");
                     break;
                case 9: System.out.println("\t--------------------ENVIO MENSAJE--------------------");
                
                String aux;
                int id;
                System.out.print("\nInserte id de grupo a enviar el mensaje: ");
                sc = new Scanner(System.in);
                id = sc.nextInt();
                System.out.print("\nInserte un mensaje: ");
                sc = new Scanner(System.in);
                aux = sc.nextLine();
                ArrayList<Byte> cadena = new ArrayList();
                     for (int i = 0; i < aux.length(); i++) {
                         cadena.add((byte) aux.charAt(i));
                     }
                    
                if(stub.isMember(id, aliascliente) != null){
                    // System.out.println(cadena);
                stub.sendGroupMessage(stub.isMember(id, aliascliente),cadena);}
                
                System.out.println("\t---------------------------------------------------");
                     break;
                case 10: System.out.println("\t--------------------RECIBO MENSAJE--------------------");
                
                String aux2 = "";
                System.out.print("\nInserte alias de grupo, para recibir mensaje de este: ");
                sc = new Scanner(System.in);
                aux2 = sc.nextLine();
                
                ArrayList<Byte> cadena2 = client.receiveGroupMessage(aux2);
                  if(cadena2 == null){
                  System.out.println("Grupo " + aux2 + " no existe...");
                  }else{
                      String aux3 = "";
                    //  aux3 +=  IOUtils.toString(cadena2, "UTF-8");
                     for (int i = 0; i < cadena2.size(); i++) {
                       // aux3 +=  IOUtils.toString(cadena2.get(i), "UTF-8");
                        int n = cadena2.get(i);
                       //  aux3 +=  Integer.toString(n);
                         aux3 += String.valueOf((char)n);
                     }
                System.out.println("El mensaje obtenido es: \n" + aux3);
                  }
                
                System.out.println("\t---------------------------------------------------");
                    break;
            case 11:  System.out.println("\t---------------------------------------------------");
                unexportObject((Remote) client, true);
                     break;

    }
         }while(num1!=11);
    }

    @Override
    public void DepositMessage(GroupMessage m) throws RemoteException {
        lock.lock();
        try {
            cola.push(m);
            
            //Desbloqueo condicion, porque ha llegado un mensaje
            condicion.signalAll();
            //condicion.
            
        } finally {
            lock.unlock();
        }
    }

    @Override
    public synchronized ArrayList<Byte> receiveGroupMessage(String galias) throws RemoteException {
        
        lock.lock();
        int id = -1;
         try {

      
        id = stub.findGroup(galias);
        //System.out.println("Llego aqui "+ id);
       if( id == -1){
       return null;
       }
       while(true){

       
       
        for (int i = 0; i < cola.size(); i++) {
            if(cola.get(i).emisor.idGroup == id){
                ArrayList<Byte> array =(ArrayList<Byte>) cola.get(i).mensaje.clone();
              //  cola.get(i).mensaje.get();
                cola.remove(i);
                return array;
            }
        }
                    //Bloqueo porque no tiene ningún mensaje de ese grupo
           try{
                condicion.await();
               
            //return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

    }//Fin while
        } finally {
            lock.unlock();
        }
}//Fin metodo
        
}

