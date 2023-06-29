package userapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HomeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext ctx = getServletContext();
		int requestServed = 0;
		if (ctx.getAttribute(ContextStorage.REQ_SERVED) == null) {
			ctx.setAttribute(ContextStorage.REQ_SERVED, ++requestServed);
		} else {
			requestServed = (Integer) ctx.getAttribute(ContextStorage.REQ_SERVED);
			ctx.setAttribute(ContextStorage.REQ_SERVED, ++requestServed);
		}
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String firstName = (String) session.getAttribute(SessionStorage.FIRSTNAME);
		String lastName = (String) session.getAttribute(SessionStorage.LASTNAME);
		pw.write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n" + "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Home</title>\r\n" + "</head>\r\n" + "<body>\r\n" + "<h5>App Home opened for "
				+ requestServed + " times since the server started!</h5>" + "    <h1>Hi " + firstName + " " + lastName
				+ "!</h1>\r\n" + "    <a href=\"logout\">Click to LogOut</a>\r\n" + "            <br/>\r\n"
				+ "            <a href=\"resetPassword\">Click to Reset password!</a><br/>"
				+ "            <a href=\"deleteAccount\">Click to Delete Account!</a>" + "</body>\r\n" + "</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
