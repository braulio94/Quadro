package com.braulio.cassule.designfocus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Braulio on 12/3/2016.
 */

public class BaseActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public FirebaseAuth.AuthStateListener mAuthListener;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;


    public void signOut() {
        mAuth.getInstance().signOut();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mAuthListener != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
    }


    public void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();

    }

    public void showMenu() {
        View  view = new View(getBaseContext());
        PopupMenu popup = new PopupMenu(BaseActivity.this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();

    }

    public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {



            switch (menuItem.getItemId()) {
                case R.id.action_settings:
                    Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_privacy:
                    Toast.makeText(getApplicationContext(), "Privacy", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_sign_out:
                    signOut();
                    return true;
                default:
            }
            return true;
        }
    }
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getUid() {

        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
