
/*-----------------------------------------------------------------------
 * Copyright (C) 2001 Green Light District Team, Utrecht University 
 *
 * This program (Green Light District) is free software.
 * You may redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by
 * the Free Software Foundation (version 2 or later).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * See the documentation of Green Light District for further information.
 *------------------------------------------------------------------------*/

package gld.algo.tlc;

import gld.*;
import gld.sim.*;
import gld.algo.tlc.*;
import gld.infra.*;
import gld.utils.*;
import gld.xml.*;
import java.io.IOException;
import java.util.* ;
import java.awt.Point;

import java.io.*;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.InetAddress;

/**
 * This controller will switch TrafficLights at random.
 *
 * @author Group Algorithms
 * @version 1.0
 */
public class ATAA_TLC1 extends TLController
{	protected int num_nodes;
	protected Random seed;
	protected final static String shortXMLName="tlc-ataa";
        protected GUIControl control;
	protected DatagramSocket socket;
        protected DatagramPacket p;
        protected int PORT = 3052;
        protected Float [] buff_rx;
        protected Byte [] buff_tx = new Byte[4];
        protected String address = "localhost";
        protected Comm comm;

        public ATAA_TLC1(Infrastructure i) {
		super(i);
		control = new GUIControl();
                control.setVisible(true);
		num_nodes = tld.length;

                buff_rx = new Float[4];

                comm = new Comm(address, PORT, buff_tx, buff_rx);

                for(int j = 0; j<buff_rx.length; j++){
                    buff_rx[j] = 0.0f;
                }
                
                //comm.connect();

                Thread serverT = new Thread(comm);
                serverT.start();
	}
	
	/*public ATAA_TLC(Infrastructure infra)
	{ 	super(infra);
	  	seed=new Random();
		num_nodes = tld.length;
	}*/
	
	public void setInfrastructure(Infrastructure i) {
		super.setInfrastructure(i);
		num_nodes = tld.length;
	}
	 
	/**
	 * Calculates how every traffic light should be switched
	 * @param The TLDecision is a tuple consisting of a traffic light and a reward (Q) value, for it to be green
	 * @see gld.algo.tlc.TLDecision
	 */	
	public TLDecision[][] decideTLs()
	{
		//System.out.println("RandomTLC.decideTLs");
            if(comm!=null && comm.isConnected())
                control.setConectado(true);


                int num_lanes, temp_len;
                float gain;
                //System.out.printf("Num Nodes = %d\n", num_nodes);

                for (int i=0; i < num_nodes; i++) {
			num_lanes = tld[i].length;
                        //System.out.printf("Num Lanes = %d\n", num_lanes);
                        gain = 0;
			for (int j=0; j < num_lanes; j++){
                            //gain = control.getQ0(j);
                            buff_tx[j] = (byte) tld[i][j].getTL().getLane().getNumRoadusersWaiting();
                            //gain = seed.nextFloat();
                            //System.out.printf("Ganancia %d = %f\n", j,buff_rx[j]);
                            gain = buff_rx[j];
                            
                            tld[i][j].setGain(gain);
                        }

		}
		return tld;
	}

	public void updateRoaduserMove(Roaduser _ru, Drivelane _prevlane, Sign _prevsign, int _prevpos, Drivelane _dlanenow, Sign _signnow, int _posnow, PosMov[] posMovs, Drivelane desired)
	{
	}
	
	// XMLSerializable implementation
	
	public XMLElement saveSelf () throws XMLCannotSaveException
	{ 	XMLElement result=super.saveSelf();
		result.setName(shortXMLName);
		return result;
	}
 
	public String getXMLName ()
 	{ 	return "model."+shortXMLName;
 	}

        private void getData(){


        }

        
}
