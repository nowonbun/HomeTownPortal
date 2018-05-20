package socket;

import java.util.ArrayList;
import java.util.List;

import common.FactoryDao;
import common.IWorkflow;
import common.JsonConverter;
import common.Workflow;
import dao.CompanyDao;
import dao.RoleDao;
import entity.NavigateNode;
import entity.SelectNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import model.Role;
import model.User;
import reference.RoleMaster;
import reference.StateMaster;

@Workflow(name = "profile")
public class Profile extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/profile", "Profile") };

	@SuppressWarnings("unused")
	private class Node {
		String given_name;
		String name;
		String nick_name;
		boolean is_img_blob;
		String img_url;
		byte[] img_blob;
		boolean canModifyPassword;
		boolean canModifyCompany;
		boolean canModifyGroup;
		int company;
		List<SelectNode> companyList;
		List<SelectNode> groupList;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = getUserinfo(node.getSession()).getUser();
		Node data = new Node();
		data.companyList = new ArrayList<>();
		data.groupList = new ArrayList<>();

		data.given_name = user.getGivenName();
		data.name = user.getName();
		data.nick_name = user.getNickName();
		if (user.getImgBlob() == null) {
			data.is_img_blob = false;
			data.img_url = user.getImgUrl();
		} else {
			data.is_img_blob = true;
			data.img_blob = user.getImgBlob();
		}
		data.canModifyPassword = StateMaster.equals(user.getStateInfo().getState(), StateMaster.getPrivateId());
		List<Role> rolelist = FactoryDao.getDao(RoleDao.class).getRolebyUser(user);
		data.canModifyCompany = RoleMaster.has(rolelist, RoleMaster.getCompanyChange());
		if (data.canModifyCompany) {
			FactoryDao.getDao(CompanyDao.class).getCompanyAll().forEach(x -> {
				SelectNode select = new SelectNode();
				data.companyList.add(select);
				select.setValue(String.valueOf(x.getId()));
				select.setName(x.getName());
			});
			data.company = user.getCompany().getId();
		}
		data.canModifyGroup = RoleMaster.has(rolelist, RoleMaster.getCompanyChange());
		//ajax
//		if(data.canModifyGroup) {
//			FactoryDao.getDao(GroupDao.class).getGroupAll().forEach(x -> {
//				SelectNode select = new SelectNode();
//				data.groupList.add(select);
//				select.setValue(value);
//			});
//		}

		return createWebSocketResult(JsonConverter.create(data), node);
	}

	public WebSocketResult apply(WebSocketNode node) {
		JsonConverter.parse(node.getData(), (data) -> {

		});
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
