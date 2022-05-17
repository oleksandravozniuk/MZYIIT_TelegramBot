package com.example;

import application.startup.Application;

public class App 
{
    public static void main( String[] args )
    {
        MetricsConfig.configureMetrics();
        Application.start();
    }
}
