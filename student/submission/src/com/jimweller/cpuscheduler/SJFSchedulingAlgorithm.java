/** SJFSchedulingAlgorithm.java
 * 
 * A shortest job first scheduling algorithm.
 *
 * @author: Charles Zhu
 * Spring 2016
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

import com.jimweller.cpuscheduler.Process;

public class SJFSchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
    
    private Vector<Process> jobs;
    private boolean preempt;
    
    SJFSchedulingAlgorithm(){
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
	long minBurst = jobs.get(0).getBurstTime();
	int minindex = 0;
	long minBurst2 = jobs.get(0).getInitBurstTime();
	if(jobs.isEmpty())
	{
	    return null;
	}

	if(isPreemptive() == true)
	{
	    for(int i = 0; i < jobs.size(); ++i)
	    {
		if(jobs.get(i).getBurstTime() < minBurst)
		{
		    minBurst = jobs.get(i).getBurstTime();
		    minindex = i;
		}
		else if(jobs.get(i).getBurstTime() == minBurst)
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
	    activeJob = activeJob;
	}
	else
	{
	    for(int x = 0; x < jobs.size(); ++x)
	    {
		if(jobs.get(x).getInitBurstTime() < minBurst2)
		{
		    minBurst2 = jobs.get(x).getInitBurstTime();
		    minindex = x;
		}
		else if(jobs.get(x).getInitBurstTime() == minBurst2)
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
        return "Shortest Job First";
}

    /**
     * @return Value of preemptive.
     */
    public boolean isPreemptive(){
	return preempt;
    }
    
    /**
     * @param v  Value to assign to preemptive.
     */
    public void setPreemptive(boolean  v){
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
