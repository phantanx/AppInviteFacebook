package com.numetriclabz.fbfriendslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String APP_ID = "1577696619218853"; // Replace with your App ID
    // Instance of Facebook Class
    String TAG = "MainActivity";
    List<String> permissionNeeds = Arrays.asList(
            ""
//            "user_photos",
//            "email",
//            "user_birthday",
//            "user_friends"
    );
    CallbackManager loginCallbackManager;
    Button btn1, btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_main);
        MyExceptionHandler myExceptionHandler = new MyExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(myExceptionHandler);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("limit", 1000);
                GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(

                        AccessToken.getCurrentAccessToken(),
                        "/me/invitable_friends",
                        bundle,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {

                                try {
                                    Log.d("response", response.toString());
                                    if (response.toString().contains("HttpStatus: 400, errorCode: 2500")) {
                                        Log.d("response", "invalid token");
                                        loginAndShowFriend();
                                    } else {
                                        JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                        Intent intent = new Intent(MainActivity.this, FriendsGrid.class);
                                        intent.putExtra("jsondata", rawName.toString());
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    ;
                                }
                            }

                        }
                ).executeAsync();

            }
        });
        //

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        loginCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    private void getListFriendAndDisplay() {
        Bundle bundle = new Bundle();
        bundle.putInt("limit", 1000);
        GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(

                AccessToken.getCurrentAccessToken(),
                "/me/invitable_friends",
                bundle,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Intent intent = new Intent(MainActivity.this, FriendsGrid.class);
                        try {
                            Log.d("response", response.toString());
                            JSONArray rawName = response.getJSONObject().getJSONArray("data");

                            intent.putExtra("jsondata", rawName.toString());
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ;
                        }
                    }

                }
        ).executeAsync();
    }

    private void loginAndShowFriend() {
        LoginManager.getInstance().registerCallback(loginCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResults) {
                        Log.e("dd", "facebook login onSuccess");
                        getListFriendAndDisplay();
                    }

                    @Override
                    public void onCancel() {

                        Log.e("dd", "facebook login canceled");

                    }


                    @Override
                    public void onError(FacebookException e) {


                        Log.e("dd", "facebook login failed error");

                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, permissionNeeds);
    }
}