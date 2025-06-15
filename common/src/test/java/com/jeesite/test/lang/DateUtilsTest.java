/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.test.lang;

import com.jeesite.common.lang.DateUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GetMonthHasDaysTest {

	// 提供测试用例：日期字符串、预期天数、是否应抛异常
	private static Stream<Arguments> provideDateCases() {
		return Stream.of(
				// 有效等价类（不抛异常）
				Arguments.of("2025-06-15", 30, false),
				Arguments.of("2025-07-15", 31, false),
				Arguments.of("2025-02-15", 28, false),
				Arguments.of("2024-02-15", 29, false),

				// 无效等价类（应抛异常）
				Arguments.of("-06-15", 0, true),           // 年份非法
				Arguments.of("ssur-06-15", 0, true),       // 年份非法
				Arguments.of("2025--1-15", 0, true),       // 月份非法
				Arguments.of("2025-13-15", 0, true),       // 月份超出范围
				Arguments.of("2025-sr-15", 0, true),       // 月份非法
				Arguments.of("2025--31", 0, true),         // 月份非法
				Arguments.of("2025-06-31", 0, true),       // 日期非法（6月无31日）
				Arguments.of("2023-06--1", 0, true),       // 日期非法
				Arguments.of("2020-06-", 0, true)          // 格式错误
		);
	}

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private static Date parseDate(String dateStr) throws ParseException {
		if (dateStr == null) return null;
		return sdf.parse(dateStr);
	}

	@ParameterizedTest
	@MethodSource("provideDateCases")
	void test_getMonthHasDays(String inputDateStr, int expectedDays, boolean expectException) throws Exception {
		if (expectException) {
			assertThrows(ParseException.class, () -> {
				Date inputDate = parseDate(inputDateStr); // 这里会抛异常
				DateUtils.getMonthHasDays(inputDate);    // 实际不会执行
			}, "输入: " + inputDateStr + " 应该抛出 ParseException");
			System.out.println("输入: " + inputDateStr + " ✅ 正确抛出异常");
		} else {
			Date inputDate = parseDate(inputDateStr); // 不会抛异常
			int actualDays = DateUtils.getMonthHasDays(inputDate);
			assertEquals(expectedDays, actualDays,
					"输入: " + inputDateStr + " 应该有 " + expectedDays + " 天");
			System.out.println("输入: " + inputDateStr + " ✅ 返回天数: " + actualDays);
		}
	}
}