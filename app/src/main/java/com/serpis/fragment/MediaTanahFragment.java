package com.serpis.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.serpis.R;
import com.serpis.activity.MainActivity;
import com.serpis.client.MqttService;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Objects;

import static com.serpis.client.MqttService.mediaTanahKelembapanTanah1Topic;
import static com.serpis.client.MqttService.mediaTanahKelembapanTanah2Topic;
import static com.serpis.client.MqttService.mediaTanahKelembapanTanah3Topic;
import static com.serpis.client.MqttService.mediaTanahKelembapanTanah4Topic;
import static com.serpis.client.MqttService.mediaTanahSprayerKontrolTopic;
import static com.serpis.client.MqttService.mediaTanahSprayerMonitorTopic;
import static com.serpis.client.MqttService.mediaTanahSprayerTandonTopic;

public class MediaTanahFragment extends Fragment {

    View mediaTanahBackButton;
    TextView mediaTanahKelembapanTanah1Text;
    TextView mediaTanahKelembapanTanah2Text;
    TextView mediaTanahKelembapanTanah3Text;
    TextView mediaTanahKelembapanTanah4Text;
    TextView mediaTanahSprayerWaterMonitorText;
    TextView mediaTanahSprayerStatusText;
    TextView mediaTanahSoilAverageText;

    int mediaTanahSoil1 = 0;
    int mediaTanahSoil2 = 0;
    int mediaTanahSoil3 = 0;
    int mediaTanahSoil4 = 0;

    public MediaTanahFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mediatanah, container, false);

        mediaTanahBackButton = (View) view.findViewById(R.id.mediaTanahBackButton);
        mediaTanahKelembapanTanah1Text = (TextView) view.findViewById(R.id.mediaTanahMediaTanah1SoilValue);
        mediaTanahKelembapanTanah2Text = (TextView) view.findViewById(R.id.mediaTanahMediaTanah2SoilValue);
        mediaTanahKelembapanTanah3Text = (TextView) view.findViewById(R.id.mediaTanahMediaTanah3SoilValue);
        mediaTanahKelembapanTanah4Text = (TextView) view.findViewById(R.id.mediaTanahMediaTanah4SoilValue);
        mediaTanahSprayerWaterMonitorText = (TextView) view.findViewById(R.id.mediaTanahReservoirStatus);
        mediaTanahSprayerStatusText = (TextView) view.findViewById(R.id.mediaTanahSprayerStatus);
        mediaTanahSoilAverageText = (TextView) view.findViewById(R.id.mediaTanahSoilAverage);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        mediaTanahBackButton.setOnClickListener(new View.OnClickListener() {
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
                    case mediaTanahKelembapanTanah1Topic:
                        mediaTanahKelembapanTanah1Text.setText(message.toString()+"%");
                        mediaTanahSoil1 = Integer.parseInt(message.toString());
                        break;
                    case mediaTanahKelembapanTanah2Topic:
                        mediaTanahKelembapanTanah2Text.setText(message.toString()+"%");
                        mediaTanahSoil2 = Integer.parseInt(message.toString());
                        break;
                    case mediaTanahKelembapanTanah3Topic:
                        mediaTanahKelembapanTanah3Text.setText(message.toString()+"%");
                        mediaTanahSoil3 = Integer.parseInt(message.toString());
                        break;
                    case mediaTanahKelembapanTanah4Topic:
                        mediaTanahKelembapanTanah4Text.setText(message.toString()+"%");
                        mediaTanahSoil4 = Integer.parseInt(message.toString());
                        mediaTanahSoilAverageText.setText(
                                (mediaTanahSoil1+
                                mediaTanahSoil2+
                                mediaTanahSoil3+
                                mediaTanahSoil4)/4
                                        + "%");
                        break;
                    case mediaTanahSprayerTandonTopic:
                        mediaTanahSprayerWaterMonitorText.setText(message.toString());
                        break;
                    case mediaTanahSprayerMonitorTopic:
                        mediaTanahSprayerStatusText.setText(message.toString());
                        break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) { }
        });
    }
}