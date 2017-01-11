package felixgiov.cmsdetector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import felixgiov.cmsdetector.R;
import felixgiov.cmsdetector.model.Model;

/**
 * Created by felix on 11 Jan 2017.
 */

public class CmsAdapter extends RecyclerView.Adapter<CmsAdapter.CmsViewHolder> {

    private List<String> cmses;
    private int rowLayout;
    private Context context;


    public static class CmsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView cmsText;

        public CmsViewHolder(View v) {
            super(v);
            linearLayout = (LinearLayout) v.findViewById(R.id.list_tab);
            cmsText = (TextView) v.findViewById(R.id.cms_tv);
        }
    }

    public CmsAdapter(List<String> cmses, int rowLayout, Context context) {
        this.cmses = cmses;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public CmsAdapter.CmsViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CmsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CmsViewHolder holder, final int position) {
        holder.cmsText.setText(cmses.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return cmses.size();
    }

}
