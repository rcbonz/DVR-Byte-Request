package com.dekosapp.connquality;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity {

private TextView targetIP;
private TextView targetPort;
private TextView txtLatencyValue;
private TextView txtResultTitle;
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
targetIP = (EditText)findViewById(R.id.targetIP);
targetPort = (EditText)findViewById(R.id.targetPort);
txtLatencyValue = (TextView)findViewById(R.id.usersResult);
txtResultTitle = (TextView)findViewById(R.id.txtResultTitle);
targetPort.setText("");
targetIP.setText("");
}



public void onClickGrabUsers(View v) {
String ip =targetIP.getText().toString();
String port =targetPort.getText().toString();
Log.v("TAG", ip);
Log.v("TAG", port);
static final byte USERS_HANDSHAKE[] =
        {
                0xa6 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x09 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00, 0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00
        };
if (ip.equals("")) {
    Toast.makeText(this.getApplicationContext(), "You cannot leave the input empty !",
            Toast.LENGTH_SHORT).show();
}
else {
    getLatencyIP(ip);
}
}

     //   public void onClickGetLatencyURL(View v) throws MalformedURLException, UnknownHostException {
    //       String url =txtInput.getText().toString();
    //      Log.v("TAG", url);
    //       if (url.equals("")) {
    //           Toast.makeText(this.getApplicationContext(), "You cannot leave the input empty !",
    //                   Toast.LENGTH_SHORT).show();
    //       }
    //       else {
    //            getLatencyURL(url);
    //           Toast.makeText(this.getApplicationContext(), "I still develop the method to convert url to ipAddress, my getLatencyURL method returning force close when i convert String to URL using new URL(). If you have any ideas just leave a comment",
    //                   Toast.LENGTH_LONG).show();
    //       }

    //     }




public int getLatencyIP(String args[]) {
    try {
        sock = new Socket(targetIP,targetPort);
    } catch (UnknownHostException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
    sent = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                while (true) {
                    System.out.println("Trying to read...");
                    String in = stdIn.readLine();
                    System.out.println(in);
                    out.print("Try" + "\r\n");
                    System.out.println("Message sent");
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    });
    sent.start();
    try {
        sent.join();
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    return Users;
}






        String pingCommand = "/system/bin/ping -c " + 1 + " " + ipAddress;
        String inputLine = "";
        double avgRtt = 0;
        int latency = 0;

        try {
            Log.v("TEST", "getLatencyIP");
            // execute the command on the environment interface
            Process process = Runtime.getRuntime().exec(pingCommand);
            // gets the input stream to get the output of the executed command
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            inputLine = bufferedReader.readLine();
            while ((inputLine != null)) {
                if (inputLine.length() > 0 && inputLine.contains("avg")) {  // when we get to the last line of executed ping command
                    break;
                }
                inputLine = bufferedReader.readLine();
            }
        }
        catch (IOException e){
            Log.v("", "getLatency: EXCEPTION");
            e.printStackTrace();
        }

// Extracting the average round trip time from the inputLine string
        String afterEqual = inputLine.substring(inputLine.indexOf("="), inputLine.length()).trim();
        String afterFirstSlash = afterEqual.substring(afterEqual.indexOf('/') + 1, afterEqual.length()).trim();
        String strAvgRtt = afterFirstSlash.substring(0, afterFirstSlash.indexOf('/'));
        avgRtt = Double.valueOf(strAvgRtt);
        latency = (int) avgRtt;
        txtLatencyValue.setText(strAvgRtt + "ms");
        txtLatencyTitle.setText("your latency to " + ipAddress + " is:");

        Log.v("TEST RESULT DOUBLE", strAvgRtt);
        Log.v("TEST RESULT INTEGER", String.valueOf(latency));

        return latency;
    }

    public int getLatencyURL(String url) throws MalformedURLException, UnknownHostException {
        String urlString = url;
        URL myURL = new URL(urlString);
        InetAddress address = InetAddress.getByName(myURL.getHost());
        String ipAddress = address.getHostAddress();
        String pingCommand = "/system/bin/ping -c " + 1 + " " + ipAddress;
        String inputLine = "";
        double avgRtt = 0;
        int latency = 0;

        try {
            Log.v("TEST", "getLatencyIP");
            // execute the command on the environment interface
            Process process = Runtime.getRuntime().exec(pingCommand);
            // gets the input stream to get the output of the executed command
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            inputLine = bufferedReader.readLine();
            while ((inputLine != null)) {
                if (inputLine.length() > 0 && inputLine.contains("avg")) {  // when we get to the last line of executed ping command
                    break;
                }
                inputLine = bufferedReader.readLine();
            }
        }
        catch (IOException e){
            Log.v("", "getLatency: EXCEPTION");
            e.printStackTrace();
        }

// Extracting the average round trip time from the inputLine string
        String afterEqual = inputLine.substring(inputLine.indexOf("="), inputLine.length()).trim();
        String afterFirstSlash = afterEqual.substring(afterEqual.indexOf('/') + 1, afterEqual.length()).trim();
        String strAvgRtt = afterFirstSlash.substring(0, afterFirstSlash.indexOf('/'));
        avgRtt = Double.valueOf(strAvgRtt);
        latency = (int) avgRtt;
        txtLatencyValue.setText(strAvgRtt + "ms");
        txtResultTitle.setText("your latency to " + ipAddress + " is:");

        Log.v("TEST RESULT DOUBLE", strAvgRtt);
        Log.v("TEST RESULT INTEGER", String.valueOf(latency));

        return latency;
    }



}
