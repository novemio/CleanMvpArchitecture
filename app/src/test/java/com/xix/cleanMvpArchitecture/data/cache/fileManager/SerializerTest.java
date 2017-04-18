package com.xix.cleanMvpArchitecture.data.cache.fileManager;

import com.xix.cleanMvpArchitecture.data.model.Item;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by xix on 4/15/17.
 */
@RunWith(JUnit4.class)
public class SerializerTest extends TestCase {
    private Serializer serializer;
    private static String ITEM = " {  \n"
        + "      \"image\":\"http://dummyimage.com/715x350/105B19/907ECC\",\n"
        + "      \"description\":\"sterilizer span ticks continuity hubs procurement  bow blots rescuers incline\",\n"
        + "      \"title\":\"terminations map autos sons utilizations\"\n"
        + "   }";

    @Before public void setUp() throws Exception {
        serializer = new Serializer();
    }

    @Test public void serialize() throws Exception {
        Item item = serializer.deserialize(ITEM, Item.class);
        String itemString = serializer.serialize(item);
        Item itemTwo = serializer.deserialize(itemString, Item.class);

        assertEquals(item.getTitle(),itemTwo.getTitle());
        assertEquals(item.getDescription(),itemTwo.getDescription());
        assertEquals(item.getImageUrl(),itemTwo.getImageUrl());
    }


    @Test public void deserialize() throws Exception {
        Item item = serializer.deserialize(ITEM, Item.class);

        assertEquals(item.getTitle(),"terminations map autos sons utilizations");
        assertEquals(item.getDescription(),"sterilizer span ticks continuity hubs procurement  bow blots rescuers incline");
        assertEquals(item.getImageUrl(),"http://dummyimage.com/715x350/105B19/907ECC");
    }




}