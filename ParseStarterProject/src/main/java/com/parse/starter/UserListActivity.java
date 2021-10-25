package com.parse.starter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.databinding.ActivityUserListBinding;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    ActivityUserListBinding activityUserListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserListBinding = ActivityUserListBinding.inflate(getLayoutInflater());
        View view = activityUserListBinding.getRoot();
        setContentView(view);

        ArrayList<String> usernames = new ArrayList<String>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,usernames);


        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null){
                    if(objects.size()>0){
                        for(ParseUser user: objects){
                            usernames.add(user.getUsername());
                        }
                        activityUserListBinding.userListView.setAdapter(arrayAdapter);
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });

    }
}