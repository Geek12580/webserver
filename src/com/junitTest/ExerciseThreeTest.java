package com.junitTest;

import org.junit.Assert;
import org.junit.Test;
import com.exercise.ExerciseThree;
import com.exercise.TNode;

/**
 * 练习题3的单元测试
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年6月24日
 */
public class ExerciseThreeTest {

	@Test
	public void testTreeLevel() {
		TNode root = ExerciseThree.createTree();
		// 测试树第三层的节点
		Assert.assertEquals("GHCF", ExerciseThree.dispatchTree(root, 3));
		// 测试树第二层的节点
		Assert.assertEquals("BD", ExerciseThree.dispatchTree(root, 2));
		// 测试树第一层的节点
		Assert.assertEquals("A", ExerciseThree.dispatchTree(root, 1));
		// 空值测试
		Assert.assertEquals(null, ExerciseThree.dispatchTree(root, 0));
		Assert.assertEquals(null, ExerciseThree.dispatchTree(null, 2));
	}

}
