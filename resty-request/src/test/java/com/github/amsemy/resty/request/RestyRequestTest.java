package com.github.amsemy.resty.request;

import com.github.amsemy.resty.request.RestyRequest.*;
import org.junit.Test;
import static com.github.amsemy.resty.request.RestyRequest.*;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("EmptyCatchBlock")
public class RestyRequestTest {

    public static class TestBundle extends ListResourceBundle {

        @Override
        protected Object[][] getContents() {
            return new String[][] {
                {bundleKeyA, bundleValueA},
                {bundleKeyB, bundleValueB},
                {bundleKeyC, bundleValueC}
            };
        }

    }

    private static final String emptyKey = "emptyKey";
    private static final String singleNull = "singleNull";
    private static final String multiNull = "multiNull";
    private static final String singleEmpty = "singleEmpty";
    private static final String multiEmpty = "multiEmpty";
    private static final String singleStr = "singleString";
    private static final String multiStr = "multiString";

    private static final String EMPTY = "";
    private static final String SST1 = "sst1";
    private static final String MST1 = "mst1";
    private static final String MST2 = "mst2";

    private static final String bundleKeyA = "keyA";
    private static final String bundleKeyB = "keyB";
    private static final String bundleKeyC = "keyC";
    private static final String bundleValueA = "valueA";
    private static final String bundleValueB = "valueB %1$s";
    private static final String bundleValueC = "valueC %1$s %2$s";

    private static RestyParams getBaseSetOfParams() {
        RestyParams params = RestyParams.buildEmptyParams();

        params.put(emptyKey, null);

        params.add(singleNull, null);
        params.add(multiNull, null);
        params.add(multiNull, null);

        params.add(singleEmpty, EMPTY);
        params.add(multiEmpty, EMPTY);
        params.add(multiEmpty, EMPTY);

        params.add(singleStr, SST1);
        params.add(multiStr, MST1);
        params.add(multiStr, MST2);

        return params;
    }

