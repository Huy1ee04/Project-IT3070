package show;

public class Result {

    private final int time;
    private final String[] processStatus;

    public Result(int time) {
        this.time = time;
        this.processStatus = new String[10]; // Assume max 10 processes for simplicity
    }

    public int getTime() {
        return time;
    }

    public String getProcess1() {
        return processStatus[0];
    }

    public String getProcess2() {
        return processStatus[1];
    }

    public String getProcess3() {
        return processStatus[2];
    }

    public String getProcess4() {
        return processStatus[3];
    }

    public String getProcess5() {
        return processStatus[4];
    }

    public String getProcess6() {
        return processStatus[5];
    }

    public String getProcess7() {
        return processStatus[6];
    }

    public String getProcess8() {
        return processStatus[7];
    }

    public String getProcess9() {
        return processStatus[8];
    }

    public String getProcess10() {
        return processStatus[9];
    }

    public void setProcessStatus(int process, String status) {
        if (process >= 1 && process <= 10) {
            processStatus[process - 1] = status;
        }
    }
}