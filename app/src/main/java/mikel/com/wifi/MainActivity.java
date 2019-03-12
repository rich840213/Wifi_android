package mikel.com.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectWifi();

        while (!isConnectedTo("HITRON-8580", MainActivity.this)) {
            Log.d("MIKEAPP", "WAITING");
        }

        Log.d("MIKEAPP", "CONNECTED");

    }

    public void connectWifi(){
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", "HITRON-8580");
        wifiConfig.preSharedKey = String.format("\"%s\"", "pacopaco");

        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);

        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();


    }

    public String checkSSID(){
        String ssid = "";
        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState()) == NetworkInfo.DetailedState.CONNECTED) {
            ssid = wifiInfo.getSSID();
        }


        ssid = wifiInfo.getSSID();
        Log.d("MIKEAPP", ssid);

        return ssid;
    }

    public boolean isConnectedTo(String ssid, Context context) {
        boolean retVal = false;
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        if (wifiInfo != null) {
            String currentConnectedSSID = wifiInfo.getSSID();
            String cleanSSID = currentConnectedSSID.substring(1,currentConnectedSSID.length()-1);
            Log.d("MIKEAPP", cleanSSID);
            Log.d("MIKEAPP", ssid);

            if (cleanSSID != null && ssid.equalsIgnoreCase(cleanSSID)) {
                retVal = true;
            }
        }
        return retVal;
    }
}
