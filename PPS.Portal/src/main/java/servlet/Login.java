package servlet;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.DatatypeConverter;

import authentication.UserServer;
import common.IServlet;
import dao.FactoryDao;
import dao.UserDao;
import model.Password;
import model.User;

@WebServlet("/login")
public class Login extends IServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet() {
		getResponse().setStatus(403);
	}

	protected void doPost() {
		String json = getStreamData();
		String id = null;
		String pw = null;
		if (json == null) {
			setStatus(403);
			return;
		}
		try (JsonReader jsonReader = Json.createReader(new StringReader(json))) {
			JsonObject object = jsonReader.readObject();
			id = object.getString("pid");
			pw = object.getString("pwd");
		}
		if (id == null || pw == null || id.length() < 1 || pw.length() < 1) {
			setStatus(403);
			return;
		}
		User info = FactoryDao.getDao(UserDao.class).getUser(id);
		if (info == null) {
			String ret = "1";
			getPrinter().print(ret);
			return;
		}
		try {
			pw = convertMD5(pw);
		} catch (NoSuchAlgorithmException e) {
			getResponse().setStatus(403);
			return;
		}
		boolean check = true;
		for (Password item : info.getPasswords()) {
			if (item.getStateInfo().getIsDelete()) {
				continue;
			}
			if (item.getPassword().toUpperCase().equals(pw.toUpperCase())) {
				check = false;
				break;
			}
		}

		if (check) {
			String ret = "1";
			getPrinter().print(ret);
			return;
		}
		UserServer userinfo = new UserServer();
		userinfo.setUser(info);
		setLoginSession(userinfo);
		String ret = "0";
		getPrinter().print(ret);
	}

	private String convertMD5(String val) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] bytes = md5.digest(val.getBytes(StandardCharsets.UTF_8));
		return DatatypeConverter.printHexBinary(bytes);
	}

}