    @Test
    public void testRestyValidatorTest$AssertBoolean_String_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "true");
        params.add("b", "false");
        params.add("c", "null");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertBoolean("a", message("errA"));
                this.assertBoolean("b", message("errB"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "c" }, "errC");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertBoolean(emptyKey, message("errEK"));
                this.assertBoolean(singleNull, message("errSN"));
                this.assertBoolean(singleEmpty, message("errSE"));

                this.assertBoolean("a", message("errA"));
                this.assertBoolean("b", message("errB"));
                this.assertBoolean("c", message("errC"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertBoolean_Param_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "true");
        params.add("a", "false");
        params.add("b", "FaLsE");
        params.add("b", "True");
        params.add("c", "null");
        params.add("c", "true");
        params.add("c", "false");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertBoolean(param("a", 0), message("errA0"));
                this.assertBoolean(param("a", 1), message("errA1"));
                this.assertBoolean(param("b", 0), message("errB0"));
                this.assertBoolean(param("b", 1), message("errB1"));
                this.assertBoolean(param("c", 1), message("errC1"));
                this.assertBoolean(param("c", 2), message("errC2"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "c" }, "errC0");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertBoolean(param(emptyKey, 0), message("errEK"));
                this.assertBoolean(param(singleNull, 0), message("errSN"));
                this.assertBoolean(param(singleEmpty, 0), message("errSE"));

                this.assertBoolean(param("a", 0), message("errA0"));
                this.assertBoolean(param("a", 1), message("errA1"));
                this.assertBoolean(param("b", 0), message("errB0"));
                this.assertBoolean(param("b", 1), message("errB1"));
                this.assertBoolean(param("c", 0), message("errC0"));
                this.assertBoolean(param("c", 1), message("errC1"));
                this.assertBoolean(param("c", 2), message("errC2"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertDate_String_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "2001-01-01");
        params.add("b", "01.01.01");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertDate("a", message("errA"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "b" }, "errB");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertDate(emptyKey, message("errEK"));
                this.assertDate(singleNull, message("errSN"));
                this.assertDate(singleEmpty, message("errSE"));

                this.assertDate("a", message("errA"));
                this.assertDate("b", message("errB"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertDate_Param_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "1999-04-24");
        params.add("a", "2005-12-30");
        params.add("b", "07.09.2010");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertDate(param("a", 0), message("errA0"));
                this.assertDate(param("a", 1), message("errA1"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "b" }, "errB0");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertDate(param(emptyKey, 0), message("errEK"));
                this.assertDate(param(singleNull, 0), message("errSN"));
                this.assertDate(param(singleEmpty, 0), message("errSE"));

                this.assertDate(param("a", 0), message("errA0"));
                this.assertDate(param("a", 1), message("errA1"));
                this.assertDate(param("b", 0), message("errB0"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertExists_String_Message() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertExists(emptyKey, message("errEK"));

                this.assertExists(singleNull, message("errSN"));
                this.assertExists(multiNull, message("errMN"));

                this.assertExists(singleEmpty, message("errSE"));
                this.assertExists(multiEmpty, message("errME"));

                this.assertExists(singleStr, message("errSS"));
                this.assertExists(multiStr, message("errMS"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { "nonexistent" }, "errNK");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertExists(emptyKey, message("errEK"));

                this.assertExists(singleNull, message("errSN"));
                this.assertExists(multiNull, message("errMN"));

                this.assertExists(singleEmpty, message("errSE"));
                this.assertExists(multiEmpty, message("errME"));

                this.assertExists(singleStr, message("errSS"));
                this.assertExists(multiStr, message("errMS"));

                this.assertExists("nonexistent", message("errNK"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertExists_Param_Message() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertExists(param(emptyKey, 0), message("errEK0"));

                this.assertExists(param(singleNull, 0), message("errSN0"));
                this.assertExists(param(multiNull, 0), message("errMN0"));
                this.assertExists(param(multiNull, 1), message("errMN1"));

                this.assertExists(param(singleEmpty, 0), message("errSE0"));
                this.assertExists(param(multiEmpty, 0), message("errME0"));
                this.assertExists(param(multiEmpty, 1), message("errME1"));

                this.assertExists(param(singleStr, 0), message("errSS0"));
                this.assertExists(param(multiStr, 0), message("errMS0"));
                this.assertExists(param(multiStr, 1), message("errMS1"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK-1");
        result.addError(new String[] { emptyKey }, "errEK1");
        result.addError(new String[] { singleNull }, "errSN-1");
        result.addError(new String[] { singleNull }, "errSN1");
        result.addError(new String[] { multiNull }, "errMN-1");
        result.addError(new String[] { multiNull }, "errMN2");
        result.addError(new String[] { singleEmpty }, "errSE-1");
        result.addError(new String[] { singleEmpty }, "errSE1");
        result.addError(new String[] { multiEmpty }, "errME-1");
        result.addError(new String[] { multiEmpty }, "errME2");
        result.addError(new String[] { singleStr }, "errSS-1");
        result.addError(new String[] { singleStr }, "errSS1");
        result.addError(new String[] { multiStr }, "errMS-1");
        result.addError(new String[] { multiStr }, "errMS2");
        result.addError(new String[] { "nonexistent" }, "errNK");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertExists(param(emptyKey, -1), message("errEK-1"));
                this.assertExists(param(emptyKey, 0), message("errEK0"));
                this.assertExists(param(emptyKey, 1), message("errEK1"));

                this.assertExists(param(singleNull, -1), message("errSN-1"));
                this.assertExists(param(singleNull, 0), message("errSN0"));
                this.assertExists(param(singleNull, 1), message("errSN1"));
                this.assertExists(param(multiNull, -1), message("errMN-1"));
                this.assertExists(param(multiNull, 0), message("errMN0"));
                this.assertExists(param(multiNull, 1), message("errMN1"));
                this.assertExists(param(multiNull, 2), message("errMN2"));

                this.assertExists(param(singleEmpty, -1), message("errSE-1"));
                this.assertExists(param(singleEmpty, 0), message("errSE0"));
                this.assertExists(param(singleEmpty, 1), message("errSE1"));
                this.assertExists(param(multiEmpty, -1), message("errME-1"));
                this.assertExists(param(multiEmpty, 0), message("errME0"));
                this.assertExists(param(multiEmpty, 1), message("errME1"));
                this.assertExists(param(multiEmpty, 2), message("errME2"));

                this.assertExists(param(singleStr, -1), message("errSS-1"));
                this.assertExists(param(singleStr, 0), message("errSS0"));
                this.assertExists(param(singleStr, 1), message("errSS1"));
                this.assertExists(param(multiStr, -1), message("errMS-1"));
                this.assertExists(param(multiStr, 0), message("errMS0"));
                this.assertExists(param(multiStr, 1), message("errMS1"));
                this.assertExists(param(multiStr, 2), message("errMS2"));

                this.assertExists(param("nonexistent"), message("errNK"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertFloat_String_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "245");
        params.add("b", "1.56");
        params.add("c", ".0000111");
        params.add("d", "1,1");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertFloat("a", message("errA"));
                this.assertFloat("b", message("errB"));
                this.assertFloat("c", message("errC"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "d" }, "errD");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertFloat(emptyKey, message("errEK"));
                this.assertFloat(singleNull, message("errSN"));
                this.assertFloat(singleEmpty, message("errSE"));

                this.assertFloat("a", message("errA"));
                this.assertFloat("b", message("errB"));
                this.assertFloat("c", message("errC"));
                this.assertFloat("d", message("errD"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertFloat_Param_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "30");
        params.add("a", "0");
        params.add("b", "0.1");
        params.add("b", "0.999");
        params.add("c", ".0000243");
        params.add("d", "28,2");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertFloat(param("a", 0), message("errA0"));
                this.assertFloat(param("a", 1), message("errA1"));
                this.assertFloat(param("b", 0), message("errB0"));
                this.assertFloat(param("b", 1), message("errB1"));
                this.assertFloat(param("c", 0), message("errC0"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "d" }, "errD0");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertFloat(param(emptyKey, 0), message("errEK"));
                this.assertFloat(param(singleNull, 0), message("errSN"));
                this.assertFloat(param(singleEmpty, 0), message("errSE"));

                this.assertFloat(param("a", 0), message("errA0"));
                this.assertFloat(param("a", 1), message("errA1"));
                this.assertFloat(param("b", 0), message("errB0"));
                this.assertFloat(param("b", 1), message("errB1"));
                this.assertFloat(param("c", 0), message("errC0"));
                this.assertFloat(param("d", 0), message("errD0"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertInteger_String_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "15");
        params.add("b", "2222222222");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertInteger("a", message("errA"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "b" }, "errB");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertInteger(emptyKey, message("errEK"));
                this.assertInteger(singleNull, message("errSN"));
                this.assertInteger(singleEmpty, message("errSE"));

                this.assertInteger("a", message("errA"));
                this.assertInteger("b", message("errB"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertInteger_Param_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "2147483647");
        params.add("a", "1");
        params.add("b", "2147483648");
        params.add("b", "2.01");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertInteger(param("a", 0), message("errA0"));
                this.assertInteger(param("a", 1), message("errA1"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "b" }, "errB0");
        result.addError(new String[] { "b" }, "errB1");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertInteger(param(emptyKey, 0), message("errEK"));
                this.assertInteger(param(singleNull, 0), message("errSN"));
                this.assertInteger(param(singleEmpty, 0), message("errSE"));

                this.assertInteger(param("a", 0), message("errA0"));
                this.assertInteger(param("a", 1), message("errA1"));
                this.assertInteger(param("b", 0), message("errB0"));
                this.assertInteger(param("b", 1), message("errB1"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertLong_String_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "123");
        params.add("b", "3.5");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertLong("a", message("errA"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "b" }, "errB");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertLong(emptyKey, message("errEK"));
                this.assertLong(singleNull, message("errSN"));
                this.assertLong(singleEmpty, message("errSE"));

                this.assertLong("a", message("errA"));
                this.assertLong("b", message("errB"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertLong_Param_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "2147483648");
        params.add("a", "999999999999");
        params.add("b", "1.1");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertLong(param("a", 0), message("errA0"));
                this.assertLong(param("a", 1), message("errA1"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "b" }, "errB0");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertLong(param(emptyKey, 0), message("errEK"));
                this.assertLong(param(singleNull, 0), message("errSN"));
                this.assertLong(param(singleEmpty, 0), message("errSE"));

                this.assertLong(param("a", 0), message("errA0"));
                this.assertLong(param("a", 1), message("errA1"));
                this.assertLong(param("b", 0), message("errB0"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertNotEmpty_String_Message() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertNotEmpty(singleStr, message("errSS"));
                this.assertNotEmpty(multiStr, message("errMS"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { multiNull }, "errMN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { multiEmpty }, "errME");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertNotEmpty(emptyKey, message("errEK"));

                this.assertNotEmpty(singleNull, message("errSN"));
                this.assertNotEmpty(multiNull, message("errMN"));

                this.assertNotEmpty(singleEmpty, message("errSE"));
                this.assertNotEmpty(multiEmpty, message("errME"));

                this.assertNotEmpty(singleStr, message("errSS"));
                this.assertNotEmpty(multiStr, message("errMS"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertNotEmpty_Param_Message() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertNotEmpty(param(singleStr, 0), message("errSS0"));
                this.assertNotEmpty(param(multiStr, 0), message("errMS0"));
                this.assertNotEmpty(param(multiStr, 1), message("errMS1"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK-1");
        result.addError(new String[] { emptyKey }, "errEK0");
        result.addError(new String[] { emptyKey }, "errEK1");
        result.addError(new String[] { singleNull }, "errSN-1");
        result.addError(new String[] { singleNull }, "errSN0");
        result.addError(new String[] { singleNull }, "errSN1");
        result.addError(new String[] { multiNull }, "errMN-1");
        result.addError(new String[] { multiNull }, "errMN0");
        result.addError(new String[] { multiNull }, "errMN1");
        result.addError(new String[] { multiNull }, "errMN2");
        result.addError(new String[] { singleEmpty }, "errSE-1");
        result.addError(new String[] { singleEmpty }, "errSE0");
        result.addError(new String[] { singleEmpty }, "errSE1");
        result.addError(new String[] { multiEmpty }, "errME-1");
        result.addError(new String[] { multiEmpty }, "errME0");
        result.addError(new String[] { multiEmpty }, "errME1");
        result.addError(new String[] { multiEmpty }, "errME2");
        result.addError(new String[] { singleStr }, "errSS-1");
        result.addError(new String[] { singleStr }, "errSS1");
        result.addError(new String[] { multiStr }, "errMS-1");
        result.addError(new String[] { multiStr }, "errMS2");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertNotEmpty(param(emptyKey, -1), message("errEK-1"));
                this.assertNotEmpty(param(emptyKey, 0), message("errEK0"));
                this.assertNotEmpty(param(emptyKey, 1), message("errEK1"));

                this.assertNotEmpty(param(singleNull, -1), message("errSN-1"));
                this.assertNotEmpty(param(singleNull, 0), message("errSN0"));
                this.assertNotEmpty(param(singleNull, 1), message("errSN1"));
                this.assertNotEmpty(param(multiNull, -1), message("errMN-1"));
                this.assertNotEmpty(param(multiNull, 0), message("errMN0"));
                this.assertNotEmpty(param(multiNull, 1), message("errMN1"));
                this.assertNotEmpty(param(multiNull, 2), message("errMN2"));

                this.assertNotEmpty(param(singleEmpty, -1), message("errSE-1"));
                this.assertNotEmpty(param(singleEmpty, 0), message("errSE0"));
                this.assertNotEmpty(param(singleEmpty, 1), message("errSE1"));
                this.assertNotEmpty(param(multiEmpty, -1), message("errME-1"));
                this.assertNotEmpty(param(multiEmpty, 0), message("errME0"));
                this.assertNotEmpty(param(multiEmpty, 1), message("errME1"));
                this.assertNotEmpty(param(multiEmpty, 2), message("errME2"));

                this.assertNotEmpty(param(singleStr, -1), message("errSS-1"));
                this.assertNotEmpty(param(singleStr, 0), message("errSS0"));
                this.assertNotEmpty(param(singleStr, 1), message("errSS1"));
                this.assertNotEmpty(param(multiStr, -1), message("errMS-1"));
                this.assertNotEmpty(param(multiStr, 0), message("errMS0"));
                this.assertNotEmpty(param(multiStr, 1), message("errMS1"));
                this.assertNotEmpty(param(multiStr, 2), message("errMS2"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertShort_String_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "43");
        params.add("b", "99999");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertShort("a", message("errA"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "b" }, "errB");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertShort(emptyKey, message("errEK"));
                this.assertShort(singleNull, message("errSN"));
                this.assertShort(singleEmpty, message("errSE"));

                this.assertShort("a", message("errA"));
                this.assertShort("b", message("errB"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertShort_Param_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "32767");
        params.add("a", "12");
        params.add("b", "32768");
        params.add("b", "5.67");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertShort(param("a", 0), message("errA0"));
                this.assertShort(param("a", 1), message("errA1"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEK");
        result.addError(new String[] { singleNull }, "errSN");
        result.addError(new String[] { singleEmpty }, "errSE");
        result.addError(new String[] { "b" }, "errB0");
        result.addError(new String[] { "b" }, "errB1");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertShort(param(emptyKey, 0), message("errEK"));
                this.assertShort(param(singleNull, 0), message("errSN"));
                this.assertShort(param(singleEmpty, 0), message("errSE"));

                this.assertShort(param("a", 0), message("errA0"));
                this.assertShort(param("a", 1), message("errA1"));
                this.assertShort(param("b", 0), message("errB0"));
                this.assertShort(param("b", 1), message("errB1"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertSize_String_Size_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "1234567890");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertSize(emptyKey, maxSize(100),
                        message("errEKsize2"));
                this.assertSize(singleNull, maxSize(100),
                        message("errSNsize2"));
                this.assertSize(singleEmpty, maxSize(100),
                        message("errSEsize2"));

                this.assertSize("a", size(0, 100),
                        message("errAsize1"));
                this.assertSize("a", size(1, 10),
                        message("errAsize2"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEKsize1");
        result.addError(new String[] { singleNull }, "errSNsize1");
        result.addError(new String[] { singleEmpty }, "errSEsize1");
        result.addError(new String[] { "a" }, "errAsize3");
        result.addError(new String[] { "a" }, "errAsize4");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertSize(emptyKey, size(1, 100),
                        message("errEKsize1"));
                this.assertSize(singleNull, size(1, 100),
                        message("errSNsize1"));
                this.assertSize(singleEmpty, size(1, 100),
                        message("errSEsize1"));

                this.assertSize(emptyKey, maxSize(100),
                        message("errEKsize2"));
                this.assertSize(singleNull, maxSize(100),
                        message("errSNsize2"));
                this.assertSize(singleEmpty, maxSize(100),
                        message("errSEsize2"));

                this.assertSize("a", size(0, 100),
                        message("errAsize1"));
                this.assertSize("a", size(1, 10),
                        message("errAsize2"));
                this.assertSize("a", size(11, 100),
                        message("errAsize3"));
                this.assertSize("a", size(0, 9),
                        message("errAsize4"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$AssertSize_Param_Size_Message() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "1234567890");
        params.add("a", "abcdef");
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertSize(param(emptyKey, 0), maxSize(100),
                        message("errEKsize2"));
                this.assertSize(param(singleNull, 0), maxSize(100),
                        message("errSNsize2"));
                this.assertSize(param(singleEmpty, 0), maxSize(100),
                        message("errSEsize2"));

                this.assertSize(param("a", 0), size(0, 10),
                        message("errA0size1"));
                this.assertSize(param("a", 0), size(1, 100),
                        message("errA0size2"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { emptyKey }, "errEKsize1");
        result.addError(new String[] { singleNull }, "errSNsize1");
        result.addError(new String[] { singleEmpty }, "errSEsize1");
        result.addError(new String[] { "a" }, "errA0size3");
        result.addError(new String[] { "a" }, "errA0size4");
        result.addError(new String[] { "a" }, "errA1size2");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                this.assertSize(param(emptyKey, 0), size(1, 100),
                        message("errEKsize1"));
                this.assertSize(param(singleNull, 0), size(1, 100),
                        message("errSNsize1"));
                this.assertSize(param(singleEmpty, 0), size(1, 100),
                        message("errSEsize1"));

                this.assertSize(param(emptyKey, 0), maxSize(100),
                        message("errEKsize2"));
                this.assertSize(param(singleNull, 0), maxSize(100),
                        message("errSNsize2"));
                this.assertSize(param(singleEmpty, 0), maxSize(100),
                        message("errSEsize2"));

                this.assertSize(param("a", 0), size(0, 10),
                        message("errA0size1"));
                this.assertSize(param("a", 0), size(1, 100),
                        message("errA0size2"));
                this.assertSize(param("a", 0), size(20, 100),
                        message("errA0size3"));
                this.assertSize(param("a", 0), size(4, 7),
                        message("errA0size4"));
                this.assertSize(param("a", 1), size(5, 6),
                        message("errA1size1"));
                this.assertSize(param("a", 1), size(7, 10),
                        message("errA1size2"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyValidatorTest$I18n() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);
        RestyRequest.RestyValidator val =
                req.new RestyValidator(Locale.ENGLISH);

        assertEquals(bundleValueA,
                val.i18n(bundleKeyA, TestBundle.class.getName()));
        assertEquals(bundleValueB,
                val.i18n(bundleKeyB, TestBundle.class.getName()));
        assertEquals(String.format(bundleValueB, "arg1"),
                val.i18n(bundleKeyB, TestBundle.class.getName(),
                        "arg1"));
        assertEquals(String.format(bundleValueC, "arg1", "arg2"),
                val.i18n(bundleKeyC, TestBundle.class.getName(),
                        "arg1", "arg2"));
        try {
            val.i18n(null, TestBundle.class.getName());
        } catch (Exception ex) {
        }
        try {
            val.i18n(null, TestBundle.class.getName(), "arg1");
            fail();
        } catch (Exception ex) {
        }
        try {
            val.i18n("nonexistent", TestBundle.class.getName());
            fail();
        } catch (Exception ex) {
        }
        try {
            val.i18n("nonexistent", TestBundle.class.getName(), "arg1");
            fail();
        } catch (Exception ex) {
        }
        try {
            val.i18n(bundleKeyA, null);
            fail();
        } catch (Exception ex) {
        }
        try {
            val.i18n(bundleKeyA, null, "arg1");
            fail();
        } catch (Exception ex) {
        }
        try {
            val.i18n(bundleKeyA, "nonexistent");
            fail();
        } catch (Exception ex) {
        }
        try {
            val.i18n(bundleKeyA, "nonexistent", "arg1");
            fail();
        } catch (Exception ex) {
        }
    }

    @Test
    public void testRestyValidatorTest$MakeValidation() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        try {
            req.new RestyValidator(Locale.ENGLISH) {

                {
                    makeValidation(null, false, message(null, "errNull"));
                    fail();
                }

            };
        } catch (Exception ex) {
        }

        Map<String, Object> expected = new HashMap<>();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                makeValidation(null, true, null);
                makeValidation(null, true, message(null, "errNull"));
                makeValidation(null, true, message("a", "errA"));
                makeValidation(null, true, commonMessage("commonErr"));
                makeValidation(param("b"), true, message("errB"));
            }

        };

        assertTrue(req.isValid());
        assertEquals(expected, req.getErrors());

        RestyValidationResult result = new RestyValidationResult();
        result.addError(new String[] { "a" }, "errA");
        result.addError(new String[] { "a" }, "errA");
        result.addError(new String[] { "b" }, "errB0");
        result.addError(new String[] { "b" }, "errB25");
        result.addError(new String[] { "fldC" }, "errC0");
        result.addError(new String[] { "fldC" }, "errC4");
        result.addError(null, "err1");
        result.addError(null, "err2");
        result.addError(null, "err3");
        expected = result.getErrors();

        req.new RestyValidator(Locale.ENGLISH) {

            {
                makeValidation(param("a"), false, message("errA"));
                makeValidation(param("a"), false, message("errA"));
                makeValidation(param("b"), false, message("errB0"));
                makeValidation(param("b", 25), false, message("errB25"));
                makeValidation(param("c"), false, message("fldC", "errC0"));
                makeValidation(param("c", 4), false, message("fldC", "errC4"));
                makeValidation(null, false, commonMessage("err1"));
                makeValidation(param("_"), false, commonMessage("err2"));
                makeValidation(param("_", 1), false, commonMessage("err3"));
            }

        };

        assertFalse(req.isValid());
        assertEquals(expected, req.getErrors());
    }

    @Test
    public void testRestyRequest_RestyParams() {
        RestyParams params = getBaseSetOfParams();
        new RestyRequest(params);
    }

    @Test
    public void testRestyRequest_RestyRequest_String() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);
        new RestyRequest(req, "sub");
    }

    @Test
    public void testCheckBoolean_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "true");
        params.add("b", "false");
        params.add("c", "null");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkBoolean(emptyKey));
        assertFalse(req.checkBoolean(singleNull));
        assertFalse(req.checkBoolean(singleEmpty));

        assertTrue(req.checkBoolean("a"));
        assertTrue(req.checkBoolean("b"));
        assertFalse(req.checkBoolean("c"));
    }

    @Test
    public void testCheckBoolean_Param() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "true");
        params.add("a", "false");
        params.add("b", "FaLsE");
        params.add("b", "True");
        params.add("c", "null");
        params.add("c", "true");
        params.add("c", "false");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkBoolean(param(emptyKey, 0)));
        assertFalse(req.checkBoolean(param(singleNull, 0)));
        assertFalse(req.checkBoolean(param(singleEmpty, 0)));

        assertTrue(req.checkBoolean(param("a", 0)));
        assertTrue(req.checkBoolean(param("a", 1)));
        assertTrue(req.checkBoolean(param("b", 0)));
        assertTrue(req.checkBoolean(param("b", 1)));
        assertFalse(req.checkBoolean(param("c", 0)));
        assertTrue(req.checkBoolean(param("c", 1)));
        assertTrue(req.checkBoolean(param("c", 2)));
    }

    @Test
    public void testCheckDate_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "2001-01-01");
        params.add("b", "01.01.01");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkDate(emptyKey));
        assertFalse(req.checkDate(singleNull));
        assertFalse(req.checkDate(singleEmpty));

        assertTrue(req.checkDate("a"));
        assertFalse(req.checkDate("b"));
    }

    @Test
    public void testCheckDate_Param() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "1999-04-24");
        params.add("a", "2005-12-30");
        params.add("b", "07.09.2010");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkDate(param(emptyKey, 0)));
        assertFalse(req.checkDate(param(singleNull, 0)));
        assertFalse(req.checkDate(param(singleEmpty, 0)));

        assertTrue(req.checkDate(param("a", 0)));
        assertTrue(req.checkDate(param("a", 1)));
        assertFalse(req.checkDate(param("b", 0)));
    }

    @Test
    public void testCheckExists_String() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        assertTrue(req.checkExists(emptyKey));

        assertTrue(req.checkExists(singleNull));
        assertTrue(req.checkExists(multiNull));

        assertTrue(req.checkExists(singleEmpty));
        assertTrue(req.checkExists(multiEmpty));

        assertTrue(req.checkExists(singleStr));
        assertTrue(req.checkExists(multiStr));

        assertFalse(req.checkExists("nonexistent"));
    }

    @Test
    public void testCheckExists_Param() {
        RestyParams params = getBaseSetOfParams();
        params.add("sub[a]", "valueA0");
        params.add("sub[b]", "valueB0");
        params.add("sub[b]", "valueB1");
        params.add("sub[b][c]", "valueC0");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkExists(param(emptyKey, -1)));
        assertTrue(req.checkExists(param(emptyKey, 0)));
        assertFalse(req.checkExists(param(emptyKey, 1)));

        assertFalse(req.checkExists(param(singleNull, -1)));
        assertTrue(req.checkExists(param(singleNull, 0)));
        assertFalse(req.checkExists(param(singleNull, 1)));
        assertFalse(req.checkExists(param(multiNull, -1)));
        assertTrue(req.checkExists(param(multiNull, 0)));
        assertTrue(req.checkExists(param(multiNull, 1)));
        assertFalse(req.checkExists(param(multiNull, 2)));

        assertFalse(req.checkExists(param(singleEmpty, -1)));
        assertTrue(req.checkExists(param(singleEmpty, 0)));
        assertFalse(req.checkExists(param(singleEmpty, 1)));
        assertFalse(req.checkExists(param(multiEmpty, -1)));
        assertTrue(req.checkExists(param(multiEmpty, 0)));
        assertTrue(req.checkExists(param(multiEmpty, 1)));
        assertFalse(req.checkExists(param(multiEmpty, 2)));

        assertFalse(req.checkExists(param(singleStr, -1)));
        assertTrue(req.checkExists(param(singleStr, 0)));
        assertFalse(req.checkExists(param(singleStr, 1)));
        assertFalse(req.checkExists(param(multiStr, -1)));
        assertTrue(req.checkExists(param(multiStr, 0)));
        assertTrue(req.checkExists(param(multiStr, 1)));
        assertFalse(req.checkExists(param(multiStr, 2)));

        assertFalse(req.checkExists(param("nonexistent")));

        RestyRequest sub = new RestyRequest(req, "sub");

        assertFalse(sub.checkExists(singleStr));
        assertTrue(sub.checkExists(param("a", 0)));
        assertTrue(sub.checkExists(param("b", 0)));
        assertTrue(sub.checkExists(param("b", 1)));
        assertFalse(sub.checkExists(param("b", 2)));
        assertFalse(sub.checkExists(param("c", 0)));

        RestyRequest b = new RestyRequest(sub, "b");
        assertFalse(b.checkExists(singleStr));
        assertFalse(b.checkExists(param("a", 0)));
        assertFalse(b.checkExists(param("b", 0)));
        assertTrue(b.checkExists(param("c", 0)));
    }

    @Test
    public void testCheckFloat_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "245");
        params.add("b", "1.56");
        params.add("c", ".0000111");
        params.add("d", "1,1");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkFloat(emptyKey));
        assertFalse(req.checkFloat(singleNull));
        assertFalse(req.checkFloat(singleEmpty));

        assertTrue(req.checkFloat("a"));
        assertTrue(req.checkFloat("b"));
        assertTrue(req.checkFloat("c"));
        assertFalse(req.checkFloat("d"));
    }

    @Test
    public void testCheckFloat_Param() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "30");
        params.add("a", "0");
        params.add("b", "0.1");
        params.add("b", "0.999");
        params.add("c", ".0000243");
        params.add("d", "28,2");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkFloat(param(emptyKey, 0)));
        assertFalse(req.checkFloat(param(singleNull, 0)));
        assertFalse(req.checkFloat(param(singleEmpty, 0)));

        assertTrue(req.checkFloat(param("a", 0)));
        assertTrue(req.checkFloat(param("a", 1)));
        assertTrue(req.checkFloat(param("b", 0)));
        assertTrue(req.checkFloat(param("b", 1)));
        assertTrue(req.checkFloat(param("c", 0)));
        assertFalse(req.checkFloat(param("d", 0)));
    }

    @Test
    public void testCheckInteger_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "15");
        params.add("b", "2222222222");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkInteger(emptyKey));
        assertFalse(req.checkInteger(singleNull));
        assertFalse(req.checkInteger(singleEmpty));

        assertTrue(req.checkInteger("a"));
        assertFalse(req.checkInteger("b"));
    }

    @Test
    public void testCheckInteger_Param() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "2147483647");
        params.add("a", "1");
        params.add("b", "2147483648");
        params.add("b", "2.01");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkInteger(param(emptyKey, 0)));
        assertFalse(req.checkInteger(param(singleNull, 0)));
        assertFalse(req.checkInteger(param(singleEmpty, 0)));

        assertTrue(req.checkInteger(param("a", 0)));
        assertTrue(req.checkInteger(param("a", 1)));
        assertFalse(req.checkInteger(param("b", 0)));
        assertFalse(req.checkInteger(param("b", 1)));
    }

    @Test
    public void testCheckLong_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "123");
        params.add("b", "3.5");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkLong(emptyKey));
        assertFalse(req.checkLong(singleNull));
        assertFalse(req.checkLong(singleEmpty));

        assertTrue(req.checkLong("a"));
        assertFalse(req.checkLong("b"));
    }

    @Test
    public void testCheckLong_Param() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "2147483648");
        params.add("a", "999999999999");
        params.add("b", "1.1");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkLong(param(emptyKey, 0)));
        assertFalse(req.checkLong(param(singleNull, 0)));
        assertFalse(req.checkLong(param(singleEmpty, 0)));

        assertTrue(req.checkLong(param("a", 0)));
        assertTrue(req.checkLong(param("a", 1)));
        assertFalse(req.checkLong(param("b", 0)));
    }

    @Test
    public void testCheckNotEmpty_String() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkNotEmpty(emptyKey));

        assertFalse(req.checkNotEmpty(singleNull));
        assertFalse(req.checkNotEmpty(multiNull));

        assertFalse(req.checkNotEmpty(singleEmpty));
        assertFalse(req.checkNotEmpty(multiEmpty));

        assertTrue(req.checkNotEmpty(singleStr));
        assertTrue(req.checkNotEmpty(multiStr));
    }

    @Test
    public void testCheckNotEmpty_Param() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkNotEmpty(param(emptyKey, -1)));
        assertFalse(req.checkNotEmpty(param(emptyKey, 0)));
        assertFalse(req.checkNotEmpty(param(emptyKey, 1)));

        assertFalse(req.checkNotEmpty(param(singleNull, -1)));
        assertFalse(req.checkNotEmpty(param(singleNull, 0)));
        assertFalse(req.checkNotEmpty(param(singleNull, 1)));
        assertFalse(req.checkNotEmpty(param(multiNull, -1)));
        assertFalse(req.checkNotEmpty(param(multiNull, 0)));
        assertFalse(req.checkNotEmpty(param(multiNull, 1)));
        assertFalse(req.checkNotEmpty(param(multiNull, 2)));

        assertFalse(req.checkNotEmpty(param(singleEmpty, -1)));
        assertFalse(req.checkNotEmpty(param(singleEmpty, 0)));
        assertFalse(req.checkNotEmpty(param(singleEmpty, 1)));
        assertFalse(req.checkNotEmpty(param(multiEmpty, -1)));
        assertFalse(req.checkNotEmpty(param(multiEmpty, 0)));
        assertFalse(req.checkNotEmpty(param(multiEmpty, 1)));
        assertFalse(req.checkNotEmpty(param(multiEmpty, 2)));

        assertFalse(req.checkNotEmpty(param(singleStr, -1)));
        assertTrue(req.checkNotEmpty(param(singleStr, 0)));
        assertFalse(req.checkNotEmpty(param(singleStr, 1)));
        assertFalse(req.checkNotEmpty(param(multiStr, -1)));
        assertTrue(req.checkNotEmpty(param(multiStr, 0)));
        assertTrue(req.checkNotEmpty(param(multiStr, 1)));
        assertFalse(req.checkNotEmpty(param(multiStr, 2)));
    }

    @Test
    public void testCheckShort_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "43");
        params.add("b", "99999");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkShort(emptyKey));
        assertFalse(req.checkShort(singleNull));
        assertFalse(req.checkShort(singleEmpty));

        assertTrue(req.checkShort("a"));
        assertFalse(req.checkShort("b"));
    }

    @Test
    public void testCheckShort_Param() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "32767");
        params.add("a", "12");
        params.add("b", "32768");
        params.add("b", "5.67");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkShort(param(emptyKey, 0)));
        assertFalse(req.checkShort(param(singleNull, 0)));
        assertFalse(req.checkShort(param(singleEmpty, 0)));

        assertTrue(req.checkShort(param("a", 0)));
        assertTrue(req.checkShort(param("a", 1)));
        assertFalse(req.checkShort(param("b", 0)));
        assertFalse(req.checkShort(param("b", 1)));
    }

    @Test
    public void testCheckSize_String_Size() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "1234567890");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkSize(emptyKey, size(1, 100)));
        assertFalse(req.checkSize(singleNull, size(1, 100)));
        assertFalse(req.checkSize(singleEmpty, size(1, 100)));

        assertTrue(req.checkSize(emptyKey, maxSize(100)));
        assertTrue(req.checkSize(singleNull, maxSize(100)));
        assertTrue(req.checkSize(singleEmpty, maxSize(100)));

        assertTrue(req.checkSize("a", size(0, 100)));
        assertTrue(req.checkSize("a", size(1, 10)));
        assertFalse(req.checkSize("a", size(11, 100)));
        assertFalse(req.checkSize("a", size(0, 9)));
    }

    @Test
    public void testCheckSize_Param_Size() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "1234567890");
        params.add("a", "abcdef");
        RestyRequest req = new RestyRequest(params);

        assertFalse(req.checkSize(param(emptyKey, 0), size(1, 100)));
        assertFalse(req.checkSize(param(singleNull, 0), size(1, 100)));
        assertFalse(req.checkSize(param(singleEmpty, 0), size(1, 100)));

        assertTrue(req.checkSize(param(emptyKey, 0), maxSize(100)));
        assertTrue(req.checkSize(param(singleNull, 0), maxSize(100)));
        assertTrue(req.checkSize(param(singleEmpty, 0), maxSize(100)));

        assertTrue(req.checkSize(param("a", 0), size(0, 10)));
        assertTrue(req.checkSize(param("a", 0), size(1, 100)));
        assertFalse(req.checkSize(param("a", 0), size(20, 100)));
        assertFalse(req.checkSize(param("a", 0), size(4, 7)));
        assertTrue(req.checkSize(param("a", 1), size(5, 6)));
        assertFalse(req.checkSize(param("a", 1), size(7, 10)));
    }

    @Test
    public void testGetBoolean_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "true");
        params.add("b", "false");
        params.add("c", "null");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getBoolean(emptyKey));
        assertNull(req.getBoolean(singleNull));
        assertNull(req.getBoolean(singleEmpty));

        assertTrue(req.getBoolean("a"));
        assertFalse(req.getBoolean("b"));
        try {
            req.getBoolean("c");
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetBoolean_String_int() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "true");
        params.add("a", "false");
        params.add("b", "FaLsE");
        params.add("b", "True");
        params.add("c", "null");
        params.add("c", "true");
        params.add("c", "false");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getBoolean(emptyKey, 0));
        assertNull(req.getBoolean(singleNull, 0));
        assertNull(req.getBoolean(singleEmpty, 0));

        assertTrue(req.getBoolean("a", 0));
        assertFalse(req.getBoolean("a", 1));
        assertFalse(req.getBoolean("b", 0));
        assertTrue(req.getBoolean("b", 1));
        try {
            req.getBoolean("c", 0);
            fail();
        } catch (IllegalArgumentException ex) {
        }
        assertTrue(req.getBoolean("c", 1));
        assertFalse(req.getBoolean("c", 2));
    }

    @Test
    public void testGetDate_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "2001-01-01");
        params.add("b", "01.01.01");
        RestyRequest req = new RestyRequest(params);
        Calendar cal = Calendar.getInstance();

        assertNull(req.getDate(emptyKey));
        assertNull(req.getDate(singleNull));
        assertNull(req.getDate(singleEmpty));

        cal.clear();
        cal.set(2001, Calendar.JANUARY, 1);
        assertEquals(cal.getTime(), req.getDate("a"));
        try {
            req.getDate("b");
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetDate_String_int() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "1999-04-24");
        params.add("a", "2005-12-30");
        params.add("b", "07.09.2010");
        RestyRequest req = new RestyRequest(params);
        Calendar cal = Calendar.getInstance();

        assertNull(req.getDate(emptyKey, 0));
        assertNull(req.getDate(singleNull, 0));
        assertNull(req.getDate(singleEmpty, 0));

        cal.clear();
        cal.set(1999, Calendar.APRIL, 24);
        assertEquals(cal.getTime(), req.getDate("a", 0));
        cal.clear();
        cal.set(2005, Calendar.DECEMBER, 30);
        assertEquals(cal.getTime(), req.getDate("a", 1));
        try {
            req.getDate("b", 0);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetErrors() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        Map<String, Object> expected = new HashMap<>();
        Map<String, Object> actual = req.getErrors();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetFloat_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "245");
        params.add("b", "1.56");
        params.add("c", ".0000111");
        params.add("d", "1,1");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getFloat(emptyKey));
        assertNull(req.getFloat(singleNull));
        assertNull(req.getFloat(singleEmpty));

        assertEquals(0, Float.compare(245F, req.getFloat("a")));
        assertEquals(0, Float.compare(1.56F, req.getFloat("b")));
        assertEquals(0, Float.compare(0.0000111F, req.getFloat("c")));
        try {
            req.getFloat("d", 0);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetFloat_String_int() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "30");
        params.add("a", "0");
        params.add("b", "0.1");
        params.add("b", "0.999");
        params.add("c", ".0000243");
        params.add("d", "28,2");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getFloat(emptyKey, 0));
        assertNull(req.getFloat(singleNull, 0));
        assertNull(req.getFloat(singleEmpty, 0));

        assertEquals(0, Float.compare(30F, req.getFloat("a", 0)));
        assertEquals(0, Float.compare(0F, req.getFloat("a", 1)));
        assertEquals(0, Float.compare(0.1F, req.getFloat("b", 0)));
        assertEquals(0, Float.compare(0.999F, req.getFloat("b", 1)));
        assertEquals(0, Float.compare(0.0000243F, req.getFloat("c", 0)));
        try {
            req.getFloat("d", 0);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetInteger_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "15");
        params.add("b", "2222222222");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getInteger(emptyKey));
        assertNull(req.getInteger(singleNull));
        assertNull(req.getInteger(singleEmpty));

        assertEquals(0, Integer.compare(15, req.getInteger("a")));
        try {
            req.getInteger("b");
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetInteger_String_int() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "2147483647");
        params.add("a", "1");
        params.add("b", "2147483648");
        params.add("b", "2.01");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getInteger(emptyKey, 0));
        assertNull(req.getInteger(singleNull, 0));
        assertNull(req.getInteger(singleEmpty, 0));

        assertEquals(0, Integer.compare(2147483647, req.getInteger("a", 0)));
        assertEquals(0, Integer.compare(1, req.getInteger("a", 1)));
        try {
            req.getInteger("b", 0);
            fail();
        } catch (IllegalArgumentException ex) {
        }
        try {
            req.getInteger("b", 1);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetLong_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "123");
        params.add("b", "3.5");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getLong(emptyKey));
        assertNull(req.getLong(singleNull));
        assertNull(req.getLong(singleEmpty));

        assertEquals(0, Long.compare(123L, req.getLong("a")));
        try {
            req.getLong("b");
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetLong_String_int() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "2147483648");
        params.add("a", "999999999999");
        params.add("b", "1.1");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getLong(emptyKey, 0));
        assertNull(req.getLong(singleNull, 0));
        assertNull(req.getLong(singleEmpty, 0));

        assertEquals(0, Long.compare(2147483648L, req.getLong("a", 0)));
        assertEquals(0, Long.compare(999999999999L, req.getLong("a", 1)));
        try {
            req.getLong("b", 0);
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetShort_String() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "43");
        params.add("b", "99999");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getShort(emptyKey));
        assertNull(req.getShort(singleNull));
        assertNull(req.getShort(singleEmpty));

        assertEquals(0, Short.compare((short) 43, req.getShort("a")));
        try {
            req.getShort("b");
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetShort_String_int() {
        RestyParams params = getBaseSetOfParams();
        params.add("a", "32767");
        params.add("a", "12");
        params.add("b", "32768");
        params.add("b", "5.67");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getShort(emptyKey, 0));
        assertNull(req.getShort(singleNull, 0));
        assertNull(req.getShort(singleEmpty, 0));

        assertEquals(0, Short.compare((short) 32767, req.getShort("a", 0)));
        assertEquals(0, Short.compare((short) 12, req.getShort("a", 1)));
        try {
            req.getShort("b", 0);
            fail();
        } catch (IllegalArgumentException ex) {
        }
        try {
            req.getShort("b", 1);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testGetString_String() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getString(emptyKey));

        assertNull(req.getString(singleNull));
        assertNull(req.getString(multiNull));

        assertEquals(EMPTY, req.getString(singleEmpty));
        assertEquals(EMPTY, req.getString(multiEmpty));

        assertEquals(SST1, req.getString(singleStr));
        assertEquals(MST1, req.getString(multiStr));
    }

    @Test
    public void testGetString_String_int() {
        RestyParams params = getBaseSetOfParams();
        params.add("sub[a]", "valueA0");
        params.add("sub[b]", "valueB0");
        params.add("sub[b]", "valueB1");
        params.add("sub[b][c]", "valueC0");
        RestyRequest req = new RestyRequest(params);

        assertNull(req.getString(emptyKey, -1));
        assertNull(req.getString(emptyKey, 0));
        assertNull(req.getString(emptyKey, 1));

        assertNull(req.getString(singleNull, -1));
        assertNull(req.getString(singleNull, 0));
        assertNull(req.getString(singleNull, 1));
        assertNull(req.getString(multiNull, -1));
        assertNull(req.getString(multiNull, 0));
        assertNull(req.getString(multiNull, 1));
        assertNull(req.getString(multiNull, 2));

        assertNull(req.getString(singleEmpty, -1));
        assertEquals(EMPTY, req.getString(singleEmpty, 0));
        assertNull(req.getString(singleEmpty, 1));
        assertNull(req.getString(multiEmpty, -1));
        assertEquals(EMPTY, req.getString(multiEmpty, 0));
        assertEquals(EMPTY, req.getString(multiEmpty, 1));
        assertNull(req.getString(multiEmpty, 2));

        assertNull(req.getString(singleStr, -1));
        assertEquals(SST1, req.getString(singleStr, 0));
        assertNull(req.getString(singleStr, 1));
        assertNull(req.getString(multiStr, -1));
        assertEquals(MST1, req.getString(multiStr, 0));
        assertEquals(MST2, req.getString(multiStr, 1));
        assertNull(req.getString(multiStr, 2));

        RestyRequest sub = new RestyRequest(req, "sub");

        assertNull(sub.getString(singleStr));
        assertEquals("valueA0", sub.getString("a", 0));
        assertEquals("valueB0", sub.getString("b", 0));
        assertEquals("valueB1", sub.getString("b", 1));
        assertNull(sub.getString("b", 2));
        assertNull(sub.getString("c", 0));

        RestyRequest b = new RestyRequest(sub, "b");
        assertNull(b.getString(singleStr));
        assertNull(b.getString("a", 0));
        assertNull(b.getString("b", 0));
        assertEquals("valueC0", b.getString("c", 0));
    }

    @Test
    public void testIsValid() {
        RestyParams params = getBaseSetOfParams();
        RestyRequest req = new RestyRequest(params);

        assertTrue(req.isValid());
    }

    //------------------------------------------------------------------------

    @Test
    public void testCommonMessage() {
        Message expected =
                new CommonMessage("Common error");
        Message actual =
                commonMessage("Common error");

        assertEquals(expected.fieldName, actual.fieldName);
        assertEquals(expected.errorDesc, actual.errorDesc);
    }

    @Test
    public void testMaxSize() {
        Size expected = new Size(0, 100);
        Size actual = maxSize(100);

        assertEquals(expected.max, actual.max);
        assertEquals(expected.min, actual.min);
    }

    @Test
    public void testMessage_String() {
        Message expected =
                new Message(null, "Invalid id value");
        Message actual =
                message("Invalid id value");

        assertEquals(expected.fieldName, actual.fieldName);
        assertEquals(expected.errorDesc, actual.errorDesc);
    }

    @Test
    public void testMessage_String_String() {
        Message expected =
                new Message("id", "Invalid id value");
        Message actual =
                message("id", "Invalid id value");

        assertEquals(expected.fieldName, actual.fieldName);
        assertEquals(expected.errorDesc, actual.errorDesc);
    }

    @Test
    public void testParam_String() {
        Param expected = new Param("a", 0);
        Param actual = param("a", 0);

        assertEquals(expected.paramName, actual.paramName);
        assertEquals(expected.index, actual.index);
    }

    @Test
    public void testParam_String_int() {
        Param expected = new Param("a", 4);
        Param actual = param("a", 4);

        assertEquals(expected.paramName, actual.paramName);
        assertEquals(expected.index, actual.index);
        try {
            new Param("", 1);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testSize() {
        Size expected = new Size(10, 40);
        Size actual = size(10, 40);

        assertEquals(expected.max, actual.max);
        assertEquals(expected.min, actual.min);
    }

}
