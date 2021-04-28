package org.cloudburstmc.server;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public enum ChatColor {
  BLACK('0', 0x00),
  DARK_BLUE('1', 0x1),
  DARK_GREEN('2', 0x2),
  DARK_AQUA('3', 0x3),
  DARK_RED('4', 0x4),
  DARK_PURPLE('5', 0x5),
  ORANGE('6', 0x6),
  GRAY('7', 0x7),
  DARK_GRAY('8', 0x8),
  BLUE('9', 0x9),
  GREEN('a', 0xA),
  AQUA('b', 0xB),
  RED('c', 0xC),
  LIGHT_PURPLE('d', 0xD),
  YELLOW('e', 0xE),
  WHITE('f', 0xF),
  GOLD('g', 0x16),
  OBFUSCATED('k', 0x10, true),
  BOLD('l', 0x11, true),
  STRIKETHROUGH('m', 0x12, true),
  UNDERLINE('n', 0x13, true),
  ITALIC('o', 0x14, true),
  RESET('r', 0x15);

  public static final char COLOR_CHAR = '\u00A7';
  
  private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + COLOR_CHAR  + "[0-9A-GK-OR]");
  
  private final static Map<Integer, ChatColor> BY_ID = Maps.newTreeMap();
  
  private final static Map<Character, ChatColor> BY_CHAR = new HashMap<>();

  static {
    for (ChatColor color : values()) {
      BY_ID.put(color.intCode, color);
      
      BY_CHAR.put(color.code, color);
    }
  }

  private final int intCode;
  
  private final char code;
  
  private final boolean isFormat;
  
  private final String toString;

  ChatColor(char code, int intCode) {
    this(code, intCode, false);
  }

  ChatColor(char code, int intCode, boolean isFormat) {
    this.code = code;
    
    this.intCode = intCode;
    
    this.isFormat = isFormat;
    
    this.toString = new String(new char[]{COLOR_CHAR, code});
  }

  public static ChatColor getByChar(char code) {
    return BY_CHAR.get(code);
  }

  public static ChatColor getByChar(String code) {
    if (code == null || code.length() <= 1) {
      return null;
    }

    return BY_CHAR.get(code.charAt(0));
  }

  public static String clean(final String input) {
    return clean(input, false);
  }

  public static String clean(final String input, final boolean recursive) {
    if (input == null) {
      return null;
    }

    String result = STRIP_COLOR_PATTERN.matcher(input).replaceAll("");

    if (recursive && STRIP_COLOR_PATTERN.matcher(result).find()) {
      return clean(result, true);
    }
    
    return result;
  }

  public static String translateAlternateColorCodes(char altFormatChar, String textToTranslate) {
      char[] b = textToTranslate.toCharArray();
        
      for (int i = 0; i < b.length - 1; i++) {
        if (b[i] == altFormatChar && "0123456789AaBbCcDdEeFfGgKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
              b[i] = ChatColor.COLOR_CHAR;
                
              b[i + 1] = Character.toLowerCase(b[i + 1]);
        }
      }
      
    return new String(b);
    }

  public static String translateAlternateColorCodes(String textToTranslate) {
    return translateAlternateColorCodes('&', textToTranslate);
  }

    public static String getLastColors(String input) {
      String result = "";
      
      int length = input.length();

      for (int index = length - 1; index > -1; index--) {
        char section = input.charAt(index);
        
        if (section == COLOR_CHAR && index < length - 1) {
          char c = input.charAt(index + 1);
          ChatColor color = getByChar(c);

          if (color != null) {
            result = color.toString() + result;

            if (color.isColor() || color.equals(RESET)) {
              break;
            }
          }
        }
      }

      return result;
    }

  public char getChar() {
    return code;
  }

    @Override
    public String toString() {
        return toString;
    }

  public boolean isFormat() {
    return isFormat;
  }

  public boolean isColor() {
    return !isFormat && this != RESET;
  }
}
