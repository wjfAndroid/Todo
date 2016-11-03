package com.wjft.odo.view.Tset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wjft.odo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvTset;
    EditText etTset;
    Button btTset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvTset = (TextView) findViewById(R.id.tv_tset);
        etTset = (EditText) findViewById(R.id.et_tset);
        btTset = (Button) findViewById(R.id.bt_tset);
        btTset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        sayHello();
    }

    public void sayHello() {
        tvTset.setText("hello" + etTset.getText().toString().trim());
    }


}
