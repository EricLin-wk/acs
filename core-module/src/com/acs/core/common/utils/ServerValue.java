/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.acs.core.common.utils.ServerValue
   Module Description   :

   Date Created      : 2012/11/26
   Original Author   : tw4149
   Team              : 
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.acs.core.common.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author tw4149
 * 
 */
public class ServerValue {
	private static ServerValue instance = new ServerValue();
	private final Log log = LogFactory.getLog(ServerValue.class);

	// 前台
	private static String frontContextPath = "/ecmall";

	private static String frontHostname = "http://www.yaodian100.com";

	private static String frontHostnameSsl = "https://www.yaodian100.com";

	private static String frontContentNotFilter = "";

	private static String frontApachePath = "/yaodian100.com/web/";

	private static String frontProdHtmlPath = "/yaodian100.com/share/product/";

	private static String frontOrderHtmlPath = "/yaodian100.com/share/order/";

	private static String trustMember = "alipaymember,qqfanlimember,fanlimember,xunleimember,taobaomember";

	private static String searchEnginePath = "http://10.0.0.240/ECSearchWebService/ECSearchWebService.asmx";
	private static String customerTel;

	private static String customerFax;
	private static String frontContentHtmlPath = "/yaodian100.com/share/content/";
	private static String imgDomain = "http://s.yaodian100.com";
	private static String innerIP = "60\\.248\\.103\\.165|220\\.130\\.152\\.30|112\\.65\\.187\\.234|116\\.236\\.229\\.18";
	private static String frontTopCategoryIdList = "1726,1727,1728,8,5,6,7,2816,9,11";
	private static String prodInnerIP = "60\\.248\\.103\\.165|220\\.130\\.152\\.30|112\\.65\\.187\\.234|116\\.236\\.229\\.18|^10\\.";
	private static String discountType = "single";
	private static String orderModiyAddress = "（您的收货地址不在服务支援地区）";
	private static String daphneLimitPrice = "1.0";
	private static String blockOrder = "off";
	private static int freightBase;
	private static String useBfd = "on";// 百分點
	private static String useCreditcard = "on";// 信用卡单期支付
	private static String useCreditcardPeriod = "on";// 信用卡分期支付
	private static String version = "v4";
	private static String prodVolumeWeight = "6000";// 商品材积
	private static String prodWeight = "12";// 商品重量(kg)
	private static String blockOrderPaymentType = "on";// 手机验证
	private static String bfdCode = "bfdtest14";// 百分點code，正式台：Cyaodian100，測試台：bfdtest14
	private static String initialDate = "";
	private static String updateProdStByERP = "on";
	private static boolean staticPageCache = false; // 是否对静态 html cache
	private static String useCod = "on";// cod

	public static String getBlockOrder() {
		return blockOrder;
	}

	public static void setBlockOrder(String blockOrder) {
		ServerValue.blockOrder = blockOrder;
	}

