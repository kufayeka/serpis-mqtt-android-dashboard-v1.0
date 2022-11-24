package com.serpis.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import java.util.Objects;

import static com.serpis.client.MqttService.greenhouse2Hidroponik1Topic;
import static com.serpis.client.MqttService.greenhouse2Hidroponik2Topic;
import static com.serpis.client.MqttService.greenhouse2Hidroponik3Topic;
import static com.serpis.client.MqttService.greenhouse2Hidroponik4Topic;
import static com.serpis.client.MqttService.greenhouse2KelembapanTopic;
import static com.serpis.client.MqttService.greenhouse2SprayerKontrolTopic;
import static com.serpis.client.MqttService.greenhouse2SprayerMonitorTopic;
import static com.serpis.client.MqttService.greenhouse2SprayerTandonTopic;
import static com.serpis.client.MqttService.greenhouse2TemperaturTopic;

public class Greenhouse2Fragment extends Fragment {

    View greenhouse2BackButton;
    TextView greenhouse2TemperatureText;
    TextView greenhouse2KelembapanText;
    TextView greenhouse2Hidroponik1Text;
    TextView greenhouse2Hidroponik2Text;
    TextView greenhouse2Hidroponik3Text;
    TextView greenhouse2Hidroponik4Text;
    TextView greenhouse2HidroponikSprayerWaterMonitorText;
    TextView greenhouse2HidroponikSprayerStatusText;

    public Greenhouse2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_greenhouse2, container, false);

        greenhouse2BackButton = (View) view.findViewById(R.id.greehouse2BackButton);
        greenhouse2TemperatureText = (TextView) view.findViewById(R.id.greenhouse2TemperaturValue);
        greenhouse2KelembapanText = (TextView) view.findViewById(R.id.greenhouse2KelembapanValue);
        greenhouse2Hidroponik1Text = (TextView) view.findViewById(R.id.greenhouse2AirHidroponik1);
        greenhouse2Hidroponik2Text = (TextView) view.findViewById(R.id.greenhouse2AirHidroponik2);
        greenhouse2Hidroponik3Text = (TextView) view.findViewById(R.id.greenhouse2AirHidroponik3);
        greenhouse2Hidroponik4Text = (TextView) view.findViewById(R.id.greenhouse2AirHidroponik4);
        greenhouse2HidroponikSprayerWaterMonitorText = (TextView) view.findViewById(R.id.greenhouse2ReservoirStatus);
        greenhouse2HidroponikSprayerStatusText = (TextView) view.findViewById(R.id.greenhouse2SprayerStatus);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        greenhouse2BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).backToHomeFragment();
            }
        });

        MqttService mqttService = new MqttService(getContext());
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
                    case greenhouse2TemperaturTopic:
                        greenhouse2TemperatureText.setText(message.toString()+"°C");
                        break;
                    case greenhouse2KelembapanTopic:
                        greenhouse2KelembapanText.setText(message.toString()+"%");
                        break;
                    case greenhouse2Hidroponik1Topic:
                        greenhouse2Hidroponik1Text.setText(message.toString());
                        break;
                    case greenhouse2Hidroponik2Topic:
                        greenhouse2Hidroponik2Text.setText(message.toString());
                        break;
                    case greenhouse2Hidroponik3Topic:
                        greenhouse2Hidroponik3Text.setText(message.toString());
                        break;
                    case greenhouse2Hidroponik4Topic:
                        greenhouse2Hidroponik4Text.setText(message.toString());
                        break;
                    case greenhouse2SprayerTandonTopic:
                        greenhouse2HidroponikSprayerWaterMonitorText.setText(message.toString());
                        break;
                    case greenhouse2SprayerMonitorTopic:
                        greenhouse2HidroponikSprayerStatusText.setText(message.toString());
                        break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) { }
        });
    }
}