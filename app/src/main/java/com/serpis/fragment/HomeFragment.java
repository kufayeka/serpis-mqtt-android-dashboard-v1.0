package com.serpis.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.serpis.R;
import com.serpis.activity.MainActivity;

import com.serpis.client.MqttService;

public class HomeFragment extends Fragment implements View.OnClickListener {

    MaterialCardView homeMediaTanahWidget;
    MaterialCardView homeGreenhouse1Widget;
    MaterialCardView homeGreenhouse2Widget;
    MaterialCardView homeKrplSerpisTitleRounded;

    NestedScrollView homeScrollbar;
    SwipeRefreshLayout homeSwipeRefresh;
    TextView homeTesText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeTesText = (TextView) view.findViewById(R.id.homeTesText);
        homeMediaTanahWidget = (MaterialCardView) view.findViewById(R.id.homeMediaTanamWidget); homeMediaTanahWidget.setOnClickListener(this);
        homeGreenhouse1Widget = (MaterialCardView) view.findViewById(R.id.homeGreenhouse1Widget); homeGreenhouse1Widget.setOnClickListener(this);
        homeGreenhouse2Widget = (MaterialCardView) view.findViewById(R.id.homeGreenhouse2Widget); homeGreenhouse2Widget.setOnClickListener(this);
        homeSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.homeSwipeRefresh);
        homeScrollbar = (NestedScrollView) view.findViewById(R.id.homeScrollbar);
        homeKrplSerpisTitleRounded = (MaterialCardView) view.findViewById(R.id.homeKrplSerpisTitleRounded);

        /* notifications definetly not needed atm :)
        recyclerView = (RecyclerView) view.findViewById(R.id.homeNotifikasiView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new homeNotifikasiAdapter(getContext(), listNotifikasi);
        recyclerView.setAdapter(adapter);

        homeCollapsedHomeBar();
        homeHorizontalScrollViewIndicator();
        */

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        MqttService mqttService = new MqttService(getContext());
        mqttService.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) { }

            @Override
            public void connectionLost(Throwable cause) {
                Toast.makeText(getContext(), Objects.requireNonNull(cause.getCause()).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                switch (topic) {
                    case greenhouse1Hidroponik1Topic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Hidroponik 1 (Greenhouse 1) : Habis");
                        }else{};
                        break;
                    case greenhouse1Hidroponik2Topic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Hidroponik 2 (Greenhouse 1) : Habis");
                        }else{};
                        break;
                    case greenhouse1Hidroponik3Topic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Hidroponik 3 (Greenhouse 1) : Habis");
                        }else{};
                        break;
                    case greenhouse1Hidroponik4Topic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Hidroponik 4 (Greenhouse 1) : Habis");
                        }else{};
                        break;
                    case greenhouse1Hidroponik5Topic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Hidroponik 5 (Greenhouse 1) : Habis");
                        }else{};
                        break;
                    case greenhouse1Hidroponik6Topic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Hidroponik 6 (Greenhouse 1) : Habis");
                        }else{};
                        break;
                    case greenhouse1SprayerMonitorTopic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Sprayer (Greenhouse 1) : Habis");
                        }else{};
                        break;

                    //----------------------------------------------------------------------------------------------------------------------//
                    case greenhouse2Hidroponik1Topic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Hidroponik 1 (Greenhouse 2) : Habis");
                        }else{};
                        break;
                    case greenhouse2Hidroponik2Topic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Hidroponik 2 (Greenhouse 2) : Habis");
                        }else{};
                        break;
                    case greenhouse2Hidroponik3Topic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Hidroponik 3 (Greenhouse 2) : Habis");
                        }else{};
                        break;
                    case greenhouse2Hidroponik4Topic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Hidroponik 4 (Greenhouse 2) : Habis");
                        }else{};
                        break;
                    case greenhouse2SprayerMonitorTopic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Sprayer (Greenhouse 2) : Habis");
                        }else{};
                        break;

                    //----------------------------------------------------------------------------------------------------------------------//
                    case mediaTanahSprayerMonitorTopic:
                        if (message.toString().matches("Habis")){addNotifikasi("Bak Sprayer (Media Tanah) : Habis");
                        }else{};
                        break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

         */

    }

    @Override
    public void onResume() {
        super.onResume();
        MqttService mqttService = new MqttService(getContext());
        homeSwipeRefresh();
    }

    /*
    public void addNotifikasi(String x){
            adapter = new homeNotifikasiAdapter(getContext(), listNotifikasi);
            listNotifikasi.add(x);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            adapter.setHasStableIds(true);
    }

    private void homeHorizontalScrollViewIndicator(){
        homeTanamanScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = homeTanamanScrollView.getScrollX();
                Log.w("X:", String.valueOf(scrollX));
                scrollX = 0;
                if (scrollX == 0){
                    homeDotsIndicator1.getBackground().setColorFilter(Color.parseColor("#277D38"), PorterDuff.Mode.SRC_IN);
                    homeDotsIndicator2.getBackground().setColorFilter(Color.parseColor("#A1A5A4"), PorterDuff.Mode.SRC_IN);
                    homeDotsIndicator3.getBackground().setColorFilter(Color.parseColor("#A1A5A4"), PorterDuff.Mode.SRC_IN);
                } else if (scrollX <= 240){
                    homeDotsIndicator1.getBackground().setColorFilter(Color.parseColor("#A1A5A4"), PorterDuff.Mode.SRC_IN);
                    homeDotsIndicator2.getBackground().setColorFilter(Color.parseColor("#277D38"), PorterDuff.Mode.SRC_IN);
                    homeDotsIndicator3.getBackground().setColorFilter(Color.parseColor("#A1A5A4"), PorterDuff.Mode.SRC_IN);
                } else if (scrollX > 400){
                    homeDotsIndicator1.getBackground().setColorFilter(Color.parseColor("#A1A5A4"), PorterDuff.Mode.SRC_IN);
                    homeDotsIndicator2.getBackground().setColorFilter(Color.parseColor("#A1A5A4"), PorterDuff.Mode.SRC_IN);
                    homeDotsIndicator3.getBackground().setColorFilter(Color.parseColor("#277D38"), PorterDuff.Mode.SRC_IN);
                }
            }
        });
    }

    private void homeCollapsedHomeBar(){
        homeScrollbar.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = homeScrollbar.getScrollY();
                Log.w("Y:", String.valueOf(scrollY));
                /*
                if (scrollY == 0) {
                    homeKrplSerpisTitleRounded.setAlpha(1);
                    homeKrplSerpisTitleSquare.setAlpha(0);
                } else if (scrollY <= 200) {
                    homeKrplSerpisTitleRounded.setAlpha(0);
                    homeKrplSerpisTitleSquare.setAlpha(1);
                }

            }
        });
    }
     */

    private void homeSwipeRefresh(){
        homeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity) requireActivity()).reloadHomeActivity();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.homeMediaTanamWidget:
                ((MainActivity) requireActivity()).changeToMediaTanahFragment();
                break;
            case R.id.homeGreenhouse1Widget:
                ((MainActivity) requireActivity()).changeToGreenhouse1Fragment();
                break;
            case R.id.homeGreenhouse2Widget:
                ((MainActivity) requireActivity()).changeToGreenhouse2Fragment();
                break;
        }
    }
}