package nksystems.cvmaker.createresume;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import nksystems.cvmaker.CreateResume;
import nksystems.cvmaker.DatabaseQueries;
import nksystems.cvmaker.R;

/**
 * Created by Charmy on 15/09/2016.
 */
public class PersonalInterestDetails extends Fragment {

    View rootView;
    DatabaseQueries dbQueries;
    EditText etHobby;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.resume_personal_interest_activity, container, false);
        dbQueries = new DatabaseQueries(PersonalInterestDetails.this.getContext());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CreateResume resume = new CreateResume();
        Boolean isEdit = getActivity().getIntent().getBooleanExtra("isEdit",false);
        if(isEdit){
            String ctNameId = resume.getEditDetails(dbQueries,getActivity().getIntent());
            String fileid = ctNameId.split("~")[1];
            Cursor piCursor= dbQueries.selectPersonalInterests(fileid);
            piCursor.moveToFirst();
            String personalInterest="";
            if(piCursor.getCount() > 0)
                personalInterest=piCursor.getString(piCursor.getColumnIndex("interestdesc"));

            etHobby=(EditText)rootView.findViewById(R.id.etHobbies);
            etHobby.setText(personalInterest);
        }
    }
}
