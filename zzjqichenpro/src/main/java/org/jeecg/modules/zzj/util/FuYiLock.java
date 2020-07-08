package org.jeecg.modules.zzj.util;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.LongByReference;

public class FuYiLock {
    public interface CLibrary extends Library {
        // DLL文件默认路径为项目根目录，若DLL文件存放在项目外，请使用绝对路径。（此处：(Platform.isWindows()?"msvcrt":"c")指本地动态库msvcrt.dll）
        CLibrary INSTANCE = (CLibrary) Native.loadLibrary("F:\\门锁对接\\富驿门锁\\多平台SDK接口函数中文版\\DLL文件\\NOURF.DLL",
                CLibrary.class);

        // 声明将要调用的DLL中的方法,可以是多个方法(此处示例调用本地动态库msvcrt.dll中的printf()方法)
        int initializeUSB(String d12);

        int GuestCard(String d12, String dlsCoID, String CardNo, String Dai, String LLock,
                      String pdoors,String BDate, String EDate, String LockNo, String cardHexStr);

        int ReadCard(String d12, String buffData);
    }
}
