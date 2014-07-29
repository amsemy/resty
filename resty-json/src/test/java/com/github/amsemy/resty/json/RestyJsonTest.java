package com.github.amsemy.resty.json;

import com.github.amsemy.resty.json.annotation.RstField;
import com.github.amsemy.resty.json.annotation.RstGetter;
import com.github.amsemy.resty.json.annotation.RstPojo;
import com.github.amsemy.resty.json.annotation.RstType;
import com.github.amsemy.resty.json.annotation.RstTypeValue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

@SuppressWarnings({"FieldMayBeFinal", "UnusedDeclaration"})
public class RestyJsonTest {

    @RstType
    private static class Color {

        public String fullName = "Striped color";

        @RstTypeValue
        public String shortName = "striped";

    }

    private static class Entity {

        @RstField("ignored_field")
        public int id = 1111;

        @RstGetter("ignored_method")
        public int getId() {
            return id;
        }
    }

    @RstPojo
    private static class Animal extends Entity {

        @RstField
        private int age = 3;

        @RstField("height")
        public int h = 25;

        @RstField("width")
        private int w = 9;

        public int code = 2222;

    }

    @RstPojo
    private static class Cat extends Animal {

        @RstField
        public String name = "Vaska";

        @RstGetter("color")
        public Color getColor() {
            return new Color();
        }

    }

    @RstPojo
    private static class Data {

        @RstField
        public Animal animal = new Cat();

        @RstField
        public Cat cat = new Cat();
    }

    private int[] emptyArray;
    private int[] filledArray;
    private Collection<Object> emptyCollection;
    private Collection<Object> filledCollection;
    private JsonArray emptyJsonArray;
    private JsonArray filledJsonArray;
    private JsonObject dataJsonObject;
    private Map<String, Object> dataMap;
    private Data dataObject;

    @Before
    public void setUp() {

        emptyArray = new int[0];
        filledArray = new int[] {10, 20, 30};

        emptyCollection = new HashSet<>();
        filledCollection = new ArrayList<>();
        filledCollection.add(10);
        filledCollection.add(20);
        filledCollection.add(30);

        emptyJsonArray = Json.createArrayBuilder().build();
        filledJsonArray = Json.createArrayBuilder()
                .add(10)
                .add(20)
                .add(30)
                .build();

        dataJsonObject = Json.createObjectBuilder()
                .add("animal", Json.createObjectBuilder()
                    .add("age", 3)
                    .add("height", 25)
                    .add("width", 9)
                    .add("name", "Vaska")
                    .add("color", "striped"))
                .add("cat", Json.createObjectBuilder()
                    .add("age", 3)
                    .add("height", 25)
                    .add("width", 9)
                    .add("name", "Vaska")
                    .add("color", "striped"))
                .build();

        dataMap = new HashMap<>();
        Map<String, Object> animalMap = new HashMap<>();
        animalMap.put("age", 3);
        animalMap.put("height", 25);
        animalMap.put("width", 9);
        animalMap.put("name", "Vaska");
        animalMap.put("color", "striped");
        dataMap.put("animal", animalMap);
        Map<String, Object> catMap = new HashMap<>();
        catMap.put("age", 3);
        catMap.put("height", 25);
        catMap.put("width", 9);
        catMap.put("name", "Vaska");
        catMap.put("color", "striped");
        dataMap.put("cat", catMap);

        dataObject = new Data();
    }

    @Test
    public void testBuild() throws Exception {
        JsonArrayBuilder filledBuilder = Json.createArrayBuilder()
                .add(10)
                .add(20)
                .add(30);
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder()
                .add("animal", Json.createObjectBuilder()
                    .add("age", 3)
                    .add("height", 25)
                    .add("width", 9)
                    .add("name", "Vaska")
                    .add("color", "striped"))
                .add("cat", Json.createObjectBuilder()
                    .add("age", 3)
                    .add("height", 25)
                    .add("width", 9)
                    .add("name", "Vaska")
                    .add("color", "striped"));

        assertEquals(filledJsonArray, RestyJson.build(filledArray));
        assertEquals(filledJsonArray, RestyJson.build(filledCollection));
        assertEquals(filledJsonArray, RestyJson.build(filledBuilder));
        assertEquals(dataJsonObject, RestyJson.build(dataMap));
        assertEquals(dataJsonObject, RestyJson.build(dataObject));
        assertEquals(dataJsonObject, RestyJson.build(dataBuilder));
    }

    @Test
    public void testGetArray() throws Exception {
        JsonArray result;

        result = RestyJson.getArray(emptyArray).build();
        assertEquals(emptyJsonArray, result);

        result = RestyJson.getArray(filledArray).build();
        assertEquals(filledJsonArray, result);
    }

    @Test
    public void testGetCollection() throws Exception {
        JsonArray result;

        result = RestyJson.getCollection(emptyCollection).build();
        assertEquals(emptyJsonArray, result);

        result = RestyJson.getCollection(filledCollection).build();
        assertEquals(filledJsonArray, result);
    }

    @Test
    public void testGetMap() throws Exception {
        JsonObject result;

        result = RestyJson.getMap(dataMap).build();
        assertEquals(dataJsonObject, result);
    }

    @Test
    public void testGetObject() throws Exception {
        JsonObject result;

        result = RestyJson.getObject(dataObject).build();
        assertEquals(dataJsonObject, result);
    }

    @Test
    public void testGetTypeValue() throws Exception {
        Object result;

        result = RestyJson.getTypeValue(new Color());
        assertEquals("striped", result);
    }

}
