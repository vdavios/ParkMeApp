package vd.parkmeapp.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import vd.parkmeapp.Models.User;
import vd.parkmeapp.Presenters.EditUserInfoPresenter;
import vd.parkmeapp.R;


public class EditUserInfoActivity extends AppCompatActivity implements EditUserInfoView {


    private User tenant;
    private TextView label;
    private EditText valueText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        valueText = (EditText) (findViewById(R.id.value_Input));
        label = (TextView) (findViewById(R.id.Label));


        final EditUserInfoPresenter mPresenter =
                new EditUserInfoPresenter(this, getIntent().getExtras());

        mPresenter.setEditableInfo();
        tenant = getIntent().getParcelableExtra("User");

        valueText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(mPresenter.newText(valueText.getText().toString().trim())){
                        userProfileActivity();
                    }

                    return true;
                }
                return false;
            }
        });

        Button saveButton = (Button) (findViewById(R.id.saveButton));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPresenter.newText(valueText.getText().toString().trim())){
                    userProfileActivity();
                }
            }
        });
    }



    @Override
    public void updateUser(User tenant) {
        this.tenant = tenant;
    }
    private void userProfileActivity(){
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("User", tenant);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        userProfileActivity();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void setFields(String labelName, String firstName) {
        label.setText(labelName);
        valueText.setText(firstName);
    }
}
