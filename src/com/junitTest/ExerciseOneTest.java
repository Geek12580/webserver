package com.junitTest;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import com.exercise.ExerciseOne;
import junit.framework.Assert;

/**
 * 练习题1的单元测试
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年6月24日
 */
@SuppressWarnings("deprecation")
public class ExerciseOneTest {

	@Test
	public void testFile2buf() throws IOException {
		/**
		 * 定义文件的路径 filePath存在 filePath1不存在 filePath2目录
		 */
		String filePath = "D:/succezIDE/workspace/study/readme.txt";
		String filePath1 = "D:/succezIDE/workspace/study/readme.tx";
		String filePath2 = "D:/succezIDE/workspace/study";
		File file = new File(filePath);
		File file1 = new File(filePath1);
		File file2 = new File(filePath2);
		Assert.assertEquals((int) file.length(), ExerciseOne.file2buf(file).length);
		Assert.assertNull(ExerciseOne.file2buf(file1));
		Assert.assertNull(ExerciseOne.file2buf(file2));
	}

}
