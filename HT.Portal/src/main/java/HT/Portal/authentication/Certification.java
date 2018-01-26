package HT.Portal.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import HT.Portal.common.PropertyMap;

@WebServlet("/certification")
public class Certification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Certification() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String code = request.getParameter("code");

		URL url = new URL("https://accounts.google.com/o/oauth2/token");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setDoOutput(true);
		con.setDoInput(true);
		con.setInstanceFollowRedirects(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		String client_id = PropertyMap.getInstance().getProperty("googleApiConfig", "client_id");
		String client_secret = PropertyMap.getInstance().getProperty("googleApiConfig", "client_secret");
		String redirect = PropertyMap.getInstance().getProperty("googleApiConfig", "redirect_url");
		//String api_key = PropertyMap.getInstance().getProperty("googleApiConfig", "api_key");

		StringBuffer sb = new StringBuffer();
		sb.append("code=").append(code).append("&");
		sb.append("client_id=").append(client_id).append("&");
		sb.append("client_secret=").append(client_secret).append("&");
		sb.append("redirect_uri=").append(redirect).append("&");
		sb.append("grant_type=").append("authorization_code");

		con.setUseCaches(false);
		con.setDefaultUseCaches(false);

		try (OutputStream stream = con.getOutputStream()) {
			byte[] temp = sb.toString().getBytes(StandardCharsets.UTF_8);
			stream.write(temp);
		}
		sb.setLength(0);
		int responseCode = con.getResponseCode();
		if (responseCode != 200) {
			throw new RuntimeException(GetReponse(con.getErrorStream()));
		}
		UserServerInfo user = new UserServerInfo();
		user.setToken(con.getInputStream());

		sb.append("https://www.googleapis.com/plus/v1/people/me?");
		sb.append("access_token=");
		sb.append(user.getAccess_token());
		url = new URL(sb.toString());
		con = (HttpURLConnection) url.openConnection();

		responseCode = con.getResponseCode();
		if (responseCode != 200) {
			throw new RuntimeException(GetReponse(con.getErrorStream()));
		}
		user.setUser(con.getInputStream());

		// TODO: The user session will change database
		HttpSession session = request.getSession();
		session.setAttribute(UserServerInfo.SESSION_ID, user);
		for (Cookie cookie : request.getCookies()) {
			if ("JSESSIONID".equals(cookie.getName())) {
				//TODO:: it will modify later.
				//System.out.println(cookie.getValue());
				break;
			}
		}
		response.sendRedirect("./index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private String GetReponse(InputStream stream) throws IOException {
		StringBuffer sb = new StringBuffer();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
		}
		return sb.toString();
	}
}
