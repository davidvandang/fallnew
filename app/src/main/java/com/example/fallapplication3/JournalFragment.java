package com.example.fallapplication3;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class JournalFragment extends Fragment {
    private LinearLayout incidentsContainer;
    private JournalDatabase journalDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        incidentsContainer = view.findViewById(R.id.incidents_container);
        journalDatabase = new JournalDatabase(getActivity());

        refreshIncidentsList();

        return view;
    }

    private void refreshIncidentsList() {
        incidentsContainer.removeAllViews();
        List<Incident> incidents = journalDatabase.getAllIncidents();

        for (int i = 0; i < incidents.size(); i++) {
            Incident incident = incidents.get(i);
            View incidentView = getLayoutInflater().inflate(R.layout.incident, incidentsContainer, false);

            TextView idTextView = incidentView.findViewById(R.id.id_row);
            TextView dateTextView = incidentView.findViewById(R.id.date_row);
            TextView timeTextView = incidentView.findViewById(R.id.time_row);

            idTextView.setText(String.valueOf(incident.getId()));
            dateTextView.setText(incident.getDate());
            timeTextView.setText(incident.getTime());

            if (i % 2 == 0) {
                incidentView.setBackgroundColor(Color.parseColor("#C2C59E"));
            } else {
                incidentView.setBackgroundColor(Color.parseColor("#EDEDED"));
            }

            incidentsContainer.addView(incidentView);
        }
    }
    private void addIncidentToTable(Incident incident, boolean isHeader) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.incident, null);

        TextView idTextView = rowView.findViewById(R.id.id_row);
        TextView dateTextView = rowView.findViewById(R.id.date_row);
        TextView timeTextView = rowView.findViewById(R.id.time_row);

        if (isHeader) {
            idTextView.setText(R.string.id_header);
            dateTextView.setText(R.string.date_header);
            timeTextView.setText(R.string.time_header);
        } else {
            idTextView.setText(String.valueOf(incident.getId()));
            dateTextView.setText(incident.getDate());
            timeTextView.setText(incident.getTime());
        }

        // Set content descriptions
        idTextView.setContentDescription(isHeader ? "ID header" : "ID: " + incident.getId());
        dateTextView.setContentDescription(isHeader ? "Date header" : "Date: " + incident.getDate());
        timeTextView.setContentDescription(isHeader ? "Time header" : "Time: " + incident.getTime());

        incidentsContainer.addView(rowView);
    }

}
