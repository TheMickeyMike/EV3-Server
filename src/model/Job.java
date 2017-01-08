package model;

/**
 * Created by Mateusz on 08.01.2017.
 */
public class Job {
    private String transaction_id;
    private String job;

    public Job(String transaction_id, String job) {
        this.transaction_id = transaction_id;
        this.job = job;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
