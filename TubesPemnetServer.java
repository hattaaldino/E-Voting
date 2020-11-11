
package tubes.pemnetserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class TubesPemnetServer {

    public static boolean isAdmin(int portAdmin, int port){
        return portAdmin==port;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO code application logic here
        ArrayList<Kandidat> ListKandidat= new ArrayList<Kandidat>();
        String nama[];
        
        Scanner sc = new Scanner(System.in);
        int portAdmin = -1;
        int port ;
        
        ServerSocket welcomeSocket = new ServerSocket(50100);
        
        while(true){
        
            Socket connectionSocket = welcomeSocket.accept();//listen
            port = connectionSocket.getPort(); //cek port client
            ObjectInputStream InputNama = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream OutputKandidat = new ObjectOutputStream(connectionSocket.getOutputStream());
            
            if (portAdmin==-1){
                portAdmin = port;
            }
            //kasus admin
            if (isAdmin(portAdmin,port)){
                nama = (String[])InputNama.readObject();
                for (int i=0; i<nama.length; i++){
                    Kandidat Calon = new Kandidat(i+1,nama[i]);
                    ListKandidat.add(Calon);
                }
//                for(int i=0; i<ListKandidat.size(); i++){
//                    System.out.println(ListKandidat.get(i).getNama());
//                }
            }
            //kasus voter
            else{
                
            }
            
            
            
            
            
            
            
            
            
            
            //do{
            BufferedReader inFromClient = new BufferedReader (new InputStreamReader(connectionSocket.getInputStream()));
       
            DataOutputStream outToClient = new DataOutputStream (connectionSocket.getOutputStream());
        
            }
    }
    
}
