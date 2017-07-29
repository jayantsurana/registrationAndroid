package com.rajat.registrationcop290;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.rajat.registrationcop290.Tools.CheckNetwork;
import com.rajat.registrationcop290.Tools.CustomTextWatcher;
import com.rajat.registrationcop290.Tools.Student;
import com.rajat.registrationcop290.Tools.Tools;
import com.rajat.registrationcop290.Tools.Validate;
import com.rajat.registrationcop290.Volley.CallVolley;
import com.rajat.registrationcop290.Volley.VolleySingleton;

public class RegisterActivity extends AppCompatActivity {
    MediaPlayer sound = null;
    Toolbar toolbar;
    int titleId;
    AutoCompleteTextView cacv;
    public ArrayAdapter<String> enAdap;
    AutoCompleteTextView cacvName;
    public ArrayAdapter<String> nameAdap;
    public String[] enNums = new String[] {"Please search...","Don't search...","Please search...","Don't search...","Please search...","Don't search..."};
    public String[] stdNames = new String[] {"Please search...","Don't search...","Please search...","Don't search...","Please search...","Don't search..."};
    EditText teamName,
            entryNum1, entryNum2, entryNum3,
            name1, name2, name3;

    String TeamName="",
            entryNumber1="", entryNumber2="", entryNumber3="",
            studentName1="", studentName2="", studentName3="";
    Button submit;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        LinearLayout layout = (LinearLayout) findViewById(R.id.main_lay);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        layout.startAnimation(a);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Registration COP290" + "</font>")));
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Student.getS();
                Student.getEntryNum();
                Student.getNames();
                enNums=Student.studentEntryNum;
                stdNames=Student.studentNames;
                initializeViews();
            }
        };
        handler.postDelayed(r, 200);
    }
    public void initializeViews(){
        teamName = (EditText)findViewById(R.id.team_name);
        entryNum1 = (EditText)findViewById(R.id.EntryNum1);
        entryNum2 = (EditText)findViewById(R.id.EntryNum2);
        entryNum3 = (EditText)findViewById(R.id.EntryNum3);
        name1 = (EditText)findViewById(R.id.Name1);
        name2 = (EditText)findViewById(R.id.Name2);
        name3 = (EditText)findViewById(R.id.Name3);
        cacv = new AutoCompleteTextView(RegisterActivity.this);
        cacvName= new AutoCompleteTextView(RegisterActivity.this);
        enAdap = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, enNums);
        nameAdap = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, stdNames);
        cacv.setThreshold(1);
        cacv.setAdapter(enAdap);
        enAdap.notifyDataSetChanged();
        cacvName.setThreshold(1);
        cacvName.setAdapter(nameAdap);
        nameAdap.notifyDataSetChanged();
        teamName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        entryNum1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        entryNum2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        entryNum3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        name1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        name2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        name3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        teamName.addTextChangedListener(new CustomTextWatcher(teamName,0));
        entryNum1.addTextChangedListener(new CustomTextWatcher(entryNum1,1,cacv,enAdap));
        entryNum2.addTextChangedListener(new CustomTextWatcher(entryNum2,1,cacv,enAdap));
        entryNum3.addTextChangedListener(new CustomTextWatcher(entryNum3,1,cacv,enAdap));
        name1.addTextChangedListener(new CustomTextWatcher(name1,2,cacvName,nameAdap));
        name2.addTextChangedListener(new CustomTextWatcher(name2,2,cacvName,nameAdap));
        name3.addTextChangedListener(new CustomTextWatcher(name3,2,cacvName,nameAdap));
        submit =(Button)findViewById(R.id.submitNames);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitClick();
            }
        });
    }
    public void onSubmitClick(){
        Validate validate1=new Validate();
        TeamName =teamName.getText().toString();
        entryNumber1=entryNum1.getText().toString().toUpperCase();
        entryNumber2=entryNum2.getText().toString().toUpperCase();
        entryNumber3=entryNum3.getText().toString().toUpperCase();
        studentName1=name1.getText().toString().toUpperCase();
        studentName2=name2.getText().toString().toUpperCase();
        studentName3=name3.getText().toString().toUpperCase();
        boolean valid=false;
        if(   teamName.getText().length()>0
                && validate1.validate_entryno(entryNumber1)&& validate1.validate_name(studentName1)
                && validate1.validate_entryno(entryNumber2)&& validate1.validate_name(studentName2)
                && !entryNumber1.equals(entryNumber2) && !studentName2.equals(studentName1)
                ) {

            if(entryNum3.getText().length()>0 || name3.getText().length()>0){
                if(validate1.validate_entryno(entryNumber3)&& validate1.validate_name(studentName3)
                        && !entryNumber1.equals(entryNumber3) && !entryNumber3.equals(entryNumber2)
                        && !studentName2.equals(studentName3) && !studentName3.equals(studentName1)
                        ){
                    valid=true;
                }else{
                    if (sound == null) {
                        if (!validate1.validate_entryno(entryNumber3)) {
                            sound = MediaPlayer.create(this, R.raw.sentry3);
                        } else if (!validate1.validate_name(studentName3)) {
                            sound = MediaPlayer.create(this, R.raw.names3);
                        }else if(entryNumber1.equals(entryNumber3)){
                            sound = MediaPlayer.create(this, R.raw.similar_entry);////////////////
                        }else if(entryNumber2.equals(entryNumber3)){
                            sound = MediaPlayer.create(this, R.raw.similar_entry);////////////////
                        }else if(studentName3.equals(studentName1)){
                            sound = MediaPlayer.create(this, R.raw.similar_names);////////////////
                        }else if(studentName2.equals(studentName3)){
                            sound = MediaPlayer.create(this, R.raw.similar_names);////////////////
                        }
                        sound.start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sound.release();
                                sound = null;
                            }
                        }, sound.getDuration());
                    }else {
                        Toast.makeText(RegisterActivity.this, "Have some patience duh...", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                valid=true;
            }
        }else{
            if (sound == null) {
                if(teamName.getText().length()==0){
                    sound = MediaPlayer.create(this, R.raw.team);
                }else if(!validate1.validate_entryno(entryNumber1)){
                    sound = MediaPlayer.create(this, R.raw.sentry1);
                }else  if(!validate1.validate_name(studentName1) ){
                    sound = MediaPlayer.create(this, R.raw.names1);
                }else if(!validate1.validate_entryno(entryNumber2)){
                    sound = MediaPlayer.create(this, R.raw.sentry2);
                }else  if(!validate1.validate_name(studentName2) ){
                    sound = MediaPlayer.create(this, R.raw.names2);
                }else if(entryNumber1.equals(entryNumber2)){
                    sound = MediaPlayer.create(this, R.raw.similar_entry);////////////////
                }else if(studentName2.equals(studentName1)){
                    sound = MediaPlayer.create(this, R.raw.similar_names);
                }
                sound.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sound.release();
                        sound = null;
                    }
                }, sound.getDuration());
            }else {
                Toast.makeText(RegisterActivity.this, "Have some patience duh...", Toast.LENGTH_SHORT).show();
            }
        }
        if(valid){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 200 milliseconds
            v.vibrate(200);
            CheckNetwork chkNet = new CheckNetwork(RegisterActivity.this);
            String URL = "http://192.168.43.4:3000/api/register";
            if (!chkNet.checkNetwork()) {
                VolleySingleton.getInstance(RegisterActivity.this).getRequestQueue().getCache().clear();

                CallVolley.makeRegistrationCall(URL, TeamName, entryNumber1, studentName1,
                      entryNumber2, studentName2,
                    entryNumber3, studentName3, RegisterActivity.this);//getActivity()//getApplicationContext()
            } else {
                Tools.showAlertDialog("Internet Unavailable", RegisterActivity.this);
            }
        }else{
            final Animation animat= AnimationUtils.loadAnimation(this, R.anim.shake);
            submit.startAnimation(animat);
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 200 milliseconds
            v.vibrate(200);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    submit.startAnimation(animat);
                }
            },100);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    submit.startAnimation(animat);
                }
            }, 200);
        }
    }
    boolean doubleBackToExitPressedOnce=false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}