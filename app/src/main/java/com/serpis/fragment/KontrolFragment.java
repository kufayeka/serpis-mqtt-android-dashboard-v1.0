package com.serpis.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.serpis.R;
import com.serpis.client.MqttService;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Objects;

import static com.serpis.client.MqttService.greenhouse1SprayerKontrolTopic;
import static com.serpis.client.MqttService.greenhouse1SprayerMonitorTopic;
import static com.serpis.client.MqttService.greenhouse1SprayerOffThresholdValueTopic;
import static com.serpis.client.MqttService.greenhouse1SprayerOffTimerTopic;
import static com.serpis.client.MqttService.greenhouse1SprayerOnThresholdValueTopic;
import static com.serpis.client.MqttService.greenhouse1SprayerOnTimerTopic;
import static com.serpis.client.MqttService.greenhouse1TemperaturTopic;
import static com.serpis.client.MqttService.greenhouse2SprayerKontrolTopic;
import static com.serpis.client.MqttService.greenhouse2SprayerMonitorTopic;
import static com.serpis.client.MqttService.greenhouse2SprayerOffThresholdValueTopic;
import static com.serpis.client.MqttService.greenhouse2SprayerOffTimerTopic;
import static com.serpis.client.MqttService.greenhouse2SprayerOnThresholdValueTopic;
import static com.serpis.client.MqttService.greenhouse2SprayerOnTimerTopic;
import static com.serpis.client.MqttService.mediaTanahSprayerKontrolTopic;
import static com.serpis.client.MqttService.mediaTanahSprayerMonitorTopic;
import static com.serpis.client.MqttService.mediaTanahSprayerOffThresholdValueTopic;
import static com.serpis.client.MqttService.mediaTanahSprayerOnThresholdValueTopic;
import static com.serpis.client.MqttService.sistemKontrolModeTopic;

public class KontrolFragment extends Fragment {

    TextView kontrolAutomatisOption;
    TextView kontrolManualOption;

    EditText kontrolEditMediaTanahSprayerOnThreshold;
    EditText kontrolEditMediaTanahSprayerOffThreshold;
    Button kontrolEditMediaTanahSprayerSimpanButton;

    EditText kontrolEditGreenhouse1SprayerOnThreshold;
    EditText kontrolEditGreenhouse1SprayerOffThreshold;
    EditText kontrolEditGreenhouse1SprayerOnTimer;
    EditText kontrolEditGreenhouse1SprayerOffTimer;
    Button kontrolEditGreenhouse1SprayerSimpanButton;

    EditText kontrolEditGreenhouse2SprayerOnThreshold;
    EditText kontrolEditGreenhouse2SprayerOffThreshold;
    EditText kontrolEditGreenhouse2SprayerOnTimer;
    EditText kontrolEditGreenhouse2SprayerOffTimer;
    Button kontrolEditGreenhouse2SprayerSimpanButton;

    View indicatorSprayerMediaTanah;
    View indicatorSprayerGreenhouse1;
    View indicatorSprayerGreenhouse2;

    Button kontrolSprayerMediaTanahSwitchOn;
    Button kontrolSprayerMediaTanahSwitchOff;

    Button kontrolSprayerGreenhouse1SwitchOn;
    Button kontrolSprayerGreenhouse1SwitchOff;

    Button kontrolSprayerGreenhouse2SwitchOn;
    Button kontrolSprayerGreenhouse2SwitchOff;

    LinearLayout kontrolManualView;
    LinearLayout kontrolAutomatisView;

    String mediaTanahSprayerOnThresholdValue;
    String mediaTanahSprayerOffThresholdValue;

    String greenhouse1SprayerOnThresholdValue;
    String greenhouse1SprayerOffThresholdValue;
    String greenhouse1SprayerOnTimerValue;
    String greenhouse1SprayerOffTimerValue;

