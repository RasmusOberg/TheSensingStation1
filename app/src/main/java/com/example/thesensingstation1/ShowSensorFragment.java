package com.example.thesensingstation1;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowSensorFragment extends Fragment {
    private Sensor sensor;
    private TextView textView, tvProperties, tvValues, tvAccuracy;
    private Button buttonStart, buttonStop;
    private SensorManager sensorManager;
    private SensorListenerCustom listenerCustom;

    public ShowSensorFragment(Sensor sensor)  {
        this.sensor = sensor;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_sensor, container, false);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view){
        textView  = view.findViewById(R.id.textView);
        tvProperties = view.findViewById(R.id.tvProperties);
        tvValues = view.findViewById(R.id.tvValues);
        tvAccuracy = view.findViewById(R.id.tvAccuracy);
        buttonStart = view.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerCustom =  new SensorListenerCustom();
                sensorManager.registerListener(listenerCustom, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });
        buttonStop = view.findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.unregisterListener(listenerCustom);
            }
        });
        textView.setText(sensor.getName());
    }

    private class SensorListenerCustom implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //Log.d(TAG, "onSensorChanged: Accuracy = " + event.accuracy);
            String prop = "Sensor = " + sensor.getName() + "\n"
                    + "MaxRange = " + sensor.getMaximumRange() + "\n"
                    + "MinDelay = " + sensor.getMinDelay() + "\n"
                    + "MaxDelay = " + sensor.getMaxDelay() + "\n"
                    + "Power = " + sensor.getPower() + "\n"
                    + "Resolution = " + sensor.getResolution();
            tvProperties.setText(prop);

            String values = "Values: ";
             for(int i = 0; i < event.values.length; i++){
                values += event.values[i] + ", \n";
             }

            tvValues.setText(values);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            String acc = "Accuracy: " + accuracy;
            tvAccuracy.setText(acc);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerCustom);
    }


}
