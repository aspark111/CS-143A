/** RoundRobinSchedulingAlgorithm.java
 * 
 * A scheduling algorithm that randomly picks the next job to go.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class RoundRobinSchedulingAlgorithm extends BaseSchedulingAlgorithm {

    /** the time slice each process gets */
    private int quantum;
    private long time;
    private Vector<Process> jobs;

    RoundRobinSchedulingAlgorithm() {
	activeJob = null;
	jobs = new Vector<Process>();
	quantum = 10;
	time = 0;
    }

    /** Add the new job to the correct queue. */
    public void addJob(Process p) {
	jobs.add(p);
    }

    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p) {
	return jobs.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
    when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the value of quantum.
     * 
     * @return Value of quantum.
     */
    public int getQuantum() {
        return quantum;
    }

    /**
     * Set the value of quantum.
     * 
     * @param v
     *            Value to assign to quantum.
     */
    public void setQuantum(int v) {
        this.quantum = v;
    }

    /**
     * Returns the next process that should be run by the CPU, null if none
     * available.
     */
    public Process getNextJob(long currentTime) {
	
	long min = jobs.get(0).PID;
	long truemin = jobs.get(0).PID;
	int trueminindex = 0;
	long max = jobs.get(0).PID;
	int index = 0;
	if(jobs.isEmpty())
	{
	    return null;
	}
	
	if(currentTime - jobs.get(0).start > getQuantum())
	{
	    for(int i = 1; i < jobs.size(); ++i)
	    {
		if(jobs.get(i).PID > max)
		{
		    max = jobs.get(i).PID;
		}
	    }
	    for(int i = 1; i < jobs.size(); ++i)
	    {
		if(jobs.get(i).PID < truemin)
		{
		    truemin = jobs.get(i).PID;
		    trueminindex = i;
		}
	    }
	    for(int i = 1; i < jobs.size(); ++i)
	    {
		if(jobs.get(i).PID < max && jobs.get(i).PID > min)
		{
		    max = jobs.get(i).PID;
		    index = i;
		}
		else if(min ==  max)
		{
		    index = trueminindex;
		}
	    }
	    activeJob = jobs.get(index);
	}
	else
	{
	    activeJob = activeJob;
	}
	return activeJob;	
    }

    public String getName() {
        return "Round Robin";
    }
    
}
