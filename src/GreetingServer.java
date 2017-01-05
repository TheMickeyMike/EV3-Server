// File Name GreetingServer.java

import model.Transaction;

import java.net.*;
import java.io.*;

public class GreetingServer extends Thread
{
    private ServerSocket serverSocket;

    public GreetingServer(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
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
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());

                Transaction transaction = (Transaction) inFromClient.readObject();

                if(transaction != null) {
                    outFromServer.writeInt(200);
                    beginTransaction(transaction);
                } else {
                    outFromServer.writeInt(404);
                }

                clientSocket.close();
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

    private void beginTransaction(Transaction transaction) {

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