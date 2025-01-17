package juego;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase abstracta que proporciona métodos para convertir objetos a su representación en JSON
 * y viceversa, utilizando la biblioteca Jackson.
 * Esta clase facilita la serialización y deserialización de objetos en formato JSON.
 */
public abstract class JSONMapper {


    public static ObjectMapper mapper = new ObjectMapper();

    /**
     * Convierte un objeto a su representación en formato JSON.
     *
     * @param obj El objeto que se desea convertir a JSON.
     * @return La representación en formato JSON del objeto, o null si ocurre un error.
     */
    public static String objectoToJson(Object obj) {
        try {
            System.out.println(obj);
            // Convierte el objeto a una cadena JSON
            return mapper.writeValueAsString(obj);
        }
        catch (JsonProcessingException ex) {
            // Registra el error si ocurre una excepción durante la conversión
            Logger.getLogger(JSONMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Convierte una cadena JSON a un objeto del tipo especificado.
     *
     * @param json La cadena JSON que se desea convertir.
     * @param classOfT La clase del tipo al que se desea convertir el JSON.
     * @param <T> El tipo del objeto resultante.
     * @return El objeto deserializado del tipo especificado, o null si ocurre un error.
     */
    public static <T> T jsonToObject(String json, Class<T> classOfT) {
        try {
            return mapper.readValue(json, classOfT);
        }
        catch (JsonProcessingException ex) {
            Logger.getLogger(JSONMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
