package com.xix.cleanMvpArchitecture.data.cache.fileManager;

import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by xix on 4/8/17.
 */
@Singleton
public class Serializer {


    @Inject Serializer() {

    }

    /**
     * Serialize an object to jsno.
     *
     * @param object the object
     * @param aClass the a class
     * @return the string
     */
    public String serialize(Object object, Class aClass) {

        return  new GsonBuilder().create().toJson(object, aClass);
    }
    /**
     * Serialize an object to jsno.
     *
     * @param object the object
     * @return the string
     */
    public String serialize(Object object) {

        return  new GsonBuilder().create().toJson(object);
    }
    /**
     * Deserialize a json .
     *
     * @param <T> the type parameter
     * @param string the string
     * @param aClass the a class
     * @return the t
     */
    public <T> T deserialize(String string, Class<T> aClass) {

        return  new GsonBuilder().create().fromJson(string, aClass);
    }
    /**
     * Deserialize a json .
     *
     * @param <T> the type parameter
     * @param string the string
     * @param type the a type of response
     * @return the t
     */
    public <T> T deserialize(String string, Type type) {

        return  new GsonBuilder().create().fromJson(string, type);
    }
    /**
     * Deserialize a json .
     *
     * @param <T> the type parameter
     * @param string the string
     * @param aClass the a class
     * @return the t
     */
    public <T> T deserializeWithExpose(String string, Class<T> aClass) {

        return  new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(string, aClass);
    }
}
