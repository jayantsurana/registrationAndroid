package com.rajat.registrationcop290.Tools;

/**
 * Created by Lenovo on 1/12/2016.
 */
public class Validate{
    public boolean validate_entryno(String text)  {
        int length=text.length();
        if(length==11){
            try{
                String year = text.substring(0,4);
                int checker = Integer.parseInt(year);
                if (checker < 2016) {
                    Integer.parseInt(text.substring(text.length() - 5, text.length()));
                    char[] comp=text.substring(4,6).toCharArray();
                    for(char c:comp){
                        if(!(Character.isLetter(c))){
                            return false;
                        }
                    }
                    return true;

                } else {
                    return false;
                }
            }
            catch(NumberFormatException e){
                return false;
            }
        }
        return false;
    }
    public boolean validate_name(String text){
        char[] comp=text.toCharArray();
        for(char c:comp){
            if(!(Character.isSpaceChar(c)||Character.isLetter(c))){
                return false;
            }
        }
        if(text.length()==0){
            return false;
        }
        return true;
    }
}

