/** PrioritySchedulingAlgorithm.java
 * 
 * A single-queue priority scheduling algorithm.
 *
 * @author: Charles Zhu
 * Spring 2016
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

import com.jimweller.cpuscheduler.Process;

public class PrioritySchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
    
    private Vector<Process> jobs;
    private boolean preempt;
    
    PrioritySchedulingAlgorithm(){
    	activeJob = null;
	jobs = new Vector<Process>();
	preempt = false;
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
	jobs.add(p);
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
	return jobs.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
    when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
        throw new UnsupportedOperationException();
    }


    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
	
	long minPriority = jobs.get(0).getPriorityWeight();
	int minindex = 0;
	if(jobs.isEmpty())
	{
	    return null;
	}
	if(isPreemptive())
	{
	    for(int i = 0; i < jobs.size(); ++i)
	    {
		if(jobs.get(i).getPriorityWeight() < minPriority)
		{
		    minPriority = jobs.get(i).getPriorityWeight();
		    minindex = i;
		}
		else if(jobs.get(i).getPriorityWeight() == minPriority)
		{
		    if(jobs.get(i).PID < jobs.get(minindex).PID)
		    {
			minindex = i;
		    }
		}
	    }
	    activeJob = jobs.get(minindex);
	}
	else
	{
	    if(activeJob != null && activeJob.isFinished() == false)
	    {
		return activeJob;
	    }
	    else
	    {
		for(int x = 0; x < jobs.size(); ++x)
		{
		    if(jobs.get(x).getPriorityWeight() < minPriority)
		    {
			minPriority =  jobs.get(x).getPriorityWeight();
			minindex = x;
		    }
		    else if(jobs.get(x).getPriorityWeight() == minPriority)
		    {
			if(jobs.get(x).PID < jobs.get(minindex).PID)
			{
			    minindex = x;
			}
		    }
		}
		activeJob = jobs.get(minindex);
	    }
	}
	return activeJob;
    }

    public String getName(){
        return "Single-Queue Priority";
    }

    /**
     * @return Value of preemptive.
     */
    public boolean isPreemptive(){
	return preempt;
    }
    
    /**
     * @param v Value to assign to preemptive.
     */
    public void setPreemptive(boolean v){
	if(v == true)
	{
	    preempt = true;
	}
	else
	{
	    preempt = false;
	}
    }
    
}
