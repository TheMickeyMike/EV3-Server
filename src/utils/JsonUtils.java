package utils;

import com.google.gson.*;
import model.Job;
import model.Transaction;

/**
 * Created by Mateusz on 06.12.2016.
 */
public class JsonUtils {

    public Transaction transactionJsonToObject(String rawJson) {
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();

        JsonElement transactionElement = parser.parse(rawJson).getAsJsonObject();
        Transaction transaction = gson.fromJson(transactionElement, Transaction.class);

        return transaction;
    }

    public Job jobJsonToObject(String rawJson) {
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();

        JsonElement jobElement = parser.parse(rawJson).getAsJsonObject();
        Job job = gson.fromJson(jobElement, Job.class);

        return job;
    }

}
