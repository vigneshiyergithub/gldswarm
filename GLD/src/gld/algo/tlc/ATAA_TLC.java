
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


/**
 * This controller will switch TrafficLights at random.
 *
 * @author Group Algorithms
 * @version 1.0
 */
public class ATAA_TLC extends TLController
{	protected int numNodes;
	protected Random seed;
	protected final static String shortXMLName="tlc-ataa";
        protected ATAA_gui control;
	protected Swarm [] swarms;

        int numAgents = 10;

        public ATAA_TLC(Infrastructure i) {
		super(i);
		//control = new ATAA_gui();
                //control.setVisible(true);
                numNodes = tld.length;
                int numJunctions = i.getNumJunctions();
                Node [] nodes = i.getAllNodes();
                swarms = new Swarm[numNodes];
                System.out.printf("OK %d\n", 1);

                for(int k = 0; k < nodes.length; k++){
                    System.out.printf("OK %d\n", k);
                    if((nodes[k] instanceof Junction) && nodes[k].getNumRealSigns()>0){
                        System.out.printf("Node Juntions Id:  %d\n", nodes[k].getId());
                        swarms[k] = new Swarm(numAgents, nodes[k]);
                    }
                }

//                // Prueba para ver las zonas generadas en los enjambres
//                for(int k = 0; k < swarms.length; k++){
//                    if(swarms[k] != null)
//                        swarms[k].printZones();
//                }
//                //////////////////////////////////////////////////////

	}
	
	/*public ATAA_TLC(Infrastructure infra)
	{ 	super(infra);
	  	seed=n-ew Random();
		num_nodes = tld.length;
	}*/
	
	public void setInfrastructure(Infrastructure i) {
		super.setInfrastructure(i);
		numNodes = tld.length;
	}
	 
	/**
	 * Calculates how every traffic light should be switched
	 * @param The TLDecision is a tuple consisting of a traffic light and a reward (Q) value, for it to be green
	 * @see gld.algo.tlc.TLDecision
	 */	
	public TLDecision[][] decideTLs()
	{
		//System.out.println("RandomTLC.decideTLs");
                //tld[0][0].getTL().
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
