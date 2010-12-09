/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gld;
import java.net.*;
import java.io.*;
/**
 *
 * @author Jonathan
 */
public class Cliente {
    static Socket socket;
    static int puerto = 3052;
    static OutputStream os;
    static DataOutputStream dos;

    public static void main(String[] params){
        try{
            socket = new Socket("localhost",puerto);
            dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            dos.writeChars("Hola mundo");
            dos.close();
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
