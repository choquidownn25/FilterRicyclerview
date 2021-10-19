package co.com.ceiba.mobile.pruebadeingreso.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.Usuario;

public class RecyclerAdapterPost extends RecyclerView.Adapter<RecyclerAdapterPost.MyViewHolder> {

    //region Atributos
    private Context context;
    private List<Post> postList;
    private List<Post> postListFiltered;
    //endregion

    public void setPostList(Context context, final List<Post> postList) {
        this.context = context;
        if(this.postList == null){
            this.postList = postList;
            this.postListFiltered = postList;
            notifyItemChanged(0, postListFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RecyclerAdapterPost.this.postList.size();
                }

                @Override
                public int getNewListSize() {
                    return postList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return RecyclerAdapterPost.this.postList.get(oldItemPosition).getTitle() == postList.get(newItemPosition).getTitle();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Post newPost = RecyclerAdapterPost.this.postList.get(oldItemPosition);
                    Post oldPost = postList.get(newItemPosition);
                    return newPost.getTitle() == oldPost.getTitle() ;
                }
            });
            this.postList = postList;
            this.postListFiltered = postList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapterPost.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item,parent,false);
        return new RecyclerAdapterPost.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterPost.MyViewHolder holder, int position) {
        holder.txttitle.setText(postList.get(position).getTitle().toString());
        holder.txtbody.setText(postList.get(position).getBody().toString());
    }

    @Override
    public int getItemCount() {
        if(postList != null){
            return postList.size();
        }
        return 0;
    }
    // Este método filtrará la lista
    // aquí estamos pasando los datos filtrados
    // y asignándolo a la lista con el método notifydatasetchanged
    public void filterLists(ArrayList<Post> filteredList) {
        postList = filteredList;
        if(postList != null){
            notifyDataSetChanged();
        }

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txttitle;
        TextView txtbody;

        public MyViewHolder(View itemView) {
            super(itemView);
            txttitle = (TextView)itemView.findViewById(R.id.title);
            txtbody = (TextView)itemView.findViewById(R.id.body);
        }

    }
}
