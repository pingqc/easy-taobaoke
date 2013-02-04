package org.pingqc.web;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pingqc.util.CookieUtil;
import org.pingqc.util.TaobaoUtil;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		final String app_key = "12668380";/* 填写appkey */
		final String secret = "b8457cfb4a69cdd538e0d4b91a74e553";/* 填入Appsecret' */
		Long time = System.currentTimeMillis();
		while (time < 1000000000000L) {// 时间戳格式为13位数字类型
			time *= 10;
		}
		final String message = secret + "app_key" + app_key + "timestamp"
				+ time + secret;
		String mysign = "";
		try {
			mysign = TaobaoUtil.getHmacMd5Bytes(secret.getBytes(),
					message.getBytes());
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		mysign = mysign.toUpperCase();

		CookieUtil.addCookie(response, "timestamp", time + "", 0);
		CookieUtil.addCookie(response, "sign", mysign, 0);

		request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
