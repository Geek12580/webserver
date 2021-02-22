package com.exercise;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 学习基础开发——练习题3
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年6月24日
 */
public class ExerciseThree {
	/**
	 * 调用树
	 * @param root 根节点
	 * @param level 需要遍历到第几层
	 * @return 返回遍历数据
	 */
	public static String dispatchTree(TNode root, int level) {
		String[] array = treeLevel(root, level);
		StringBuffer sb = new StringBuffer();
		if (array != null) {
			for (String str : array) {
				sb.append(str);
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * 创建树
	 * @return 返回根节点
	 */
	public static TNode createTree() {
		TNode A = new TNode();
		A.value = "A";
		TNode B = new TNode();
		B.value = "B";
		TNode D = new TNode();
		D.value = "D";
		TNode G = new TNode();
		G.value = "G";
		TNode H = new TNode();
		H.value = "H";
		TNode C = new TNode();
		C.value = "C";
		TNode F = new TNode();
		F.value = "F";
		A.left = B;
		A.right = D;
		B.left = G;
		B.right = H;
		D.left = C;
		D.right = F;
		return A;
	}

	/**
	 * 从左往右返回树tree的第level层的所有节点值
	 * @param tree传入树的根节点
	 * @param level树的第几层
	 * @return 返回节点值的数组
	 */
	public static String[] treeLevel(TNode tree, int level) {
		// 传入的根节点为空或者请求第0层的节点，返回null
		if (tree == null || level == 0) {
			return null;
		}
		String[] nodeArray = null;// 定义一个字符串数组存储返回的节点
		int currentLevel = 0;// 初始化当前层数为0
		/**
		 * 利用队列对树进行层序遍历 这里选用LinkedBlockingQueue，增加删除数据的效率比较高
		 */
		Queue<TNode> que = new LinkedBlockingQueue<TNode>();
		que.add(tree);
		// 队列不为空，持续遍历
		while (que.size() > 0) {
			currentLevel++;
			// 遍历到指定level，将当前level中的节点数据保存到字符串数组中，并结束整个循环
			if (currentLevel == level) {
				nodeArray = new String[que.size()];
				int j = 0;
				while (que.size() > 0) {
					nodeArray[j++] = que.poll().value;
				}
				break;
			}
			int currentLevelNum = que.size();// 保存当前level中的节点数目
			// 保证一次遍历完一层的节点
			for (int i = 0; i < currentLevelNum; i++) {
				TNode node = que.poll();
				if (node.left != null) {
					que.add(node.left);
				}
				if (node.right != null) {
					que.add(node.right);
				}
			}
		}
		return nodeArray;
	}
}