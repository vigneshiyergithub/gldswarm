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

        protected Site sites [][];
        protected Drivelane drivelanes [][];
        
        int numAgents = 10;

        public ATAA_TLC(Infrastructure i) {
		super(i);
		//control = new ATAA_gui();
                //control.setVisible(true);
                Node [] nodes = i.getAllNodes();
                
                sites = new Site[nodes.length][];
                drivelanes = new Drivelane[nodes.length][];
                numNodes = tld.length;
                Drivelane dl = null;
                Node nodeTemp = null;
                Node nodeFrom = null;
                //Se crea la matriz de sites //////////////////////////////////
                for(int j = 0; j < nodes.length; j++){
                    //System.out.printf("\nIndice 1 = %d\n", j);
                    int temp = tld[j].length;
                    sites[j] = new Site[temp];
                    drivelanes[j] = new Drivelane[temp];
                    for(int k = 0; k < temp; k++){
                        //System.out.printf("\nIndice 2 = %d\n", k);
                        dl = tld[j][k].getTL().getLane();
                        sites[j][k] = new Site(dl);
                        drivelanes[j][k] = dl;
                    }
                }
                ///////////////////////////////////////////////////////////////

                //Se establecen las vecindades entre sites /////////////////////
                for(int j = 0; j < sites.length; j++){
                    int temp = sites[j].length;
                    for(int k = 0; k < temp; k++){
                        dl = sites[j][k].getDrivelane();
                        nodeTemp = dl.getNodeLeadsTo();
                        nodeFrom = dl.getNodeComesFrom();
                        try{
                            Drivelane [] outlanes = nodeTemp.getOutboundLanes();
                            for(int l = 0; l < outlanes.length; l++){
                                Node nl = outlanes[l].getNodeLeadsTo();
                                int indexNode = Site.searchIndex(nodes, nl);
                                System.out.printf("\nIndice Node = %d\n", indexNode);
                                int indexDl = Site.searchIndex(drivelanes[indexNode], outlanes[l]);
                                System.out.printf("\nIndice Drivelane = %d\n", indexDl);
                                if((indexNode != -1)&&(indexDl != -1)&& nodeFrom != nodes[indexNode]){
                                    sites[j][k].addAdj(sites[indexNode][indexDl]);
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                ///////////////////////////////////////////////////////////////
                //Se crean los swarms///////////////////////////////////////////
                swarms = new Swarm[numNodes];
                Node node = null;

                System.out.printf("All Nodes = \t");
                for(int j = 0; j < nodes.length; j++)
                    System.out.printf("%d \t", nodes[j].getId());

                for(int k = 0; k < numNodes; k++){
                    if(tld[k].length > 0)
                        node = tld[k][1].getTL().getNode();
                    if((node instanceof Junction) && node.getNumRealSigns()>0){
                        System.out.printf("\nDrivelanes = ");
                        for(int j = 0; j < tld[k].length; j++)
                            System.out.printf("%d\t", tld[k][j].getTL().getLane().getId());
                        //System.out.printf("Node Junctions Id:  %d\n", nodes[k].getId());
                        System.out.printf("\nIndice = %d\n", k);
                        swarms[k] = new Swarm(numAgents, node, k, sites);
                    }
                }

                // Prueba para ver las zonas generadas en los enjambres
                for(int k = 0; k < swarms.length; k++){
                    if(swarms[k] != null)
                        swarms[k].printZones();
                }
                //////////////////////////////////////////////////////

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
                for(int j = 0; j < sites.length; j++)
                    for(int k = 0; k < sites[j].length; k++)
                        sites[j][k].calculateDemand();

                for(int i = 0; i < swarms.length; i++){
                    if(swarms[i] != null)
                        swarms[i].step();
                }

                for(int i = 0; i < sites.length; i++){
                    for(int j = 0; j < sites[i].length; j++){
                        tld[i][j].setGain(sites[i][j].calculateOutput());
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
