package com.numetriclabz.fbfriendslist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import com.android.volley.toolbox.NetworkImageView;
import com.numetriclabz.fbfriendslist.R;
import com.numetriclabz.fbfriendslist.model.User;
import com.numetriclabz.fbfriendslist.utils.volley.AppController;

import java.util.ArrayList;

/**
 * Created by phantanx on 05/03/2016.
 */
public class UserGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<User> listUser;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public UserGridAdapter(Context mContext, ArrayList<User> listUser) {
        this.mContext = mContext;
        this.listUser = listUser;
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.user_grid_item,null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        GridView grid = (GridView)parent;
        int size = grid.getRequestedColumnWidth();

        User user = listUser.get(position);

        NetworkImageView avatarUserNiv= (NetworkImageView) convertView
                .findViewById(R.id.user_avatar);
        avatarUserNiv.setImageUrl(user.getAvatarUrl(),imageLoader);
        TextView userNameTv = (TextView)convertView.findViewById(R.id.user_name);

        userNameTv.setText(user.getName());

        return convertView;
    }
}
