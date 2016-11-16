package com.wjft.odo.view.custom_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wjft.odo.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewDragHelperActivity extends AppCompatActivity {

    @Bind(R.id.vl)
    VDHLayout vl;
    ArrayList<View> mViews = new ArrayList<>();
    ArrayList<String> mStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drag_helper);
        ButterKnife.bind(this);
        for (int i = 0; i < 50; i++) {
            View view = getLayoutInflater().inflate(R.layout.item_view_drag_helper_layout, vl, false);
            TextView tv = (TextView) view.findViewById(R.id.tv);
            tv.setText("viewDragHelper  " + i);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ViewDragHelperActivity", "i:" + finalI);
                }
            });
            mViews.add(view);
            mStrings.add("viewDragHelper" + i);
        }
        vl.setViews(mViews);
    }
}
