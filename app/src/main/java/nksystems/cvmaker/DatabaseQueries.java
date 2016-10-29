package nksystems.cvmaker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Charmy on 19/06/2016.
 */
public class DatabaseQueries {
    SQLiteDatabase db;
    DatabaseObject dbObject;
    Calendar c= Calendar.getInstance();
    SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");


    public DatabaseQueries(Context context){
        dbObject = new DatabaseObject(context);
        db = dbObject.getConnection();
    }

    public SQLiteDatabase returnInstance(){

        return db;
    }


    public Cursor getFileList(){


        Cursor myCursor = db.rawQuery("select coalesce(filename,'') || '~'|| coalesce(strftime('%d-%m-%Y',updationdate),'') || '~' || coalesce(template,'') as filename, " +
                "template from personal_details",null);
        long count = db.compileStatement("select count(*) from database_theme").simpleQueryForLong();
        String currenttheme = db.compileStatement("select current_theme from database_theme").simpleQueryForString();
        Log.i("DatabaseQueries","Count:"+count + " Current Theme: " + currenttheme);
        return myCursor;
    }

    public Cursor getFileContent(String filename){
        filename=filename.replaceAll("'","");
        Cursor myCursor = db.rawQuery("select * from personal_details a left join personal_interests b on a.fileid=b.fileid " +
                "left join extra_curriculars c on a.fileid=c.fileid left join skills_details d on a.fileid=d.fileid " +
                "left join achievement_details e on a.fileid=e.fileid left join certification_details f on a.fileid=f.fileid " +
                "left join education_details g on a.fileid=g.fileid left join experience_details h on a.fileid=h.fileid where a.filename='"+filename+"'",null);
        return myCursor;
    }

    public void addPersonalInfo(String filename, String objective, String firstname,String lastname, String template,String DOB,
                                String email,String ext,String phone,String address,String title){
        filename=filename.replaceAll("'","");
        objective=objective.replaceAll("'","''");
        firstname=firstname.replaceAll("'","''");
        lastname=lastname.replaceAll("'","''");
        long count = db.compileStatement("select count(*) from personal_details where filename='"+filename+"'").simpleQueryForLong();
        if(count > 0){
            /*String txtSql = "update content_information set objective='"+objective+"',fullname='"+fullname+"'," +
                    "creationdate=date('now'),updationdate=date('now'),template='"+template+"' " +
                    "where filename='"+filename+"'";*/
            String txtSql = "update personal_details set title='"+title+"',firstname='"+firstname+"',lastname='"+lastname+"',dateofbirth='"+DOB+"'," +
                    "email='"+email+"',phoneext='"+ext+"',phoneno='"+phone+"',address='"+address+"',objective='"+objective+"'," +
                    "updationdate=date('now'),template='"+template+"' where filename='"+filename+"'";
            db.execSQL(txtSql);
        }
        else{
            /*String txtSql = "insert into content_information (filename,objective,fullname,creationdate,template) " +
                    "values ('"+filename+"','"+objective+"','"+fullname+"',date('now'),'"+template+"')";*/
            String txtSql = "insert into personal_details(filename,title,firstname,lastname,dateofbirth,email,phoneext," +
                    "phoneno,address,objective,updationdate,template) "+"values('"+filename+"','"+title+"','"+firstname+"'," +
                    "'"+lastname+"','"+DOB+"','"+email+"','"+ext+"','"+phone+"','"+address+"','"+objective+"'," +
                    "date('now'),'"+template+"')";
            db.execSQL(txtSql);
        }
    }

    public void deleteFileFromDb(String filename){
        long fileid=getFileId(filename);
        db.execSQL("delete from personal_details where filename='"+filename+"'");
        db.execSQL("delete from education_details where fileid="+fileid);
        db.execSQL("delete from certification_details where fileid="+fileid);
        db.execSQL("delete from experience_details where fileid="+fileid);
        db.execSQL("delete from achievement_details where fileid="+fileid);
        db.execSQL("delete from skills_details where fileid="+fileid);
        db.execSQL("delete from extra_curriculars where fileid="+fileid);
        db.execSQL("delete from personal_interests where fileid="+fileid);
    }

