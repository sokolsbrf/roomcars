package ru.dimasokol.demo.roomcars.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ru.dimasokol.demo.roomcars.R;
import ru.dimasokol.demo.roomcars.model.Model;

/**
 * Created by sokol on 01.02.2018.
 */

public class ModelsAdapter extends RecyclerView.Adapter<ModelsAdapter.Holder> {

    private List<Model> mModels = Collections.emptyList();

    public ModelsAdapter() {
        super();
        setHasStableIds(true);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.selectable_simple_text, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(mModels.get(position));
    }

    public void setModels(List<Model> models) {
        mModels = models;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    @Override
    public long getItemId(int position) {
        return mModels.get(position).getId();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }

        public void bind(Model model) {
            ((TextView) itemView).setText(model.getName());
        }
    }

}
