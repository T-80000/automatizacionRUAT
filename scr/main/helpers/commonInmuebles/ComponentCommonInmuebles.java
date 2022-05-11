package helpers.commonInmuebles;

import helpers.common.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ComponentCommonInmuebles {

    protected StringBuilder        mensajeError;
    protected long                 tiempoInicioTotal;
    protected long                 tiempoInicioTest;
    protected ArrayList < String > nombresArchivoReportes;
    protected boolean              usarDatosAmbienteTest;
    protected String               fechaHoraInicio;

    public ComponentCommonInmuebles()
    {
        this.mensajeError               = new StringBuilder(" ERROR: ");
        this.tiempoInicioTotal          = 0;
        this.tiempoInicioTest           = 0;
        this.nombresArchivoReportes     = new ArrayList<String>();
        this.usarDatosAmbienteTest      = false;
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, CommonConstans.DIRECTORIO_LOGS.concat(CommonConstans.ARCHIVO_LOG_GECKODRIVER));
        inicializarDriver();
    }

    public void inicializarDriver()
    {
        CommonComponent.registrarEnLog("************************************************************");
        CommonComponent.registrarEnLog("                       ".concat(this.getClass().getSimpleName().toUpperCase()));
        CommonComponent.registrarEnLog("************************************************************");
        this.fechaHoraInicio   = new SimpleDateFormat(CommonConstans.MASCARA_FECHA_HORA).format(Calendar.getInstance().getTime());
        this.tiempoInicioTotal = System.currentTimeMillis();
        this.tiempoInicioTest  = System.currentTimeMillis();
        //CommonComponent.registrarEnLog("Ambiente: ".concat(getAmbiente()));
        CommonComponent.registrarEnLog("Ambiente: "+"parte para TINA");
        System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, CommonConstans.GECKO_DRIVER);
    }
}
