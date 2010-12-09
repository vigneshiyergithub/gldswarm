/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gld.algo.tlc;
import java.io.*;
import java.util.Vector;
import java.net.SocketException;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
/**
 *
 * @author Jonathan
 * Esta clase implementa el sistema servidor a nivel de enlace.
 */
public class Comm implements Runnable{
    final int HEADER = 'G';
    final int COMMAND = 10;
    
    final int RQST_DATA = 'R';
    final int SEND_DATA = 'S';
    
    private Socket socket;
    private ServerSocket server;
    private Byte [] buff_tx;
    private Float [] buff_rx;

    private Vector tx_array;
    private Vector rx_array;
    private int port;
    private boolean isConnected = false;
    private InputStream is;
    private OutputStream os;

    private DataInputStream dis;
    private DataOutputStream dos;

    /////////////////////////////////
    private int state = 1;
    byte aux = 0;
    int numData = 0;
    /////////////////////////////////
    public void run(){
        while(true){
            if(!this.isConnected)
                this.connect();

            processMessage();

            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public Comm(String address,int port, Byte [] buff_tx, Float [] buff_rx){
        try{
            server = new ServerSocket(port);
        }catch(IOException e){
            e.printStackTrace();
        }
        this.port = port;
        this.buff_rx = buff_rx;
        this.buff_tx = buff_tx;

        tx_array = new Vector();
        rx_array = new Vector();
    }

    public void connect(){
        try{
            socket = server.accept();

            if(socket != null)
                isConnected = true;


            System.out.printf("Aceptó conexión\n");
            is = socket.getInputStream();
            os = socket.getOutputStream();

            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);

            
        }catch(SocketTimeoutException e){
            System.out.printf("El servidor no ha aceptado conexión: TIMEOUT\n");
        }catch(IOException e){
            e.printStackTrace();
        }}

    public void closePort(){
        try{
            is.close();
            os.close();
            socket.close();
            server.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        isConnected = false;
    }

    public void processMessage(){
        float dato;
        byte cab, command;

        try{
            if(dis.available() > 0){
                //System.out.printf("Dato disponible, estado = %d\n", state);
                //README : Aquí se procesan los datos que llegan al socket
                switch(state){

                    case 1:
                        cab = dis.readByte();

                        //System.out.printf("Cabecera = %c", cab);
                        if(cab == HEADER){
                            state = COMMAND;
                        }else{
                            state = 1;
                        }
                    break;

                    case COMMAND:
                        command = dis.readByte();

                        switch(command){
                            case RQST_DATA:
                                //System.out.printf("Recibió petición de datos\n");
                                sendData();
                                state = 2;
                            break;

                            case SEND_DATA:
                                state = 2;
                            break;
                        }
                    break;

                    case 2:
                        aux = dis.readByte();
                        //System.out.printf("Datos a recibir = %d\n", aux);
                        if(aux>0)
                            state = 3;
                        else
                            state = 1;
                    break;

                    case 3:
                        dato = dis.readFloat();
                        buff_rx[numData] = dato;
                        numData++;
                        //System.out.printf("NumData = %d - %d\n", numData, aux);
                        if(numData == aux){
                            //System.out.printf("Prueba\n");
                            state = 1;
                            aux = 0;
                            numData = 0;
                        }
                    break;
                }
            }else{

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return isConnected;
    }

    public Float [] getRx_Buff(){
        return buff_rx;
    }

    public void sendData(){
        try{
            dos.writeChar('G');
            dos.writeByte(4);
            dos.writeInt(buff_tx[0]);
            dos.writeInt(buff_tx[1]);
            dos.writeInt(buff_tx[2]);
            dos.writeInt(buff_tx[3]);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
