package chom.arikui.waffle.addresstestapp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class UserState {

    public static final String TAG = "UserState";

    private static final String VER = "0";

    public static final String fileWatchMovie = String.format("user_id_%s.dat", VER);
    public static final String fileSpiritualStage = String.format("user_password_%s.dat", VER);

    public static boolean didWatchMovie = false;

    public static long spiritualStage = 0;

    public static String Id = "";
    public static String PASSWORD = "";

}
