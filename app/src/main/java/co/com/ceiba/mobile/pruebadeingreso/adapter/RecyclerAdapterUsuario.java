package co.com.ceiba.mobile.pruebadeingreso.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.Usuario;
import co.com.ceiba.mobile.pruebadeingreso.view.PostActivity;

public class RecyclerAdapterUsuario extends RecyclerView.Adapter<RecyclerAdapterUsuario.MyViewHolder>{

    //region Atributos
    public Context context;
    private List<Usuario> usuarioList;
    private List<Usuario> usuarioListFiltered;
    private ArrayList<Usuario> musuarioListFiltered;
    //endregion

    @NonNull
    @Override
    public RecyclerAdapterUsuario.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,parent,false);
        return new MyViewHolder(view);
    }
    public void setUsuarioList(Context context, final List<Usuario> usuarioList) {
        this.context = context;
        if(this.usuarioList == null){
            this.usuarioList = usuarioList;
            this.usuarioListFiltered = usuarioList;
            notifyItemChanged(0, usuarioListFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RecyclerAdapterUsuario.this.usuarioList.size();
                }

                @Override
                public int getNewListSize() {
                    return usuarioList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return RecyclerAdapterUsuario.this.usuarioList.get(oldItemPosition).getName() == usuarioList.get(newItemPosition).getName();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Usuario newMovie = RecyclerAdapterUsuario.this.usuarioList.get(oldItemPosition);
                    Usuario oldMovie = usuarioList.get(newItemPosition);
                    return newMovie.getName() == oldMovie.getName() ;
                }
            });
            this.usuarioList = usuarioList;
            this.usuarioListFiltered = usuarioList;
            result.dispatchUpdatesTo(this);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterUsuario.MyViewHolder holder, int position) {
        holder.txtid.setText(usuarioList.get(position).getId().toString());
        holder.txtid.setVisibility(View.GONE);
        holder.txtname.setText(usuarioList.get(position).getName().toString());
        holder.txtphone.setText(usuarioList.get(position).getPhone().toString());
        holder.txtemail.setText(usuarioList.get(position).getEmail().toString());
    }

    @Override
    public int getItemCount() {
        if(usuarioList != null){
            return usuarioList.size();
        }
        return 0;
    }
    // Este método filtrará la lista
    // aquí estamos pasando los datos filtrados
    // y asignándolo a la lista con el método notifydatasetchanged
    public void filterLists(ArrayList<Usuario> filteredList) {
        usuarioList = filteredList;
        if(usuarioList != null){
            notifyDataSetChanged();
        }

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtid;
        TextView txtname;
        TextView txtphone;
        TextView txtemail;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtid = (TextView)itemView.findViewById(R.id.txtid);
            txtname = (TextView)itemView.findViewById(R.id.name);
            txtphone = (TextView)itemView.findViewById(R.id.phone);
            txtemail = (TextView)itemView.findViewById(R.id.email);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (v.getId() != 0) {
                        //Bundle bundle = new Bundle();
                        Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), PostActivity.class);
                        String datoId = String.valueOf(txtid.getText());
                        String datoName = String.valueOf(txtname.getText());
                        String datoPhone = String.valueOf(txtphone.getText());
                        String datoEmail = String.valueOf(txtemail.getText());
                        Bundle mBundle = new Bundle();
                        mBundle.putString("name",datoName);
                        mBundle.putString("phone",datoPhone);
                        mBundle.putString("email",datoEmail);
                        mBundle.putString("id", datoId);
                        intent.putExtras(mBundle);
                        v.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }
}
