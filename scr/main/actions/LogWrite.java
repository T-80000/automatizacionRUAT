package actions;

import helpers.common.CommonComponent;

public class LogWrite {

    public static void with(String text){
        CommonComponent cc= new CommonComponent();
        cc.StartCommonLog();
        cc.RecordInLog(text);
    }
}
