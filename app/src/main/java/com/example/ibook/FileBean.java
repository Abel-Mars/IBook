package com.example.ibook;

import org.litepal.crud.LitePalSupport;

public class FileBean extends LitePalSupport {
    /** 文件的路径*/
    public String file_lujin="";
    public int choose=0;
    public int exst=0;
    public int getExst() {
        return exst;
    }

    public void setExst(int exst) {
        this.exst = exst;
    }



    public int getChoose() {
        return choose;
    }
    public void setChoose(int choose) {
        this.choose = choose;
    }
    public FileBean(String file_lujin) {
        this.file_lujin = file_lujin;
    }
    @Override
    public String toString() {
        return file_lujin ;
    }
}