package actions;

import helpers.common.CommonComponent;
import helpers.common.CommonConstans;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogEnvironment {

    static String  fechaHoraInicio;
    static long    tiempoInicioTotal;
    static long    tiempoInicioTest;

    public static void on(String dato){

        CommonComponent cc= new CommonComponent();
        cc.StartCommonLog();
        // obtener el ambiente cuando ya este el excel
        cc.RecordInLog("************************************************************");
        cc.RecordInLog("                       ".concat(dato));
        cc.RecordInLog("************************************************************");
        fechaHoraInicio   = new SimpleDateFormat(CommonConstans.MASCARA_FECHA_HORA).format(Calendar.getInstance().getTime());
        cc.RecordInLog("FECHA INICIO: ".concat(fechaHoraInicio));
        //CommonComponent.registrarEnLog("Ambiente: ".concat(getAmbiente()));
        cc.RecordInLog("Ambiente: "+"parte para TINA");
        System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, CommonConstans.GECKO_DRIVER);
    }
}