	// 中台
	private static String midContextPath = "/ecadmin";
	private static String midHostname = "http://ecadmin.yaodian100.com";
	private static String midHostnameSsl = "https://ecadmin.yaodian100.com";
	private static String midContentNotFilter = "";
	private static String midApachePath = "/yaodian100.com/web/";
	private static String midHtmlPath = "/yaodian100.com/share/";
	private static String midProdHtmlPath = "/yaodian100.com/share/product/";
	private static String midOrderHtmlPath = "/yaodian100.com/share/order/";
	private static String midGiveBonusFile = "/yaodian100.com/share/content/";
	private static String midRankShortDays = "1";
	private static String midRankLongDays = "30";
	private static String mainStep0 = "<strong>提醒：</strong>很抱歉，商品  ＃＃＃库存数量已有变化，少于您所选购的数量。<br /><br />请回商品页确认您可购买的数量，再继续结帐。";
	private static String prtStep0 = "<strong>提醒：</strong>赠品  ＃＃＃为限量发送，目前库存数量不足或已送完！<br /><br />请回商品页重新确认，再继续结帐。";
	private static String mainloginStep0 = "<strong>提醒：</strong>很抱歉，商品  ＃＃＃库存数量已有变化，少于您所选购的数量。<br /><br />请刷新商品页，以再次确认您可购买的商品数量。";
	private static String prtloginStep0 = "<strong>提醒：</strong>赠品  ＃＃＃为限量发送，目前库存数量不足或已送完！<br /><br /> 您可在下一步展开 \"订单明细\" 查看所能获得的赠品数量，再决定是否购买。 ";
	private static String mainStep1 = "<strong>提醒：</strong>很抱歉，商品 ＃＃＃库存数量已有变化，少于您所选购的数量。<br /><br />刷新购物车后，页面将会显示您所能购买的数量。<br />如有已售完的商品请先删除，确认数量后再继续结帐。";
	private static String prtStep1 = "<strong>提醒：</strong>赠品  ＃＃＃为限量发送，目前库存数量不足或已送完！<br /><br />您可刷新购物车再次确认，页面将会显示您所能获得的赠品数；<br/>或放弃不足的缺货赠品，继续结帐。";
	private static String mainCartStep2 = "<strong>提醒：</strong>很抱歉，商品 ＃＃＃库存数量已有变化，少于您所选购的数量。<br /><br />请回购物车重新修改数量，再继续结帐。";
	private static String prtCartStep2 = "<strong>提醒：</strong> 抱歉，赠品为限量发送，目前库存不足！<br />＃＃＃<br /><br />您可回购物车查看或修改主商品数量，<br />若选择完成订购，可于订单明细查询实际获得之赠品数。<br />";
	private static String mainBuyStep2 = "<strong>提醒：</strong>很抱歉，商品 ＃＃＃库存数量已有变化，少于您所选购的数量。<br /><br />请回商品页确认您所能订购的数量，再重新结帐。";
	private static String prtBuyStep2 = "<strong>提醒：</strong> 抱歉，赠品为限量发送，目前库存不足！<br />＃＃＃<br />您可回商品页确认数量再继续，<br />若选择完成订购，可于订单明细查询实际获得之赠品数。";
	private static String useDoubleSpec = "N";
	private static String nmlType = "NML";
	private static String scmType = "SCM";
	private static String luxType = "LUX";
	// 後台
	private static String backContextPath = "/erp";
	private static String backHostname = "http://erp.yaodian100.com";
	private static String backHostnameSsl = "https://erp.yaodian100.com";
	private static String backContentNotFilter = "";
	private static String backApachePath = "/yaodian100.com/web/";
	private static String scmHostname = "http://scm.yaodian100.com";
	private static String dpnHostname = "http://scm.yaodian100.com";
	private static String csrLockOrderWS = "http://erp.yaodian100.com/erp/remote/remoteSalesOrderService";

	/**
	 * @return the csrLockOrderWS
	 */
	public static String getCsrLockOrderWS() {
		return csrLockOrderWS;
	}

	/**
	 * @param csrLockOrderWS
	 *           the csrLockOrderWS to set
	 */
	public static void setCsrLockOrderWS(String csrLockOrderWS) {
		ServerValue.csrLockOrderWS = csrLockOrderWS;
	}

	private static Properties seoProp = new Properties();

