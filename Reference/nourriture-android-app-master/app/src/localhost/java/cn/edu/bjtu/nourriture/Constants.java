package cn.edu.bjtu.nourriture;

/**
 * Created by Pavel Procházka on 31/12/14.
 */
public interface Constants {
/**
     * An emulated device can not see your development machine or other emulator instances on the
     * network. Instead, it sees only that it is connected through Ethernet to a router/firewall.
     *
     * 10.0.2.2 is a special alias to your host loopback interface (i.e., 127.0.0.1 on your development machine)
     *
     * @see http://developer.android.com/tools/devices/emulator.html#emulatornetworking
     */
    public String NOURRITURE_PLATFORM_URL            = "http://10.0.2.2:2121";
    public String NOURRITURE_PLATFORM_ANDROID_URL    = "http://10.0.2.2:8081";

    public String GOOGLE_ANALYTICS_TRACKING_ID      = "";
}
