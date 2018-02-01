package ru.dimasokol.demo.roomcars.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ru.dimasokol.demo.roomcars.ModelsActivity;
import ru.dimasokol.demo.roomcars.R;
import ru.dimasokol.demo.roomcars.model.Vendor;

/**
 * Created by sokol on 01.02.2018.
 */

public class VendorsAdapter extends RecyclerView.Adapter<VendorsAdapter.Holder> {

    private List<Vendor> mVendors = Collections.emptyList();

    public VendorsAdapter() {
        setHasStableIds(true);
    }

    public void setVendors(List<Vendor> vendors) {
        mVendors = vendors;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selectable_simple_text, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(mVendors.get(position));
    }

    @Override
    public int getItemCount() {
        return mVendors.size();
    }

    @Override
    public long getItemId(int position) {
        return mVendors.get(position).getId();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private View.OnClickListener mModelsClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vendor vnd = (Vendor) v.getTag();
                Intent modelsActivity = new Intent(v.getContext(), ModelsActivity.class);
                modelsActivity.putExtra(ModelsActivity.ARG_VENDOR_ID, vnd.getId());

                v.getContext().startActivity(modelsActivity);
            }
        };

        public Holder(View itemView) {
            super(itemView);
        }

        public void bind(Vendor vendor) {
            ((TextView) itemView).setText(vendor.getBrandName());
            itemView.setTag(vendor);

            itemView.setOnClickListener(mModelsClick);
        }
    }

}
