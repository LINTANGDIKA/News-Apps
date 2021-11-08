package com.example.newsapps.Ui;
import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.newsapps.Activity.MainActivity;
import com.example.newsapps.R;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginForm extends AppCompatActivity {
    private String clientid = "288813297829-31hqi39vhvkr9dgl1hso4vdkd1v5uggs.apps.googleusercontent.com";
    private ProgressBar progressBar;
    private TextView textView, text;
    private CheckBox ed_rememberme;
    private Button register, login;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private Button verify;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Dialog dialog;
    private EditText ed_email, ed_password;
    private String email, password;
    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            overridePendingTransition(R.anim.fade, R.anim.fade_out);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_loginform);
        dialog = new Dialog(this);
        register = findViewById(R.id.bt_register);
        login = findViewById(R.id.bt_login);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        verify = findViewById(R.id.imb_google);
        register = findViewById(R.id.bt_register);
        progressBar = findViewById(R.id.progress_bar);
        login = findViewById(R.id.bt_login);
        ed_rememberme = findViewById(R.id.ed_rememberme);
        dialog.setContentView(R.layout.dialog_center);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textView = dialog.findViewById(R.id.txtyes);
        mAuth = FirebaseAuth.getInstance();
        middleware();
        navigation();
        createRequest();
    }
    public void login(String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("testing data "+email+", "+password);
        AndroidNetworking.post("https://news-appapi.herokuapp.com/api/login")
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            progressBar.setVisibility(View.INVISIBLE);
                            dialog.show();
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.root_toast));
                        text = layout.findViewById(R.id.text);
                        text.setText("Akun Email atau Password salah");
                        Toast toast = new Toast(getApplicationContext());
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        progressBar.setVisibility(View.INVISIBLE);
                        toast.show();
                        Log.d("","" + error.getErrorDetail());
                        Log.d(""," "+ error.getErrorBody());
                        Log.d(""," "+ error.getErrorCode());
                        Log.d(""," "+ error.getResponse());
                    }
                });
    }


    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(clientid))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private String getString(String clientid) {
        this.clientid = clientid;
        return clientid;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("","" + task + data + ApiException.class);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                Toast.makeText(getApplicationContext(), "Berhasil Login", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Log.d("", "Google : " + e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginForm.this, "sorry gagal login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void navigation() {
        ed_rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    editor.putString("remember","true");
                    editor.apply();
                } else if(!buttonView.isChecked()){
                    editor.putString("remember","false");
                    editor.apply();
                }
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginForm.this, RegisterForm.class);
                startActivity(intent);
            }
        });
    }
    private void toast() {
        email = ed_email.getText().toString();
        password = ed_password.getText().toString();

        if(email.trim().isEmpty() && password.trim().isEmpty()){
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.root_toast));
            text = layout.findViewById(R.id.text);
            text.setText("Harus di isi Formnya");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        } else {
            Log.d("testing","data : " + email + password);
            login(email, password);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
                finish();
            }
        });
    }
    public void middleware() {
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String check = preferences.getString("remember","");
        if(check.equals("true")){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if(check.equals("false")){
        }
    }
}