
package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import Kandidat.Kandidat;
import Response.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TubesPemnetServer {
    static String[] namaKandidat;
    static ArrayList<Kandidat> listKandidat= new ArrayList<>(); //Kumpulan objek kandidat
        
    static ArrayList<Integer> voterHasVoted = new ArrayList<>(); //Kumpulan port milik voter yang sudah memilih
    static int voterCount = 1;//untuk memberi id kepada voter berdasarkan urutan mereka terkoneksi ke server
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
       

        ServerSocket welcomeSocket = new ServerSocket(50100);
        
        while(true){
            Socket connectionSocket = welcomeSocket.accept();//listen
            
            final ObjectInputStream fromClient = new ObjectInputStream(connectionSocket.getInputStream());
            final ObjectOutputStream toClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            
            ClientIdentity currClient = (ClientIdentity) fromClient.readObject();
            
            //kasus admin
            if(currClient.getRole().equals("admin")){       
                namaKandidat = (String[]) fromClient.readObject();
                
                for (int i=0; i<namaKandidat.length; i++){
                    Kandidat Calon = new Kandidat(i+1,namaKandidat[i]);
                    listKandidat.add(Calon);
                }
            }
            
            //kasus voter
            else if(currClient.getRole().equals("voter")){
                //dibungkus thread
                new Thread(() -> {
                    try {
                        int currId;
                        
                        if(currClient.getId() == -1){
                            //mengirim id ke client voter apabila voter belum memiliki id
                            currId = generateID();
                            toClient.writeInt(currId);
                        }
                        else
                            currId = currClient.getId();
                        
                        //cek apakah voter ini termasuk voter yang sudah memilih atau belum
                        if(hasVoted(currId)){
                            //send 0 as client can't vote
                            toClient.writeInt(0);
                        }
                        else{
                            //send 1 as client can vote
                            toClient.writeInt(1);
                            
                            //Mengirimkan seluruh data kandidat
                            int listKanSize = listKandidat.size();
                            toClient.writeObject(listKandidat.toArray(new Kandidat[listKanSize]));
                            
                            //Menambahkan jumlah suara dari chosenKandidat(kandidat yang divote)
                            int chosenKandidat = fromClient.readInt();
                            upvoteKandidat(chosenKandidat);
                            
                            //Mengirimkan seluruh data kandidat yang sudah diperbarui
                            toClient.writeObject(listKandidat.toArray(new Kandidat[listKanSize]));
                            
                            //menandai voter sudah memilih
                            voterHasVote(currId);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(TubesPemnetServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
        }
    }
    
    //membuat id untuk voter
    static int generateID(){
        return voterCount++;
    }
    
    //cek apakah voter dengan id ini sudah melakukan vote
    static boolean hasVoted(int id){
        return voterHasVoted.contains(id);
    }
    
    //menambahkan satu suara dari kandidat yang memiliki id berikut
    static void upvoteKandidat(int id){
        for(int i = 0; i < listKandidat.size(); i++){
            if(listKandidat.get(i).equals(id)){
                listKandidat.get(i).upvote();
            }
        }
    }
    
    //menandai voter sudah memilih
    static void voterHasVote(int id){
        voterHasVoted.add(id);
    }
}
