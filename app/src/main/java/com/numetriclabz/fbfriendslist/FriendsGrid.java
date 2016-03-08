package com.numetriclabz.fbfriendslist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;
import com.numetriclabz.fbfriendslist.adapter.UserGridAdapter;
import com.numetriclabz.fbfriendslist.model.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FriendsGrid extends AppCompatActivity {
    GridView gv;
    ArrayList<User> listUserWillInvite = new ArrayList<>();
    String TAG = "FriendsGrid";
    GameRequestDialog gameRequestDialog;
    TextView friend_count_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
//
        friend_count_tv = (TextView) findViewById(R.id.friend_count_tv);
        //


        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("jsondata");

        JSONArray friendslist;
        final ArrayList<User> listUser = new ArrayList<User>();

        try {
            friendslist = new JSONArray(jsondata);
            for (int l = 0; l < friendslist.length(); l++) {
                String userName = friendslist.getJSONObject(l).getString("name");
                String inviteToken = friendslist.getJSONObject(l).getString("id");
                String avatarUrl = friendslist.getJSONObject(l).getJSONObject("picture").getJSONObject("data").getString("url");
                listUser.add(new User(userName, avatarUrl, inviteToken));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        gv = (GridView) findViewById(R.id.grid_friend);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView checkImg = (ImageView) view.findViewById(R.id.check);
                if (listUserWillInvite.size() > 50) {

                } else {
                    if (checkImg.getVisibility() == View.INVISIBLE) {
                        checkImg.setVisibility(View.VISIBLE);
                        listUserWillInvite.add(listUser.get(position));
                        friend_count_tv.setText("Bạn đã chọn " + listUserWillInvite.size() + "/50 người");

                    } else {
                        checkImg.setVisibility(View.INVISIBLE);
                        listUserWillInvite.remove(listUser.get(position));
                        friend_count_tv.setText("Bạn đã chọn " + listUserWillInvite.size() + "/50 người");
                    }
                }
            }
        });
        Button invite_btn = (Button) findViewById(R.id.invite_btn);

        //
        CallbackManager callbackManagerInvite = CallbackManager.Factory.create();
        gameRequestDialog = new GameRequestDialog(FriendsGrid.this);
        gameRequestDialog.registerCallback(callbackManagerInvite,
                new FacebookCallback<GameRequestDialog.Result>() {
                    public void onSuccess(GameRequestDialog.Result result) {
                        Log.d(TAG, "gros");

                    }

                    public void onCancel() {
                        Log.d(TAG, "groc");
                    }

                    public void onError(FacebookException error) {
                        Log.d(TAG, "groe");
                    }
                }
        );
        invite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String listFriend = "";
                for (int i = 0; i < listUserWillInvite.size(); i++) {
                    if (i == listUserWillInvite.size() - 1) {
                        listFriend += listUserWillInvite.get(i).getInviteToken();
                    } else {
                        listFriend += listUserWillInvite.get(i).getInviteToken() + ",";
                    }
                }
                if (listUserWillInvite.size() == 0) {
                    showDialog(FriendsGrid.this);
                } else {
                    GameRequestContent content = new GameRequestContent.Builder()
                            .setMessage("Come play this level with me")
                            .setTo(listFriend)
//                                .setRecipients(frs)
//                                .setActionType(GameRequestContent.ActionType.SEND)
//                                .setObjectId("YOUR_OBJECT_ID")
                            .build();
                    gameRequestDialog.show(content);
                }
            }
        });


        UserGridAdapter mUserGridAdapter = new UserGridAdapter(this, listUser);
        gv.setAdapter(mUserGridAdapter);

    }

    private void showDialog(Context mContext) {

    }

}
