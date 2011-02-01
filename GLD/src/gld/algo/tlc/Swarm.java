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

    private Site [][] zones;  // Matriz que almacena las zonas y tiene en cuenta la vecindad entre ellas.

    private Vector zonas;
    private Site [][] sites;
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

    public Swarm(int numAgents, Node node, int k, Site [][] sites){
        Drivelane [] allLanes;
        this.sites = sites;
        agents = new Ant[numAgents];
        
        this.node = node;
        this.alpha = 0.5f;
        zones = new Site[sites[k].length][];
//        zonas =
        for(int i = 0; i < zones.length; i++){
            Vector ady = sites[k][i].getAdy();
            zones[i] = new Site[ady.size()+1];
            zones[i][0] = sites[k][i];
            for(int j = 0; j < ady.size(); j++){
                zones[i][j+1] = (Site) ady.elementAt(j);
            }
        }

        for(int i = 0; i < numAgents; i++){
            agents[i] = new Ant(this, zones);
        }
    }
    
    public void step(){
        Ant agent;
        
        for(int i = 0; i < agents.length; i++){
            agent = agents[i];
            agent.step();
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
        for(int i = 0; i < sites.length; i++){
           for(int j = 0; j < sites[i].length; j++){
                System.out.printf("%d \t", sites[i][j].getId());
                
           }
           System.out.printf("\n");
        }
        System.out.printf("\n");
        System.out.printf("-----------------------------------------------\n");
        System.out.printf("\n");

        System.out.println("Swarm zones: \n");
        for(int i = 0; i < zones.length; i++){
           for(int j = 0; j < zones[i].length; j++){
                System.out.printf("%d \t", zones[i][j].getId());
           }
           System.out.printf("\n");
        }
        System.out.printf("\n");
        System.out.printf("//////////////////////////////////////////////\n");
        System.out.printf("\n");
    }
}
