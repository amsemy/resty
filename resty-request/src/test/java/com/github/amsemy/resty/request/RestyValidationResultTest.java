package com.github.amsemy.resty.request;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestyValidationResultTest {

    @Test
    public void testRestyValidationResult() {
        new RestyValidationResult();
    }

    @Test
    public void testAddError() {
        List<String> barList = new ArrayList<>();
        barList.add("errD");
        Map<String, Object> barMap = new HashMap<>();
        barMap.put("__self__", barList);

        List<String> fooList = new ArrayList<>();
        fooList.add("errC");
        Map<String, Object> fooMap = new HashMap<>();
        fooMap.put("__self__", fooList);
        fooMap.put("bar", barMap);

        List<String> commonList = new ArrayList<>();
        commonList.add("errA");
        commonList.add("errB");
        Map<String, Object> expected = new HashMap<>();
        expected.put("__self__", commonList);
        expected.put("foo", fooMap);

        RestyValidationResult result = new RestyValidationResult();
        result.addError(null, "errA");
        result.addError(null, "errB");
        result.addError(new String[] { "foo" }, "errC");
        result.addError(new String[] { "foo", "bar" }, "errD");

        assertEquals(expected, result.getErrors());
        assertFalse(result.isValid());
    }

    @Test
    public void testGetErrors() {
        RestyValidationResult result = new RestyValidationResult();
        assertEquals(new HashMap<String, Object>(), result.getErrors());
    }

    @Test
    public void testIsValid() {
        RestyValidationResult result = new RestyValidationResult();
        assertTrue(result.isValid());
    }

}
