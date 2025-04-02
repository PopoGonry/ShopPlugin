package com.popogonry.shopPlugin;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatColorUtil {
    private static final Pattern RGB_PATTERN = Pattern.compile("&RGB\\{(\\d{1,3}),\\s*(\\d{1,3}),\\s*(\\d{1,3})\\}(.+)");

    public static String translateRGBColors(String message) {
        Matcher matcher = RGB_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            int r = Integer.parseInt(matcher.group(1));
            int g = Integer.parseInt(matcher.group(2));
            int b = Integer.parseInt(matcher.group(3));
            String text = matcher.group(4); // 색상을 적용할 텍스트

            // RGB 값이 0~255 범위를 벗어나지 않도록 제한
            r = Math.max(0, Math.min(255, r));
            g = Math.max(0, Math.min(255, g));
            b = Math.max(0, Math.min(255, b));

            // RGB 색상을 적용한 ChatColor 변환
            ChatColor color = ChatColor.of(new java.awt.Color(r, g, b));
            String coloredText = color + text + "§r";

            matcher.appendReplacement(buffer, coloredText);
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }
}
