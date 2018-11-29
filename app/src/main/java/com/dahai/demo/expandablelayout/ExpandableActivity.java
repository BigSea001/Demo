package com.dahai.demo.expandablelayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahai.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 大海
 * 时间： 2018/11/29
 * 描述：
 */
public class ExpandableActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StringAdapter(getData()));
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();

        for(int i=0; i<50; i++)
            data.add("data " +i);

        return data;
    }


    class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringViewHolder> {

        private final List<String> data;

        public StringAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public StringAdapter.StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable, parent, false);
            return new StringViewHolder(view);
        }

        @Override
        public void onBindViewHolder(StringAdapter.StringViewHolder holder, int position) {
            holder.nonExpandableView.setText(data.get(position));

            holder.expandableLayout.collapse(false);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class StringViewHolder extends RecyclerView.ViewHolder {

            final ExpandableLayout expandableLayout;
            final TextView nonExpandableView;

            StringViewHolder(View itemView) {
                super(itemView);

                expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
                nonExpandableView = (TextView) itemView.findViewById(R.id.text_view);

                nonExpandableView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        expandableLayout.toggle(true);
                    }
                });

            }
        }
    }
}
