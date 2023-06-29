package userapp;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginManageServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		boolean loginVerified = false;
		if (session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("LoginPage.html");
			rd.forward(request, response);
		} else {
			if (session.getAttribute(SessionStorage.COOKIE_TEXT) == null) {
				RequestDispatcher rd = request.getRequestDispatcher("LoginPage.html");
				rd.forward(request, response);
			} else {
				String cookieTextInSession = (String) session.getAttribute(SessionStorage.COOKIE_TEXT);
				Cookie[] cookieInRequestArray = request.getCookies();
				for (int i = 0; i < cookieInRequestArray.length; i++) {
					if (cookieInRequestArray[i].getValue().equals(cookieTextInSession)) {
						loginVerified = true;
						break;
					}
				}
				if (!loginVerified) {
					session.invalidate();
					RequestDispatcher rd = request.getRequestDispatcher("LoginPage.html");
					rd.forward(request, response);
				} else {
					RequestDispatcher rd = request.getRequestDispatcher("/home");
					rd.forward(request, response);
				}
			}
		}
	}
}
