package nksystems.cvmaker.createresume;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class AchievementDetails extends Fragment {

    View rootView;
    DatabaseQueries dbQueries;
    EditText etAchieveDesc1,etAchieveDesc2,etAchieveDesc3,etAchieveDesc4,etAchieveDesc5,etAchieveDesc6;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.resume_achievement_details_activity, container, false);
        dbQueries = new DatabaseQueries(AchievementDetails.this.getContext());
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
            Cursor achievementCursor= dbQueries.selectAchieveInformation(fileid);
            achievementCursor.moveToFirst();

            String achieveDesc1,achieveDesc2,achieveDesc3,achieveDesc4,achieveDesc5,achieveDesc6;

            achieveDesc1=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc1"));
            achieveDesc2=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc2"));
            achieveDesc3=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc3"));
            achieveDesc4=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc4"));
            achieveDesc5=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc5"));
            achieveDesc6=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc6"));

            etAchieveDesc1=(EditText)rootView.findViewById(R.id.etAchieveDesc1);
            etAchieveDesc2=(EditText)rootView.findViewById(R.id.etAchieveDesc2);
            etAchieveDesc3=(EditText)rootView.findViewById(R.id.etAchieveDesc3);
            etAchieveDesc4=(EditText)rootView.findViewById(R.id.etAchieveDesc4);
            etAchieveDesc5=(EditText)rootView.findViewById(R.id.etAchieveDesc5);
            etAchieveDesc6=(EditText)rootView.findViewById(R.id.etAchieveDesc6);

            etAchieveDesc1.setText(achieveDesc1);
            etAchieveDesc2.setText(achieveDesc2);
            etAchieveDesc3.setText(achieveDesc3);
            etAchieveDesc4.setText(achieveDesc4);
            etAchieveDesc5.setText(achieveDesc5);
            etAchieveDesc6.setText(achieveDesc6);
        }
    }
}
