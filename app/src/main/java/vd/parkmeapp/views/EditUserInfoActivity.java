package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.EditUserInfoPresenter;
import vd.parkmeapp.R;


public class EditUserInfoActivity extends AppCompatActivity implements EditUserInfoView {


    private User tenant;
    private TextView label;
    private EditText valueText;
    private String caller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        valueText = (findViewById(R.id.value_Input));
        label = (findViewById(R.id.Label));
        tenant = getIntent().getParcelableExtra("User");
        caller = getIntent().getStringExtra("caller");
        int id = getIntent().getIntExtra("R.id",1);

        final EditUserInfoPresenter mPresenter =
                new EditUserInfoPresenter(this, tenant,id);

        mPresenter.setEditableInfo();

        valueText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(mPresenter.newText(valueText.getText().toString().trim())){
                        Log.d("Caller: ", caller);
                        if(caller.equals("AddParkingActivity")){
                            editParkingInfoActivity();
                        } else {
                            userProfileActivity();
                        }

                    }

                    return true;
                }
                return false;
            }
        });

      Button saveButton = findViewById(R.id.saveButton);
      saveButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(mPresenter.newText(valueText.getText().toString())){
                  if(caller.equals("EditParkingInfoActivity")){
                      editParkingInfoActivity();
                  } else {
                      userProfileActivity();
                  }

              }

          }
      });
    }

    public void editParkingInfoActivity(){
        Log.d("Going to: ", "EditParkingInfoActivity");
        Intent intent = new Intent(EditUserInfoActivity.this, EditParkingInfoActivity.class);
        intent.putExtra("User", tenant);
        startActivity(intent);
    }

    @Override
    public void updateUser(User tenant) {
        this.tenant = tenant;
    }

    private void userProfileActivity(){
        Intent intent = new Intent(EditUserInfoActivity.this, UserProfileActivity.class);
        intent.putExtra("User", tenant);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
         if(caller.equals("EditParkingInfoActivity")){
            editParkingInfoActivity();
         } else {
            userProfileActivity();
         }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void setFields(String labelName, String userInfo) {
        label.setText(labelName);
        valueText.setText(userInfo);
    }
}