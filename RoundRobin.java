package ur_os;

/**
 *
 * @author prestamour
 */
public class RoundRobin extends Scheduler {

    private int q;
    private int cont;
    private boolean multiqueue;

    RoundRobin(OS os) {
        super(os);
        this.q = 5;
        this.cont = 0;
        this.multiqueue = false;
    }

    RoundRobin(OS os, int q) {
        super(os);
        this.q = q;
        this.cont = 0;
        this.multiqueue = false;
    }

    RoundRobin(OS os, int q, boolean multiqueue) {
        super(os);
        this.q = q;
        this.cont = 0;
        this.multiqueue = multiqueue;
    }

    void resetCounter() {
        cont = 0;
    }

    @Override
    public void getNext(boolean cpuEmpty) {
        if (!cpuEmpty) {
            cont++;
            if (cont >= q) {
                os.interrupt(InterruptType.SCHEDULER_CPU_TO_RQ, null);
                resetCounter();
            }
        } else if (!processes.isEmpty()) {
            Process p = processes.remove();
            os.interrupt(InterruptType.SCHEDULER_RQ_TO_CPU, p);
            resetCounter();
        }
    }

    @Override
    public void newProcess(boolean cpuEmpty) {
    }

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {
    }
}
