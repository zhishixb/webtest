package com.jeesite.test.lang;

import com.jeesite.common.lang.WorkDayUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GetWorkingDayParamTest {

	private final WorkDayUtils utils = new WorkDayUtils();

	// 将字符串解析为 Calendar 对象
	private Calendar parseDate(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(dateStr));
		return cal;
	}

	@ParameterizedTest
	@CsvSource({
			"2025-06-13, 2025-06-13, 1, false",
			"2025-06-15, 2025-06-15, 0, false",
			"2025-06-13, 2025-06-15, 1	, false",
			"2025-06-15, 2025-06-17, 2, false",
			"2025-06-01, 2025-06-15, 10, false",
			"2025-06-02, 2025-06-17, 12, false",

			"2025-06-15, 2025-06-13, 0, true",
			"2025-06-31, 2025-07-01, 0, true",
			"2025-06-15, 2025-06-31, 0, true",
			"2025-06-01, invalid-date, 0, true",
			"invalid-date, 2025-06-01, 0, true"
	})
	void test_getWorkingDay_with_ExceptionHandling(
			String startDateStr,
			String endDateStr,
			int expectedDays,
			boolean expectException) throws ParseException {

		if (expectException) {
			assertThrows(ParseException.class, () -> {
				Calendar d1 = parseDate(startDateStr);
				Calendar d2 = parseDate(endDateStr);
				utils.getWorkingDay(d1, d2);
			}, "应该抛出异常但未抛出");
		} else {
			Calendar d1 = parseDate(startDateStr);
			Calendar d2 = parseDate(endDateStr);

			int result = utils.getWorkingDay(d1, d2);
			assertEquals(expectedDays, result);
		}
	}
}