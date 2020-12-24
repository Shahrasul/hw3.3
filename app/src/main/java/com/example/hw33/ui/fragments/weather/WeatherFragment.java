package com.example.hw33.ui.fragments.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hw33.R;
import com.example.hw33.data.models.Weather;
import com.example.hw33.data.models.WeatherModel;
import com.example.hw33.data.network.WeatherService;
import com.example.hw33.databinding.FragmentWeatherBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;

    public WeatherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setWeatherStats();
        Toast.makeText(requireContext(),"chto-to", Toast.LENGTH_SHORT).show();
    }

    private void setWeatherStats() {
        WeatherService.getInstance().getCurrentWeather("bishkek","4bbf5a1ed98cd9f688ebb3651474cdd2").enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherModel weatherModel = response.body();
                    binding.locationHomeFrag.setText("city: " + weatherModel.getName() + " " + weatherModel.getSys().getCountry());
                    binding.humidityPercent.setText(weatherModel.getMain().getHumidity() + "%");
                    binding.temperature.setText(String.valueOf(weatherModel.getMain().getTemp()));
                    binding.pressureMp.setText(weatherModel.getMain().getPressure() + "mp");
                    binding.mSWind.setText(weatherModel.getWind().getDeg() + " " + weatherModel.getWind().getSpeed().toString() + "m/s");
                    binding.percentCloudiness.setText(weatherModel.getClouds().getAll() + "%");
                    binding.temperatureMin.setText("min: " + weatherModel.getMain().getTempMin());
                    binding.temperatureMax.setText("max: " + weatherModel.getMain().getTempMax());
                    Date dateRise = new Date(weatherModel.getSys().getSunrise());
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                    binding.timeSunrise.setText(df.format(dateRise));
                    Date dateSet = new Date(weatherModel.getSys().getSunset());
                    binding.timeSunset.setText(df.format(dateSet));

                }

                Log.d("Shahhgjhjhg", "onResponse" + "СРАТОТАЛО");

            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.e("ololo_fcgfg", t.getMessage());
            }
        });

        Date currentTime = new Date(System.currentTimeMillis());
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        binding.timeHomeFrag.setText(dfTime.format(currentTime));
        Date currentDate = new Date(System.currentTimeMillis());
        DateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy");
        binding.dateHomeFrag.setText(dfDate.format(currentDate));
    }

}
