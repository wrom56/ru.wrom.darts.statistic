package ru.wrom.darts.statistic.util;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

	@Test
	public void testGetScoreAsInt() {
		Assert.assertEquals(60, Utils.getScoreAsInt("T20"));
		Assert.assertEquals(15, Utils.getScoreAsInt("T5"));
		Assert.assertEquals(40, Utils.getScoreAsInt("D20"));
		Assert.assertEquals(2, Utils.getScoreAsInt("D1"));
		Assert.assertEquals(0, Utils.getScoreAsInt(null));
		Assert.assertEquals(0, Utils.getScoreAsInt(""));
		Assert.assertEquals(25, Utils.getScoreAsInt("25"));
	}

	@Test(expected = NumberFormatException.class)
	public void testGetScoreAsIntExc() {
		Utils.getScoreAsInt("a");
	}

	@Test
	public void testValidDartEditValue() throws Exception {
		Assert.assertTrue(Utils.validDartEditValue("19"));
		Assert.assertTrue(Utils.validDartEditValue("T11"));
	}
}