	public static String backToHtmlString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" backJsPath = ").append(backContextPath);
		sb.append("<BR>\n backHostname = ").append(backHostname);
		sb.append("<BR>\n backHostnameSsl = ").append(backHostnameSsl);
		sb.append("<BR>\n backContentNotFilter = ").append(backContentNotFilter);
		return sb.toString();
	}

	public static String frontToHtmlString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" frontJsPath = ").append(frontContextPath);
		sb.append("<BR>\n frontHostname = ").append(frontHostname);
		sb.append("<BR>\n frontHostnameSsl = ").append(frontHostnameSsl);
		sb.append("<BR>\n frontContentNotFilter = ").append(frontContentNotFilter);
		return sb.toString();
	}

	public static String getBackApachePath() {
		return backApachePath;
	}

	public static String getBackContentNotFilter() {
		return backContentNotFilter;
	}

	public static String getBackContextPath() {
		return backContextPath;
	}

	public static String getBackHostname() {
		return backHostname;
	}

	public static String getBackHostnameSsl() {
		return backHostnameSsl;
	}

	public static String getCustomerFax() {
		return customerFax;
	}

	public static String getCustomerTel() {
		return customerTel;
	}

	public static String getFrontApachePath() {
		return frontApachePath;
	}

	public static String getFrontContentHtmlPath() {
		return frontContentHtmlPath;
	}

	public static String getFrontContentNotFilter() {
		return frontContentNotFilter;
	}

	public static String getFrontContextPath() {
		return frontContextPath;
	}

	public static String getFrontHostname() {
		return frontHostname;
	}

	public static String getFrontHostnameSsl() {
		return frontHostnameSsl;
	}

	public static String getFrontOrderHtmlPath() {
		return frontOrderHtmlPath;
	}

	public static String getFrontProdHtmlPath() {
		return frontProdHtmlPath;
	}

	public static String getImgDomain() {
		return imgDomain;
	}

	public static String getInnerIP() {
		return innerIP;
	}

	public static ServerValue getInstance() {
		return instance;
	}

	public static String getMidApachePath() {
		return midApachePath;
	}

	public static String getMidContentNotFilter() {
		return midContentNotFilter;
	}

	public static String getMidContextPath() {
		return midContextPath;
	}

	public static String getMidGiveBonusFile() {
		return midGiveBonusFile;
	}

	public static String getMidHostname() {
		return midHostname;
	}

	public static String getMidHostnameSsl() {
		return midHostnameSsl;
	}

	public static String getMidHtmlPath() {
		return midHtmlPath;
	}

	public static String getMidOrderHtmlPath() {
		return midOrderHtmlPath;
	}

	public static String getMidProdHtmlPath() {
		return midProdHtmlPath;
	}

	public static String getScmHostname() {
		return scmHostname;
	}

	public static Properties getSeoProp() {
		return seoProp;
	}

	public static String midToHtmlString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" midJsPath = ").append(midContextPath);
		sb.append("<BR>\n midHostname = ").append(midHostname);
		sb.append("<BR>\n midHostnameSsl = ").append(midHostnameSsl);
		sb.append("<BR>\n midContentNotFilter = ").append(midContentNotFilter);
		return sb.toString();
	}

	public static void setBackApachePath(String backApachePath) {
		ServerValue.backApachePath = backApachePath;
	}

	public static void setBackContentNotFilter(String backContentNotFilter) {
		ServerValue.backContentNotFilter = backContentNotFilter;
	}

	public static void setBackContextPath(String backJsPath) {
		ServerValue.backContextPath = backJsPath;
	}

	public static void setBackHostname(String backHostname) {
		ServerValue.backHostname = backHostname;
	}

	public static void setBackHostnameSsl(String backHostnameSsl) {
		ServerValue.backHostnameSsl = backHostnameSsl;
	}

	public static void setCustomerFax(String customerFax) {
		ServerValue.customerFax = customerFax;
	}

	public static void setCustomerTel(String customerTel) {
		ServerValue.customerTel = customerTel;
	}

	public static void setFrontApachePath(String frontApachePath) {
		ServerValue.frontApachePath = frontApachePath;
	}

	public static void setFrontContentHtmlPath(String frontContentHtmlPath) {
		ServerValue.frontContentHtmlPath = frontContentHtmlPath;
	}

	public static void setFrontContentNotFilter(String frontContentNotFilter) {
		ServerValue.frontContentNotFilter = frontContentNotFilter;
	}

	public static void setFrontContextPath(String frontJsPath) {
		ServerValue.frontContextPath = frontJsPath;
	}

	public static void setFrontHostname(String frontHostname) {
		ServerValue.frontHostname = frontHostname;
	}

	public static void setFrontHostnameSsl(String frontHostnameSsl) {
		ServerValue.frontHostnameSsl = frontHostnameSsl;
	}

	public static void setFrontOrderHtmlPath(String frontOrderHtmlPath) {
		ServerValue.frontOrderHtmlPath = frontOrderHtmlPath;
	}

	public static void setFrontProdHtmlPath(String frontProdHtmlPath) {
		ServerValue.frontProdHtmlPath = frontProdHtmlPath;
	}

	public static void setImgDomain(String imgDomain) {
		ServerValue.imgDomain = imgDomain;
	}

	public static void setInnerIP(String innerIP) {
		ServerValue.innerIP = innerIP;
	}

	public static void setMidApachePath(String midApachePath) {
		ServerValue.midApachePath = midApachePath;
	}

	public static void setMidContentNotFilter(String midContentNotFilter) {
		ServerValue.midContentNotFilter = midContentNotFilter;
	}

	public static void setMidContextPath(String midJsPath) {
		ServerValue.midContextPath = midJsPath;
	}

	public static void setMidGiveBonusFile(String midGiveBonusFile) {
		ServerValue.midGiveBonusFile = midGiveBonusFile;
	}

	public static void setMidHostname(String midHostname) {
		ServerValue.midHostname = midHostname;
	}

	public static void setMidHostnameSsl(String midHostnameSsl) {
		ServerValue.midHostnameSsl = midHostnameSsl;
	}

	public static void setMidHtmlPath(String midHtmlPath) {
		ServerValue.midHtmlPath = midHtmlPath;
	}

	public static void setMidOrderHtmlPath(String midOrderHtmlPath) {
		ServerValue.midOrderHtmlPath = midOrderHtmlPath;
	}

	public static void setMidProdHtmlPath(String midProdHtmlPath) {
		ServerValue.midProdHtmlPath = midProdHtmlPath;
	}

	public static void setScmHostname(String scmHostname) {
		ServerValue.scmHostname = scmHostname;
	}

	public static String getDpnHostname() {
		return dpnHostname;
	}

	public static void setDpnHostname(String dpnHostname) {
		ServerValue.dpnHostname = dpnHostname;
	}

	public static void setSeoProp(Properties seoProp) {
		ServerValue.seoProp = seoProp;
	}

	public static String getMidRankShortDays() {
		return midRankShortDays;
	}

	public static void setMidRankShortDays(String midRankShortDays) {
		if (midRankShortDays != null) {
			ServerValue.midRankShortDays = midRankShortDays;
		}
	}

	public static String getMidRankLongDays() {
		return midRankLongDays;
	}

	public static void setMidRankLongDays(String midRankLongDays) {
		if (midRankLongDays != null) {
			ServerValue.midRankLongDays = midRankLongDays;
		}
	}

	public static String getFrontTopCategoryIdList() {
		return frontTopCategoryIdList;
	}

	public static void setFrontTopCategoryIdList(String frontTopCategoryIdList) {
		if (frontTopCategoryIdList != null) {
			ServerValue.frontTopCategoryIdList = frontTopCategoryIdList;
		}
	}

	/** default constructor */
	private ServerValue() { // NOPMD
		super();
	}

	public void setDefault(String urlpath, String seopath) {
		Properties props = new java.util.Properties();
		Properties seoProps = new java.util.Properties();
		log.info("Start server init...");
		log.info("Load url:" + urlpath);
		log.info("Load seoUrl:" + seopath);
		try {
			props.load((new URL(urlpath)).openStream());
			// 前台
			log.info("server prop begin ............");
			ServerValue.setFrontHostname(props.getProperty("frontHostname"));
			ServerValue.setFrontHostnameSsl(props.getProperty("frontHostnameSsl"));
			ServerValue.setFrontContextPath(props.getProperty("frontContextPath"));
			ServerValue.setFrontContentNotFilter(props.getProperty("frontContentNotFilter"));
			ServerValue.setCustomerFax(props.getProperty("customerFax"));
			ServerValue.setCustomerTel(props.getProperty("customerTel"));
			ServerValue.setFrontApachePath(props.getProperty("frontApachePath"));
			ServerValue.setFrontProdHtmlPath(props.getProperty("frontProdHtmlPath"));
			ServerValue.setFrontOrderHtmlPath(props.getProperty("frontOrderHtmlPath"));
			ServerValue.setFrontContentHtmlPath(props.getProperty("frontContentHtmlPath"));
			ServerValue.setImgDomain(props.getProperty("imgDomain"));
			ServerValue.setSearchEnginePath(props.getProperty("searchEnginePath"));
			ServerValue.setFrontTopCategoryIdList(props.getProperty("frontTopCategoryIdList"));
			ServerValue.setMainStep0(props.getProperty("mainStep0"));
			ServerValue.setPrtStep0(props.getProperty("prtStep0"));
			ServerValue.setMainStep1(props.getProperty("mainStep1"));
			ServerValue.setPrtStep1(props.getProperty("prtStep1"));
			ServerValue.setMainCartStep2(props.getProperty("mainCartStep2"));
			ServerValue.setPrtCartStep2(props.getProperty("prtCartStep2"));
			ServerValue.setMainBuyStep2(props.getProperty("mainBuyStep2"));
			ServerValue.setPrtBuyStep2(props.getProperty("prtBuyStep2"));
			ServerValue.setDiscountType(props.getProperty("discountType"));
			ServerValue.setTrustMember(props.getProperty("trustMember"));
			ServerValue.setDaphneLimitPrice(props.getProperty("daphneLimitPrice"));
			ServerValue.setBlockOrder(props.getProperty("blockOrder"));
			ServerValue.setFreightBase(props.getProperty("freightBase"));
			ServerValue.setInnerIP(props.getProperty("innerIP"));
			ServerValue.setProdInnerIP(props.getProperty("prodInnerIP"));
			ServerValue.setInitialDate(new SimpleDateFormat("yyyyMMddHH").format(new Date()));
			ServerValue.setVersion(props.getProperty("version"));
			ServerValue.setBlockOrderPaymentType(props.getProperty("blockOrderPaymentType"));
			if (StringUtils.isNotBlank(props.getProperty("staticPageCache"))) {
				ServerValue.setStaticPageCache(Boolean.parseBoolean(props.getProperty("staticPageCache")));
			}

			if (StringUtils.isNotBlank(props.getProperty("useBfd"))) {
				ServerValue.setUseBfd(props.getProperty("useBfd"));
			}
			if (StringUtils.isNotBlank(props.getProperty("useCreditcard"))) {
				ServerValue.setUseCreditcard(props.getProperty("useCreditcard"));
			}
			if (StringUtils.isNotBlank(props.getProperty("useCreditcardPeriod"))) {
				ServerValue.setUseCreditcardPeriod(props.getProperty("useCreditcardPeriod"));
			}

			if (StringUtils.isNotBlank(props.getProperty("prodVolumeWeight"))) {
				ServerValue.setProdVolumeWeight(props.getProperty("prodVolumeWeight"));
			}
			if (StringUtils.isNotBlank(props.getProperty("prodWeight"))) {
				ServerValue.setProdWeight(props.getProperty("prodWeight"));
			}
			if (StringUtils.isNotBlank(props.getProperty("bfdCode"))) {
				ServerValue.setBfdCode(props.getProperty("bfdCode"));
			}
			if (StringUtils.isNotBlank(props.getProperty("updateProdStByERP"))) {
				ServerValue.setUpdateProdStByERP(props.getProperty("updateProdStByERP"));
			}
			if (StringUtils.isNotBlank(props.getProperty("useCod"))) {
				ServerValue.setUseCod(props.getProperty("useCod"));
			}
			// 中台
			ServerValue.setMidHostname(props.getProperty("midHostname"));
			ServerValue.setMidHostnameSsl(props.getProperty("midHostnameSsl"));
			ServerValue.setMidContextPath(props.getProperty("midContextPath"));
			ServerValue.setMidContentNotFilter(props.getProperty("midContentNotFilter"));
			ServerValue.setMidApachePath(props.getProperty("midApachePath"));
			ServerValue.setMidProdHtmlPath(props.getProperty("midProdHtmlPath"));
			ServerValue.setMidOrderHtmlPath(props.getProperty("midOrderHtmlPath"));
			ServerValue.setMidGiveBonusFile(props.getProperty("midGiveBonusFile"));
			ServerValue.setMidRankShortDays(props.getProperty("midRankShortDays"));
			ServerValue.setMidRankLongDays(props.getProperty("midRankLongDays"));
			ServerValue.setUseDoubleSpec(props.getProperty("useDoubleSpec"));

			if (StringUtils.isNotBlank(props.getProperty("scmType"))) {
				ServerValue.setScmType(props.getProperty("scmType"));
			}
			if (StringUtils.isNotBlank(props.getProperty("nmlType"))) {
				ServerValue.setNmlType(props.getProperty("nmlType"));
			}
			if (StringUtils.isNotBlank(props.getProperty("luxType"))) {
				ServerValue.setLuxType(props.getProperty("luxType"));
			}
			// 后台
			ServerValue.setBackHostname(props.getProperty("backHostname"));
			ServerValue.setBackHostnameSsl(props.getProperty("backHostnameSsl"));
			ServerValue.setBackContextPath(props.getProperty("backContextPath"));
			ServerValue.setBackContentNotFilter(props.getProperty("backContentNotFilter"));
			ServerValue.setBackApachePath(props.getProperty("backApachePath"));
			String csrLockOrderWS = props.getProperty("csrLockOrderWS");
			if (csrLockOrderWS != null) {
				ServerValue.setCsrLockOrderWS(csrLockOrderWS);
			}
			log.info("server prop end............");
		} catch (Exception e) {
			log.error("Init Exception:" + e.getMessage() + ",change to Online prop.");
		}
		try {
			log.info("server seoprop begin ............");
			seoProps.load((new URL(seopath)).openStream());
			ServerValue.setSeoProp(seoProps);
			log.info("server seoprop end............");
		} catch (IOException e) {
			ResourceBundle resource = ResourceBundle.getBundle("frontpage", Locale.CHINA);
			Enumeration<String> keys = resource.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				seoProps.put(key, resource.getString(key));
			}
			ServerValue.setSeoProp(seoProps);
			log.error("Init Exception:" + e.getMessage() + ",change to Online SEO prop.");
		}

		log.info("End server init...");
	}

	/**
	 * @return the searchEnginePath
	 */
	public static String getSearchEnginePath() {
		return searchEnginePath;
	}

	/**
	 * @param searchEnginePath
	 *           the searchEnginePath to set
	 */
	public static void setSearchEnginePath(String searchEnginePath) {
		ServerValue.searchEnginePath = searchEnginePath;
	}

	public static String getTrustMember() {
		return trustMember;
	}

	public static void setTrustMember(String trustMember) {
		ServerValue.trustMember = trustMember;
	}

	public static String getProdInnerIP() {
		return prodInnerIP;
	}

	public static void setProdInnerIP(String prodInnerIP) {
		ServerValue.prodInnerIP = prodInnerIP;
	}

	public static List<String> getTrustMembers() {
		List<String> temps = new ArrayList<String>();
		for (String tmp : StringUtils.split(trustMember, ",")) {
			temps.add(tmp);
		}
		return temps;
	}

	public static String getTrustMemberString() {
		return trustMember;
	}

	public static String getMainStep0() {
		return mainStep0;
	}

	public static String getPrtStep0() {
		return prtStep0;
	}

	public static String getMainStep1() {
		return mainStep1;
	}

	public static String getPrtStep1() {
		return prtStep1;
	}

	public static String getMainCartStep2() {
		return mainCartStep2;
	}

	public static String getPrtCartStep2() {
		return prtCartStep2;
	}

	public static String getMainBuyStep2() {
		return mainBuyStep2;
	}

	public static String getPrtBuyStep2() {
		return prtBuyStep2;
	}

	public static void setMainStep0(String mainStep0) {
		if (null != mainStep0) {
			ServerValue.mainStep0 = mainStep0;
		}
	}

	public static void setPrtStep0(String prtStep0) {
		if (null != prtStep0) {
			ServerValue.prtStep0 = prtStep0;
		}
	}

	public static void setMainStep1(String mainStep1) {
		if (null != mainStep1) {
			ServerValue.mainStep1 = mainStep1;
		}
	}

	public static void setPrtStep1(String prtStep1) {
		if (null != prtStep1) {
			ServerValue.prtStep1 = prtStep1;
		}
	}

	public static void setMainCartStep2(String mainCartStep2) {
		if (null != mainCartStep2) {
			ServerValue.mainCartStep2 = mainCartStep2;
		}
	}

	public static void setPrtCartStep2(String prtCartStep2) {
		if (null != prtCartStep2) {
			ServerValue.prtCartStep2 = prtCartStep2;
		}
	}

	public static void setMainBuyStep2(String mainBuyStep2) {
		if (null != mainBuyStep2) {
			ServerValue.mainBuyStep2 = mainBuyStep2;
		}
	}

	public static void setPrtBuyStep2(String prtBuyStep2) {
		if (null != prtBuyStep2) {
			ServerValue.prtBuyStep2 = prtBuyStep2;
		}
	}

	public static String getMainloginStep0() {
		return mainloginStep0;
	}

	public static String getPrtloginStep0() {
		return prtloginStep0;
	}

	public static void setMainloginStep0(String mainloginStep0) {
		ServerValue.mainloginStep0 = mainloginStep0;
	}

	public static void setPrtloginStep0(String prtloginStep0) {
		ServerValue.prtloginStep0 = prtloginStep0;
	}

	public static String getDiscountType() {
		return discountType;
	}

	public static void setDiscountType(String discountType) {
		if (null != discountType) {
			ServerValue.discountType = discountType;
		}
	}

	public static String getOrderModiyAddress() {
		return orderModiyAddress;
	}

	public static void setOrderModiyAddress(String orderModiyAddress) {
		if (null != orderModiyAddress) {
			ServerValue.orderModiyAddress = orderModiyAddress;
		}
	}

	public static BigDecimal getDaphneLimitPrice() {
		return new BigDecimal(daphneLimitPrice);
	}

	public static void setDaphneLimitPrice(String daphneLimitPrice) {
		ServerValue.daphneLimitPrice = daphneLimitPrice;
	}

	public static String getUseDoubleSpec() {
		return useDoubleSpec;
	}

	public static void setUseDoubleSpec(String useDoubleSpec) {
		ServerValue.useDoubleSpec = useDoubleSpec;
	}

	public static int getFreightBase() {
		return freightBase;
	}

	public static void setFreightBase(String freightBase) {
		try {
			ServerValue.freightBase = Integer.parseInt(freightBase);
		} catch (Exception e) {
			System.out.println("freightBase is not integer");
			ServerValue.freightBase = 3;
		}
	}

	public static String getNmlType() {
		return nmlType;
	}

	public static String getScmType() {
		return scmType;
	}

	public static String getLuxType() {
		return luxType;
	}

	public static void setNmlType(String nmlType) {
		ServerValue.nmlType = nmlType;
	}

	public static void setScmType(String scmType) {
		ServerValue.scmType = scmType;
	}

	public static void setLuxType(String luxType) {
		ServerValue.luxType = luxType;
	}

	public static String getUseBfd() {
		return useBfd;
	}

	public static String getUseCreditcard() {
		return useCreditcard;
	}

	public static String getUseCreditcardPeriod() {
		return useCreditcardPeriod;
	}

	public static void setUseBfd(String useBfd) {
		ServerValue.useBfd = useBfd;
	}

	public static void setUseCreditcard(String useCreditcard) {
		ServerValue.useCreditcard = useCreditcard;
	}

	public static void setUseCreditcardPeriod(String useCreditcardPeriod) {
		ServerValue.useCreditcardPeriod = useCreditcardPeriod;
	}

	public static String getVersion() {
		return version;
	}

	public static void setVersion(String version) {
		ServerValue.version = version;
	}

	public static String getProdVolumeWeight() {
		return prodVolumeWeight;
	}

	public static String getProdWeight() {
		return prodWeight;
	}

	public static void setProdVolumeWeight(String prodVolumeWeight) {
		ServerValue.prodVolumeWeight = prodVolumeWeight;
	}

	public static void setProdWeight(String prodWeight) {
		ServerValue.prodWeight = prodWeight;
	}

	public static String getBlockOrderPaymentType() {
		return blockOrderPaymentType;
	}

	public static void setBlockOrderPaymentType(String blockOrderPaymentType) {
		ServerValue.blockOrderPaymentType = blockOrderPaymentType;
	}

	public static String getBfdCode() {
		return bfdCode;
	}

	public static void setBfdCode(String bfdCode) {
		ServerValue.bfdCode = bfdCode;
	}

	public static String getInitialDate() {
		return initialDate;
	}

	public static void setInitialDate(String initialDate) {
		ServerValue.initialDate = initialDate;
	}

	/**
	 * @return the updateProdStByERP
	 */
	public static String getUpdateProdStByERP() {
		return updateProdStByERP;
	}

	/**
	 * @param updateProdStByERP
	 *           the updateProdStByERP to set
	 */
	public static void setUpdateProdStByERP(String updateProdStByERP) {
		ServerValue.updateProdStByERP = updateProdStByERP;
	}

	public static boolean isStaticPageCache() {
		return staticPageCache;
	}

	public static void setStaticPageCache(boolean staticPageCache) {
		ServerValue.staticPageCache = staticPageCache;
	}

	public static String getUseCod() {
		return useCod;
	}

	public static void setUseCod(String useCod) {
		ServerValue.useCod = useCod;
	}
}
