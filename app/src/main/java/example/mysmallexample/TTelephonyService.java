package example.mysmallexample;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.test.AndroidTestCase;

/**
 * Created by zhengnan on 2016/5/24.
 * 主要提供了一系列用于访问与手机通讯相关的状态和信息的get方法
 * 位置，sim卡，监听电话。
 */
public class TTelephonyService extends AndroidTestCase {
    public void testMain(){
        TelephonyManager t  = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }

    //基本信息获取
    public void phoneInfo(TelephonyManager t){
        String imei = t.getDeviceId();
        String imsi = t.getSubscriberId();
        //sim卡相关信息
        ///String simInfo = t.getSimXXX;
    }
    //位置信息  -- 需要位置权限。
    public void positionAbout(TelephonyManager t){
        //基站获取
        CellLocation cellLocation = t.getCellLocation();
        if(cellLocation.getClass()== GsmCellLocation.class){
            //移动手机的可获取cellID，lac等基站信息
            GsmCellLocation gs = (GsmCellLocation)cellLocation;
            int cellId = gs.getCid();
            int lac = gs.getLac();
        }else if(cellLocation.getClass() == CdmaCellLocation.class) {
            CdmaCellLocation cd = (CdmaCellLocation) cellLocation;
            //获取电信获取联通相关的信息。
        }
        //其它位置相关的获取，如经纬度，见：LocationManager

    }

    public void elseInfo(TelephonyManager t){
        //监听通话状态
        //t.listen(xxx, PhoneStateListener.LISTEN_CALL_STATE);

    }

}
