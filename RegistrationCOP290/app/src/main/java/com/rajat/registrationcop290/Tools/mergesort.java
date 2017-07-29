package com.rajat.registrationcop290.Tools;


/**
 * Created by Lenovo on 1/19/2016.
 */

    public class mergesort {
        public mergesort(String[] tobe){
            String[] aux=new String[tobe.length];
            sort(tobe,aux,0,tobe.length-1);
        }
        private void merge(String[] tobe,String[] aux,int start,int mid,int end){
            for(int k=start;k<=end;k++){
                aux[k]=tobe[k];
            }
            int j = mid+1;
            for(int k=start;k<=end;k++){
                if(start>mid){tobe[k]=aux[j++];}
                else if(j>end){tobe[k]=aux[start++];}
                else if(aux[start].compareToIgnoreCase(aux[j])<=0){tobe[k]=aux[start++];}
                else{tobe[k]=aux[j++];}
            }
        }
        private void sort(String[] tobe,String[] aux,int i,int j){
            if (j<=i)return;
            int mid=i+((j-i)/2);
            sort(tobe,aux,i,mid);
            sort(tobe,aux,mid+1,j);
            merge(tobe,aux,i,mid,j);
        }
    }


