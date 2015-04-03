package com.boakye.daniel.spotbot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogInActivity extends Activity {

    TextView msignUp;
    EditText LogInusername;
    EditText LogInpassword;
    Button mLogInBut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Parse.initialize(this, "aV4oLiG745IHpmjdGotoYpfkrcidpMegxvhs6PAx",
                "K2VUItIYGQMK73RLhys2t1tymlirLeFaig16nrYG");
        msignUp = (TextView) findViewById(R.id.signUp_L);
        LogInusername = (EditText) findViewById(R.id.usernameField);
        LogInpassword = (EditText) findViewById(R.id.passwordField);
        mLogInBut = (Button)findViewById(R.id.userlogin);
        msignUp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SginUpActivity.class);
                startActivity(intent);

            }
        });

        mLogInBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = LogInusername.getText().toString();
                String password = LogInpassword.getText().toString();
                String uSname = username.trim();
                String uSpass = password.trim();
                if (uSname.isEmpty() || uSpass.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            LogInActivity.this);
                    builder.setMessage(R.string.loginError)
                            .setTitle(R.string.errorTitle)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    ParseUser.logInInBackground(uSname, uSpass, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (e == null) {
                                //Login sucess
                                Intent intent = new Intent(LogInActivity.this, VMainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                        LogInActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(R.string.errorTitle)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        }
                    });
                }

            }
        });
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_LogOut) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
