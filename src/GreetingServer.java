// File Name GreetingServer.java

import model.Job;
import model.Transaction;
import utils.Headers;
import utils.HttpQueries;
import utils.JsonUtils;

import java.net.*;
import java.io.*;

public class GreetingServer extends Thread
{
    private ServerSocket serverSocket;
    private JsonUtils jsonUtils;
    private int responseCode;
    private String robotId = "7a365494-0c02-4d28-8348-6f883f062702";

    public GreetingServer(int port) throws IOException
    {
        this.jsonUtils = new JsonUtils();
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000000);
    }

    public void run()
    {
        while(true)
        {
            try
            {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket clientSocket  = serverSocket.accept();
                System.out.println("Just connected to "
                        + clientSocket.getRemoteSocketAddress());

                DataOutputStream outFromServer = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream inFromClient = new DataInputStream(clientSocket.getInputStream());

                String transactionJSON = inFromClient.readUTF();
                System.out.println(transactionJSON);

                if(!transactionJSON.equals("")) {
                    outFromServer.writeInt(200);

                    Transaction transaction = jsonUtils.transactionJsonToObject(transactionJSON);
                    putDownloading(transaction);
                    Thread.sleep(3000);
                    putReady(transaction.getTransaction_id());

                } else {
                    outFromServer.writeInt(404);
                }

                clientSocket.close();

                Socket clientSocket2  = serverSocket.accept();
                System.out.println("Just connected to "
                        + clientSocket.getRemoteSocketAddress());

                DataOutputStream outFromServer2 = new DataOutputStream(clientSocket2.getOutputStream());
                DataInputStream inFromClient2 = new DataInputStream(clientSocket2.getInputStream());

                String jobJSON = inFromClient2.readUTF();
                System.out.println(jobJSON);

                if(!jobJSON.equals("")) {
                    outFromServer2.writeInt(200);
                    Job job = jsonUtils.jobJsonToObject(jobJSON);
                    putPlaying(job.getTransaction_id());
                    Thread.sleep(5000);

                    if(jobJSON.contains("ABORTED")) {
                        Job job2 = jsonUtils.jobJsonToObject(transactionJSON);
                        putAborted(job2.getTransaction_id());
                    }

                    putCompleted(job.getTransaction_id());

                } else {
                    outFromServer2.writeInt(404);
                }

                clientSocket2.close();
            }catch(SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
                break;
            }catch(Exception e)
            {
                e.printStackTrace();
                break;
            }
        }
    }

    private void putAborted(String transaction_id) {
        String url = "http://s396393.vm.wmi.amu.edu.pl/api/games/transactions/" + transaction_id;
        System.out.println("Aborting..");
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("PUT");
            con.setRequestProperty("Accept", Headers.ACCEPT);
            con.setRequestProperty("Content-Type", Headers.CONTENT_TYPE);
            con.setRequestProperty("X-Robot", robotId);

            String body = "{\n" +
                    "\"new_status\": \"ARCHIVED\"\n" +
                    "}";

            //send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();



        } catch (Exception e) {
            switch(responseCode) {
                case 400:
                    System.err.println("Problem");
                    break;
                case 500:
                    System.err.println("Check your Internet connection.");
                    break;
                default:
                    System.err.println("Something went wrong.");
                    break;
            }
        }
    }

    private void putCompleted(String transaction_id) {
        String url = "http://s396393.vm.wmi.amu.edu.pl/api/games/transactions/" + transaction_id;
        System.out.println("Completed..");
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("PUT");
            con.setRequestProperty("Accept", Headers.ACCEPT);
            con.setRequestProperty("Content-Type", Headers.CONTENT_TYPE);
            con.setRequestProperty("X-Robot", robotId);

            String body = "{\n" +
                    "\"new_status\": \"COMPLETED\"\n" +
                    "}";

            //send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();



        } catch (Exception e) {
            switch(responseCode) {
                case 400:
                    System.err.println("Problem");
                    break;
                case 500:
                    System.err.println("Check your Internet connection.");
                    break;
                default:
                    System.err.println("Something went wrong.");
                    break;
            }
        }
    }

    private void putPlaying(String transaction_id) {
        String url = "http://s396393.vm.wmi.amu.edu.pl/api/games/transactions/" + transaction_id;
        System.out.println("Playing..");
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("PUT");
            con.setRequestProperty("Accept", Headers.ACCEPT);
            con.setRequestProperty("Content-Type", Headers.CONTENT_TYPE);
            con.setRequestProperty("X-Robot", robotId);

            String body = "{\n" +
                    "\"new_status\": \"PLAYING\"\n" +
                    "}";

            //send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();



        } catch (Exception e) {
            switch(responseCode) {
                case 400:
                    System.err.println("Problem");
                    break;
                case 500:
                    System.err.println("Check your Internet connection.");
                    break;
                default:
                    System.err.println("Something went wrong.");
                    break;
            }
        }
    }

    private void putReady(String transactionId) {
        String url = "http://s396393.vm.wmi.amu.edu.pl/api/games/transactions/" + transactionId;
        System.out.println("Ready..");
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("PUT");
            con.setRequestProperty("Accept", Headers.ACCEPT);
            con.setRequestProperty("Content-Type", Headers.CONTENT_TYPE);
            con.setRequestProperty("X-Robot", robotId);

            String body = "{\n" +
                    "\"new_status\": \"READY\"\n" +
                    "}";

            //send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();



        } catch (Exception e) {
            switch(responseCode) {
                case 400:
                    System.err.println("Problem");
                    break;
                case 500:
                    System.err.println("Check your Internet connection.");
                    break;
                default:
                    System.err.println("Something went wrong.");
                    break;
            }
        }
    }

    private void putDownloading(Transaction transaction) {
        String url = "http://s396393.vm.wmi.amu.edu.pl/api/games/transactions/" + transaction.getTransaction_id();
        System.out.println("Downloading game..");

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("PUT");
            con.setRequestProperty("Accept", Headers.ACCEPT);
            con.setRequestProperty("Content-Type", Headers.CONTENT_TYPE);
            con.setRequestProperty("X-Robot", robotId);

            String body = "{\n" +
                    "\"new_status\": \"DOWNLOADING\"\n" +
                    "}";

            //send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();




        } catch (Exception e) {
            switch(responseCode) {
                case 400:
                    System.err.println("Problem");
                    break;
                case 500:
                    System.err.println("Check your Internet connection.");
                    break;
                default:
                    System.err.println("Something went wrong.");
                    break;
            }
        }

    }

    public static void main(String [] args)
    {
        int port = Integer.parseInt("3456");
        try
        {
            Thread t = new GreetingServer(port);
            t.start();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}