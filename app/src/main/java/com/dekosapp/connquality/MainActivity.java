package com.dekosapp.connquality;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private TextView targetIP;
    private int targetPort;
    private TextView txtUsersResult;
    private TextView txtResultTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        targetIP = (EditText)findViewById(R.id.targetIP);
      //  targetPort = (Edi)findViewById(R.id.targetPort);
        txtUsersResult = (TextView)findViewById(R.id.txtUsers);
        txtResultTitle = (TextView)findViewById(R.id.txtResultTitle);
        // targetPort.setText("");
        targetIP.setText("");
        }

    private final byte[] userHandShake = new byte[]{(byte) 0xA6, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x09, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

    public void onClickGrabUsers(View v) {
        String ip = targetIP.getText().toString();
        int port = targetPort;
        Log.v("TAG", ip);
        Log.v("TAG", Integer.toString(port));
        if (ip.equals("")) {
            Toast.makeText(this.getApplicationContext(), "You cannot leave the input empty !",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            getUsers(ip, port);
        }
    }

    public void getUsers(String targetIP , int targetPort) {
        try {
            Socket sock = new Socket(targetIP,targetPort);
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
            String inputLine = "";
            dataOutputStream.write(userHandShake);
            Log.v("TEST", "getUsers");
            txtUsersResult.setText(dataOutputStream + "ms");
            txtResultTitle.setText("Users for " + targetIP + " are:");
            Log.v("TEST RESULT DOUBLE", dataOutputStream.toString());
        } catch (IOException e){
            Log.v("", "getLatency: EXCEPTION");
            e.printStackTrace();
        }


    }



}
