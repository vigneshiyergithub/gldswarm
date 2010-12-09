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
    private float actionPerAgent;
    private Ant [] agents;

    private Drivelane [][] zones;
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

    public Swarm(int numAgents, Node node){
        agents = new Ant[numAgents];
        for(int i = 0; i < numAgents; i++){
            agents[i] = new Ant(this);
        }
        this.node = node;

        try{
            Drivelane [] outlanes = node.getOutboundLanes();
            Drivelane [] inlanes = node.getInboundLanes();

            zones = new Drivelane[inlanes.length][];
            Drivelane [] temp = null;
            Drivelane ltemp;
            Vector drivelanes = new Vector();

            for(int i = 0; i < inlanes.length; i++){
                ltemp = inlanes[i];
                drivelanes.add(ltemp);
                Road roadlane = ltemp.getRoad();
                for(int j = 0; j < outlanes.length; j++){
                    if(!(outlanes[j].getRoad().equals(roadlane))){
                        drivelanes.add(outlanes[j]);
                    }
                //temp = (Drivelane []) drivelanes.toArray();
                }
                
                temp = new Drivelane[drivelanes.size()];
                for(int j = 0; j < drivelanes.size(); j++){
                    temp[j] = (Drivelane) drivelanes.get(j);
                }
                drivelanes.clear();
                zones[i] = temp;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    public void step(){
        Ant agent;
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
        System.out.println("Swarm Zones: \n");
        for(int i = 0; i < zones.length; i++){
            for(int k = 0; k < zones[i].length; k++){
                System.out.printf("%d \t", zones[i][k].getId());
            }
            System.out.printf("\n");
        }
        System.out.printf("//////////////////////////////////////////////\n");
        System.out.printf("\n");

    }
}
