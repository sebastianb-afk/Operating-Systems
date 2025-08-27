/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ur_os;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author super
 */
public class SystemOS implements Runnable{
    
    private static int clock = 0;
    private static final int MAX_SIM_CYCLES = 1000;
    private static final int MAX_SIM_PROC_CREATION_TIME = 50;
    private static final double PROB_PROC_CREATION = 0.1;
    private static Random r = new Random(1235);
    private OS os;
    private CPU cpu;
    private IOQueue ioq;
    
    protected ArrayList<Process> processes;
    ArrayList<Integer> execution;

    public SystemOS() {
        cpu = new CPU();
        ioq = new IOQueue();
        os = new OS(this, cpu, ioq);
        cpu.setOS(os);
        ioq.setOS(os);
        execution = new ArrayList();
        processes = new ArrayList();
        initSimulationQueueSimpler();
        showProcesses();
    }
    
    public int getTime(){
        return clock;
    }
    
    public ArrayList<Process> getProcessAtI(int i){
        ArrayList<Process> ps = new ArrayList();
        
        for (Process process : processes) {
            if(process.getTime_init() == i){
                ps.add(process);
            }
        }
        
        return ps;
    }

    public void initSimulationQueue(){
        double tp;
        Process p;
        for (int i = 0; i < MAX_SIM_PROC_CREATION_TIME; i++) {
            tp = r.nextDouble();
            if(PROB_PROC_CREATION >= tp){
                p = new Process();
                p.setTime_init(clock);
                processes.add(p);
            }
            clock++;
        }
        clock = 0;
    }
    
    public void initSimulationQueueSimple(){
        Process p;
        int cont = 0;
        for (int i = 0; i < MAX_SIM_PROC_CREATION_TIME; i++) {
            if(i % 4 == 0){
                p = new Process(cont++,-1);
                p.setTime_init(clock);
                processes.add(p);
            }
            clock++;
        }
        clock = 0;
    }
    
    public void initSimulationQueueSimpler(){
        
        Process p = new Process(false);
        p.setPriority(0);
        ProcessBurst temp = new ProcessBurst(5,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(4,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(3,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(0);
        processes.add(p);
        
        
        p = new Process(false);
        p.setPriority(1);
        temp = new ProcessBurst(3,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(5,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(6,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(2);
        processes.add(p);
        
        p = new Process(false);
        p.setPriority(2);
        temp = new ProcessBurst(7,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(3,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(5,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(6);
        processes.add(p);
        
        p = new Process(false);
        p.setPriority(3);
        temp = new ProcessBurst(4,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(3,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(7,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(8);
        processes.add(p);
        
        clock = 0;
    }
    
    public void initSimulationQueueSimpler2(){
        
        Process p = new Process(false);
        p.setPriority(0);
        ProcessBurst temp = new ProcessBurst(15,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(12,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(21,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(0);
        p.setPid(0);
        processes.add(p);
        
        
        p = new Process(false);
        p.setPriority(0);
        temp = new ProcessBurst(8,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(4,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(16,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(2);
        p.setPid(1);
        processes.add(p);
        
        p = new Process(false);
        p.setPriority(1);
        temp = new ProcessBurst(10,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(5,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(12,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(6);
        p.setPid(2);
        processes.add(p);
        
        p = new Process(false);
        p.setPriority(1);
        temp = new ProcessBurst(9,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(6,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(17,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(8);
        p.setPid(3);
        processes.add(p);
        
        clock = 0;
    }
    
    public boolean isSimulationFinished(){
        
        boolean finished = true;
        
        for (Process p : processes) {
            finished = finished && p.isFinished();
        }
        
        return finished;
    
    }
    
    
    @Override
    public void run() {
        double tp;
        ArrayList<Process> ps;
        
        System.out.println("******SIMULATION START******");
        
        int i=0;
        Process temp_exec;
        int tempID;
        while(!isSimulationFinished() && i < MAX_SIM_CYCLES){
            System.out.println("******Clock: "+i+"******");
            System.out.println(cpu);
            System.out.println(ioq);
            if(i == 32){
                int a=0;
            }

            ps = getProcessAtI(i);
            for (Process p : ps) {
                os.create_process(p);
            }           

            os.update();
               
            clock++;
            
            temp_exec = cpu.getProcess();
            if(temp_exec == null){
                tempID = -1;
            }else{
                tempID = temp_exec.getPid();
            }
            execution.add(tempID);
            
            cpu.update();
            
            ioq.update();
            
            System.out.println("After the cycle: ");
            System.out.println(cpu);
            System.out.println(ioq);
            i++;

        }
        System.out.println("******SIMULATION FINISHES******");
        
        System.out.println("******Process Execution******");
        for (Integer num : execution) {
            System.out.print(num+" ");
        }
        System.out.println("");
        System.out.println("******Performance Indicators******");
        System.out.println("Total execution cycles: "+clock);
        System.out.println("CPU Utilization: "+this.calcCPUUtilization());
        System.out.println("Throughput: "+this.calcThroughput());
        System.out.println("Average Turnaround Time: "+this.calcTurnaroundTime());
        System.out.println("Average Waiting Time: "+this.calcAvgWaitingTime());
        System.out.println("Average Context Switches (solo Gantt): "+this.calcAvgContextSwitches());
        System.out.println("Average Context Switches (completo): "+this.calcAvgContextSwitches2());
        System.out.println("Average Response Time: "+this.calcResponseTime());
    }
    
    public void showProcesses(){
        System.out.println("Process list:");
        StringBuilder sb = new StringBuilder();
        
        for (Process process : processes) {
            sb.append(process);
            sb.append("\n");
        }
        
        System.out.println(sb.toString());
    }
    
    
    public double calcCPUUtilization(){
        int cpu_empty_cycles = 0;
        for (int execution_code:execution) {
            if (execution_code == -1) {
                cpu_empty_cycles++;
            }
        }

        return (double)(clock-cpu_empty_cycles)/(clock);
    }
    
    public double calcTurnaroundTime(){
        int tot = 0;
        for(Process p : processes) {
            tot += p.time_finished - p.time_init;
        } 
        
        return (double)tot/processes.size();
    }
    
    public double calcThroughput(){
        return (double)processes.size()/clock;
    }
    
    public double calcAvgWaitingTime(){
        int tot = 0;
        for(Process p : processes) {
            tot += p.time_finished - p.time_init - p.getTotalExecutionTime();
        } 
        
        return (double)tot/processes.size();
    }
    
    public double calcAvgContextSwitches(){
        int cont_switches = 1;
        
        for(int i= 1; i< execution.size();i++){
            if(execution.get(i) != -1 && execution.get(i) !=  execution.get(i-1)){
                cont_switches++;
            }
        }
        return (double)cont_switches / processes.size();
    }
    
    public double calcAvgContextSwitches2(){
        int switches = os.rq.s.getTotalContextSwitches();
        
        
        return (double)switches / processes.size();
    }

    public double calcResponseTime(){
        int tot = 0;
        for(Process p : processes) {
            int response_time = p.time_init;
            while (execution.get(response_time) != p.pid) {
                response_time++;
            }
            tot += response_time - p.time_init;
        } 
        
        
        return (double)tot/processes.size();
    }
    
}