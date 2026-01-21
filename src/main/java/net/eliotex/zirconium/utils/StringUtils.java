package net.eliotex.zirconium.utils;

// All credits go to Alexdoru for this optimization!
public class StringUtils {

    /**
     * A faster version of {@link net.minecraft.util.EnumChatFormatting#getTextWithoutFormattingCodes(String)}
     */
    public static String strip(String s) {
        if (s == null || s.length() < 2) return s;

        final int len = s.length();
        final char[] chars = s.toCharArray();
        final char[] newChars = new char[len];

        int count = 0;

        for (int i = 0; i < len; i++) {
            final char c = chars[i];

            if (c == '§' && i + 1 < len && "0123456789abcdefklmnorABCDEFKLMNOR".indexOf(chars[i + 1]) != -1) {
                i++;
                continue;
            }

            newChars[count] = c;
            count++;
        }
        
        return new String(newChars, 0, count);
    }
}