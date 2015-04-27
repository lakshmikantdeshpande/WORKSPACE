package com.mithi.androidnotifier;
 
import static com.mithi.androidnotifier.CommonUtilities.SENDER_ID;
import static com.mithi.androidnotifier.CommonUtilities.SERVER_URL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 
public class RegisterActivity extends Activity {
    // alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();
     
    // Internet detector
    ConnectionDetector cd;
     
    // UI elements
    EditText txtEmail;
    EditText txtPassword;
    
     
    // Register button
    Button btnRegister;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         
        cd = new ConnectionDetector(getApplicationContext());
 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(RegisterActivity.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
 
        // Check if GCM configuration is set
        if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
                || SENDER_ID.length() == 0) {
            // GCM sernder id / server url is missing
            alert.showAlertDialog(RegisterActivity.this, "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID", false);
            // stop executing code by return
             return;
        }
         
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
         
        
        
        /*
         * Click event on Register button
         * */
        btnRegister.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
            	
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString();
                
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                
                // Check if user filled the form
                if(email.trim().length() > 0 && password.trim().length() > 0 && email.matches(emailPattern))
               {
                	// If everything is valid, 
                	// Launch Main Activity
                    Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                     
                    // Registering user on our server                   
                    // Sending registration details to RegistrationActivity
                    i.putExtra("email", email);
                    i.putExtra("password", password);
                    startActivity(i);
                    //finish();
                }
                
                else if(!email.matches(emailPattern))
                {
                	alert.showAlertDialog(RegisterActivity.this, "Invalid Email ID!", "Please enter a valid Email address !", false);
                }
                else
                {
                	// Details not inputted
                    alert.showAlertDialog(RegisterActivity.this, "Registration Error!", "Please enter your details", false);
                }
            }
        });
    }
 
}
