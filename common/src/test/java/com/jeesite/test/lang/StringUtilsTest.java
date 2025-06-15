package com.jeesite.test.lang;

import com.jeesite.common.lang.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HtmlAbbrTest {

    // 提供测试参数的方法：输入、截断长度、预期输出、是否期望异常
    private static Stream<Arguments> provideHtmlAbbrCases() {
        return Stream.of(
                // 正常情况
                Arguments.of("<p>这是一个测试内容<span style='color:red'>，用于演示HTML截断</span></p>", 20, "<p>这是一个测试内容<span style='color:red'>，用...</span></p>", false),
                Arguments.of("Hello&nbsp;World&nbsp;This is a long text.", 15, "Hello&nbsp;World&nbsp;...", false),
                Arguments.of("这是一段纯文本，用于测试截断功能", 20, "这是一段纯文本，...", false),
                Arguments.of("这是刚好合适的内容", 20, "这是刚好合适的内容", false),
                Arguments.of("较短的内容", 20, "较短的内容", false),

                // 特殊值
                Arguments.of(null, 20, "", false),
                Arguments.of("", 20, "", false),

                // 非法长度但不抛异常（假设函数内部处理）
                Arguments.of("<p>这是一个测试内容<span style='color:red'>，用于演示HTML截断</span></p>", -1, "...", false),

                // 可能导致异常的情况
                Arguments.of("<p>这是一个很长很长很长很长很长很长很长的测试内容</p>", "a", "", true),
                Arguments.of("一些文本", -1000000, "...", false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideHtmlAbbrCases")
    void test_htmlAbbr_Parameterized_withExpectedOutput(String input, int length, String expected, boolean expectException) {
        if (expectException) {
            assertThrows(Exception.class, () -> {
                String result = StringUtils.htmlAbbr(input, length);
                System.out.printf("输入: %-40s 输出: %s%n", input, result);
            });
        } else {
            String result = StringUtils.htmlAbbr(input, length);
            assertEquals(expected, result);
            System.out.printf("输入: %-40s 输出: %s%n", input, result);
        }
    }
}