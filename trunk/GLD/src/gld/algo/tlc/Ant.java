/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gld.algo.tlc;
import java.util.Random;
import java.lang.Math;
/**
 *
 * @author Jonathan
 */
public class Ant {
    final static int RS = 5;
    public float FOREING_FACTOR = 0.5f; //Factor of performance for foreing agents
    private Swarm swarm;
    private Float [][] tresholds;
    private int available;
    private Site [][] zones;
    private int currentTask;
    Random ran;
    private float action;

    public Ant(Swarm swarm, Site [][] zones){
        int numTasks = Task.numTasks;
        this.zones = zones;
        this.swarm = swarm;
        
        tresholds = new Float[zones.length][];
        for(int i = 0; i < tresholds.length; i++){
            tresholds[i] = new Float[zones[i].length];
        }

        for(int i = 0; i < tresholds.length; i++){
            for(int j = 0; j < tresholds[i].length; j++){
                tresholds[i][j] = swarm.getInitial_treshold();
            }
        }

        this.action = swarm.getActionPerAgent();
        this.available = 0;
        //this.tasks = tasks;
        ran = new Random();
        currentTask = (int) Math.floor(numTasks*ran.nextFloat());
    }

    public void step(){
        double random;
        Site [] workZone;
        random = Math.floor((zones.length)*Math.random());
        workZone = zones[(int)random];
        double [] pi = new double[workZone.length];
        double [] r = new double[workZone.length];
        double Sj2, th2;

        if(this.isAvailable()){
            for(int i = 0; i < workZone.length; i++){
                r[i] = Math.random();
                Sj2 = Math.pow(workZone[i].getDemand(),2);
                th2 = Math.pow(tresholds[(int)random][i], 2);
                pi[i] = (Sj2)/(Sj2 + swarm.getAlpha()*th2);

                if(r[i] < pi[i]){
                    workZone[i].addAgent(this);
                    this.unavailable(5);
                }
            }
        }
    }

    public void changeTask(int newTask){
        this.currentTask = newTask;
    }

    public void unavailable(int time){
        available = time;
    }

    public boolean isAvailable() {
        available--;
        if(available <= 0)
            return true;
        else
            return false;
    }

    //    public float getTreshold(int numTask) {
//        return tresholds[numTask];
//    }

//    public void setTreshold(int numTask, float treshold) {
//        this.tresholds[numTask] = treshold;
//    }

    public float getAction() {
        return action;
    }

    public void setAction(float action) {
        this.action = action;
    }

    public boolean belongTo(Swarm swarm){
        if(swarm == this.swarm)
            return true;
        else
            return false;
    }

    public Swarm getSwarm(){
        return this.swarm;
    }
}