    public void addEducationInfo(String filename,String highestEdu, String tenthGrade, String tenthInsti, String tenthBoard,
                                 String tenthYOP,String twelfthGrade, String twelfthInsti, String twelfthBoard, String twelfthYOP,
                                 String dipGrade, String dipInsti, String dipBoard, String dipYOP,String collegeGrade,
                                 String collegeDegree, String collegeBoard, String collegeYOP, String collegeName,
                                 String pgGrade, String pgDegree, String pgBoard, String pgYOP, String pgName){
        filename=filename.replaceAll("'","");
        highestEdu=highestEdu.replaceAll("'","''");
        tenthGrade=tenthGrade.replaceAll("'","''");
        tenthInsti=tenthInsti.replaceAll("'","''");
        tenthBoard=tenthBoard.replaceAll("'","''");
        tenthYOP=tenthYOP.replaceAll("'","''");
        twelfthGrade=twelfthGrade.replaceAll("'","''");
        twelfthInsti=twelfthInsti.replaceAll("'","''");
        twelfthBoard=twelfthBoard.replaceAll("'","''");
        twelfthYOP=twelfthYOP.replaceAll("'","''");
        dipGrade=dipGrade.replaceAll("'","''");
        dipInsti=dipInsti.replaceAll("'","''");
        dipBoard=dipBoard.replaceAll("'","''");
        dipYOP=dipYOP.replaceAll("'","''");
        collegeGrade=collegeGrade.replaceAll("'","''");
        collegeDegree=collegeDegree.replaceAll("'","''");
        collegeBoard=collegeBoard.replaceAll("'","''");
        collegeYOP=collegeYOP.replaceAll("'","''");
        collegeName=collegeName.replaceAll("'","''");
        pgGrade=pgGrade.replaceAll("'","''");
        pgDegree=pgDegree.replaceAll("'","''");
        pgBoard=pgBoard.replaceAll("'","''");
        pgYOP=pgYOP.replaceAll("'","''");
        pgName=pgName.replaceAll("'","''");
        long fileid=getFileId(filename);
        long count = db.compileStatement("select count(*) from education_details " +
                "where fileid="+ fileid).simpleQueryForLong();
        if(count > 0){
            String txtSql = "update education_details set highestedu='"+highestEdu+"',tenthgrade='"+tenthGrade+"'," +
                    "tenthinsti='"+tenthInsti+"',tenthboard='"+tenthBoard+"',tenthyop='"+tenthYOP+"',twelfthgrade='"+twelfthGrade+"'," +
                    "twelfthinsti='"+twelfthInsti+"',twelfthboard='"+twelfthBoard+"',twelfthyop='"+twelfthYOP+"'," +
                    "diplomagrade='"+dipGrade+"',diplomainsti='"+dipInsti+"',diplomaboard='"+dipBoard+"',diplomayop='"+dipYOP+"'," +
                    "graddegree='"+collegeDegree+"',gradgrade='"+collegeGrade+"',gradinsti='"+collegeBoard+"',gradcollege='"+collegeName+"'," +
                    "gradyop='"+collegeYOP+"',pgdegree='"+pgDegree+"',pggrade='"+pgGrade+"',pginsti='"+pgBoard+"',pgcollege='"+pgName+"'," +
                    "pgyop='"+pgYOP+"' where fileid="+fileid;
            db.execSQL(txtSql);
        }
        else{
            String txtSql = "insert into education_details(fileid,highestedu,tenthgrade,tenthinsti,tenthboard,tenthyop,twelfthgrade," +
                    "twelfthinsti,twelfthboard,twelfthyop,diplomagrade,diplomainsti,diplomaboard,diplomayop,graddegree,gradgrade," +
                    "gradinsti,gradcollege,gradyop,pgdegree,pggrade,pginsti,pgcollege,pgyop)" +
                    "values("+fileid+",'"+highestEdu+"','"+tenthGrade+"','"+tenthInsti+"','"+tenthBoard+"','"+tenthYOP+"'," +
                    "'"+twelfthGrade+"','"+twelfthInsti+"','"+twelfthBoard+"','"+twelfthYOP+"','"+dipGrade+"','"+dipInsti+"'," +
                    "'"+dipBoard+"','"+dipYOP+"','"+collegeDegree+"','"+collegeGrade+"','"+collegeBoard+"','"+collegeName+"'," +
                    "'"+collegeYOP+"','"+pgDegree+"','"+pgGrade+"','"+pgBoard+"','"+pgName+"','"+pgYOP+"')";
            db.execSQL(txtSql);
        }
        updatePersonalDetailsDate(filename);
    }

