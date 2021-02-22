package com.exercise;

/**
 * 学习基础开发——练习题2
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>succez</p>
 * @author zengshenw
 * @createdate 2019年6月24日
 */
public class ExerciseTwo {
	/**
	 * 将10进制数转化为16进制
	 * @param number
	 * 传入的10进制数 注意：在这里特意将number类型设置为long类型，
	 * int类型边界值测试下限Integer.MIN_VALUE的绝对值 溢出Integer的范围
	 * @return 返回16进制的字符串
	 */
	public static String intToHex(long number) {
		/*
		 * 优化过程：之前没有考虑负数的情况 修改日期 2019/6/26
		 */
		boolean isNegative = false;
		if (number < 0) {
			isNegative = true;
		}
		number = Math.abs(number);
		// 绝对值小于16的数直接返回字符表示
		if (number < 16) {
			char hexNumber = convertToHexChar(number);
			return isNegative ? ("-" + hexNumber) : (hexNumber + "");
		}
		String hexNumber;// 最终转换得到的16进制数字符串
		StringBuffer buffer = new StringBuffer();
		int reminder;// 余数
		/**
		 * 实现过程类似于10进制转换为2进制的做法 除16取余法，保留每次的余数到StringBuffer中
		 * 循环结束后将StringBuffer元素倒序
		 */
		while (number != 0) {
			// 使用位运算效率比较高 number%16和number&15等价
			reminder = (int) (number & 15);
			number /= 16;
			buffer.append(convertToHexChar(reminder));
		}
		// 将得到的字符串颠倒
		hexNumber = buffer.reverse().toString();
		return isNegative ? ("-" + hexNumber) : (hexNumber + "");
	}
	/**
	 * 将余数转换为16进制表示
	 * @param reminder余数
	 * @return 返回16进制表示的字符，比如11->b
	 */
	private static char convertToHexChar(long reminder) {
		// 小于10，返回原数的字符表示
		if (reminder <= 9 && reminder >= 0) {
			return (char) (reminder + '0');
		}
		// 大于10，返回转换的字符表示
		return (char) (reminder - 10 + 'a');
	}
}
