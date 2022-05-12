package helpers.common;

public class CommonConstans {

  // TODO Auto-generated constructor stub

  public static final String  DIRECTORIO_REPORTES                                      = System.getProperty("user.home").replace('\\', '/') + "/Downloads/";
  public static final String  DIRECTORIO_PROYECTOS                            = System.getProperty("user.dir").replace('\\', '/') + "/";
  public static final String  GECKO_DRIVER                                             = "./resource/geckodriver.exe";

  public static final int     TIME_OUT                                                 = 60;
  public static final String  DIRECTORIO_LOGS                                          = "./logs/";
  public static final String  ARCHIVO_LOG                                              = "log.log";
  public static final String  ARCHIVO_LOG_GECKODRIVER                                  = "logGecko.log";

  public static final String  MASCARA_FECHA_HORA                                       = "dd/MM/yyyy HH:mm:ss.SSS";
  public static final String  MASCARA_FECHA                                            = "dd/MM/yyyy";
  public static final String  TITULO_VENTANA_REPORTE                                   = "RUAT.NET";

  public static final String  ARCHIVO_DATOS_PAGINA                                     = "DataPageGenerator.xls";
  public static final String  HOJA_DATOS_O_PARAMETRO_AMBIENTE                          = "AMBIENTE";
  public static final String  ARCHIVO_DATOS_PRUEBA                                     = "DatosPrueba.xls";


}