    public void addPersonalInterestInfo(String filename, String personalInterest){
        filename=filename.replaceAll("'","");
        personalInterest=personalInterest.replaceAll("'","''");
        long fileid=getFileId(filename);
        long count = db.compileStatement("select count(*) from personal_interests " +
                "where fileid="+ fileid).simpleQueryForLong();
        if(count > 0){
            String txtSql = "update personal_interests set interestdesc='"+personalInterest+"' where fileid="+fileid;
            db.execSQL(txtSql);
        }
        else{
            String txtSql = "insert into personal_interests (fileid,interestdesc) values ("+fileid+",'"+personalInterest+"')";
            db.execSQL(txtSql);
        }
        updatePersonalDetailsDate(filename);
    }

    public void addExtraCurricularsInfo(String filename, String ec1, String ec2, String ec3, String ec4, String ec5, String ec6){
        filename=filename.replaceAll("'","");
        ec1=ec1.replaceAll("'","''");
        ec2=ec2.replaceAll("'","''");
        ec3=ec3.replaceAll("'","''");
        ec4=ec4.replaceAll("'","''");
        ec5=ec5.replaceAll("'","''");
        ec6=ec6.replaceAll("'","''");
        long fileid=getFileId(filename);
        long count = db.compileStatement("select count(*) from extra_curriculars " +
                "where fileid="+ fileid).simpleQueryForLong();
        if(count > 0){
            String txtSql = "update extra_curriculars set extracurr1='"+ec1+"',extracurr2='"+ec2+"',extracurr3='"+ec3+"'," +
                    "extracurr4='"+ec4+"',extracurr5='"+ec5+"',extracurr6='"+ec6+"' where fileid="+fileid;
            db.execSQL(txtSql);
        }
        else{
            String txtSql = "insert into extra_curriculars (fileid,extracurr1,extracurr2,extracurr3,extracurr4,extracurr5,extracurr6) " +
                    "values ("+fileid+",'"+ec1+"','"+ec2+"','"+ec3+"','"+ec4+"','"+ec5+"','"+ec6+"')";
            db.execSQL(txtSql);
        }
        updatePersonalDetailsDate(filename);
    }

    public void addSkillsInfo(String filename, String domain1, String skill1, String domain2, String skill2, String domain3, String skill3,
                              String domain4, String skill4, String domain5, String skill5, String domain6, String skill6){
        filename=filename.replaceAll("'","");
        domain1=domain1.replaceAll("'","''");
        skill1=skill1.replaceAll("'","''");
        domain2=domain2.replaceAll("'","''");
        skill2=skill2.replaceAll("'","''");
        domain3=domain3.replaceAll("'","''");
        skill3=skill3.replaceAll("'","''");
        domain4=domain4.replaceAll("'","''");
        skill4=skill4.replaceAll("'","''");
        domain5=domain5.replaceAll("'","''");
        skill5=skill5.replaceAll("'","''");
        domain6=domain6.replaceAll("'","''");
        skill6=skill6.replaceAll("'","''");
        long fileid=getFileId(filename);
        long count = db.compileStatement("select count(*) from skills_details " +
                "where fileid="+ fileid).simpleQueryForLong();
        if(count > 0){
            String txtSql = "update skills_details set skillarea1='"+domain1+"',skillset1='"+skill1+"',skillarea2='"+domain2+"'," +
                    "skillset2='"+skill2+"',skillarea3='"+domain3+"',skillset3='"+skill3+"',skillarea4='"+domain4+"'," +
                    "skillset4='"+skill4+"',skillarea5='"+domain4+"',skillset5='"+skill5+"',skillarea6='"+domain6+"'," +
                    "skillset6='"+skill6+"' where fileid="+fileid;
            db.execSQL(txtSql);
        }
        else{
            String txtSql = "insert into skills_details (fileid,skillarea1,skillset1,skillarea2,skillset2,skillarea3,skillset3," +
                    "skillarea4,skillset4,skillarea5,skillset5,skillarea6,skillset6) " +
                    "values ("+fileid+",'"+domain1+"','"+skill1+"','"+domain2+"','"+skill2+"','"+domain3+"','"+skill3+"','"+domain4+"'," +
                    "'"+skill4+"','"+domain5+"','"+skill5+"','"+domain6+"','"+skill6+"')";
            db.execSQL(txtSql);
        }
        updatePersonalDetailsDate(filename);
    }

