package com.confia.mailbot.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.confia.mailbot.dto.SmtpErrorEnum;

public class MailBotUtils {

	private static final String smtpErrorCode = "[0-9]{1}\\.[0-9]{1}\\.[0-9]{1}";

	public static Object beanLookUp(String lookupJndi) {
		Context env = null;

		try {
			env = new InitialContext();
			return env.lookup(lookupJndi);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String obtenerNumeroGestion(String referencia) {

		if (referencia == null) {
			return null;
		}

		int guion = referencia.indexOf("-", 0);

		if (guion == -1) {
			return null;
		}

		try {
			String numeroGestion = referencia.substring(0, guion);
			return numeroGestion;

		} catch (Exception ex) {
			return null;
		}
	}

	public static String obtenerPeriodo(String referencia) {
		if (referencia == null) {
			return null;
		}
		int guion = referencia.indexOf("-", 0);

		if (guion == -1) {
			return null;
		}

		try {
			String periodo = referencia.substring(guion + 1);
			return periodo;

		} catch (Exception ex) {
			return null;
		}

	}

	public static boolean checkCurrentJobs(String[] names, String jobName) {
		for (String name : names) {
			if (name.equals(jobName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean valPeriodoCotiza(String pPeriodoCotiza) {
		if (!valInt(pPeriodoCotiza)) {
			return false;
		}
		if (pPeriodoCotiza.length() != 6) {
			return false;
		}
		int anno = Integer.parseInt(pPeriodoCotiza.substring(0, 4));
		int mes = Integer.parseInt(pPeriodoCotiza.substring(4, 6));
		if (anno < 1998 || anno > 2050) {
			return false;
		}
		if (mes > 12 || mes < 1) {
			return false;
		}
		if (anno == 1998 && mes < 4) {
			return false;
		}
		return true;
	}

	public static boolean valInt(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static boolean valDouble(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	// Determina si está desplegado en Windows o Unix
	public static boolean isWindows() {
		//verificar en solaris
		String os = System.getProperty("os.name").toLowerCase();
		if (os != null) {
			if (os.indexOf("windows") != -1 || os.indexOf("nt") != -1 || os.equals("windows 95")
					|| os.equals("windows 98")) {
				//System.out.println("Sistema Operativo es Windows => " + os);
				return true;
			} else if (os.indexOf("mac") != -1) {
				//System.out.println("Sistema Operativo es Mac => " + os);
				return false;
			} else if (os.indexOf("linux") != -1) {
				//System.out.println("Sistema Operativo es Linux => " + os);
				return false;
			} else {
				//System.out.println("Sistema Operativo es Unix => " + os);
				return false;
			}
		} else {
			System.out.println("Sistema Operativo no fue posible identificarlo => null ");
			return false;
		}
	}

	// Modifica el path del directorio Windows a Unix
	public static String convertPath(String ruta, String serverUnix, boolean osFlag) {
		String rutaNueva = "";
		int niveles = 2; // Nümero de niveles del árbol jerárquico de directorios de windows al que se
							// desea accesar
		int nivelDir = niveles + 2; // Número total de backslash de windows. Se agregan 2 más que son los que
									// enlazan al pc remoto ej. \\lacslvcfwdes268...
		if (!osFlag) {
			String backslash = "\\";
			String rutaUnix = ruta.replace(backslash, "/");
			char[] cadena = rutaUnix.toCharArray();
			int found = 0;
			int count = 0;
			for (int i = 0; i < cadena.length; i++) {
				if (cadena[i] == '/') {
					found++;
				}
				count++;
				if (found == nivelDir) {
					break;
				}
			}
			if (found == nivelDir) {
				rutaNueva = rutaUnix.replace(rutaUnix.substring(0, count), serverUnix);
				//System.out.println("Ruta Windows  => " + ruta);
				//System.out.println("Ruta Unix AIX => " + rutaNueva);
			} else {
				rutaNueva = ruta;
				//System.out.println("Ruta inválida! No fue posible determinar unidad montaje de Unix AIX");
			}
		} else {
			rutaNueva = ruta;
		}
		return rutaNueva;
	}

	public static List<String> getErrorCodes(String exceptionString) {
		Pattern pattern;
		Matcher matcher;
		List<String> foundCodes = new ArrayList<String>();

		pattern = Pattern.compile(smtpErrorCode, Pattern.CASE_INSENSITIVE);
		if (exceptionString == null || exceptionString.isEmpty())
			return new ArrayList<String>();

		matcher = pattern.matcher(exceptionString);
		while (matcher.find()) {
			foundCodes.add(matcher.group(0));
			System.out.println(matcher.group(0));
		}

		return foundCodes;
	}

	public static boolean validateErrorString(String exceptionString) {
		List<String> errorCodes = getErrorCodes(exceptionString);
		if (errorCodes != null && !errorCodes.isEmpty()) {
			for (String code : errorCodes) {
				SmtpErrorEnum errorEnum = SmtpErrorEnum.getEnumFromErrorCodes(code);
				if (errorEnum != null && errorEnum.getErrorCode() != null 
						&& errorEnum.getErrorString() != null) {
					return exceptionString.contains(errorEnum.getErrorCode())
							&& exceptionString.contains(errorEnum.getErrorString());
				}
			}
		}
		return false;
	}

	public static String saveAttachment(String fileAddress, String idAttachment, byte[] fileToSave) {
        File outputFile = null;
        FileOutputStream fos = null;
        try {
	        outputFile = new File(fileAddress);
	        fos = new FileOutputStream(outputFile);
			fos.write(fileToSave);
			fos.close();
			return fileAddress;
        }catch(Exception ex) {
        	ex.printStackTrace();
        }finally {
        	try {
        		if(fos!=null)
        			fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return null;

	}
	
	public static String getFileAddress(Map<Integer,String> params, boolean isWindows) {
		String url = "";
		if(isWindows) {
			url = "\\\\" + params.get(0) + "\\WebMailFiles$\\adjuntos\\" + params.get(1)+ params.get(2).toUpperCase();
		}else {
			url = params.get(0) + "adjuntos/" + params.get(1) + params.get(2);
		}
		return url;
	}

}
