/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gld.algo.tlc;
import gld.infra.*;
import java.util.Vector;
/**
 * A cada Drivelane en un nodo corresponde un objeto clase Site.
 * En este objeto se calculan las ganacias con las que se calcula el estado de
 * los semáforos y la demanda correspondiente en cada Drivelane.
 * @author Jonathan
 */
public class Site {
    private Node node;
    private Drivelane dl;
    private double demand = 0.0f;
    private float spc = 2.5f; //Stimuli per car
    private float alpha = 0.5f; //Time rate variable
    private int numSlots = 5;
    private int freeSlots = numSlots;
    private long initialTime = 0;

    private Ant [] agents = new Ant[numSlots];

    public Site(Drivelane dl){
        this.dl = dl;
    }

    public void calculateDemand(){
        //En este método va la función que calcula la demanda en cada semáforo
        int numCars = dl.getNumRoadusersWaiting();
        if( numCars == 0){
            demand = 0.0f;
            initialTime = System.currentTimeMillis();
        }else{
            long actualTime = System.currentTimeMillis();
            long time = (actualTime - initialTime)*1000;
            //TODO: revisar calculo de la demanda.
            demand = numCars*spc*Math.exp(spc*time);
        }
    }

    public boolean addAgent(Ant agent){
        if(freeSlots>0){
           agents[numSlots - freeSlots + 1] = agent;
           freeSlots--;
           return true;
        }else
            return false;
    }

    public float calculateOutput(){
        float output = 0.0f;
        Node agentNode = null;
        Node siteNode = dl.getNodeLeadsTo();
        for(int i = 0; i < agents.length; i++){
            agentNode = agents[i].getSwarm().getNode();
            if(agentNode == siteNode)
                output += agents[i].getAction();
            else
                //TODO: revisar la asignación de ganancia cuando el agente pertenece a otro nodo.
                output += (agents[i].getAction()*agents[i].FOREING_FACTOR);
        }
        return output;
    }
}