    public void addAchievementsInfo(String filename, String achieveDesc1, String achieveDesc2, String achieveDesc3, String achieveDesc4,
                                    String achieveDesc5, String achieveDesc6){
        filename=filename.replaceAll("'","");
        achieveDesc1=achieveDesc1.replaceAll("'","");
        achieveDesc2=achieveDesc2.replaceAll("'","");
        achieveDesc3=achieveDesc3.replaceAll("'","");
        achieveDesc4=achieveDesc4.replaceAll("'","");
        achieveDesc5=achieveDesc5.replaceAll("'","");
        achieveDesc6=achieveDesc6.replaceAll("'","");

        long fileid=getFileId(filename);
        long count = db.compileStatement("select count(*) from achievement_details " +
                "where fileid="+ fileid).simpleQueryForLong();
        if(count > 0){
            String txtSql = "update achievement_details set achievedesc1='"+achieveDesc1+"',achievedesc2='"+achieveDesc2+"'," +
                    "achievedesc3='"+achieveDesc3+"',achievedesc4='"+achieveDesc4+"',achievedesc5='"+achieveDesc5+"'," +
                    "achievedesc6='"+achieveDesc6+"' where fileid="+fileid;
            db.execSQL(txtSql);
        }
        else{
            String txtSql = "insert into achievement_details (fileid,achievedesc1,achievedesc2,achievedesc3,achievedesc4,achievedesc5," +
                    "achievedesc6) values ("+fileid+",'"+achieveDesc1+"','"+achieveDesc2+"','"+achieveDesc3+"','"+achieveDesc4+"'," +
                    "'"+achieveDesc5+"','"+achieveDesc6+"')";
            db.execSQL(txtSql);
        }
        updatePersonalDetailsDate(filename);
    }

    public void addCertificationInfo(String filename, String certTitle1, String certYear1, String certTitle2, String certYear2,
                                     String certTitle3, String certYear3, String certTitle4, String certYear4, String certTitle5,
                                     String certYear5, String certTitle6, String certYear6){
        filename=filename.replaceAll("'","");
        certTitle1=certTitle1.replaceAll("'","''");
        certTitle2=certTitle2.replaceAll("'","''");
        certTitle3=certTitle3.replaceAll("'","''");
        certTitle4=certTitle4.replaceAll("'","''");
        certTitle5=certTitle5.replaceAll("'","''");
        certTitle6=certTitle6.replaceAll("'","''");
        long fileid=getFileId(filename);
        long count = db.compileStatement("select count(*) from certification_details " +
                "where fileid="+ fileid).simpleQueryForLong();
        if(count > 0){
            String txtSql = "update certification_details set certtitle1='"+certTitle1+"',certyear1='"+certYear1+"'," +
                    "certtitle2='"+certTitle2+"',certyear2='"+certYear2+"',certtitle3='"+certTitle3+"'," +
                    "certyear3='"+certYear3+"',certtitle4='"+certTitle4+"',certyear4='"+certYear4+"',certtitle5='"+certTitle5+"'," +
                    "certyear5='"+certYear5+"',certtitle6='"+certTitle6+"',certyear6='"+certYear6+"' where fileid="+fileid;
            db.execSQL(txtSql);
        }
        else{
            String txtSql = "insert into certification_details (fileid,certtitle1,certyear1,certtitle2,certyear2,certtitle3," +
                    "certyear3,certtitle4,certyear4,certtitle5,certyear5,certtitle6,certyear6) values ("+fileid+",'"+certTitle1+"'," +
                    "'"+certYear1+"','"+certTitle2+"','"+certYear2+"','"+certTitle3+"','"+certYear3+"','"+certTitle4+"','"+certYear4+"'," +
                    "'"+certTitle5+"','"+certYear5+"','"+certTitle6+"','"+certYear6+"')";
            db.execSQL(txtSql);
        }
        updatePersonalDetailsDate(filename);
    }

