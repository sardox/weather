package com.sardox.weatherapp.weather;


import com.sardox.weatherapp.utils.WeatherForecast;

interface WeatherView {
    void showLoading();
    void hideLoading();
    void updateWeather(WeatherForecast weatherForecast);
    void checkPermissions( );
    void askUserToEnablePermissions( );
    void onNetworkProviderDisabled();
    void onError(String message);
    void showMessage(String message);
}
