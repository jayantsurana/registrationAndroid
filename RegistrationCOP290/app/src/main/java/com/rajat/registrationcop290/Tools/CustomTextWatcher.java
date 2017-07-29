package com.rajat.registrationcop290.Tools;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import com.rajat.registrationcop290.R;

/**
 * Created by Rajat on 14-01-2016.
 */
public class CustomTextWatcher implements TextWatcher {
    EditText eText;
    int id;
    Validate validation=new Validate();
    public AutoCompleteTextView cv;
    ArrayAdapter<String > cvAdapter;
    public CustomTextWatcher(EditText et,int id){
        this.eText =et;
        this.id=id;
    }
    public CustomTextWatcher(EditText et,int id,AutoCompleteTextView cacv,ArrayAdapter<String> arrAdap){
        eText =et;
        this.id=id;
        cv=cacv;
        cvAdapter=arrAdap;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void onTextChanged(CharSequence userInput, int i, int i1, int i2) {
        if(id==1 || id==2){
            if(cv!=null && cvAdapter!=null){
                cvDrop();
            }
        }
    }
    @Override
    public void afterTextChanged(Editable editable) {

        if (editable.length() == 0) {
            if(id==0){
                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_group_white_24dp, 0, 0,0);
            }else if(id==1){
                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_payment_white_24dp,0, 0,0);
            }else if(id==2){
                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_account_circle_white_24dp,0, 0,0);
            }
        } else {
            if(id==1){
                if(!(validation.validate_entryno(eText.getText().toString()))){
                    eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_payment_white_24dp,0, R.drawable.ic_thumb_down_white_24dp,0);
                }
                else{
                    eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_payment_white_24dp, 0, R.drawable.ic_thumb_up_white_24dp, 0);
                }
                cvSelect();
            }
            else if(id==2){
                if(!(validation.validate_name(eText.getText().toString()))) {
                    eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_account_circle_white_24dp, 0, R.drawable.ic_thumb_down_white_24dp, 0);
                }
                else{
                    eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_account_circle_white_24dp, 0, R.drawable.ic_thumb_up_white_24dp, 0);
                }
                cvSelect();
            }
            else{
                if(eText.getText().length()==0) {
                    eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_group_white_24dp, 0, R.drawable.ic_thumb_down_white_24dp, 0);
                }
                else{
                    eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_group_white_24dp, 0, R.drawable.ic_thumb_up_white_24dp, 0);
                }
            }
        }
    }
    public boolean dropOrNot=true;
public void cvDrop(){

    Log.i("rajat", "cv.isPopUpShowing: " + cv.isPopupShowing() + " cv.isFocusable(); " + cv.isFocusable());
    final int[] edTextLocation = new int[2];
    eText.getLocationOnScreen(edTextLocation);
    cv.setText(eText.getText());
    cv.setVerticalScrollBarEnabled(true);
    cv.setDropDownBackgroundResource(R.color.ultraLightGray);

    if(eText!=null){
        int width=ContextCompat.getDrawable(eText.getContext(), R.drawable.transparent).getIntrinsicWidth();
        cv.setDropDownHorizontalOffset(edTextLocation[0]+width);
        cv.setDropDownWidth(eText.getWidth() / 2 -width/2);
    }else{
        cv.setDropDownHorizontalOffset(edTextLocation[0]);
        cv.setDropDownWidth(eText.getWidth() / 2);
    }
    cv.setDropDownVerticalOffset(edTextLocation[1]+(eText.getHeight()*3)/4);

        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("rajat", cv.getLineCount()+": cv.getLineCount()"+ cv.getAdapter().getCount());
                    if (dropOrNot) {
                        if (cv.getAdapter().getCount() == 0) {
                            cv.setDropDownHeight(0);
                        } else if (cv.getAdapter().getCount() == 1) {
                            cv.setDropDownHeight(eText.getHeight() );
                        }  else if (cv.getAdapter().getCount() == 2) {
                            cv.setDropDownHeight(eText.getHeight() * 3);
                        } else if (cv.getAdapter().getCount() == 3) {
                            cv.setDropDownHeight(eText.getHeight() * 4);
                        } else {
                            cv.setDropDownHeight(eText.getHeight() * 5);
                        }
                        cv.showDropDown();
                    }
                }
            }, 100L);

        } catch (WindowManager.BadTokenException b) {
            Log.i("rajat", "showDropdown" + b.getLocalizedMessage());
        }

    Log.i("rajat", edTextLocation[0] + " x,y " + edTextLocation[1] + "editText.getY(): " + eText.getY());
}
    public void cvSelect(){
        if (cv != null) {
            Log.i("rajat", "cv.isPopUpShowing: " + cv.isPopupShowing() + " cv.isFocusable(); " + cv.isFocusable());
            cv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("rajat", "cv.onItemClickCalled: " + adapterView.getItemAtPosition(i));

                    eText.setText(adapterView.getItemAtPosition(i) + "");//i + " " + l + " " +
                    dropOrNot=false;
                    cv.dismissDropDown();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dropOrNot = true;
                        }
                    }, 500);
                }
            });
        }
    }
}
