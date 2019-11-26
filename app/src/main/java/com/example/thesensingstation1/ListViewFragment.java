package com.example.thesensingstation1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListViewFragment extends Fragment {
    private static final String TAG = "ListViewFragment";
    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private ListView listView;
    private ArrayList<String> arrayList;
    private ShowSensorFragment sensorFragment;

    public ListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        arrayList = new ArrayList<>();
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        listView = view.findViewById(R.id.listView1);
        for (int i = 0; i < sensorList.size(); i++) {
            arrayList.add(sensorList.get(i).getName());
            Log.d(TAG, "onCreateView: " + sensorList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListListener());
        return view;
    }

    private class ListListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Sensor sensor = sensorList.get(position);
            FragmentManager fragmentManager = getFragmentManager();
            sensorFragment = new ShowSensorFragment(sensor);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sensorFragment).addToBackStack(null).commit();
        }
    }
}
