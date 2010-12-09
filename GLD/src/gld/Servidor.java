/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gld;
import java.net.*;
import java.io.*;
import gld.algo.tlc.Comm;
/**
 *
 * @author Jonathan
 */
public class Servidor {
    static Socket socket;
    static int puerto = 3052;
    static OutputStream os;
    static DataOutputStream dos;
    static Comm server;

    static Byte [] buff_tx;
    static Float [] buff_rx = new Float[4];

    public static void main(String[] params){
        server = new Comm("localhost",puerto, buff_tx, buff_rx);

        server.connect();

        Thread serverT = new Thread(server);

        serverT.start();
        while(true){
            //buff_rx = server.getRx_Buff();
            if(buff_rx != null)
                System.out.printf("Datos Recibidos = %f - %f - %f - %f\n", buff_rx[0], buff_rx[1], buff_rx[2], buff_rx[3]);

            try{
                Thread.sleep(100);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        
    }
}
