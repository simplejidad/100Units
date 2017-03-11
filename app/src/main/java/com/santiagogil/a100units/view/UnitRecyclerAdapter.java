package com.santiagogil.a100units.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiagogil.a100units.R;
import com.santiagogil.a100units.model.pojos.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitRecyclerAdapter extends RecyclerView.Adapter {

    private List<Unit> units;
    private Context context;
    private FragmentMain.ActivityCommunicator activityCommunicator;

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public UnitRecyclerAdapter(Context context, FragmentMain.ActivityCommunicator activityCommunicator) {
        this.context = context;
        this.units = new ArrayList<>();

        this.activityCommunicator = activityCommunicator;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.little_square_unit, parent, false);
        return new UnitViewHolder(view, activityCommunicator);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        Unit unit = units.get(position);
        UnitViewHolder unitViewHolder = (UnitViewHolder) holder;
        unitViewHolder.loadUnit(unit);
        unitViewHolder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    static class UnitViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout innerLayout;
        private Unit unit;
        private Integer position;
        private View itemView;

        public void setPosition(Integer position) {
            this.position = position;
        }

        private TextView textView;
        private FragmentMain.ActivityCommunicator activityCommunicator;

        public UnitViewHolder(final View itemView, final FragmentMain.ActivityCommunicator activityCommunicator) {
            super(itemView);
            this.itemView = itemView;
            textView = (TextView) itemView.findViewById(R.id.text_view_unit_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   activityCommunicator.onUnitTouched(unit, position);
                }
            });
            innerLayout = (LinearLayout) itemView.findViewById(R.id.inner_layout);

        }

        public void loadUnit(Unit unit) {
            this.unit = unit;
            textView.setText(unit.getID());
            if(!unit.getDescription().equals("")){
                innerLayout.setBackgroundColor(Color.CYAN);
            }
        }
    }
}