    public void addWorkExperienceInfo(String filename, String coName1, String duration1, String role1, String desc1, String coName2, String duration2,
                                      String role2, String desc2, String coName3, String duration3, String role3, String desc3, String coName4, String duration4,
                                      String role4, String desc4, String coName5, String duration5, String role5, String desc5, String coName6, String duration6,
                                      String role6, String desc6){
        filename=filename.replaceAll("'","");
        coName1=coName1.replaceAll("'","''");
        duration1=duration1.replaceAll("'","''");
        role1=role1.replaceAll("'","''");
        desc1=desc1.replaceAll("'","''");
        coName2=coName2.replaceAll("'","''");
        duration2=duration2.replaceAll("'","''");
        role2=role2.replaceAll("'","''");
        desc2=desc2.replaceAll("'","''");
        coName3=coName3.replaceAll("'","''");
        duration3=duration3.replaceAll("'","''");
        role3=role3.replaceAll("'","''");
        desc3=desc3.replaceAll("'","''");
        coName4=coName4.replaceAll("'","''");
        duration4=duration4.replaceAll("'","''");
        role4=role4.replaceAll("'","''");
        desc4=desc4.replaceAll("'","''");
        coName5=coName5.replaceAll("'","''");
        duration5=duration5.replaceAll("'","''");
        role5=role5.replaceAll("'","''");
        desc5=desc5.replaceAll("'","''");
        coName6=coName6.replaceAll("'","''");
        duration6=duration6.replaceAll("'","''");
        role6=role6.replaceAll("'","''");
        desc6=desc6.replaceAll("'","''");
        long fileid=getFileId(filename);
        long count = db.compileStatement("select count(*) from experience_details " +
                "where fileid="+ fileid).simpleQueryForLong();
        if(count > 0){
            String txtSql = "update experience_details set companyname1='"+coName1+"',duration1='"+duration1+"'," +
                    "projectdesc1='"+desc1+"',role1='"+role1+"',companyname2='"+coName2+"'," +
                    "duration2='"+duration2+"',projectdesc2='"+desc2+"',role2='"+role2+"',companyname3='"+coName3+"'," +
                    "duration3='"+duration3+"',projectdesc3='"+desc3+"',role3='"+role3+"',companyname4='"+coName4+"',duration4='"+duration4+"'," +
                    "projectdesc4='"+desc4+"',role4='"+role4+"',companyname5='"+coName5+"',duration5='"+duration5+"',projectdesc5='"+desc5+"'," +
                    "role5='"+role5+"',companyname6='"+coName6+"',duration6='"+duration6+"',projectdesc6='"+desc6+"',role6='"+role6+"' where fileid="+fileid;
            db.execSQL(txtSql);
        }
        else{
            String txtSql = "insert into experience_details (fileid,companyname1,duration1,projectdesc1,role1,companyname2,duration2,projectdesc2,role2,companyname3,duration3," +
                    "projectdesc3,role3,companyname4,duration4,projectdesc4,role4,companyname5,duration5,projectdesc5,role5,companyname6,duration6,projectdesc6,role6) " +
                    "values ("+fileid+",'"+coName1+"','"+duration1+"','"+desc1+"','"+role1+"','"+coName2+"','"+duration2+"','"+desc2+"','"+role2+"','"+coName3+"','"+duration3+"'," +
                    "'"+desc3+"','"+role3+"','"+coName4+"','"+duration4+"','"+desc4+"','"+role4+"','"+coName5+"','"+duration5+"','"+desc5+"','"+role5+"','"+coName6+"'," +
                    "'"+duration6+"','"+desc6+"','"+role6+"')";
            db.execSQL(txtSql);
        }
        updatePersonalDetailsDate(filename);
    }

    public long getFileId(String filename){
        long fileid = db.compileStatement("select fileid from personal_details where filename='"+filename+"'").simpleQueryForLong();
        return fileid;
    }

    public void updatePersonalDetailsDate(String filename){
        db.execSQL("update personal_details set updationdate=date('now') where filename='"+filename+"'");
    }

    public Cursor selectPersonalInformation(String filename){

        Cursor myCursor= db.rawQuery("select * from personal_details where filename='"+filename+"'",null);
        return myCursor;

    }

    public Cursor selectEducationInformation(String fileid){

        Cursor myCursor= db.rawQuery("select * from education_details where fileid='"+fileid+"'",null);
        return myCursor;

    }

    public Cursor selectCertificationInformation(String fileid){

        Cursor myCursor= db.rawQuery("select * from certification_details where fileid='"+fileid+"'",null);
        return myCursor;

    }

    public Cursor selectExpInformation(String fileid){

        Cursor myCursor= db.rawQuery("select * from experience_details where fileid='"+fileid+"'",null);
        return myCursor;

    }

    public Cursor selectAchieveInformation(String fileid){

        Cursor myCursor= db.rawQuery("select * from achievement_details where fileid='"+fileid+"'",null);
        return myCursor;

    }

    public Cursor selectSkillInformation(String fileid){

        Cursor myCursor= db.rawQuery("select * from skills_details where fileid='"+fileid+"'",null);
        return myCursor;

    }

    public Cursor selectECInformation(String fileid){

        Cursor myCursor= db.rawQuery("select * from extra_curriculars where fileid='"+fileid+"'",null);
        return myCursor;

    }

    public Cursor selectPersonalInterests(String fileid){

        Cursor myCursor= db.rawQuery("select * from personal_interests where fileid="+fileid,null);
        return myCursor;

    }
}
