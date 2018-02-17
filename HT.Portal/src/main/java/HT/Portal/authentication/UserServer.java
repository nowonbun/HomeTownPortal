package HT.Portal.authentication;

import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import dao.FactoryDao;
import dao.GroupDao;
import dao.UserDao;
import model.User;

public class UserServer {
	public static final String SESSION_ID = "user";
	private String access_token;
	private int expires_in;
	private String id_token;
	private String refresh_token;
	private String token_type;
	private String kind;
	private String etag;
	private String nickname;
	private String gender;
	private String objectType;
	private String id;
	private String displayName;
	private String familyName;
	private String givenName;
	private String tagline;
	private String url;
	private String image_url;
	private boolean image_isDefault;
	private boolean isPlusUser;
	private String language;
	private int circledByCount;
	private boolean verified;
	private String cover_layout;
	private String coverPhoto_url;
	private int coverPhoto_height;
	private int coverPhoto_width;
	private int coverInfo_topImageOffset;
	private int coverInfo_leftImageOffset;
	private User user;

	public static String getSESSION_ID() {
		return SESSION_ID;
	}

	public String getAccess_token() {
		return access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public String getId_token() {
		return id_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public String getKind() {
		return kind;
	}

	public String getEtag() {
		return etag;
	}

	public String getNickname() {
		return nickname;
	}

	public String getGender() {
		return gender;
	}

	public String getLanguage() {
		return language;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getTagline() {
		return tagline;
	}

	public String getUrl() {
		return url;
	}

	public String getImage_url() {
		return image_url;
	}

	public boolean isImage_isDefault() {
		return image_isDefault;
	}

	public boolean isPlusUser() {
		return isPlusUser;
	}

	public int getCircledByCount() {
		return circledByCount;
	}

	public boolean isVerified() {
		return verified;
	}

	public String getCover_layout() {
		return cover_layout;
	}

	public String getCoverPhoto_url() {
		return coverPhoto_url;
	}

	public int getCoverPhoto_height() {
		return coverPhoto_height;
	}

	public int getCoverPhoto_width() {
		return coverPhoto_width;
	}

	public int getCoverInfo_topImageOffset() {
		return coverInfo_topImageOffset;
	}

	public int getCoverInfo_leftImageOffset() {
		return coverInfo_leftImageOffset;
	}

	public User getUser() {
		return user;
	}

	public void setToken(InputStream stream) {
		try (JsonReader reader = Json.createReader(stream)) {
			JsonObject obj = reader.readObject();
			this.access_token = obj.getString("access_token");
			this.expires_in = obj.getInt("expires_in");
			this.id_token = obj.getString("id_token");
			this.refresh_token = obj.getString("refresh_token");
			this.token_type = obj.getString("token_type");
		}
	}

	public void setUser(InputStream stream) {
		try (JsonReader reader = Json.createReader(stream)) {
			JsonObject obj = reader.readObject();
			this.kind = obj.getString("kind");
			this.etag = obj.getString("etag");
			this.nickname = obj.getString("nickname");
			this.gender = obj.getString("gender");
			this.objectType = obj.getString("objectType");
			this.id = obj.getString("id");
			this.displayName = obj.getString("displayName");
			this.familyName = obj.getJsonObject("name").getString("familyName");
			this.givenName = obj.getJsonObject("name").getString("givenName");
			this.tagline = obj.getString("tagline");
			this.url = obj.getString("url");
			this.image_url = obj.getJsonObject("image").getString("url");
			this.image_isDefault = obj.getJsonObject("image").getBoolean("isDefault");
			this.isPlusUser = obj.getBoolean("isPlusUser");
			this.language = obj.getString("language");
			this.circledByCount = obj.getInt("circledByCount");
			this.verified = obj.getBoolean("verified");
			this.cover_layout = obj.getJsonObject("cover").getString("layout");
			this.coverPhoto_url = obj.getJsonObject("cover").getJsonObject("coverPhoto").getString("url");
			this.coverPhoto_height = obj.getJsonObject("cover").getJsonObject("coverPhoto").getInt("height");
			this.coverPhoto_width = obj.getJsonObject("cover").getJsonObject("coverPhoto").getInt("width");
			this.coverInfo_topImageOffset = obj.getJsonObject("cover").getJsonObject("coverInfo")
					.getInt("topImageOffset");
			this.coverInfo_leftImageOffset = obj.getJsonObject("cover").getJsonObject("coverInfo")
					.getInt("leftImageOffset");
		}
		UserDao dao = FactoryDao.getDao(UserDao.class);
		this.user = dao.getUser(this.id);
		if (this.user == null) {
			this.user = new User(this.id);
		}
		this.user.setId(this.id);
		this.user.setGivenName(this.givenName);
		this.user.setName(this.displayName);
		this.user.setNickName(this.nickname);
		this.user.setImg(this.image_url);
		this.user.setBackgroundImg(this.coverPhoto_url);
		this.user.setGroup(FactoryDao.getDao(GroupDao.class).getGroup("GUEST"));
		dao.create(this.user);
	}

	public void setUser(User info) {
		this.user = info;
		this.id = this.user.getId();
		this.givenName = this.user.getGivenName();
		this.displayName = this.user.getName();
		this.nickname = this.user.getNickName();
		this.image_url = this.user.getImg();
		this.coverPhoto_url = this.user.getBackgroundImg();
	}
}
