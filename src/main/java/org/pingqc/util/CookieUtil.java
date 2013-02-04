package org.pingqc.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author pingqc
 * 
 */
public class CookieUtil {
	/**
	 * ���cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie������
	 * @param value
	 *            cookie��ֵ
	 * @param maxAge
	 *            cookie��ŵ�ʱ��(����Ϊ��λ,����������,��3*24*60*60; ���ֵΪ0,cookie����������رն����)
	 */
	public static void addCookie(final HttpServletResponse response,
			final String name, final String value, final int maxAge) {
		final Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	public static void addCookie(final HttpServletResponse response,
			final String[] name, final String[] value, final int maxAge) {
		if (name.length != value.length) {
			return;
		}
		for (int i = 0; i < name.length; i++) {
			addCookie(response, name[i], value[i], maxAge);
		}
	}

	/**
	 * ��ȡcookie��ֵ
	 * 
	 * @param request
	 * @param name
	 *            cookie������
	 * @return
	 */
	public static String getCookieByName(final HttpServletRequest request,
			final String name) {
		final Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			final Cookie cookie = cookieMap.get(name);
			return cookie.getValue();
		} else {
			return null;
		}
	}

	protected static Map<String, Cookie> readCookieMap(
			final HttpServletRequest request) {
		final Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		final Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				cookieMap.put(cookies[i].getName(), cookies[i]);
			}
		}
		return cookieMap;
	}

	public static void removeCookie(final HttpServletResponse response,
			final String name) {
		final Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void removeCookie(final HttpServletResponse response,
			final String[] name) {
		for (int i = 0; i < name.length; i++) {
			removeCookie(response, name[i]);
		}
	}
}
