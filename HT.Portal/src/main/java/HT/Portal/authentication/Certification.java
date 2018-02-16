package HT.Portal.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import HT.Portal.common.IServlet;
import HT.Portal.common.PropertyMap;
import HT.Portal.common.Util;
import common.DBUtil;
import dao.CookieDao;
import dao.FactoryDao;
import model.CookiePK;

@WebServlet("/Certification")
public class Certification extends IServlet {
	private static final long serialVersionUID = 1L;
	public static final String COOKIE_KEY = "HomePortalKey";

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
			UserServer user = new UserServer();
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
			user.setUser(con.getInputStream());

			// TODO: The user session will change database
			HttpSession session = getRequest().getSession();
			session.setAttribute(UserServer.SESSION_ID, user);
			String key = Util.createCookieKey();
			Cookie cookie = new Cookie(COOKIE_KEY, key);
			cookie.setMaxAge(Util.getCookieExpire());
			cookie.setPath(Util.getCookiePath());
			getResponse().addCookie(cookie);

			CookieDao dao = FactoryDao.getDao(CookieDao.class);
			model.Cookie entity = dao.getEntityByCookiekey(key);
			if (entity != null) {
				dao.delete(entity);
			}
			entity = new model.Cookie();
			CookiePK pk = new CookiePK();
			pk.setId(user.getId());
			pk.setCookiekey(key);
			entity.setId(pk);
			entity.setUser(user.getUser());
			entity.setIpaddress(Util.getRemoteAddr(getRequest()));
			entity.setState(DBUtil.createState(user.getId()));
			dao.create(entity);
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
