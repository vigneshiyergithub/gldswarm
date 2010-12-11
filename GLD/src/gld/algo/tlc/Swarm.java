/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gld.algo.tlc;
import gld.infra.*;
import gld.infra.*;
import java.util.Vector;
/**
 *
 * @author Jonathan
 */
public class Swarm {
    private Node node;
    private int positionTLD; //Posición del nodo en la matriz de decisión
    private float actionPerAgent;
    private Ant [] agents;

    private int [][] zones;
    private Site [] sites2;
    private float [] demand;
    private float [] action;

    Drivelane [] outlanes;
    Drivelane [] inlanes;
    Drivelane [] temp = null;
    Drivelane ltemp;
    Vector drivelanes;
    ////////////////////////////////////////////////////////////////
    // Parámetros del algoritmo ////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    private float initial_treshold;
    private float max_treshold;
    private float min_treshold;
    private float alpha;
    private float beta;
    private float epsilon0;
    private float epsilon1;
    private float rho;
    ////////////////////////////////////////////////////////////////

    public Swarm(int numAgents, Node node, int k){
        Drivelane [] allLanes;
        agents = new Ant[numAgents];
        for(int i = 0; i < numAgents; i++){
            agents[i] = new Ant(this);
        }
        this.node = node;

        try{
            allLanes = node.getAllLanes();
            Drivelane [] outlanes = node.getOutboundLanes();
            Drivelane [] inlanes = node.getInboundLanes();

            allLanes = new Drivelane[outlanes.length + inlanes.length];
            for(int i = 0; i < inlanes.length; i++){
                allLanes[i] = inlanes[i];
            }
            for(int i = 0; i < outlanes.length; i++){
                allLanes[inlanes.length + i] = outlanes[i];
            }

            sites2 = new Site[allLanes.length];
            for(int i = 0; i < allLanes.length; i++)
                sites2[i] = new Site(allLanes[i]);

            zones = new int[allLanes.length][];
            int [] temp = null;
            Drivelane ltemp;
            Vector drivelanes = new Vector();

            for(int i = 0; i < inlanes.length; i++){
                ltemp = inlanes[i];
                Road roadlane = ltemp.getRoad();
                for(int j = 0; j < outlanes.length; j++){
                    if(!(outlanes[j].getRoad().equals(roadlane))){
                        drivelanes.add(inlanes.length + j);
                    }
                }
                temp = new int[drivelanes.size()];
                for(int j = 0; j < drivelanes.size(); j++){
                    temp[j] = (Integer) drivelanes.get(j);
                }
                drivelanes.clear();
                zones[i] = temp;
            }
            for(int i = 0; i < outlanes.length; i++){
                ltemp = outlanes[i];
                Road roadlane = ltemp.getRoad();
                for(int j = 0; j < inlanes.length; j++){
                    if(!(inlanes[j].getRoad().equals(roadlane))){
                        drivelanes.add(j);
                    }
                }
                temp = new int[drivelanes.size()];
                for(int j = 0; j < drivelanes.size(); j++){
                    temp[j] = (Integer) drivelanes.get(j);
                }
                drivelanes.clear();
                zones[inlanes.length + i] = temp;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    public void step(){
        Ant agent;
        for(int j = 0; j < sites2.length; j++)
            sites2[j].calculateDemand();

        for(int i = 0; i < agents.length; i++){
            agent = agents[i];
            
        }
        
    }

    public float getInitial_treshold() {
        return initial_treshold;
    }

    public void setInitial_treshold(float initial_treshold) {
        this.initial_treshold = initial_treshold;
    }

    public float getActionPerAgent() {
        return actionPerAgent;
    }

    public void setActionPerAgent(float acctionPerAgent) {
        this.actionPerAgent = acctionPerAgent;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getBeta() {
        return beta;
    }

    public void setBeta(float beta) {
        this.beta = beta;
    }

    public float getEpsilon0() {
        return epsilon0;
    }

    public void setEpsilon0(float epsilon0) {
        this.epsilon0 = epsilon0;
    }

    public float getEpsilon1() {
        return epsilon1;
    }

    public void setEpsilon1(float epsilon1) {
        this.epsilon1 = epsilon1;
    }

    public float getMax_treshold() {
        return max_treshold;
    }

    public void setMax_treshold(float max_treshold) {
        this.max_treshold = max_treshold;
    }

    public float getMin_treshold() {
        return min_treshold;
    }

    public void setMin_treshold(float min_treshold) {
        this.min_treshold = min_treshold;
    }

    public float getRho() {
        return rho;
    }

    public void setRho(float rho) {
        this.rho = rho;
    }

    public Node getNode(){
        return this.node;
    }

    public void printZones(){
        System.out.printf("//////////////////////////////////////////////\n");
        System.out.printf("Swarm Node Id: %d \n", this.node.getId());
        System.out.println("Swarm Sites: \n");
        //for(int i = 0; i < sites.length; i++)
            //System.out.printf("%d \t", sites[i].getId());

        System.out.println("\nSwarm Zones: \n");
        for(int i = 0; i < zones.length; i++){
            for(int k = 0; k < zones[i].length; k++){
                System.out.printf("%d \t", zones[i][k]);
            }
            System.out.printf("\n");
        }
        System.out.printf("//////////////////////////////////////////////\n");
        System.out.printf("\n");

    }
}
