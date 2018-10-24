/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usojdom;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author dmorenoar
 */
public class UsoJDOM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String moneda = "";
        /*Creamos el objeto builder para leer de la librería 
        encargada de parseat el archivo. Para poder utilizarlo se ha
        de importar en nuestro proyecto el jar de JDOM*/
        SAXBuilder builder = new SAXBuilder();
        //Creamos la referencia al archivo
        File xmlFile = new File("datos.xml");

        try {
            //Se crea el documento a través del archivo a leer
            Document document = (Document) builder.build(xmlFile);

            //Obtenemos la raíz del documento (Raíz -> Videojuegos)
            Element rootNode = document.getRootElement();

            //Obtenemos la lista de hijos de la raíz(Hijos -> Videojuego
            List listaVideojuegos = rootNode.getChildren();

            //Recorremos todos los hijos
            for (int i = 0; i < listaVideojuegos.size(); i++) {
                System.out.println("Videojuego:");
                //Obtendremos cada elemento videojuego
                Element videojuego = (Element) listaVideojuegos.get(i);

                //Por cada videojuego obtenemos los hijos que tiene, es decir, las propiedades
                List listaPropiedades = videojuego.getChildren();

                System.out.println("****************************");
                //Ahora hemos de recorrer todas las propiedades de cada elemento
                for (int j = 0; j < listaPropiedades.size(); j++) {
                    Element propiedad = (Element) listaPropiedades.get(j);

                    //System.out.println("TipoMoneda:" + propiedad.getAttributeValue("moneda"));
                    //Obtenemos el par-valor, es decir nombre de la propiedad y su valor
                    String nombre = propiedad.getName();
                    String valor = propiedad.getText();

                    //Obtenemos el tag de una propiedad y realizamos una comprovación
                    if (nombre.equals("compania")) {
                        List compania = propiedad.getChildren();
                        System.out.println("Compañia");
                        for (int z = 0; z < compania.size(); z++) {
                            Element prop = (Element) compania.get(z);
                            nombre = prop.getName();
                            valor = prop.getText();
                            System.out.println("\t" + nombre + ":" + valor);
                        }
                    } else {

                        if (propiedad.getAttributeValue("moneda") != null) {
                            switch (propiedad.getAttributeValue("moneda")) {

                                case "Euros":
                                    moneda = " €";
                                    break;
                                case "Dolares":
                                    moneda = " $";
                                    break;
                            }
                        }

                        if (propiedad.getName().equals("precio")) {
                            valor += moneda;
                        }

                        System.out.println("\t" + nombre + ":" + valor);

                    }
                }

                System.out.println("****************************");
            }

        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
