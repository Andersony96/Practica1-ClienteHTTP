import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;



/**
 * Created by Andersony96 on 5/26/2017.
 */
public class Main {

    public static void main(String[] args) {

        Document documentoHTML;
        Document newDocumentoHTML;
        int cantidadLineas = 0;
        int cantidadParrafos = 0;
        int cantidadImagenes = 0;
        int formPost =0;
        int formGet =0;
        int n=0;
        int a=0;
        int valor;
        String newAction;
        String tipos;

        Scanner escaner = new Scanner(System.in);
        System.out.println("Favor digitar URL valida:");
        String urlEscaneada = escaner.next();

        try {
            documentoHTML = Jsoup.connect(urlEscaneada).get();
            System.out.println("URL valida!");
            /////////////////////////Cantidad de lineas en el documento////////////////////////////////
            URL urlObjeto = new URL(urlEscaneada);
            InputStreamReader urlLeida = new InputStreamReader(urlObjeto.openStream());
            BufferedReader arregloLeido = new BufferedReader(urlLeida);
            while(arregloLeido.readLine()!=null) {
                cantidadLineas++;
            }
            /////////////////////////Cantidad de formularios por metodo////////////////////////////////

            formPost = documentoHTML.getElementsByAttributeValueContaining("method","post").size();
            formGet= documentoHTML.getElementsByAttributeValueContaining("method","get").size();

            //////////////////Cantidad de parrrafos y imagenes dentro de parrafos////////////////////////

            Elements parrafos = documentoHTML.getElementsByTag("p");
            cantidadParrafos = parrafos.size();
            for (Element p: parrafos) {
                   Elements elementos =  p.children();
                    for (Element imagen: elementos) {
                        cantidadImagenes+= imagen.getElementsByTag("img").size();
                    }
                }
            //////////////Impresion de los Resultados//////////////////////

            System.out.println("El documento HTML contine:");
            System.out.println("Lineas:"+cantidadLineas);
            System.out.println("Parrasfo:"+cantidadParrafos);
            System.out.println("Imagenes:"+cantidadImagenes+"\n");
            System.out.println("Cantidad de formularios:");
            System.out.println("Metodo post:"+formPost);
            System.out.println("Metodo get:"+formGet);

            ////////////Formularios-->Tipos de Input///////////////////

            Elements form = documentoHTML.getElementsByTag("form");
            for (Element formularios: form) {
                n++;
                System.out.println("\n"+"Formulario "+n+":");
                for (Element input: formularios.children().select("input")) {
                    tipos = input.getElementsByTag("input").attr("type");
                    System.out.println("input-->tipo:"+tipos);
                }
            }

           ///////////////////petición al servidor y respuesta//////////////////////
            System.out.println("\n");
            Elements fPost = documentoHTML.getElementsByAttributeValueContaining("method","post");
            for (Element formpost: fPost) {
                a++;
                System.out.println("Formulario-->"+a);
                newAction = formpost.absUrl("action");
                newDocumentoHTML = Jsoup.connect(newAction).data("asignatura","practica1").post();
                System.out.println("Respuesta del Servidor:" +newDocumentoHTML);
            }

        } catch (UnknownHostException errorHost) {
            System.err.println("URL no valida");
        }
        catch (MalformedURLException e) {
            System.out.println("URL mal Formulada");
        }
        catch (IOException errorIO) {
            System.out.println("Error de Entrada/Salida");
        }

    }

}
