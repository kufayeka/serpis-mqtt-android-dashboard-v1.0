package com.serpis.client;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.serpis.activity.MainActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MqttService extends Service {

    public static final String sistemKontrolModeTopic = "petra/iot/krpl/serpis/kontrol/mode";

    public static final String greenhouse1TemperaturTopic = "petra/iot/krpl/serpis/monitor/greenhouse1/temperatur";
    public static final String greenhouse1KelembapanTopic = "petra/iot/krpl/serpis/monitor/greenhouse1/kelembapan";
    public static final String greenhouse1Hidroponik1Topic = "petra/iot/krpl/serpis/monitor/greenhouse1/water1";
    public static final String greenhouse1Hidroponik2Topic = "petra/iot/krpl/serpis/monitor/greenhouse1/water2";
    public static final String greenhouse1Hidroponik3Topic = "petra/iot/krpl/serpis/monitor/greenhouse1/water3";
    public static final String greenhouse1Hidroponik4Topic = "petra/iot/krpl/serpis/monitor/greenhouse1/water4";
    public static final String greenhouse1Hidroponik5Topic = "petra/iot/krpl/serpis/monitor/greenhouse1/water5";
    public static final String greenhouse1Hidroponik6Topic = "petra/iot/krpl/serpis/monitor/greenhouse1/water6";
    public static final String greenhouse1SprayerTandonTopic = "petra/iot/krpl/serpis/monitor/greenhouse1/tandon_sprayer";
    public static final String greenhouse1SprayerMonitorTopic = "petra/iot/krpl/serpis/monitor/greenhouse1/sprayer_greenhouse1";
    public static final String greenhouse1SprayerKontrolTopic = "petra/iot/krpl/serpis/kontrol/greenhouse1/sprayer_greenhouse1";
    public static final String greenhouse1SprayerOnThresholdValueTopic = "petra/iot/krpl/serpis/kontrol/greenhouse1/sprayer_greenhouse1/on_thres_value";
    public static final String greenhouse1SprayerOffThresholdValueTopic = "petra/iot/krpl/serpis/kontrol/greenhouse1/sprayer_greenhouse1/off_thres_value";
    public static final String greenhouse1SprayerOnTimerTopic = "petra/iot/krpl/serpis/kontrol/greenhouse1/sprayer_greenhouse1/on_time_duration";
    public static final String greenhouse1SprayerOffTimerTopic = "petra/iot/krpl/serpis/kontrol/greenhouse1/sprayer_greenhouse1/off_time_duration";

    public static final String greenhouse2TemperaturTopic = "petra/iot/krpl/serpis/monitor/greenhouse2/temperatur";
    public static final String greenhouse2KelembapanTopic = "petra/iot/krpl/serpis/monitor/greenhouse2/kelembapan";
    public static final String greenhouse2Hidroponik1Topic = "petra/iot/krpl/serpis/monitor/greenhouse2/water1";
    public static final String greenhouse2Hidroponik2Topic = "petra/iot/krpl/serpis/monitor/greenhouse2/water2";
    public static final String greenhouse2Hidroponik3Topic = "petra/iot/krpl/serpis/monitor/greenhouse2/water3";
    public static final String greenhouse2Hidroponik4Topic = "petra/iot/krpl/serpis/monitor/greenhouse2/water4";
    public static final String greenhouse2SprayerTandonTopic = "petra/iot/krpl/serpis/monitor/greenhouse2/tandon_sprayer";
    public static final String greenhouse2SprayerMonitorTopic = "petra/iot/krpl/serpis/monitor/greenhouse2/sprayer_greenhouse2";
    public static final String greenhouse2SprayerKontrolTopic = "petra/iot/krpl/serpis/kontrol/greenhouse2/sprayer_greenhouse2";
    public static final String greenhouse2SprayerOnThresholdValueTopic = "petra/iot/krpl/serpis/kontrol/greenhouse2/sprayer_greenhouse2/on_thres_value";
    public static final String greenhouse2SprayerOffThresholdValueTopic = "petra/iot/krpl/serpis/kontrol/greenhouse2/sprayer_greenhouse2/off_thres_value";
    public static final String greenhouse2SprayerOnTimerTopic = "petra/iot/krpl/serpis/kontrol/greenhouse2/sprayer_greenhouse2/on_time_duration";
    public static final String greenhouse2SprayerOffTimerTopic = "petra/iot/krpl/serpis/kontrol/greenhouse2/sprayer_greenhouse2/off_time_duration";

    public static final String mediaTanahKelembapanTanah1Topic = "petra/iot/krpl/serpis/monitor/mediatanah/kelembapan_tanah1";
    public static final String mediaTanahKelembapanTanah2Topic = "petra/iot/krpl/serpis/monitor/mediatanah/kelembapan_tanah2";
    public static final String mediaTanahKelembapanTanah3Topic = "petra/iot/krpl/serpis/monitor/mediatanah/kelembapan_tanah3";
    public static final String mediaTanahKelembapanTanah4Topic = "petra/iot/krpl/serpis/monitor/mediatanah/kelembapan_tanah4";
    public static final String mediaTanahSprayerMonitorTopic = "petra/iot/krpl/serpis/monitor/mediatanah/sprayer_mediatanah";
    public static final String mediaTanahSprayerKontrolTopic = "petra/iot/krpl/serpis/kontrol/mediatanah/sprayer_mediatanah";
    public static final String mediaTanahSprayerOnThresholdValueTopic = "petra/iot/krpl/serpis/kontrol/mediatanah/sprayer_mediatanah/on_thres_value";
    public static final String mediaTanahSprayerOffThresholdValueTopic = "petra/iot/krpl/serpis/kontrol/mediatanah/sprayer_mediatanah/off_thres_value";
    public static final String mediaTanahSprayerTandonTopic = "petra/iot/krpl/serpis/monitor/mediatanah/tandon_sprayer";

    MqttAndroidClient client;
    byte[] encodedPayload = new byte[0];
    //public static final String serverURI = "tcp://broker.hivemq.com:1883";
    public static final String serverURI = "tcp://203.189.123.207:1883";
    public static final String clientId = MqttClient.generateClientId();
    int subscribeToTopicsAttempts = 0;

    public MqttService(Context context) {
        MqttConnectOptions options = new MqttConnectOptions();
        client = new MqttAndroidClient(context, serverURI, clientId);
        if (!client.isConnected()) {
            try {
                IMqttToken token = client.connect(options);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        // We are connected
                        Log.d("Status:", "onSuccess");
                        if (subscribeToTopicsAttempts <= 0) {
                            subscribeToTopic();
                            ((MainActivity) context).showConnectedNotificationBar();
                            subscribeToTopicsAttempts++;
                        } else {
                            //do nun.
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        // Something went wrong e.g. connection timeout or firewall problems
                        Log.d("Status:", "onFailure");
                        //Toast.makeText(context, "Gagal Terhubung:(", Toast.LENGTH_SHORT).show();
                        ((MainActivity) context).showDisconnectedNotificationBar();
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            // do nun
        }
    }

    public void setCallback (MqttCallbackExtended callback){
        client.setCallback(callback);
    }

    public void subscribeToTopic(){
        try {
            client.subscribe(sistemKontrolModeTopic, 0);

            client.subscribe(greenhouse1TemperaturTopic, 0);
            client.subscribe(greenhouse1KelembapanTopic, 0);
            client.subscribe(greenhouse1Hidroponik1Topic, 0);
            client.subscribe(greenhouse1Hidroponik2Topic, 0);
            client.subscribe(greenhouse1Hidroponik3Topic, 0);
            client.subscribe(greenhouse1Hidroponik4Topic, 0);
            client.subscribe(greenhouse1Hidroponik5Topic, 0);
            client.subscribe(greenhouse1Hidroponik6Topic, 0);
            client.subscribe(greenhouse1SprayerTandonTopic, 0);
            client.subscribe(greenhouse1SprayerMonitorTopic, 0);
            client.subscribe(greenhouse1SprayerKontrolTopic, 0);
            client.subscribe(greenhouse1SprayerOnThresholdValueTopic, 0);
            client.subscribe(greenhouse1SprayerOffThresholdValueTopic, 0);
            client.subscribe(greenhouse1SprayerOnTimerTopic, 0);
            client.subscribe(greenhouse1SprayerOffTimerTopic,0);

            client.subscribe(greenhouse2TemperaturTopic, 0);
            client.subscribe(greenhouse2KelembapanTopic, 0);
            client.subscribe(greenhouse2Hidroponik1Topic, 0);
            client.subscribe(greenhouse2Hidroponik2Topic, 0);
            client.subscribe(greenhouse2Hidroponik3Topic, 0);
            client.subscribe(greenhouse2Hidroponik4Topic, 0);
            client.subscribe(greenhouse2SprayerTandonTopic, 0);
            client.subscribe(greenhouse2SprayerMonitorTopic, 0);
            client.subscribe(greenhouse2SprayerKontrolTopic, 0);
            client.subscribe(greenhouse2SprayerOnThresholdValueTopic, 0);
            client.subscribe(greenhouse2SprayerOffThresholdValueTopic, 0);
            client.subscribe(greenhouse2SprayerOnTimerTopic, 0);
            client.subscribe(greenhouse2SprayerOffTimerTopic,0);

            client.subscribe(mediaTanahKelembapanTanah1Topic, 0);
            client.subscribe(mediaTanahKelembapanTanah2Topic, 0);
            client.subscribe(mediaTanahKelembapanTanah3Topic, 0);
            client.subscribe(mediaTanahKelembapanTanah4Topic, 0);
            client.subscribe(mediaTanahSprayerTandonTopic, 0);
            client.subscribe(mediaTanahSprayerMonitorTopic, 0);
            client.subscribe(mediaTanahSprayerKontrolTopic, 0);
            client.subscribe(mediaTanahSprayerOnThresholdValueTopic, 0);
            client.subscribe(mediaTanahSprayerOffThresholdValueTopic, 0);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publishRetainedMessage(String topic, String m, Context context)  {
        if (client.isConnected()) {
            try {
                encodedPayload = m.getBytes("UTF-8");
                MqttMessage message = new MqttMessage(encodedPayload);
                message.setRetained(true);
                client.publish(topic, message);
                Toast.makeText(context, "OK :)", Toast.LENGTH_SHORT).show();
            } catch (UnsupportedEncodingException | MqttException e) {
                e.printStackTrace();
                Toast.makeText(context, "Gagal :" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        if (!client.isConnected()){
            Toast.makeText(context, "Gagal :( \n\n Periksa jaringan internet!", Toast.LENGTH_SHORT).show();
        }
    }

    public void publishMessage(String topic, String m, Context context) {
        try {
            encodedPayload = m.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            message.setRetained(false);
            client.publish(topic, message);
            Toast.makeText(context, "OK :)", Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
            Toast.makeText(context, "Gagal :"+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
