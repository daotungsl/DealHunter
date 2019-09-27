package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.util.StringUtil;

public class TEST {
    public static void main(String[] args) {
        String password = "Tung1991";
        String md51 = new StringUtil().encryptMD5(password);
        String md52 = new StringUtil().encryptMD5(password);
        if (md51.equals(md52)){
            System.out.println(md51);
            System.out.println(true);
        }
        else {
            System.out.println(false);
        }
    }
}
