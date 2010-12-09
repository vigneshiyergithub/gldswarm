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
    public float FOREING_FACTOR = 0.5f; //Factor of performance for foreing agents
    private Swarm swarm;
    private Float [] tresholds;
    private boolean available;
    private Task [] tasks;
    private int currentTask;
    Random ran;
    private float action;

    public Ant(Swarm swarm){
        int numTasks = Task.numTasks;
        tresholds = new Float[numTasks];
        for(int i = 0; i < tresholds.length; i++){
            tresholds[i] = swarm.getInitial_treshold();
        }
        this.action = swarm.getActionPerAgent();
        this.available = true;
        //this.tasks = tasks;
        ran = new Random();
        currentTask = (int) Math.floor(numTasks*ran.nextFloat());
    }

    public void step(){
        double random = Math.random();
        
    }

    public void changeTask(int newTask){
        this.currentTask = newTask;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public float getTreshold(int numTask) {
        return tresholds[numTask];
    }

    public void setTreshold(int numTask, float treshold) {
        this.tresholds[numTask] = treshold;
    }

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
