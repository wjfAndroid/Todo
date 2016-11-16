package com.wjft.odo.view.multitype;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.wjft.odo.R;
import com.wjft.odo.view.recyclerview_pullpush.DividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MultiTypeActivity extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView rv;

    Items mItems;
    MultiTypeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        ButterKnife.bind(this);

        mItems = new Items();
        adapter = new MultiTypeAdapter(mItems);

        adapter.register(CateGory.class, new CateGoryViewProvider());
        adapter.register(Song.class, new SongViewProvider());

        for (int i = 0; i < 10; i++) {
            mItems.add(new CateGory("category"+i));
            mItems.add(new Song("song"+i,R.mipmap.icon_beijing));
            mItems.add(new Song("wjf"+i,R.mipmap.icon_gongshang));
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        rv.setAdapter(adapter);

    }


}
