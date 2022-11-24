package com.serpis.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.serpis.R;
import com.serpis.activity.MainActivity;
import com.serpis.client.MqttService;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static com.serpis.client.MqttService.greenhouse1Hidroponik1Topic;
import static com.serpis.client.MqttService.greenhouse1Hidroponik2Topic;
import static com.serpis.client.MqttService.greenhouse1Hidroponik3Topic;
import static com.serpis.client.MqttService.greenhouse1Hidroponik4Topic;
import static com.serpis.client.MqttService.greenhouse1Hidroponik5Topic;
import static com.serpis.client.MqttService.greenhouse1Hidroponik6Topic;
import static com.serpis.client.MqttService.greenhouse1KelembapanTopic;
import static com.serpis.client.MqttService.greenhouse1SprayerKontrolTopic;
import static com.serpis.client.MqttService.greenhouse1SprayerMonitorTopic;
import static com.serpis.client.MqttService.greenhouse1SprayerTandonTopic;
import static com.serpis.client.MqttService.greenhouse1TemperaturTopic;

public class Greenhouse1Fragment extends Fragment {

    View greenhouse1BackButton;
    TextView greenhouse1TemperatureText;
    TextView greenhouse1KelembapanText;
    TextView greenhouse1Hidroponik1Text;
    TextView greenhouse1Hidroponik2Text;
    TextView greenhouse1Hidroponik3Text;
    TextView greenhouse1Hidroponik4Text;
    TextView greenhouse1Hidroponik5Text;
    TextView greenhouse1Hidroponik6Text;
    TextView greenhouse1HidroponikSprayerWaterMonitorText;
    TextView greenhouse1HidroponikSprayerStatusText;
    String result = "";

    public Greenhouse1Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_greenhouse1, container, false);

        greenhouse1BackButton = (View) view.findViewById(R.id.greehouse1BackButton);
        greenhouse1TemperatureText = (TextView) view.findViewById(R.id.greenhouse1TemperaturValue);
        greenhouse1KelembapanText = (TextView) view.findViewById(R.id.greenhouse1KelembapanValue);
        greenhouse1Hidroponik1Text = (TextView) view.findViewById(R.id.greenhouse1AirHidroponik1);
        greenhouse1Hidroponik2Text = (TextView) view.findViewById(R.id.greenhouse1AirHidroponik2);
        greenhouse1Hidroponik3Text = (TextView) view.findViewById(R.id.greenhouse1AirHidroponik3);
        greenhouse1Hidroponik4Text = (TextView) view.findViewById(R.id.greenhouse1AirHidroponik4);
        greenhouse1Hidroponik5Text = (TextView) view.findViewById(R.id.greenhouse1AirHidroponik5);
        greenhouse1Hidroponik6Text = (TextView) view.findViewById(R.id.greenhouse1AirHidroponik6);
        greenhouse1HidroponikSprayerWaterMonitorText = (TextView) view.findViewById(R.id.greenhouse1ReservoirStatus);
        greenhouse1HidroponikSprayerStatusText = (TextView) view.findViewById(R.id.greenhouse1SprayerStatus);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        greenhouse1BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).backToHomeFragment();
            }
        });

        MqttService mqttService = new MqttService(getContext());
        //startRepeatingTask();
        mqttService.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) { }

            @Override
            public void connectionLost(Throwable cause) {
                Toast.makeText(getContext(), Objects.requireNonNull(cause.getCause()).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                switch (topic) {
                    case greenhouse1TemperaturTopic:
                        greenhouse1TemperatureText.setText(message.toString()+"°C");
                        break;
                    case greenhouse1KelembapanTopic:
                        greenhouse1KelembapanText.setText(message.toString()+"%");
                        break;
                    case greenhouse1Hidroponik1Topic:
                        greenhouse1Hidroponik1Text.setText(message.toString());
                        break;
                    case greenhouse1Hidroponik2Topic:
                        greenhouse1Hidroponik2Text.setText(message.toString());
                        break;
                    case greenhouse1Hidroponik3Topic:
                        greenhouse1Hidroponik3Text.setText(message.toString());
                        break;
                    case greenhouse1Hidroponik4Topic:
                        greenhouse1Hidroponik4Text.setText(message.toString());
                        break;
                    case greenhouse1Hidroponik5Topic:
                        greenhouse1Hidroponik5Text.setText(message.toString());
                        break;
                    case greenhouse1Hidroponik6Topic:
                        greenhouse1Hidroponik6Text.setText(message.toString());
                        break;
                    case greenhouse1SprayerTandonTopic:
                        greenhouse1HidroponikSprayerWaterMonitorText.setText(message.toString());
                        break;
                    case greenhouse1SprayerMonitorTopic:
                        greenhouse1HidroponikSprayerStatusText.setText(message.toString());
                        break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) { }
        });
    }
}