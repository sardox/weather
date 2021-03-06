package com.sardox.weatherapp.presenter;

import com.sardox.weatherapp.main.MainPresenterImpl;
import com.sardox.weatherapp.main.MainView;
import com.sardox.weatherapp.model.MainModel;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by sardox on 9/3/2017.
 */
public class MainPresenterImplTest {


    MainView mainView;

    MainModel mainModel;

    @Before
    public void setUp() throws Exception {

        mainModel = mock(MainModel.class);
        mainView = mock(MainView.class);
    }

    @Test
    public void onWeatherRequestedFromRecent() throws Exception {
        MainPresenterImpl mainPresenter = new MainPresenterImpl(mainModel);
        mainPresenter.attachView(mainView);
        mainPresenter.onWeatherRequestedFromRecent();
        verify(mainView).showWeatherTab();
    }

}