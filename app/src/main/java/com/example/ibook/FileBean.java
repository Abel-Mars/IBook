package com.example.ibook;
public class FileBean {
    /** 文件的路径*/
    public String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**文件图片资源的id，drawable或mipmap文件中已经存放doc、xml、xls等文件的图片*/
    public String name;
    public FileBean(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path ;
    }
}