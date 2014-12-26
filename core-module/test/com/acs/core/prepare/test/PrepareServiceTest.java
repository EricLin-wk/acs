/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : prepare.PrepareService
   Module Description   :

   Date Created      : 2012/12/17
   Original Author   : tw4149
   Team              :
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.prepare.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.acs.biz.device.service.DeviceService;
import com.acs.core.common.service.AppConfigService;
import com.acs.core.common.utils.SpringCommonTest;
import com.acs.core.menu.entity.Menu;
import com.acs.core.menu.service.MenuService;
import com.acs.core.user.entity.Group;
import com.acs.core.user.entity.Role;
import com.acs.core.user.entity.User;
import com.acs.core.user.service.GroupService;
import com.acs.core.user.service.PermissionService;
import com.acs.core.user.service.RoleService;
import com.acs.core.user.service.UserService;

/**
 * @author tw4149
 */
public class PrepareServiceTest extends SpringCommonTest {
	private static MenuService menuService;
	private static UserService userService;
	private static GroupService groupService;
	private static RoleService roleService;
	private static PermissionService permissionService;
	private static JdbcTemplate jdbcTemplate;
	private static AppConfigService appConfigService;

	private static final String MAIN_GROUP_ID = "GRP000";
	private static final String ROLE_USER = "USER";
	private static final String ROLE_HR = "HR";
	private static final String ROLE_DEVICE_MGMT = "DEVICE_MGMT";
	private static final String ROLE_MONITOR_RPT = "MONITOR_RPT";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		configCtx();
		menuService = (MenuService) ctx.getBean("menuService");
		userService = (UserService) ctx.getBean("userService");
		groupService = (GroupService) ctx.getBean("groupService");
		roleService = (RoleService) ctx.getBean("roleService");
		permissionService = (PermissionService) ctx.getBean("permissionService");
		jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
		appConfigService = (AppConfigService) ctx.getBean("appConfigService");
	}

	@Test
	public void initAppConfig() {

		// if (appConfigService.get(AppConstants.APPCONFIG_OSA_DASHBOARD_WAITING_LIMIT) == null) {
		// AppConfig appConfig = new AppConfig();
		// appConfig.setKey(AppConstants.APPCONFIG_OSA_DASHBOARD_WAITING_LIMIT);
		// appConfig.setDescription("OSA DashBoard Active Waiting Limit");
		// appConfig.setValue("300");
		// appConfigService.save(appConfig);
		// }
	}

	@Test
	public void prepMenu() {
		if (menuService.get(MenuService.MENU_USER_STATUS) == null) {
			Menu menu = new Menu();
			menu.setKey(MenuService.MENU_USER_STATUS);
			menu.setDescription("使用者状态");
			menu.addOption("9", "启用");
			menu.addOption("1", "锁定");
			menu.addOption("0", "停用");
			menuService.save(menu);
		}

		if (menuService.get(MenuService.MENU_DEVICE_TYPE) == null) {
			Menu menu = new Menu();
			menu.setKey(MenuService.MENU_DEVICE_TYPE);
			menu.setDescription("设备类型");
			menu.addOption(DeviceService.DEVICE_TYPE_AIR_CONDITION, "空调");
			menu.addOption(DeviceService.DEVICE_TYPE_HUMIDIFIER, "加湿器");
			menu.addOption(DeviceService.DEVICE_TYPE_DEHUMIDIFIER, "除湿器");
			menu.addOption(DeviceService.DEVICE_TYPE_AIR_PURIFY, "净化器");
			menuService.save(menu);
		}

	}

	@Test
	public void prepGroup() {
		// Permission rUser = permissionService.get("ROLE_USER");
		Group entity = groupService.get(MAIN_GROUP_ID);
		if (entity == null) {
			entity = new Group(MAIN_GROUP_ID, "Main Group");
			// if (rUser != null) {
			// entity.getRole().getPermissions().put(rUser.getKey(), rUser);
			// }
			groupService.save(entity);
		}
	}

	@Test
	public void prepRole() {
		Role r = roleService.get(ROLE_USER, Role.Type.OTHER);
		if (r == null) {
			r = new Role(ROLE_USER, "一般使用者", Role.Type.OTHER);
			roleService.save(r);
			logger.info("entity: {}", r);
		}

		r = roleService.get(ROLE_HR, Role.Type.OTHER);
		if (r == null) {
			r = new Role(ROLE_HR, "使用者管理", Role.Type.OTHER);
			roleService.save(r);
			logger.info("entity: {}", r);
		}

		r = roleService.get(ROLE_DEVICE_MGMT, Role.Type.OTHER);
		if (r == null) {
			r = new Role(ROLE_DEVICE_MGMT, "设备管理", Role.Type.OTHER);
			roleService.save(r);
			logger.info("entity: {}", r);
		}

		r = roleService.get(ROLE_MONITOR_RPT, Role.Type.OTHER);
		if (r == null) {
			r = new Role(ROLE_MONITOR_RPT, "监控报表", Role.Type.OTHER);
			roleService.save(r);
			logger.info("entity: {}", r);
		}
	}

	@Test
	public void prepUser() {
		User user = userService.get("admin");
		if (user == null) {
			user = new User("admin");

			Group group = groupService.get(MAIN_GROUP_ID);
			user.setGroup(group);

			Role role = roleService.get(ROLE_USER, Role.Type.OTHER);
			if (role != null) {
				user.getRoles().add(role);
			}
			role = roleService.get(ROLE_HR, Role.Type.OTHER);
			if (role != null) {
				user.getRoles().add(role);
			}
			user.setNameNative("系统管理者");
			userService.createUser(user, "password", MAIN_GROUP_ID);
		}

	}

}
