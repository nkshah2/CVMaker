package nksystems.cvmaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;


/**
 * Created by apple on 20/06/16.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    List<String> data;
    DatabaseQueries dbQueries;
    private static LayoutInflater inflater = null;
    String filename="",date="";

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.custom_list_item, null);
        TextView text = (TextView) vi.findViewById(R.id.tvTitle);

        //remove this comment
        String[] filearray = data.get(position).split("~");
        for(int i=0;i<filearray.length;i++){
            if(i == 0){
                filename = filearray[i];
            }
            else if(i == 1){
                date = filearray[i];
            }
        }
        text.setText(filename);
        TextView datetext = (TextView) vi.findViewById(R.id.tvDate);
        datetext.setText(date);
        dbQueries = new DatabaseQueries(CustomAdapter.this.context);
        final ImageButton menu=(ImageButton)vi.findViewById(R.id.btMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(CustomAdapter.this.context,menu);
                popupMenu.getMenuInflater().inflate(R.menu.action_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Edit")){

                            if(checkIfFileExists(data.get(position).split("~")[0]+".pdf")){


                                Intent intent= new Intent(CustomAdapter.this.context,CreateResume.class);
                                intent.putExtra("isEdit",true);
                                intent.putExtra("filename",data.get(position).split("~")[0]);
                                CustomAdapter.this.context.startActivity(intent);

                                /*Cursor personalCursor= dbQueries.selectPersonalInformation(data.get(position));
                                String fileid=personalCursor.getString(personalCursor.getColumnIndex("fileid"));
                                Cursor piCursor= dbQueries.selectPersonalInterests(fileid);
                                Cursor ecCursor= dbQueries.selectECInformation(fileid);
                                Cursor skillCursor= dbQueries.selectSkillInformation(fileid);
                                Cursor achievementCursor= dbQueries.selectCertificationInformation(fileid);
                                Cursor experienceCursor= dbQueries.selectCertificationInformation(fileid);
                                Cursor certificationCursor= dbQueries.selectCertificationInformation(fileid);
                                Cursor educationCursor= dbQueries.selectEducationInformation(fileid);

                                String title=personalCursor.getString(personalCursor.getColumnIndex("title"));
                                //String fileid=personalCursor.getString(personalCursor.getColumnIndex("fileid"));
                                String firstname=personalCursor.getString(personalCursor.getColumnIndex("firstname"));
                                String lastname=personalCursor.getString(personalCursor.getColumnIndex("lastname"));
                                String dateofbirth=personalCursor.getString(personalCursor.getColumnIndex("dateofbirth"));
                                String email=personalCursor.getString(personalCursor.getColumnIndex("email"));
                                String phoneext=personalCursor.getString(personalCursor.getColumnIndex("phoneext"));
                                String phoneno=personalCursor.getString(personalCursor.getColumnIndex("phoneno"));
                                String adress=personalCursor.getString(personalCursor.getColumnIndex("address"));
                                String objective=personalCursor.getString(personalCursor.getColumnIndex("objective"));
                                String template=personalCursor.getString(personalCursor.getColumnIndex("template"));



                                String highestEdu,tenthCGPA,tenthSchool,tenthBoard,tenthYOP,twelfthCGPA,twelfthInstitute,twelfthBoard,twelfthYOP,dipCGPA,
                                        dipInsti,dipBoard,dipYOP,collegeCGPA,collegeInsti,collegeBoard,collegeYOP,collegeDegree,pgCGPA,pgInsti,pgBoard,
                                        pgYOP,pgDegree;

                                highestEdu=educationCursor.getString(educationCursor.getColumnIndex("highestedu"));
                                tenthCGPA=educationCursor.getString(educationCursor.getColumnIndex("tenthgrade"));
                                tenthSchool=educationCursor.getString(educationCursor.getColumnIndex("tenthinsti"));
                                tenthBoard=educationCursor.getString(educationCursor.getColumnIndex("tenthboard"));
                                tenthYOP=educationCursor.getString(educationCursor.getColumnIndex("tenthyop"));
                                twelfthCGPA=educationCursor.getString(educationCursor.getColumnIndex("twelfthgrade"));
                                twelfthInstitute=educationCursor.getString(educationCursor.getColumnIndex("twelfthinsti"));
                                twelfthBoard=educationCursor.getString(educationCursor.getColumnIndex("twelfthboard"));
                                twelfthYOP=educationCursor.getString(educationCursor.getColumnIndex("twelfthyop"));
                                dipCGPA=educationCursor.getString(educationCursor.getColumnIndex("diplomagrade"));
                                dipInsti=educationCursor.getString(educationCursor.getColumnIndex("diplomainsti"));
                                dipBoard=educationCursor.getString(educationCursor.getColumnIndex("diplomaboard"));
                                dipYOP=educationCursor.getString(educationCursor.getColumnIndex("diplomayop"));
                                collegeCGPA=educationCursor.getString(educationCursor.getColumnIndex("gradgrade"));
                                collegeInsti=educationCursor.getString(educationCursor.getColumnIndex("gradinsti"));
                                collegeBoard=educationCursor.getString(educationCursor.getColumnIndex("gradboard"));
                                collegeYOP=educationCursor.getString(educationCursor.getColumnIndex("gradyop"));
                                collegeDegree=educationCursor.getString(educationCursor.getColumnIndex("graddegree"));
                                pgCGPA=educationCursor.getString(educationCursor.getColumnIndex("pggrade"));
                                pgInsti=educationCursor.getString(educationCursor.getColumnIndex("pginsti"));
                                pgBoard=educationCursor.getString(educationCursor.getColumnIndex("pgboard"));
                                pgYOP=educationCursor.getString(educationCursor.getColumnIndex("pgyop"));
                                pgDegree=educationCursor.getString(educationCursor.getColumnIndex("pgdegree"));



                                String certTitle1,certYear1,certTitle2,certYear2,certTitle3,certYear3,certTitle4,certYear4,certTitle5,certYear5,certTitle6,
                                        certYear6;

                                certTitle1=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle1"));
                                certYear1=certificationCursor.getString(certificationCursor.getColumnIndex("certyear1"));
                                certTitle2=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle2"));
                                certYear2=certificationCursor.getString(certificationCursor.getColumnIndex("certyear2"));
                                certTitle3=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle3"));
                                certYear3=certificationCursor.getString(certificationCursor.getColumnIndex("certyear3"));
                                certTitle4=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle4"));
                                certYear4=certificationCursor.getString(certificationCursor.getColumnIndex("certyear4"));
                                certTitle5=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle5"));
                                certYear5=certificationCursor.getString(certificationCursor.getColumnIndex("certyear5"));
                                certTitle6=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle6"));
                                certYear6=certificationCursor.getString(certificationCursor.getColumnIndex("certyear6"));



                                String coName1,duration1,proName1,role1,roleDesc1,proDesc1,coName2,duration2,proName2,role2,roleDesc2,proDesc2,coName3,
                                        duration3,proName3,role3,roleDesc3,proDesc3,coName4,duration4,proName4,role4,roleDesc4,proDesc4,coName5,duration5,
                                        proName5,role5,roleDesc5,proDesc5,coName6,duration6,proName6,role6,roleDesc6,proDesc6;

                                coName1=experienceCursor.getString(experienceCursor.getColumnIndex("companyname1"));
                                duration1=experienceCursor.getString(experienceCursor.getColumnIndex("duration1"));
                                role1=experienceCursor.getString(experienceCursor.getColumnIndex("role1"));
                                proDesc1=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc1"));
                                coName2=experienceCursor.getString(experienceCursor.getColumnIndex("companyname2"));
                                duration2=experienceCursor.getString(experienceCursor.getColumnIndex("duration2"));
                                role2=experienceCursor.getString(experienceCursor.getColumnIndex("role2"));
                                proDesc2=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc2"));
                                coName3=experienceCursor.getString(experienceCursor.getColumnIndex("companyname3"));
                                duration3=experienceCursor.getString(experienceCursor.getColumnIndex("duration3"));
                                role3=experienceCursor.getString(experienceCursor.getColumnIndex("role3"));
                                proDesc3=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc3"));
                                coName4=experienceCursor.getString(experienceCursor.getColumnIndex("companyname4"));
                                duration4=experienceCursor.getString(experienceCursor.getColumnIndex("duration4"));
                                role4=experienceCursor.getString(experienceCursor.getColumnIndex("role4"));
                                proDesc4=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc4"));
                                coName5=experienceCursor.getString(experienceCursor.getColumnIndex("companyname5"));
                                duration5=experienceCursor.getString(experienceCursor.getColumnIndex("duration5"));
                                role5=experienceCursor.getString(experienceCursor.getColumnIndex("role5"));
                                proDesc5=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc5"));
                                coName6=experienceCursor.getString(experienceCursor.getColumnIndex("companyname6"));
                                duration6=experienceCursor.getString(experienceCursor.getColumnIndex("duration6"));
                                role6=experienceCursor.getString(experienceCursor.getColumnIndex("role6"));
                                proDesc6=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc6"));

                                String achieveTitle1,achieveDesc1,achieveTitle2,achieveDesc2,achieveTitle3,achieveDesc3,achieveTitle4,achieveDesc4,
                                        achieveTitle5,achieveDesc5,achieveTitle6,achieveDesc6;
                                String domain1,skill1,domain2,skill2,domain3,skill3,domain4,skill4,domain5,skill5,domain6,skill6;
                                String ec1,ec2,ec3,ec4,ec5,ec6;
                                String personalInterest;



                                achieveDesc1=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc1"));
                                achieveDesc2=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc2"));
                                achieveDesc3=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc3"));
                                achieveDesc4=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc4"));
                                achieveDesc5=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc5"));
                                achieveDesc6=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc6"));



                                domain1=skillCursor.getString(skillCursor.getColumnIndex("skillarea1"));
                                skill1=skillCursor.getString(skillCursor.getColumnIndex("skillset1"));
                                domain2=skillCursor.getString(skillCursor.getColumnIndex("skillarea2"));
                                skill2=skillCursor.getString(skillCursor.getColumnIndex("skillset2"));
                                domain3=skillCursor.getString(skillCursor.getColumnIndex("skillarea3"));
                                skill3=skillCursor.getString(skillCursor.getColumnIndex("skillset3"));
                                domain4=skillCursor.getString(skillCursor.getColumnIndex("skillarea4"));
                                skill4=skillCursor.getString(skillCursor.getColumnIndex("skillset4"));
                                domain5=skillCursor.getString(skillCursor.getColumnIndex("skillarea5"));
                                skill5=skillCursor.getString(skillCursor.getColumnIndex("skillset5"));
                                domain6=skillCursor.getString(skillCursor.getColumnIndex("skillarea6"));
                                skill6=skillCursor.getString(skillCursor.getColumnIndex("skillset6"));



                                ec1=ecCursor.getString(ecCursor.getColumnIndex("extracurr1"));
                                ec2=ecCursor.getString(ecCursor.getColumnIndex("extracurr2"));
                                ec3=ecCursor.getString(ecCursor.getColumnIndex("extracurr3"));
                                ec4=ecCursor.getString(ecCursor.getColumnIndex("extracurr4"));
                                ec5=ecCursor.getString(ecCursor.getColumnIndex("extracurr5"));
                                ec6=ecCursor.getString(ecCursor.getColumnIndex("extracurr6"));



                                personalInterest=piCursor.getString(piCursor.getColumnIndex("interestdesc"));
*/
                }
                else{
                    Toast.makeText(context,"The file seems to have been deleted.",Toast.LENGTH_SHORT).show();
                    dbQueries.deleteFileFromDb(data.get(position).split("~")[0]);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            CustomAdapter.this.context.startActivity(new Intent(CustomAdapter.this.context,ActivityMain.class));
                            // Actions to do after 10 seconds
                        }
                    }, 2000);
                }
                        }else if(item.getTitle().equals("Delete")){
                            dbQueries.deleteFileFromDb(data.get(position).split("~")[0]);
                if(checkIfFileExists(data.get(position).split("~")[0]+".pdf")){
                    File file = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator
                            +data.get(position).split("~")[0]+".pdf");
                    file.delete();
                }
                CustomAdapter.this.context.startActivity(new Intent(CustomAdapter.this.context,ActivityMain.class));
                        }else if(item.getTitle().equals("View")){
                            if(checkIfFileExists(data.get(position).split("~")[0]+".pdf")){
                                String filename = data.get(position).split("~")[0];
                                File file = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator
                                        +filename+".pdf");
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.fromFile(file),"application/pdf");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                CustomAdapter.this.context.startActivity(intent);
                            }
                            else{
                                Toast.makeText(context,"The file seems to have been deleted.",Toast.LENGTH_SHORT).show();
                                dbQueries.deleteFileFromDb(data.get(position).split("~")[0]);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        CustomAdapter.this.context.startActivity(new Intent(CustomAdapter.this.context,ActivityMain.class));
                                        // Actions to do after 10 seconds
                                    }
                                }, 2000);
                            }

                        }
                        return true;
                    }


                });
                popupMenu.show();
            }
        });
        /*ImageButton btEdit=(ImageButton)vi.findViewById(R.id.btEdit);
        ImageButton btDelete=(ImageButton)vi.findViewById(R.id.btDelete);
        ImageButton btShare=(ImageButton)vi.findViewById(R.id.btShare);
        dbQueries = new DatabaseQueries(CustomAdapter.this.context);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfFileExists(data.get(position)+".pdf")){
                    Cursor myCursor = dbQueries.getFileContent(data.get(position));
                    myCursor.moveToFirst();

                    if(myCursor.getCount() > 0){
                        String name=myCursor.getString(myCursor.getColumnIndex("fullname"));
                        String objective=myCursor.getString(myCursor.getColumnIndex("objective"));
                        String template=myCursor.getString(myCursor.getColumnIndex("template"));

                        Intent intent=new Intent(CustomAdapter.this.context,CreateResume.class);
                        intent.putExtra("filename",data.get(position));
                        intent.putExtra("name",name);
                        intent.putExtra("objective",objective);
                        intent.putExtra("forward",true);
                        intent.putExtra("template",template);
                        CustomAdapter.this.context.startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(context,"The file seems to have been deleted.",Toast.LENGTH_SHORT).show();
                    dbQueries.deleteFileFromDb(data.get(position));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            CustomAdapter.this.context.startActivity(new Intent(CustomAdapter.this.context,ActivityMain.class));
                            // Actions to do after 10 seconds
                        }
                    }, 2000);
                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbQueries.deleteFileFromDb(data.get(position));
                if(checkIfFileExists(data.get(position)+".pdf")){
                    File file = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator
                            +data.get(position)+".pdf");
                    file.delete();
                }
                CustomAdapter.this.context.startActivity(new Intent(CustomAdapter.this.context,ActivityMain.class));
            }
        });

        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfFileExists(data.get(position)+".pdf")){
                    String filename = data.get(position);
                    File file = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator
                            +filename+".pdf");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file),"application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    CustomAdapter.this.context.startActivity(intent);
                }
                else{
                    Toast.makeText(context,"The file seems to have been deleted.",Toast.LENGTH_SHORT).show();
                    dbQueries.deleteFileFromDb(data.get(position));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            CustomAdapter.this.context.startActivity(new Intent(CustomAdapter.this.context,ActivityMain.class));
                            // Actions to do after 10 seconds
                        }
                    }, 2000);
                }
            }
        });*/
        return vi;
    }

    public CustomAdapter(Context context, List<String> data){

        this.context=context;
        this.data=data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private boolean checkIfFileExists(String filename){
        /*Toast.makeText(CustomAdapter.this.context, Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+filename, Toast.LENGTH_LONG).show();*/
        File file = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+filename);
        return file.exists();
    }

}
