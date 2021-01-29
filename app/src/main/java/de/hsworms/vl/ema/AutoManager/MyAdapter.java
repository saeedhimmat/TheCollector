package de.hsworms.vl.ema.AutoManager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ExampleViewHolder> implements Filterable {
    private ArrayList<Cars> mExampleList;
    private ArrayList<Cars> mEampleListFull;
    private OnItemClickListener mListener;



    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener; }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mDeleteImage;
        public ExampleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView10);
            mDeleteImage=itemView.findViewById(R.id.image_delete);
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public MyAdapter(ArrayList<Cars> exampleList) {

        this.mExampleList = exampleList;
        mEampleListFull=new ArrayList<>(exampleList);
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Cars currentItem = mExampleList.get(position);
        holder.mImageView.setImageBitmap(BitmapExtraConverters.StringToBitMap(currentItem.getImageResource()));
        String defaultText = currentItem.getName();

        if (defaultText.isEmpty()) {
            currentItem.setName("Unnamed");
        }
        holder.mTextView1.setText(currentItem.getName());
        holder.mTextView2.setText(currentItem.getModel());
        if (currentItem.getYearServer() == 0) {
            holder.mTextView3.setText("");
        } else if (currentItem.ServiceIn().equals("SERVICE IST FÄLLIG !!")) {

            holder.mTextView3.setTextColor(Color.RED);
            holder.mTextView3.setText(currentItem.ServiceIn());
        } else {
            holder.mTextView3.setText(currentItem.ServiceIn());
        }
    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
    @Override
    public Filter getFilter() {
        return mExampleFilter;
    }

    private Filter mExampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Cars> filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                filteredList.addAll(mEampleListFull);
            }
            else {
                String filterPattern= constraint.toString().toLowerCase().trim();

                for(Cars item: mEampleListFull){

                    if (item.getName().toLowerCase().contains(filterPattern)|| item.getModel().toLowerCase().contains(filterPattern )|| item.ServiceIn().toLowerCase().contains(filterPattern )){
                        filteredList.add(item);

                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mExampleList.clear();
            mExampleList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


}
