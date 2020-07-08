package org.jeecg.modules.zzj.util;

public class FuYiClock {
    static {
        //System.loadLibrary("K7X0_Dll");
        //需要将K7X0_DLL.dll文件放到项目的同级目录
        System.load(System.getProperty("user.dir")+"\\FxHotels.Fresh.View.Doors.dll");
    }

    public static void main(String[] args) {
        System.load(System.getProperty("user.dir")+"\\FxHotels.Fresh.View.Doors.dll");
        FuYiClock fuYiClock=new FuYiClock();

    }

    byte[] ReceBuf=new byte[500];
    public int Buflength=0;
    public int CardPosition=0;
    public int CardType=0;
    public int RCH=0;

    public native int CreateFlowID();

}
