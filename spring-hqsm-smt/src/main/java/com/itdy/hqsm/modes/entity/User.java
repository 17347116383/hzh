package com.itdy.hqsm.modes.entity;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.IntStream;


public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;
    //@JSONField(format = "yyyy-MM-dd")
    private String    createDate;

   public String getCreateDate() {
        return createDate;
    }
/**
 * @JSONField(serialize = false) 不序列化
 * @JSONField(deserialize = false)  反序列化  不被序列化
 */

    public void setCreateDate(String createDate) {
       this.createDate = createDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public User(String id, String name, Integer age, String createDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.createDate = createDate;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", createDate=" + createDate +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(age, user.age) &&
                Objects.equals(createDate, user.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, createDate);
    }

    public int length() {
        return id.length();
    }

    public boolean isEmpty() {
        return id.isEmpty();
    }

    public char charAt(int index) {
        return id.charAt(index);
    }

    public int codePointAt(int index) {
        return id.codePointAt(index);
    }

    public int codePointBefore(int index) {
        return id.codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return id.codePointCount(beginIndex, endIndex);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return id.offsetByCodePoints(index, codePointOffset);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        id.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    @Deprecated
    public void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
        id.getBytes(srcBegin, srcEnd, dst, dstBegin);
    }

    public byte[] getBytes(String charsetName) throws UnsupportedEncodingException {
        return id.getBytes(charsetName);
    }

    public byte[] getBytes(Charset charset) {
        return id.getBytes(charset);
    }

    public byte[] getBytes() {
        return id.getBytes();
    }

    public boolean contentEquals(StringBuffer sb) {
        return id.contentEquals(sb);
    }

    public boolean contentEquals(CharSequence cs) {
        return id.contentEquals(cs);
    }

    public boolean equalsIgnoreCase(String anotherString) {
        return id.equalsIgnoreCase(anotherString);
    }

    public int compareTo(String anotherString) {
        return id.compareTo(anotherString);
    }

    public int compareToIgnoreCase(String str) {
        return id.compareToIgnoreCase(str);
    }

    public boolean regionMatches(int toffset, String other, int ooffset, int len) {
        return id.regionMatches(toffset, other, ooffset, len);
    }

    public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
        return id.regionMatches(ignoreCase, toffset, other, ooffset, len);
    }

    public boolean startsWith(String prefix, int toffset) {
        return id.startsWith(prefix, toffset);
    }

    public boolean startsWith(String prefix) {
        return id.startsWith(prefix);
    }

    public boolean endsWith(String suffix) {
        return id.endsWith(suffix);
    }

    public int indexOf(int ch) {
        return id.indexOf(ch);
    }

    public int indexOf(int ch, int fromIndex) {
        return id.indexOf(ch, fromIndex);
    }

    public int lastIndexOf(int ch) {
        return id.lastIndexOf(ch);
    }

    public int lastIndexOf(int ch, int fromIndex) {
        return id.lastIndexOf(ch, fromIndex);
    }

    public int indexOf(String str) {
        return id.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return id.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return id.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return id.lastIndexOf(str, fromIndex);
    }

    public String substring(int beginIndex) {
        return id.substring(beginIndex);
    }

    public String substring(int beginIndex, int endIndex) {
        return id.substring(beginIndex, endIndex);
    }

    public CharSequence subSequence(int beginIndex, int endIndex) {
        return id.subSequence(beginIndex, endIndex);
    }

    public String concat(String str) {
        return id.concat(str);
    }

    public String replace(char oldChar, char newChar) {
        return id.replace(oldChar, newChar);
    }

    public boolean matches(String regex) {
        return id.matches(regex);
    }

    public boolean contains(CharSequence s) {
        return id.contains(s);
    }

    public String replaceFirst(String regex, String replacement) {
        return id.replaceFirst(regex, replacement);
    }

    public String replaceAll(String regex, String replacement) {
        return id.replaceAll(regex, replacement);
    }

    public String replace(CharSequence target, CharSequence replacement) {
        return id.replace(target, replacement);
    }

    public String[] split(String regex, int limit) {
        return id.split(regex, limit);
    }

    public String[] split(String regex) {
        return id.split(regex);
    }

    public static String join(CharSequence delimiter, CharSequence... elements) {
        return String.join(delimiter, elements);
    }

    public static String join(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
        return String.join(delimiter, elements);
    }

    public String toLowerCase(Locale locale) {
        return id.toLowerCase(locale);
    }

    public String toLowerCase() {
        return id.toLowerCase();
    }

    public String toUpperCase(Locale locale) {
        return id.toUpperCase(locale);
    }

    public String toUpperCase() {
        return id.toUpperCase();
    }

    public String trim() {
        return id.trim();
    }

    public char[] toCharArray() {
        return id.toCharArray();
    }

    public static String format(String format, Object... args) {
        return String.format(format, args);
    }

    public static String format(Locale l, String format, Object... args) {
        return String.format(l, format, args);
    }

    public static String valueOf(Object obj) {
        return String.valueOf(obj);
    }

    public static String valueOf(char[] data) {
        return String.valueOf(data);
    }

    public static String valueOf(char[] data, int offset, int count) {
        return String.valueOf(data, offset, count);
    }

    public static String copyValueOf(char[] data, int offset, int count) {
        return String.copyValueOf(data, offset, count);
    }

    public static String copyValueOf(char[] data) {
        return String.copyValueOf(data);
    }

    public static String valueOf(boolean b) {
        return String.valueOf(b);
    }

    public static String valueOf(char c) {
        return String.valueOf(c);
    }

    public static String valueOf(int i) {
        return String.valueOf(i);
    }

    public static String valueOf(long l) {
        return String.valueOf(l);
    }

    public static String valueOf(float f) {
        return String.valueOf(f);
    }

    public static String valueOf(double d) {
        return String.valueOf(d);
    }

    public String intern() {
        return id.intern();
    }

    public IntStream chars() {
        return id.chars();
    }

    public IntStream codePoints() {
        return id.codePoints();
    }



}
