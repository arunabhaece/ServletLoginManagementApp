package userapp;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ResetPasswordServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("LoginPage.html");
			rd.forward(request, response);
		} else {
			String firstName = (String) session.getAttribute(SessionStorage.FIRSTNAME);
			String lastName = (String) session.getAttribute(SessionStorage.LASTNAME);
			pw.write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "    <title>Login</title>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
					+ "    <div style=\"width: min-content; margin: 10px; padding: 20px; border: 2px black solid;\">\r\n"
					+ "        <h1>Hi " + firstName + " " + lastName + "!</h1>\r\n"
					+ "        <h2>Reset Password!</h2>\r\n" + "        <form action=\"reset\" method=\"post\">\r\n"
					+ "            <label><b>Enter New Password:</b></label>\r\n"
					+ "            <input type=\"password\" name=\"npass\" />\r\n" + "            <br /><br />\r\n"
					+ "            <label><b>Repeat New Password:</b></label>\r\n"
					+ "            <input type=\"password\" name=\"rnpass\" />\r\n" + "            <br /><br />\r\n"
					+ "            <input type=\"submit\" />\r\n" + "            <br/>\r\n" + "        </form>\r\n"
					+ "    </div>\r\n" + "</body>\r\n" + "</html>");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
