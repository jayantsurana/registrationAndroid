package com.rajat.registrationcop290.Volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rajat.registrationcop290.R;
import com.rajat.registrationcop290.Tools.Tools;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
public class CallVolley {
        private static ProgressDialog pDialog;
               private static MediaPlayer dsound=null;
        private static void setCustomRetryPolicy(StringRequest jsonObjReq) {
                Log.i("rajat", "setCustomRetryPolicy");
                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }

        public static void makeRegistrationCall(String url, final String teamname,final String entrynum1,final String name1,final String entrynum2,final String name2,final String entrynum3,final String name3, final Context context)
        {
                pDialog=  Tools.showProgressBar(context);

                //RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                        {


                                @Override
                                public void onResponse(String response)
                                {
                                        try
                                        {
                                                Log.i("rajat", " onResponseActive " + response);
                                                SubmitApiJsonParser(response, context);
                                                pDialog.dismiss();
                                        }
                                        catch (Exception localException)
                                        {
                                                Log.i("rajat"," onResponseException "+localException.getMessage());
                                                localException.printStackTrace();
                                        }
                                }
                        }
                                , new Response.ErrorListener()
                        {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                                //Toast.makeText(context,"Timeout",Toast.LENGTH_LONG).show();
                                                Tools.showAlertDialog("Timeout",context);
                                        } else if (error instanceof AuthFailureError) {
                                                //TODO
                                                Tools.showAlertDialog("Auth Fail",context);
                                        } else if (error instanceof ServerError) {
                                                //TODO
                                                Tools.showAlertDialog("Server Error",context);
                                        } else if (error instanceof NetworkError) {
                                                //TODO
                                                Tools.showAlertDialog("NetworkError",context);
                                        } else if (error instanceof ParseError) {
                                                //TODO
                                                Tools.showAlertDialog("ParseError",context);

                                        }
                                        //Tools.showAlertDialog(error.toString(),context);
                                        Log.i("rajat", "onErrorResponse" + error.toString());
                                           pDialog.dismiss();


                                }
                        })
                        {
                                @Override
                                protected Map<String, String> getParams()
                                {
                                        Log.i("rajat", " onResponseParamsExecute");
                                        Map<String, String> params = new HashMap<String, String>();

                                        try {
                                                Log.i("rajat", " onResponseParams" + teamname);
                                                params.put("teamname", teamname);
                                                params.put("entry1",entrynum1);
                                                params.put("name1",name1);
                                                params.put("entry2",entrynum2);
                                                params.put("name2",name2);
                                                params.put("entry3",entrynum3);
                                                params.put("name3",name3);
                                                Log.i("rajat","request: "+params);
                                        }
                                        catch (Exception e)
                                        {

                                                Log.i("rajat", "onExceptionParams" + e.toString());
                                        }

                                        return params;
                                }
                        };

                        setCustomRetryPolicy(request);
                        VolleySingleton.getInstance(context).addToRequestQueue(request);
                }
                public static void SubmitApiJsonParser(String JsonStringResult,Context con)
                {
                        try {
                                String status = "";
                                String message="";


                                JSONObject resultJson = new JSONObject(JsonStringResult);
                                if (resultJson.has("RESPONSE_SUCCESS"))
                                {
                                        status = resultJson.getString("RESPONSE_SUCCESS");
                                        message = resultJson.getString("RESPONSE_MESSAGE");
                                        if(status.equals("0"))
                                        {
                                                if(message.equals("Data not posted!"))
                                                {
                                                        Tools.showAlertDialog("Data not posted!",con);
                                                        dsound = MediaPlayer.create(con,R.raw.data_not_posted);
                                                        dsound.start();
                                                }
                                                else if(message.equals("User already registered"))
                                                {
                                                        Tools.showAlertDialog("User Already Registered",con);
                                                        dsound = MediaPlayer.create(con,R.raw.user_already_registered);
                                                        dsound.start();
                                                }
                                        }
                                        else
                                        {
                                                if(message.equals("Registration completed"))
                                                {
                                                        Tools.showAlertDialog("Registration completed",con);
                                                        dsound = MediaPlayer.create(con, R.raw.registration_completed);
                                                        dsound.start();

                                                }
                                        }
                                }else{
                                        Tools.showAlertDialog("Request Not Sent",con);
                                }
                        }
                        catch (Exception e)
                        {
                                Log.i("rajat", e.getLocalizedMessage());
                        }
                }
        }

