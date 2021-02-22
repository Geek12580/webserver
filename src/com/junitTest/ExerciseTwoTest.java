package com.junitTest;

import org.junit.Assert;
import org.junit.Test;
import com.exercise.ExerciseTwo;

/**
 * 练习题2的单元测试
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年6月24日
 */
public class ExerciseTwoTest {

	@Test
	public void testIntToHex() {
		ExerciseTwo.intToHex(Integer.MIN_VALUE);
		// 大于16的数测试
		Assert.assertEquals("520", ExerciseTwo.intToHex(1312));
		// 小于16的数测试
		Assert.assertEquals("c", ExerciseTwo.intToHex(12));
		// 负数测试
		Assert.assertEquals("-8", ExerciseTwo.intToHex(-8));
		Assert.assertEquals("-12", ExerciseTwo.intToHex(-18));
		Assert.assertEquals("-1cd278", ExerciseTwo.intToHex(-1888888));
		// 比较大的数测试
		Assert.assertEquals("7d5ccb8", ExerciseTwo.intToHex(131452088));
		Assert.assertEquals("70962838", ExerciseTwo.intToHex(1888888888));
		// 边界值测试
		Assert.assertEquals("7fffffff", ExerciseTwo.intToHex(Integer.MAX_VALUE));
		Assert.assertEquals("-80000000", ExerciseTwo.intToHex(Integer.MIN_VALUE));
	}

}