    String greenhouse2SprayerOnThresholdValue;
    String greenhouse2SprayerOffThresholdValue;
    String greenhouse2SprayerOnTimerValue;
    String greenhouse2SprayerOffTimerValue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kontrol, container, false);

        kontrolAutomatisOption = (TextView) view.findViewById(R.id.kontrolAutomatisText);
        kontrolManualOption = (TextView) view.findViewById(R.id.kontrolManualText);

        kontrolManualView = (LinearLayout) view.findViewById(R.id.kontrolManualView);
        kontrolAutomatisView = (LinearLayout) view.findViewById(R.id.kontrolAutomatisView);

        kontrolEditMediaTanahSprayerOnThreshold = (EditText) view.findViewById(R.id.kontrolEditMediaTanahSprayerOnThreshold);
        kontrolEditMediaTanahSprayerOffThreshold = (EditText) view.findViewById(R.id.kontrolEditMediaTanahSprayerOffThreshold);
        kontrolEditMediaTanahSprayerSimpanButton = (Button) view.findViewById(R.id.kontrolEditMediaTanahSprayerSimpanButton);

        kontrolEditGreenhouse1SprayerOnThreshold = (EditText) view.findViewById(R.id.kontrolEditGreenhouse1SprayerOnThreshold);
        kontrolEditGreenhouse1SprayerOffThreshold = (EditText) view.findViewById(R.id.kontrolEditGreenhouse1SprayerOffThreshold);
        kontrolEditGreenhouse1SprayerOnTimer = (EditText) view.findViewById(R.id.kontrolEditGreenhouse1SprayerOnTimer);
        kontrolEditGreenhouse1SprayerOffTimer = (EditText) view.findViewById(R.id.kontrolEditGreenhouse1SprayerOffTimer);
        kontrolEditGreenhouse1SprayerSimpanButton = (Button) view.findViewById(R.id.kontrolEditGreenhouse1SprayerSimpanButton);

        kontrolEditGreenhouse2SprayerOnThreshold = (EditText) view.findViewById(R.id.kontrolEditGreenhouse2SprayerOnThreshold);
        kontrolEditGreenhouse2SprayerOffThreshold = (EditText) view.findViewById(R.id.kontrolEditGreenhouse2SprayerOffThreshold);
        kontrolEditGreenhouse2SprayerOnTimer = (EditText) view.findViewById(R.id.kontrolEditGreenhouse2SprayerOnTimer);
        kontrolEditGreenhouse2SprayerOffTimer = (EditText) view.findViewById(R.id.kontrolEditGreenhouse2SprayerOffTimer);
        kontrolEditGreenhouse2SprayerSimpanButton = (Button) view.findViewById(R.id.kontrolEditGreenhouse2SprayerSimpanButton);

        kontrolSprayerMediaTanahSwitchOn = (Button) view.findViewById(R.id.kontrolSprayerMediaTanahSwitchOn);
        kontrolSprayerMediaTanahSwitchOff = (Button) view.findViewById(R.id.kontrolSprayerMediaTanahSwitchOff);

        kontrolSprayerGreenhouse1SwitchOn = (Button) view.findViewById(R.id.kontrolSprayerGreenhouse1SwitchOn);
        kontrolSprayerGreenhouse1SwitchOff = (Button) view.findViewById(R.id.kontrolSprayerGreenhouse1SwitchOff);

        kontrolSprayerGreenhouse2SwitchOn = (Button) view.findViewById(R.id.kontrolSprayerGreenhouse2SwitchOn);
        kontrolSprayerGreenhouse2SwitchOff = (Button) view.findViewById(R.id.kontrolSprayerGreenhouse2SwitchOff);

        indicatorSprayerMediaTanah = (View) view.findViewById(R.id.kontrolSprayerMediaTanahIndicator);
        indicatorSprayerGreenhouse1 = (View) view.findViewById(R.id.kontrolSprayerGreenhouse1Indicator);
        indicatorSprayerGreenhouse2 = (View) view.findViewById(R.id.kontrolSprayerGreenhouse2Indicator);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();

        MqttService mqttService = new MqttService(getContext());

        kontrolAutomatisOption.getBackground().setAlpha(255);
        kontrolManualOption.getBackground().setAlpha(180);

        kontrolAutomatisOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setEsp32ControlMode
                setKontrolAutomatisView();
                mqttService.publishRetainedMessage(sistemKontrolModeTopic, "Automatis", getContext());
                Toast.makeText(getContext(), "Mode kontrol diubah ke Automatis", Toast.LENGTH_SHORT).show();
            }
        });
        kontrolManualOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setEsp32ControlMode
                setKontrolManualView();
                mqttService.publishRetainedMessage(sistemKontrolModeTopic, "Manual", getContext());
                Toast.makeText(getContext(), "Mode kontrol diubah ke Manual", Toast.LENGTH_SHORT).show();
            }
        });

        kontrolEditMediaTanahSprayerSimpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaTanahSprayerOnThresholdValue = kontrolEditMediaTanahSprayerOnThreshold.getText().toString();
                mediaTanahSprayerOffThresholdValue = kontrolEditMediaTanahSprayerOffThreshold.getText().toString();
                mqttService.publishRetainedMessage(mediaTanahSprayerOnThresholdValueTopic, mediaTanahSprayerOnThresholdValue, getContext());
                mqttService.publishRetainedMessage(mediaTanahSprayerOffThresholdValueTopic, mediaTanahSprayerOffThresholdValue, getContext());
            }
        });
        kontrolEditGreenhouse1SprayerSimpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greenhouse1SprayerOnThresholdValue = kontrolEditGreenhouse1SprayerOnThreshold.getText().toString();
                greenhouse1SprayerOffThresholdValue = kontrolEditGreenhouse1SprayerOffThreshold.getText().toString();
                greenhouse1SprayerOnTimerValue = kontrolEditGreenhouse1SprayerOnTimer.getText().toString();
                greenhouse1SprayerOffTimerValue = kontrolEditGreenhouse1SprayerOffTimer.getText().toString();
                mqttService.publishRetainedMessage(greenhouse1SprayerOnThresholdValueTopic, greenhouse1SprayerOnThresholdValue, getContext());
                mqttService.publishRetainedMessage(greenhouse1SprayerOffThresholdValueTopic, greenhouse1SprayerOffThresholdValue, getContext());
                mqttService.publishRetainedMessage(greenhouse1SprayerOnTimerTopic, greenhouse1SprayerOnTimerValue, getContext());
                mqttService.publishRetainedMessage(greenhouse1SprayerOffTimerTopic, greenhouse1SprayerOffTimerValue, getContext());
            }
        });
        kontrolEditGreenhouse2SprayerSimpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greenhouse2SprayerOnThresholdValue = kontrolEditGreenhouse2SprayerOnThreshold.getText().toString();
                greenhouse2SprayerOffThresholdValue = kontrolEditGreenhouse2SprayerOffThreshold.getText().toString();
                greenhouse2SprayerOnTimerValue = kontrolEditGreenhouse2SprayerOnTimer.getText().toString();
                greenhouse2SprayerOffTimerValue = kontrolEditGreenhouse2SprayerOffTimer.getText().toString();
                mqttService.publishRetainedMessage(greenhouse2SprayerOnThresholdValueTopic, greenhouse2SprayerOnThresholdValue, getContext());
                mqttService.publishRetainedMessage(greenhouse2SprayerOffThresholdValueTopic, greenhouse2SprayerOffThresholdValue, getContext());
                mqttService.publishRetainedMessage(greenhouse2SprayerOnTimerTopic, greenhouse2SprayerOnTimerValue, getContext());
                mqttService.publishRetainedMessage(greenhouse2SprayerOffTimerTopic, greenhouse2SprayerOffTimerValue, getContext());
            }
        });

        kontrolSprayerMediaTanahSwitchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttService.publishRetainedMessage(mediaTanahSprayerKontrolTopic, "ON", getContext());
            }
        });
        kontrolSprayerMediaTanahSwitchOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttService.publishRetainedMessage(mediaTanahSprayerKontrolTopic, "OFF", getContext());
            }
        });

        kontrolSprayerGreenhouse1SwitchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttService.publishRetainedMessage(greenhouse1SprayerKontrolTopic, "ON", getContext());
            }
        });
        kontrolSprayerGreenhouse1SwitchOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttService.publishRetainedMessage(greenhouse1SprayerKontrolTopic, "OFF", getContext());
            }
        });

        kontrolSprayerGreenhouse2SwitchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttService.publishRetainedMessage(greenhouse2SprayerKontrolTopic, "ON", getContext());
            }
        });
        kontrolSprayerGreenhouse2SwitchOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttService.publishRetainedMessage(greenhouse2SprayerKontrolTopic, "OFF", getContext());
            }
        });

        mqttService.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {
                Toast.makeText(getContext(), Objects.requireNonNull(cause.getCause()).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                switch (topic) {
                    case sistemKontrolModeTopic:
                        if (message.toString().matches("Automatis")){setKontrolAutomatisView();}
                        else if (message.toString().matches("Manual")){setKontrolManualView();}
                        break;
                    case mediaTanahSprayerOnThresholdValueTopic:
                        kontrolEditMediaTanahSprayerOnThreshold.setText(message.toString());
                        break;
                    case mediaTanahSprayerOffThresholdValueTopic:
                        kontrolEditMediaTanahSprayerOffThreshold.setText(message.toString());
                        break;
                         //----------------------------------------------------------------------------------//
                    case greenhouse1SprayerOnThresholdValueTopic:
                        kontrolEditGreenhouse1SprayerOnThreshold.setText(message.toString());
                        break;
                    case greenhouse1SprayerOffThresholdValueTopic:
                        kontrolEditGreenhouse1SprayerOffThreshold.setText(message.toString());
                        break;
                    case greenhouse1SprayerOnTimerTopic:
                        kontrolEditGreenhouse1SprayerOnTimer.setText(message.toString());
                        break;
                    case greenhouse1SprayerOffTimerTopic:
                        kontrolEditGreenhouse1SprayerOffTimer.setText(message.toString());
                        break;
                        //--------------------------------------------------------------------------------------------//
                    case greenhouse2SprayerOnThresholdValueTopic:
                        kontrolEditGreenhouse2SprayerOnThreshold.setText(message.toString());
                        break;
                    case greenhouse2SprayerOffThresholdValueTopic:
                        kontrolEditGreenhouse2SprayerOffThreshold.setText(message.toString());
                        break;
                    case greenhouse2SprayerOnTimerTopic:
                        kontrolEditGreenhouse2SprayerOnTimer.setText(message.toString());
                        break;
                    case greenhouse2SprayerOffTimerTopic:
                        kontrolEditGreenhouse2SprayerOffTimer.setText(message.toString());
                        break;
                        //---------------------------------------------------------------------------------------//
                    case greenhouse1SprayerMonitorTopic:
                        if (message.toString().matches("ON")){
                            indicatorSprayerGreenhouse1.getBackground().setColorFilter(Color.parseColor("#23A626"), PorterDuff.Mode.SRC_IN);
                        } else if (message.toString().matches("OFF")){
                            indicatorSprayerGreenhouse1.getBackground().setColorFilter(Color.parseColor("#E91E1E"), PorterDuff.Mode.SRC_IN);
                        }
                        break;
                    case greenhouse2SprayerMonitorTopic:
                        if (message.toString().matches("ON")){
                            indicatorSprayerGreenhouse2.getBackground().setColorFilter(Color.parseColor("#23A626"), PorterDuff.Mode.SRC_IN);
                        } else if (message.toString().matches("OFF")){
                            indicatorSprayerGreenhouse2.getBackground().setColorFilter(Color.parseColor("#E91E1E"), PorterDuff.Mode.SRC_IN);
                        }
                        break;
                    case mediaTanahSprayerMonitorTopic:
                        if (message.toString().matches("ON")){
                            indicatorSprayerMediaTanah.getBackground().setColorFilter(Color.parseColor("#23A626"), PorterDuff.Mode.SRC_IN);
                        } else if (message.toString().matches("OFF")){
                            indicatorSprayerMediaTanah.getBackground().setColorFilter(Color.parseColor("#E91E1E"), PorterDuff.Mode.SRC_IN);
                        }
                        break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public void setKontrolManualView(){
        kontrolAutomatisOption.getBackground().setAlpha(180);
        kontrolManualOption.getBackground().setAlpha(255);
        //switch view visibilty
        kontrolAutomatisView.setVisibility(View.GONE);
        kontrolManualView.setVisibility(View.VISIBLE);
        kontrolAutomatisOption.setEnabled(true);
        kontrolManualOption.setEnabled(false);
    }

    public void setKontrolAutomatisView(){
        kontrolAutomatisOption.getBackground().setAlpha(255);
        kontrolManualOption.getBackground().setAlpha(180);
        //switch view visibilty
        kontrolAutomatisView.setVisibility(View.VISIBLE);
        kontrolManualView.setVisibility(View.GONE);
        kontrolAutomatisOption.setEnabled(false);
        kontrolManualOption.setEnabled(true);
    }
}