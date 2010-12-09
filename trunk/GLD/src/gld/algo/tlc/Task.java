/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gld.algo.tlc;

/**
 *
 * @author Jonathan
 */
public class Task {
    static int numTasks = 0;
    private float demand;
    private float action = 0;

    public Task(float initialDemand){
        demand = initialDemand;
        this.numTasks++;

    }

    public float getDemand() {
        return demand;
    }

    public void setDemand(float demand) {
        this.demand = demand;
    }

    public void addAgent(Ant agent){
        this.action+=agent.getAction();
    }

    public void subAgent(Ant agent){
        this.action-=agent.getAction();
    }
}
