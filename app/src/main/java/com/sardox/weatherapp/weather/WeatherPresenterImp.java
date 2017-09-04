package com.sardox.weatherapp.weather;

import android.util.Log;

import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.utils.Utils;
import com.sardox.weatherapp.utils.WeatherForecast;
import com.sardox.weatherapp.model.MainModel;
import com.sardox.weatherapp.model.Providers.LocationProvider.LocationCallback;
import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;

import javax.inject.Inject;

/**
 * Created by sardox on 9/2/2017.
 */

public class WeatherPresenterImp implements WeatherPresenter, WeatherPresenterCallback {

    private static final String TAG = "WeatherPresenterImp";
    private WeatherView view;
    private MainModel mainModel;
    private Utils.TEMPERATURE selectedTempUnit = Utils.TEMPERATURE.Fahrenheit;  //i can let user chose units later


    @Inject
    public WeatherPresenterImp(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public void onLocationReceived(MyLocation location) {
        view.showLoading();
        mainModel.getWeatherByLatLon(location);
    }

    @Override
    public void onNetworkProviderDisabled() {
        view.onNetworkProviderDisabled();
    }

    @Override
    public void onViewPrepared() {
        Log.v("weatherApp", TAG + " onViewPrepared");
        mainModel.setupWeatherPresenterCallback(this);
        mainModel.getMostRecentItem(new Consumer<RecentItem>() {
            @Override
            public void onSuccess(RecentItem item) {
                if (item != null) mainModel.getWeatherByLatLon(new MyLocation(item.getLat(), item.getLon()));
            }

            @Override
            public void onFailed(String error) {
            }
        });
    }

    @Override
    public void onAttach(WeatherView view) {
        Log.v("weatherApp", TAG + " onAttach");
        this.view = view;
    }

    @Override
    public void onDetach() {
        Log.v("weatherApp", TAG + " onDetach");
        this.view = null;
         //mainModel.setupWeatherPresenterCallback(null);
    }

    @Override
    public void onLocationPermissionGranted() {
        Log.v("weatherApp", TAG + " onLocationPermissionGranted");

        view.showLoading();
        mainModel.getUserLocation(new LocationCallback() {
            @Override
            public void onLocationReceived(MyLocation location) {
                Log.v("weatherApp", TAG + " onLocationReceived");
                mainModel.getWeatherByLatLon(location);
            }

            @Override
            public void onProviderDisabled() {
                Log.v("weatherApp", "onProviderDisabled123 NEVER CALLED ***************************************");
                view.hideLoading();
                view.onNetworkProviderDisabled();
            }
        });
    }

    @Override
    public void onLocationPermissionDenied() {
        view.askUserToEnablePermissions();
    }

    @Override
    public void getWeatherWithMyLocation() {
        view.checkPermissions();
    }

    @Override
    public void onNetworkError(String error) {
        view.showMessage("onNetworkError" + error);
    }

    @Override
    public void getWeatherByUserInput(final String user_input) {

        switch (Utils.validateInput(user_input)) {
            case CITY:
                Log.v("weatherApp", "getWeatherBy CITY");
                view.showLoading();
                mainModel.getWeatherByCity(user_input);
                break;
            case ZIP:
                Log.v("weatherApp", "getWeatherBy ZIP");
                view.showLoading();
                mainModel.getWeatherByZip(user_input);
                break;
            case UNKNOWN:
                Log.v("weatherApp", "getWeatherBy UNKNOWN");
                view.showMessage("Please make sure you entered correct ZIP/City");
                break;
        }
    }

    @Override
    public void onWeatherReceiveError(String error) {
        view.hideLoading();
        view.showMessage(error);
    }

    @Override
    public void onWeatherReceived(WeatherForecast weatherForecast) {
        Log.v("weatherApp", "Weather received");
        weatherForecast.setTemp(Utils.convertTemp(weatherForecast.getTemp(), selectedTempUnit));
        mainModel.addItemToRecent(weatherForecast);
        view.hideLoading();
        view.updateWeather(weatherForecast);
    }
}
