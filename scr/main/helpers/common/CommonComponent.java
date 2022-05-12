package helpers.common;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

import helpers.common.CommonConstans;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CommonComponent 
{

	public static String getNroAleatorio (int nroDigitos)
	{
		StringBuilder numAleatorio = new StringBuilder();
		
		for(int i = 0; i < nroDigitos; i ++)
			numAleatorio.append(String.valueOf((int) (Math.random() * 10)));
		
		return numAleatorio.toString();
	}
	
	public static int getNroAleatorio ( int min, int max )
	{
		return ThreadLocalRandom.current ( ).nextInt ( min, max + 1 );
	}
	
	public static String generarNroTramite()
	{
		return new StringBuilder().append("T-JAPONES").append(getNroAleatorio(6)).toString();
	}
	
	public static void cerrarVentana(Robot r)
	{
		r.keyPress(KeyEvent.VK_ALT);
	    r.keyPress(KeyEvent.VK_F4);	
	    r.keyRelease(KeyEvent.VK_ALT);
	    r.keyRelease(KeyEvent.VK_F4);
	}
	
	public static String obtenerCadenaTipoTitulo(String cadena)
	{
		StringTokenizer separador = new StringTokenizer(cadena);
		StringBuilder nuevaCadena = new StringBuilder();
		String aux = "";
		
		while(separador.hasMoreTokens())
		{
			aux = separador.nextToken();
			if(!aux.equals("IP") && !aux.equals("IMT"))
			{
				if(aux.equalsIgnoreCase("Y") || aux.equalsIgnoreCase("A"))
				{
					nuevaCadena.append(aux.toLowerCase());
				}
				else
				{
					nuevaCadena.append(aux.substring(0, 1).toUpperCase());
					nuevaCadena.append(aux.substring(1).toLowerCase());					
				}			
			}			
			else
			{
				nuevaCadena.append(aux);
			}
			nuevaCadena.append(" ");
		}
		nuevaCadena.deleteCharAt(nuevaCadena.length() - 1);
		
		return nuevaCadena.toString();
	}
	
	public static String obtenerNombreArchivoUltimoPdfGenerado()
	{
		File        archivos[]         = new File(CommonConstans.DIRECTORIO_REPORTES).listFiles();
		String      ultimoArchivo      = archivos[0].getName();
		long        ultimaModificacion = archivos[0].lastModified();		
		
		for(File f: archivos)
		{
			if(f.lastModified() > ultimaModificacion)
			{
				ultimoArchivo      = f.getName();
				ultimaModificacion = f.lastModified(); 
			}
		}
		return ultimoArchivo;
	}	
	
	public static String getContenidoPdf(String nombreArchivo)
	{
		String          contenidoPdf = "";	
		PDFParser       parserPdf    = null;
		COSDocument     cosDocumento = null;
		PDDocument      pdDocumento  = null;
		PDFTextStripper textStripper = null;
		
		try
		{
			parserPdf    = new PDFParser(new RandomAccessFile(new File (CommonConstans.DIRECTORIO_REPORTES.concat(nombreArchivo)), "r"));
			parserPdf.parse();
			
			cosDocumento = parserPdf.getDocument();
			textStripper = new PDFTextStripper();
			textStripper.setStartPage(1);
			
			pdDocumento  = new PDDocument(cosDocumento);
			contenidoPdf = textStripper.getText(pdDocumento);
			
			pdDocumento.close();
			cosDocumento.close();
		}
		catch(IOException archivoPdfExcepcion)
		{
			StringWriter cadenaExcepcion      = null;
			PrintWriter  redireccionExcepcion = null;	
			
			cadenaExcepcion      = new StringWriter();
		    redireccionExcepcion = new PrintWriter(cadenaExcepcion);
		    archivoPdfExcepcion.printStackTrace(redireccionExcepcion);
		}
		return contenidoPdf;
	}
	
	public static String obtenerContenidoUltimoPdfGenerado()
	{		
		return getContenidoPdf(obtenerNombreArchivoUltimoPdfGenerado());
	}
	
	public static Calendar convertirCadenaAFechaOrigenDatos(String fecha)
	{
		Calendar fechaResultado = Calendar.getInstance();;
		String   mascaraFecha   = "";		 
		
		if(fecha.contains("-"))
		{
			mascaraFecha = "dd-mm-yy";
		}
		else if(fecha.contains("/"))
		{
			mascaraFecha = "dd/mm/yy";
		}		
		try 
		{
			fechaResultado.setTime(new SimpleDateFormat(mascaraFecha).parse(fecha));
		} 
		catch (ParseException conversionFechaExcepcion) 
		{
			conversionFechaExcepcion.printStackTrace();
		}
		return fechaResultado;
	}
		
	
	/**
	 * M�todo que recibe el tiempo de ejecuci�n en milisegundos y le da el formato X min, Y seg, Z mseg.
	 * @param tiempoEjecucion
	 * @return tiempoFormateadoEjecucion
	 */
	public static String formatearTiempoEjecucion(long tiempoEjecucion)
	{
		String milisegundos = String.valueOf(tiempoEjecucion % 1000);
		tiempoEjecucion    /=  1000;
		String segundos     = (tiempoEjecucion % 60) > 0? String.valueOf(tiempoEjecucion % 60): String.valueOf(tiempoEjecucion);		
		tiempoEjecucion    /= 60;
		String minutos      = (tiempoEjecucion % 60 > 0)? String.valueOf(tiempoEjecucion): "";		
		
		return minutos + (minutos.isEmpty()? "": " min. ") + segundos + " seg. " + milisegundos + " mseg.";
	}
	

	
	 public static double redondearDecimales(double valorInicial, int numeroDecimales) 
	 {         
		 double parteEntera = 0; 
		 double resultado   = 0;
		 
		 resultado   = valorInicial;         
		 parteEntera = Math.floor(resultado); 
		 resultado   = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
		 resultado   = Math.round(resultado);
		 resultado   = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
		 return resultado;     
	}
	 
	public static String eliminarSaltosLinea(String cadena)
	{
		return ((cadena.replace('\n', ' ')).replace('\r', ' ')).replaceAll("  ", " ").trim();
	}
	
	public static void procesarExcepcion(Exception excepcion)
	{
		StringWriter cadenaExcepcion      = null;
		PrintWriter  redireccionExcepcion = null;	
		
		cadenaExcepcion      = new StringWriter();
	    redireccionExcepcion = new PrintWriter(cadenaExcepcion);
	    excepcion.printStackTrace(redireccionExcepcion);
	    //CommonComponent.registrarEnLog("EXCEPCI�N NO CONTROLADA: ".concat(cadenaExcepcion.toString()));
	}
	
	public static String eliminarPieEncabezadoPagina(String texto)
	{
		String parte1 = "";
		String parte2 = "";
		
		if(texto.contains("P�gina"))
		{
			parte1 = texto.substring(0,texto.indexOf("P�gina"));
			parte2 = texto.substring(texto.lastIndexOf(":") + 3, texto.length());
			texto = parte1.concat(parte2);			
		}
		return texto;
	}
	
	public static StringBuilder getHashtableEnFormatoTabla(String NOMBRES_COLUMNAS[], Hashtable<String, ArrayList<String>> tablaHash)
	{
		int           espacios [] = null;
		StringBuilder tabla       = null;
		
		espacios = new int[NOMBRES_COLUMNAS.length];		
		tabla    = new StringBuilder();		
		
		tabla.append(setEspacios(10));
		for(int i = 0; i <  tablaHash.size(); i++)
		{
			espacios[i] = getTamanioValorMaximoLista(NOMBRES_COLUMNAS[i], tablaHash.get(NOMBRES_COLUMNAS[i]));
			tabla.append(NOMBRES_COLUMNAS[i].concat(setEspacios(espacios[i] - NOMBRES_COLUMNAS[i].length() + 5)));
		}
		tabla.append("\n");
		
		for(int j = 0; j < tablaHash.get(NOMBRES_COLUMNAS[0]).size(); j ++)
		{
			tabla.append(setEspacios(10));
			for(int i = 0; i <  tablaHash.size(); i++)
			{	
				tabla.append(tablaHash.get(NOMBRES_COLUMNAS[i]).get(j).concat(setEspacios(espacios[i] - tablaHash.get(NOMBRES_COLUMNAS[i]).get(j).length() + 5)));			
			}
			tabla.append("\n");
		}
		
		return tabla;
	}
	
	private static int getTamanioValorMaximoLista(String nombreLista, ArrayList<String> lista)
	{
		int tamanioValorMaximo = 0;
		
		tamanioValorMaximo = nombreLista.length();		
		for(int i = 0; i < lista.size(); i++)
		{
			if(lista.get(i).length() > tamanioValorMaximo)
			{
				tamanioValorMaximo = lista.get(i).length();
			}
		}		
		return tamanioValorMaximo;
	}
	
	private static String setEspacios(int n)
	{
		StringBuilder espacios = new StringBuilder();
		
		for(int i = 0; i < n; i ++)
		{
			espacios.append(" ");
		}		
		return espacios.toString();
	}
	
	public static Calendar formateaFecha(String fechaEnCadena)
	{
		Calendar fecha = null;
		
		fecha = Calendar.getInstance();
		try 
		{
			fecha.setTime(new SimpleDateFormat(fechaEnCadena.contains("-")? "dd-mm-yy": "dd/mm/yy").parse(fechaEnCadena));
		} 
		catch (ParseException parseoFechaExcepcion) 
		{
			throw new AssertionError("Formato de fecha incorrecto, favor verificar.");
		}
		
		return fecha;
	}
	
	/**
	 * M�todo liberarRecursos(): mata los procesos geckodriver y firefox.
	 */
	public static void liberarRecursos()
	{
		try 
		{			
			Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
			Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
		}
		catch (IOException noEncuentraProcesoGeckodriverExcepcion) { }
	}
	
	/**
	 * Funci�n eliminarAcentos ( ): devuelve una cadena sin acentos.
	 * @param palabra
	 */
	public static String eliminarAcentos ( String palabra )
	{
		StringBuilder palabraSinAcentos = new StringBuilder ( );
		
		for ( int caracter_i = 0; caracter_i < palabra.length ( ); caracter_i ++ )
		{
			switch ( palabra.charAt ( caracter_i ) )
			{
				/*case '�': palabraSinAcentos.append ( 'A' ); break;
				case '�': palabraSinAcentos.append ( 'E' ); break;
				case '�': palabraSinAcentos.append ( 'I' ); break;
				case '�': palabraSinAcentos.append ( 'O' ); break;
				case '�': palabraSinAcentos.append ( 'U' ); break;
				case '�': palabraSinAcentos.append ( 'a' ); break;
				case '�': palabraSinAcentos.append ( 'e' ); break;
				case '�': palabraSinAcentos.append ( 'i' ); break;
				case '�': palabraSinAcentos.append ( 'o' ); break;
				case '�': palabraSinAcentos.append ( 'u' ); break;
				default : palabraSinAcentos.append ( palabra.charAt ( caracter_i ) );*/
			}		
		}
		
		return palabraSinAcentos.toString ( );
	}
	
	/**
	 * Funci�n formatearNombreAtributo ( ): devuelve el nombre de un atributo sin espacios, reemplazando la � por ni y en formato
	 * Camel case.
	 * @param nombreAtributo
	 */
	public static String formatearNombreAtributo ( String nombreAtributo )
	{
		StringTokenizer separador          = null;
		StringBuilder   atributoFormateado = new StringBuilder ( );
		
		if ( nombreAtributo.contains ( "�" ) )
		{
			nombreAtributo = nombreAtributo.replaceAll ( "�", "ni" );
		}
		
		if ( nombreAtributo.contains ( " " ) )
		{
			separador          = new StringTokenizer ( nombreAtributo );			
			atributoFormateado = atributoFormateado.append ( separador.nextToken ( ) );
			
			while ( separador.hasMoreTokens ( ) )
			{
				atributoFormateado.append ( obtenerCadenaTipoTitulo ( separador.nextToken ( ) ) );
			}
		}
		else
		{
			atributoFormateado.append ( nombreAtributo );
		}
		
		return atributoFormateado.toString ( );
	}
	
	/**
	 * Funci�n esNumero ( ): valida si una cadena es un n�mero o no.
	 * @param cadena
	 */
	public static boolean esNumero ( String cadena )
	{
		try
		{
			Double.parseDouble ( cadena );
			return true;
		}
		catch ( NumberFormatException noEsNumeroExcepcion )
		{
			return false;
		}
	}
	
	/**
	 * Funci�n extraerNumeros ( ): dada una cadena, devuelve en una lista todos los n�meros que contiene.
	 * @param cadena
	 */
	public static ArrayList < Integer > extraerNumeros ( String cadena )
	{
		String                nuevoNumero      = "";
		ArrayList < Integer > listaNumeros     = new ArrayList < Integer > ( );
		
		for ( int caracter_i = 0; caracter_i < cadena.length ( ); caracter_i ++)
		{
			if ( cadena.charAt ( caracter_i ) >= 48 && cadena.charAt ( caracter_i ) <=57 )
			{
				nuevoNumero = nuevoNumero.concat ( String.valueOf ( cadena.charAt ( caracter_i ) ) );
			}
			else
			{
				if ( !nuevoNumero.isEmpty ( ) )
				{
					listaNumeros.add ( Integer.parseInt ( nuevoNumero ) );
					nuevoNumero = "";
				}
			}
			
		}
		
		return listaNumeros;
	}

	public static void RecordInLog(String msgLog)
	{
		File           log      = new File(CommonConstans.DIRECTORIO_PROYECTOS.concat("logs/").concat(CommonConstans.ARCHIVO_LOG));
		BufferedWriter escritor = null;

		try
		{
			escritor = new BufferedWriter(new FileWriter(log, true));
			msgLog   = getFechaHoraActual().concat(": ").concat(msgLog);
			if(msgLog.contains("\n"))
			{
				escritor.newLine();
				escritor.write(msgLog.replaceAll("\n", ""));
			}
			else
				escritor.write(msgLog);

			escritor.newLine();
			escritor.close();
			System.out.println(msgLog);
		}
		catch (IOException archivoNoEncontradoExcepcion)
		{
			//throw new AssertionFailedError("ERROR: I/O - Archivo no encontrado o no es posible leer/escribir en �l.");
		}
	}

	public static String getFechaHoraActual()
	{
		return new SimpleDateFormat(CommonConstans.MASCARA_FECHA_HORA).format(Calendar.getInstance().getTime());
	}

	public static String convertirAFormatoFecha(String fechaFormatoDistinto)
	{
		final String MASCARA_FECHA_ORIGEN = "yyyy-MM-dd";

		String fechaConFormato = "";

		DateFormat formatoOrigen  = new SimpleDateFormat(MASCARA_FECHA_ORIGEN);
		DateFormat formatoDestino = new SimpleDateFormat(CommonConstans.MASCARA_FECHA);
		formatoOrigen.setLenient(false);
		formatoDestino.setLenient(false);
		try
		{
			fechaConFormato = formatoDestino.format(formatoOrigen.parse(fechaFormatoDistinto));
		}
		catch (ParseException parseoExcepcion)
		{
			parseoExcepcion.printStackTrace();
		}
		return fechaConFormato;
	}

	public void StartCommonLog()
	{
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, CommonConstans.DIRECTORIO_LOGS.concat(CommonConstans.ARCHIVO_LOG_GECKODRIVER));
	}
}
