package com.autosenseindia.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autosenseindia.R;
import com.autosenseindia.models.User;
import com.autosenseindia.activities.DetailsActivity;
import com.autosenseindia.activities.UsersActivity;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> implements Filterable {

    private Context context;
    private ArrayList<User> userArrayList;
    private ArrayList<User> usersListFiltered;

    private UsersActivity usersActivity;

    public UsersAdapter(UsersActivity usersActivity, Context context, ArrayList<User> userArrayList) {

        this.usersActivity = usersActivity;
        this.context = context;
        this.userArrayList = userArrayList;
        this.usersListFiltered = userArrayList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_row, viewGroup, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int i) {

        usersViewHolder.name.setText(usersListFiltered.get(i).getName());
        usersViewHolder.designation.setText(usersListFiltered.get(i).getDesignation());
        usersViewHolder.salary.setText(usersListFiltered.get(i).getSalary());
    }

    @Override
    public int getItemCount() {
        return usersListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    usersListFiltered = userArrayList;
                } else {
                    ArrayList<User> filteredList = new ArrayList<User>();
                    for (User user : userArrayList) {

                        if (user.getName().toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(user);
                        }
                    }

                    usersListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = usersListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                usersListFiltered = (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        private TextView name, designation, salary;

        public UsersViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            designation = itemView.findViewById(R.id.designation);
            salary = itemView.findViewById(R.id.salary);

            final RelativeLayout layout = itemView.findViewById(R.id.userLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DetailsActivity.class);

                    intent.putExtra("user", usersListFiltered.get(getAdapterPosition()));

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(usersActivity,
                            layout,
                            ViewCompat.getTransitionName(layout));

                    context.startActivity(intent, options.toBundle());
                }
            });
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getDesignation() {
            return designation;
        }

        public void setDesignation(TextView designation) {
            this.designation = designation;
        }
    }

}
