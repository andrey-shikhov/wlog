package me.shikhov.wlogtest;

public class Application extends android.app.Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        System.setProperty("wlog.logLevel", "info");
    }
}
