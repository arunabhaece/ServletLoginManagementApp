package userapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErrorServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object obj = request.getAttribute("errorMessage");
		String errorMessage = "";
		if (obj != null) {
			errorMessage = (String) obj;
		}
		response.getWriter().write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Error</title>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
				+ "    <div style=\"margin: 10px;padding: 20px; border: 2px black solid;width: fit-content;\">\r\n"
				+ "        <h1 style=\"color: red;\">Sorry :( Something wrong happened!</h1>\r\n"
				+ "        <h4>Error Message - " + errorMessage + "</h4>\r\n"
				+ "        <h3>Please contact admin for support -> arunabhamondal18@gmail.com</h3>\r\n"
				+ "    <a href=\"HomePage.html\">Click to go back to Homepage</a>\r\n" + "</body>\r\n"
				+ "    </div>\r\n" + "</body>\r\n" + "\r\n" + "</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
