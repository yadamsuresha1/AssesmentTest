package com.autosenseindia.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.autosenseindia.utils.AssesmentUtils;
import com.autosenseindia.R;
import com.autosenseindia.SingletonRequestQueue;
import com.autosenseindia.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserNameEditText, mPasswordEditText;
    private TextInputLayout mUserNameInputLayout, mPasswordInputLayout;

    private Button mSubmitButton;
    private CoordinatorLayout rootLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {

        mUserNameEditText = findViewById(R.id.username);
        mPasswordEditText = findViewById(R.id.password);

        mUserNameInputLayout = findViewById(R.id.username_textinputLayout);
        mPasswordInputLayout = findViewById(R.id.password_inputlayout);

        rootLayout = findViewById(R.id.rootLayout);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        mSubmitButton = findViewById(R.id.submit);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidUser()) {
                    String usernameEntered = mUserNameEditText.getText().toString();
                    String passwordEntered = mPasswordEditText.getText().toString();

                    getData(usernameEntered, passwordEntered);

                } else {

                    //                                invalid credentials
                }
            }
        });
    }

    private boolean isValidUser() {
        String usernameEntered = mUserNameEditText.getText().toString();
        String passwordEntered = mPasswordEditText.getText().toString();

        if (usernameEntered.length() == 0) {
            mUserNameInputLayout.setError("Please enter user name");
            return false;
        } else {
            mUserNameInputLayout.setError(null);
        }
        if (passwordEntered.length() == 0) {
            mPasswordInputLayout.setError("Please enter password");
            return false;
        } else {
            mUserNameInputLayout.setError(null);
        }
        return true;

//        if (usernameEntered.equalsIgnoreCase("test") && passwordEntered.equalsIgnoreCase("123456")) {
//            return true;
//        } else {
//            mUserNameInputLayout.setError(null);
//            mPasswordInputLayout.setError(null);
//
//            showSnackbar("Username or password is incorrect!");
//            return false;
//        }
    }


    private void getData(final String username, final String password) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(this).getRequestQueue();

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        JSONObject jsonObject = new JSONObject(data);

        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AssesmentUtils.URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    progressBar.setVisibility(View.GONE);

                    Log.d("suresh", "response: " + response);

                    String finalResponse = response.getString("TABLE_DATA");

                    JSONObject finalJsonObject = new JSONObject(finalResponse);

                    JSONArray dataArray = finalJsonObject.getJSONArray("data");

                    ArrayList<User> userArrayList = new ArrayList<User>();

                    for (int i = 0; i < dataArray.length(); i++) {


                        JSONArray userArray = dataArray.getJSONArray(i);
                        String name = userArray.getString(0);
                        String position = userArray.getString(1);
                        String city = userArray.getString(2);
                        String pincode = userArray.getString(3);
                        String date = userArray.getString(4);
                        String salary = userArray.getString(5);
                        User user = new User(name, position, city, pincode, date, salary);

                        userArrayList.add(user);
                    }

                    Intent usersListActivity = new Intent(LoginActivity.this, UsersActivity.class);
                    usersListActivity.putExtra("users", userArrayList);
                    startActivity(usersListActivity);

                    finish();

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("suresh", "error: " + error.toString());
                if (error instanceof NetworkError) {
                    showSnackbar("Network not available!");
                } else {
                    showSnackbar("Invalid Credentials");
                }
                progressBar.setVisibility(View.GONE);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(rootLayout, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
        CoordinatorLayout.LayoutParams params1;
        params.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(params);

        TextView tv = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);

        snackbar.show();
    }
}
