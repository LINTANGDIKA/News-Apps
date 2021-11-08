package com.example.newsapps.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.newsapps.R;
import com.example.newsapps.Ui.LoginForm;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private BottomNavigationView bottomNavigationView;
    private TextView nama, email, umur, alamat, logout, notelepon, txtdone, txtout;
    private TextView txtemail, txtumur, txtalamat, txtnotelepon;
    private CircleImageView circleImageView ;
    private Dialog dialog;
    private FirebaseAuth mAuth;
    private LoginForm loginForm;
    private Uri mImageuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        loginForm = new LoginForm();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_center);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        nama = findViewById(R.id.tv_user_contentname);
        email = findViewById(R.id.tv_user_contentemail);
        umur = findViewById(R.id.tv_user_contentumur);
        alamat = findViewById(R.id.tv_user_contentalamat);
        notelepon = findViewById(R.id.tv_user_contentnotelepon);
        circleImageView = findViewById(R.id.im_profile);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        txtdone = dialog.findViewById(R.id.txtyes);
        txtout = dialog.findViewById(R.id.txt_pesan);
        logout = findViewById(R.id.tv_logout);
        txtemail = findViewById(R.id.tv_user_email);
        txtumur = findViewById(R.id.tv_user_umur);
        txtalamat = findViewById(R.id.tv_user_alamat);
        txtnotelepon = findViewById(R.id.tv_user_notelepon);
        mAuth = FirebaseAuth.getInstance();

        //action menambahkan gambar profil
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfile();
            }
        });

        GoogleSignInAccount user = GoogleSignIn.getLastSignedInAccount(this);

        if (user != null) {
            nama.setText("Halo " + user.getDisplayName());
            email.setText(user.getEmail());
            umur.setVisibility(View.INVISIBLE);
            alamat.setVisibility(View.INVISIBLE);
            notelepon.setVisibility(View.INVISIBLE);
            txtumur.setVisibility(View.INVISIBLE);
            txtalamat.setVisibility(View.INVISIBLE);
            txtnotelepon.setVisibility(View.INVISIBLE);
        }
        //action tombol logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user;
                user = mAuth.getCurrentUser();
                if (user != null) {
                    FirebaseAuth.getInstance().signOut();
                    dialog.show();
                }
//                }else  if (){
//                    Intent intent = new Intent(getApplicationContext(), LoginForm.class);
//                    startActivity(intent);
//                    finish();
//                }

            }
        });
        //konfirmasi keluar di dialog button
        txtdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginForm.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
                finish();
            }
        });
        txtout.setText("Akun anda telah di Log Out");
        txtdone.setText("Sampai Jumpa");

        bottomNavigationView.setSelectedItemId(R.id.User);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Favorite:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.Trnding:
                        startActivity(new Intent(getApplicationContext(), TrendingActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return  true;
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
    //mengambil gambar dari file manager
    private void openfile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageuri = data.getData();
            circleImageView.setImageURI(mImageuri);
        }
    }
}