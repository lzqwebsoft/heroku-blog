package com.herokuapp.lzqwebsoft.util;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String xssCharClear(String html, String baseUrl) {
        String value = html;
        String cleanValue = null;
        if (value != null) {
            cleanValue = Normalizer.normalize(value, Normalizer.Form.NFD);

            // Avoid null characters
            cleanValue = cleanValue.replaceAll("\0", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script(.*?)/script>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid anything between style tags
            Pattern stylePattern = Pattern.compile("<style(.*?)/style>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = stylePattern.matcher(cleanValue).replaceAll("");

            // Avoid anything between a tags
            cleanValue = cleanValue.replaceAll("(?im)<a.*>(.*?)</a>", "$1");

            // Avoid anything in a href='...' type of expression
            scriptPattern = Pattern.compile("href[\r\n]*=[\r\n]*(['\"])(.*?)\\1", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid anything in a src='...' type of expression
            // without location emoji image src
            String emojiPattern = "^(?i)" + baseUrl + "/resources/js/ke4/plugins/emoticons/images/\\d{1,2}\\.gif$";
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*(['\"])(.*?)\\1",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            Matcher matcher = scriptPattern.matcher(cleanValue);
            StringBuffer stringBuffer = new StringBuffer();
            while (matcher.find()) {
                if (!matcher.group(2).matches(emojiPattern)) {
                    matcher.appendReplacement(stringBuffer, "");
                }
            }
            matcher.appendTail(stringBuffer);
            cleanValue = stringBuffer.toString();

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>|<script(.*?)>",
                    Pattern.CASE_INSENSITIVE | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Remove any lonesome </style> tag
            stylePattern = Pattern.compile("</style>|<style(.*?)>",
                    Pattern.CASE_INSENSITIVE | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = stylePattern.matcher(cleanValue).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid javascript event expressions
            scriptPattern = Pattern.compile("on(load|click|submit|focus|blur|change|key|reset|mouse|resize)(.*?)=(['\"])(.*?)\\3",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
        }
        return cleanValue;
    }
}
