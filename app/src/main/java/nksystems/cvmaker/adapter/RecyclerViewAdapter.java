package nksystems.cvmaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import nksystems.cvmaker.ActivityMain;
import nksystems.cvmaker.CreateResume;
import nksystems.cvmaker.CustomAdapter;
import nksystems.cvmaker.DatabaseQueries;
import nksystems.cvmaker.R;

/**
 * Created by nksha on 22-09-2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    List<String> filelist;
    private static final int PENDING_REMOVAL_TIMEOUT = 3000;
    Context context;
    String filename,date, template;
    boolean undoOn;
    List<View> items;
    List<View> itemsPendingRemoval;
    int lastInsertedIndex;
    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<String, Runnable> pendingRunnables = new HashMap<>();
    DatabaseQueries dbQueries;

    public RecyclerViewAdapter(List<String> fileList, Context context){
        this.filelist=fileList;
        this.context=context;


    }

    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_recyclerview_item,parent,false);
        RecyclerViewHolder rh= new RecyclerViewHolder(item);
        return rh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {


        String name=filelist.get(position);
        //name=name.replace("~","");

        String[] filearray = name.split("~");
        for(int i=0;i<filearray.length;i++){
            if(i == 0){
                filename = filearray[i];
            }
            else if(i == 1){
                date = filearray[i];
            }else if(i==2){
                template=filearray[i];
            }
        }
//        String template=myCursor.getString(myCursor.getColumnIndex("template"));

        holder.tvName.setText(filename);
        holder.tvTemplate.setText(template);
        holder.tvDate.setText(date);

        String[] colors=context.getResources().getStringArray(R.array.colors);
        String[] temps=context.getResources().getStringArray(R.array.temps);

        for(int i=0;i<temps.length;i++){
            if(template.toLowerCase().equals(temps[i])){
                Drawable myDrawable=holder.tvImage.getBackground();
                int color= Color.parseColor(colors[i]);

                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                holder.tvImage.setBackground(myDrawable);
                String firstChar= temps[i].substring(0,1).toUpperCase();
                holder.tvImage.setText(firstChar);
            }
        }

    }



    @Override
    public int getItemCount() {
        return filelist.size();
    }





    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        protected TextView tvName;
        protected TextView tvTemplate;
        protected TextView tvDate;
        protected TextView tvImage;


        public RecyclerViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvTemplate=(TextView)itemView.findViewById(R.id.tvTemplate);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvImage=(TextView)itemView.findViewById(R.id.tvImage);
        }

        @Override
        public void onClick(View v) {

            dbQueries = new DatabaseQueries(RecyclerViewAdapter.this.context);
            TextView txtFilename=(TextView)v.findViewById(R.id.tvName);
            String filename = txtFilename.getText().toString();
            if(checkIfFileExists(filename+".pdf")){
                File file = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+filename+".pdf");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),"application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                RecyclerViewAdapter.this.context.startActivity(intent);
            }
            else{
                Toast.makeText(context,"The file seems to have been deleted.",Toast.LENGTH_SHORT).show();
                dbQueries.deleteFileFromDb(filename);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        RecyclerViewAdapter.this.context.startActivity(new Intent(RecyclerViewAdapter.this.context,ActivityMain.class));
                        // Actions to do after 10 seconds
                    }
                }, 2000);
            }

        }

        @Override
        public boolean onLongClick(View v) {
            TextView txtFilename=(TextView)v.findViewById(R.id.tvName);
            String filename = txtFilename.getText().toString();
            dbQueries = new DatabaseQueries(RecyclerViewAdapter.this.context);
            if(checkIfFileExists(filename+".pdf")){
               Intent intent=new Intent(RecyclerViewAdapter.this.context,CreateResume.class);
               intent.putExtra("filename",filename);
               intent.putExtra("isEdit",true);

               RecyclerViewAdapter.this.context.startActivity(intent);
            }
            else{
                Toast.makeText(context,"The file seems to have been deleted.",Toast.LENGTH_SHORT).show();
                dbQueries.deleteFileFromDb(filename);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        RecyclerViewAdapter.this.context.startActivity(new Intent(RecyclerViewAdapter.this.context,ActivityMain.class));
                        // Actions to do after 10 seconds
                    }
                }, 2000);
            }
            return true;
        }
    }

    private boolean checkIfFileExists(String filename){
        File file = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+filename);
        return file.exists();
    }
}
