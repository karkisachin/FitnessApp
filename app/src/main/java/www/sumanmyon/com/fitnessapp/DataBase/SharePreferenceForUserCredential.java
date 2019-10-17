package www.sumanmyon.com.fitnessapp.DataBase;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferenceForUserCredential {
    Activity activity;
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences prefs;

    public SharePreferenceForUserCredential(Activity activity){
        this.activity = activity;
         prefs = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    //Setting values in Preference
    public void setCredential(String username, String password, String id){
        SharedPreferences.Editor editor= prefs.edit();
       // String data = "username="+username+"::password="+password;
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("id",id);
        editor.apply();
    }

    //Retrieve data from preference
    public String getCredential(){
        String getUserData = null;
        String username = prefs.getString("username", "No name defined");//"No name defined" is the default value.
        String password = prefs.getString("password", "No name defined"); //0 is the default value.
        String id = prefs.getString("id", "No name defined");
        getUserData =username+"::"+password+"::"+id;
        //showMessage(getUserData);
        return getUserData;
    }

    //Setting values in Preference
    public void logoutCredential(String username, String password, String id){
        SharedPreferences.Editor editor= prefs.edit();
        // String data = "username="+username+"::password="+password;
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("id",id);
        editor.apply();
    }

    private void showMessage(String message) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }

}
