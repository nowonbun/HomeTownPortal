package authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;

import common.IServlet;
import common.PropertyMap;

@WebServlet("/Certification")
public class Certification extends IServlet {
	private static final long serialVersionUID = 1L;

	public Certification() {
		super();
	}

	protected void doGet() {
		try {
			String code = getRequest().getParameter("code");

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
				throw new RuntimeException(GetResponse(con.getErrorStream()));
			}
			UserService user = new UserService();
			user.setToken(con.getInputStream());

			sb.append("https://www.googleapis.com/plus/v1/people/me?");
			sb.append("access_token=");
			sb.append(user.getAccess_token());
			url = new URL(sb.toString());
			con = (HttpURLConnection) url.openConnection();

			responseCode = con.getResponseCode();
			if (responseCode != 200) {
				throw new RuntimeException(GetResponse(con.getErrorStream()));
			}
			user.createUser(con.getInputStream());
			setLoginSession(user);
			Redirect("./index.jsp");
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	protected void doPost() {
		doGet();
	}

	private String GetResponse(InputStream stream) throws IOException {
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
