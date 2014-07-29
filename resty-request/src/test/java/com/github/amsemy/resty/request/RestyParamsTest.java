package com.github.amsemy.resty.request;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;

public class RestyParamsTest {

    private Map<String, String[]> arrayParams;
    private Map<String, List<String>> listParams;

    @Before
    public void setUp() {

        arrayParams = new HashMap<>();
        arrayParams.put("0", null);
        arrayParams.put("a", new String[] {null});
        arrayParams.put("b", new String[] {"valueB0"});
        arrayParams.put("c", new String[] {null, "valueC1"});
        arrayParams.put("d", new String[] {"valueD0", "valueD1"});

        listParams = new HashMap<>();
        listParams.put("0", null);
        List<String> paramA = new ArrayList<>();
        paramA.add(null);
        listParams.put("a", paramA);
        List<String> paramB = new ArrayList<>();
        paramB.add("valueB0");
        listParams.put("b", paramB);
        List<String> paramC = new ArrayList<>();
        paramC.add(null);
        paramC.add("valueC1");
        listParams.put("c", paramC);
        List<String> paramD = new ArrayList<>();
        paramD.add("valueD0");
        paramD.add("valueD1");
        listParams.put("d", paramD);

    }

    @Test
    public void testAdd() {
        RestyParams restyParams = RestyParams.buildServletParams(arrayParams);

        restyParams.add("0", "value00");
        assertArrayEquals(new String[] {null, "value00"},
                restyParams.get("0").toArray());

        restyParams.add("a", "valueA0");
        assertArrayEquals(new String[] {null, "valueA0"},
                restyParams.get("a").toArray());

        restyParams.add("b", "valueB1");
        assertArrayEquals(new String[] {"valueB0", "valueB1"},
                restyParams.get("b").toArray());

        restyParams.add("c", "valueC2");
        assertArrayEquals(new String[] {null, "valueC1", "valueC2"},
                restyParams.get("c").toArray());

        restyParams.add("d", "valueD1");
        assertArrayEquals(new String[] {"valueD0", "valueD1", "valueD1"},
                restyParams.get("d").toArray());
    }

    @Test
    public void testBuildEmptyParams() {
        RestyParams params = RestyParams.buildEmptyParams();

        assertEquals(0, params.keySet().size());
    }

    @Test
    public void testBuildJsonParams() {
        RestyParams restyParams;

        JsonObject jsonParams = Json.createObjectBuilder()
                .addNull("0")
                .add("a", Json.createArrayBuilder()
                        .addNull())
                .add("b", Json.createArrayBuilder()
                        .add("valueB0"))
                .add("c", Json.createArrayBuilder()
                        .addNull()
                        .add("valueC1"))
                .add("d", Json.createArrayBuilder()
                        .add("valueD0")
                        .add("valueD1"))
                .build();

        restyParams = RestyParams.buildJsonParams(jsonParams);

        assertNull(restyParams.get("0").get(0));
        assertArrayEquals(arrayParams.get("a"), restyParams.get("a").toArray());
        assertArrayEquals(arrayParams.get("b"), restyParams.get("b").toArray());
        assertArrayEquals(arrayParams.get("c"), restyParams.get("c").toArray());
        assertArrayEquals(arrayParams.get("d"), restyParams.get("d").toArray());

        restyParams = RestyParams.buildJsonParams(Json.createObjectBuilder()
                .add("a", "valueA")
                .add("b", Json.createObjectBuilder()
                        .add("a", "valueB.A"))
                .add("c", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("a", "valueC0.A")
                                .add("b", "valueC0.B"))
                        .add(Json.createObjectBuilder()
                                .add("a", "valueC1.A")
                                .add("b", Json.createArrayBuilder()
                                        .add(Json.createObjectBuilder()
                                                .add("a", "valueC1.B0.A"))
                                        .add(Json.createObjectBuilder()
                                                .add("a", "valueC1.B1.A")))))
                .build());

        assertArrayEquals(new String[] {"valueA"},
                restyParams.get("a").toArray());
        assertArrayEquals(new String[] {"valueB.A"},
                restyParams.get("b[a]").toArray());
        assertArrayEquals(new String[] {"valueC0.A", "valueC1.A"},
                restyParams.get("c[a]").toArray());
        assertArrayEquals(new String[] {"valueC0.B"},
                restyParams.get("c[b]").toArray());
        assertArrayEquals(new String[] {"valueC1.B0.A", "valueC1.B1.A"},
                restyParams.get("c[b][a]").toArray());
    }

    @Test
    public void testBuildServiceParams() {
        RestyParams restyParams = RestyParams.buildServiceParams(listParams);

        assertEquals(listParams, restyParams);
    }

    @Test
    public void testBuildServletParams() {
        RestyParams restyParams = RestyParams.buildServletParams(arrayParams);

        assertEquals(listParams, restyParams);
    }

}
