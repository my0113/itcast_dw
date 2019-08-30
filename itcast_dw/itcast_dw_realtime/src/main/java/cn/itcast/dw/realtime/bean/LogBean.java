package cn.itcast.dw.realtime.bean;

/**
 * 用户行为
 * Created by: mengyao
 * 2019年7月2日
 */
public class LogBean extends Bean {
	
	private static final long serialVersionUID = -4432274786218106262L;
	private long id;              // 唯一标识
	private String url;             // 当前链接地址
	private String referer;         // 前一页链接地址
	private String keyword;         // 搜索引擎关键字
	private String type;            // 访问类型
	private String guid;            // 唯一用户ID
	private String pageId;          // tracker自动化打点的pageType + "." + pageId +".0.0.0."+uniId(已废弃)
	private String moduleId;        // tracker自动化打点的tracker position（tp）(可废弃)
	private String linkId;          // tracker自动化打点的tracker content（tc）(可废弃)
	private String attachedInfo;    // 附加信息，记录了库存信息等
	private String sessionId;       // 对应cookie.tracker_msessionid
	private String trackerU;        // 网盟用户id
	private String trackerType;     // 跟踪类型
	private String ip;              // 用户ip
	private String trackerSrc;      // 页面来源
	private String cookie;          // 传入的cookie参数
	private String orderCode;       // 订单编号
	private String trackTime;       // 跟踪时间
	private String endUserId;       // 客户ID
	private String firstLink;       // 首次访问的链接
	private String sessionViewNo;   // 客户访问网站的顺序
	private String productId;       // 产品ID
	private String curMerchantId;   // 用户当前选择的merchantId（已废弃）
	private String provinceId;      // 省份ID
	private String cityId;          // 城市ID
	private String fee;             // 判断搜索引擎关键字是否收费
	private String edmActivity;     // EDM邮件是否被打开,Y为打开
	private String edmEmail;        // EDM邮件地址或者邮件ID
	private String edmJobId;        // 发送该邮件地址的edm jod id
	private String ieVersion;       // 浏览器版本
	private String platform;        // 操作系统
	private String internalKeyword; // 站内搜索关键字
	private String resultSum;       // 搜索结果数
	private String currentPage;     // 搜索当前页
	private String linkPosition;    // 链接位置
	private String buttonPosition;  // 按钮位置
	public LogBean() {
	}
	public LogBean(String guid, String ip, String url, long ts) {
		this.guid = guid;
		this.ip = ip;
		this.url = url;
		super.setTs(ts);
	}
	public LogBean(String guid,long count,String trackTime,String url,String referer,String orderCode) {
		this.guid = guid;
		this.trackTime = trackTime;
		this.url = url;
		this.referer = referer;
		this.orderCode = orderCode;
	}
	public LogBean(long id, String url, String referer, String keyword, String type, String guid, String pageId,
			String moduleId, String linkId, String attachedInfo, String sessionId, String trackerU, String trackerType,
			String ip, String trackerSrc, String cookie, String orderCode, String trackTime, String endUserId,
			String firstLink, String sessionViewNo, String productId, String curMerchantId, String provinceId,
			String cityId, String fee, String edmActivity, String edmEmail, String edmJobId, String ieVersion,
			String platform, String internalKeyword, String resultSum, String currentPage, String linkPosition,
			String buttonPosition) {
		super();
		this.id = id;
		this.url = url;
		this.referer = referer;
		this.keyword = keyword;
		this.type = type;
		this.guid = guid;
		this.pageId = pageId;
		this.moduleId = moduleId;
		this.linkId = linkId;
		this.attachedInfo = attachedInfo;
		this.sessionId = sessionId;
		this.trackerU = trackerU;
		this.trackerType = trackerType;
		this.ip = ip;
		this.trackerSrc = trackerSrc;
		this.cookie = cookie;
		this.orderCode = orderCode;
		this.trackTime = trackTime;
		this.endUserId = endUserId;
		this.firstLink = firstLink;
		this.sessionViewNo = sessionViewNo;
		this.productId = productId;
		this.curMerchantId = curMerchantId;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.fee = fee;
		this.edmActivity = edmActivity;
		this.edmEmail = edmEmail;
		this.edmJobId = edmJobId;
		this.ieVersion = ieVersion;
		this.platform = platform;
		this.internalKeyword = internalKeyword;
		this.resultSum = resultSum;
		this.currentPage = currentPage;
		this.linkPosition = linkPosition;
		this.buttonPosition = buttonPosition;
	}
	public long getId() {             
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public String getAttachedInfo() {
		return attachedInfo;
	}
	public void setAttachedInfo(String attachedInfo) {
		this.attachedInfo = attachedInfo;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTrackerU() {
		return trackerU;
	}
	public void setTrackerU(String trackerU) {
		this.trackerU = trackerU;
	}
	public String getTrackerType() {
		return trackerType;
	}
	public void setTrackerType(String trackerType) {
		this.trackerType = trackerType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTrackerSrc() {
		return trackerSrc;
	}
	public void setTrackerSrc(String trackerSrc) {
		this.trackerSrc = trackerSrc;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getTrackTime() {
		return trackTime;
	}
	public void setTrackTime(String trackTime) {
		this.trackTime = trackTime;
	}
	public String getEndUserId() {
		return endUserId;
	}
	public void setEndUserId(String endUserId) {
		this.endUserId = endUserId;
	}
	public String getFirstLink() {
		return firstLink;
	}
	public void setFirstLink(String firstLink) {
		this.firstLink = firstLink;
	}
	public String getSessionViewNo() {
		return sessionViewNo;
	}
	public void setSessionViewNo(String sessionViewNo) {
		this.sessionViewNo = sessionViewNo;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCurMerchantId() {
		return curMerchantId;
	}
	public void setCurMerchantId(String curMerchantId) {
		this.curMerchantId = curMerchantId;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getEdmActivity() {
		return edmActivity;
	}
	public void setEdmActivity(String edmActivity) {
		this.edmActivity = edmActivity;
	}
	public String getEdmEmail() {
		return edmEmail;
	}
	public void setEdmEmail(String edmEmail) {
		this.edmEmail = edmEmail;
	}
	public String getEdmJobId() {
		return edmJobId;
	}
	public void setEdmJobId(String edmJobId) {
		this.edmJobId = edmJobId;
	}
	public String getIeVersion() {
		return ieVersion;
	}
	public void setIeVersion(String ieVersion) {
		this.ieVersion = ieVersion;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getInternalKeyword() {
		return internalKeyword;
	}
	public void setInternalKeyword(String internalKeyword) {
		this.internalKeyword = internalKeyword;
	}
	public String getResultSum() {
		return resultSum;
	}
	public void setResultSum(String resultSum) {
		this.resultSum = resultSum;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getLinkPosition() {
		return linkPosition;
	}
	public void setLinkPosition(String linkPosition) {
		this.linkPosition = linkPosition;
	}
	public String getButtonPosition() {
		return buttonPosition;
	}
	public void setButtonPosition(String buttonPosition) {
		this.buttonPosition = buttonPosition;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachedInfo == null) ? 0 : attachedInfo.hashCode());
		result = prime * result + ((buttonPosition == null) ? 0 : buttonPosition.hashCode());
		result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
		result = prime * result + ((cookie == null) ? 0 : cookie.hashCode());
		result = prime * result + ((curMerchantId == null) ? 0 : curMerchantId.hashCode());
		result = prime * result + ((currentPage == null) ? 0 : currentPage.hashCode());
		result = prime * result + ((edmActivity == null) ? 0 : edmActivity.hashCode());
		result = prime * result + ((edmEmail == null) ? 0 : edmEmail.hashCode());
		result = prime * result + ((edmJobId == null) ? 0 : edmJobId.hashCode());
		result = prime * result + ((endUserId == null) ? 0 : endUserId.hashCode());
		result = prime * result + ((fee == null) ? 0 : fee.hashCode());
		result = prime * result + ((firstLink == null) ? 0 : firstLink.hashCode());
		result = prime * result + ((guid == null) ? 0 : guid.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((ieVersion == null) ? 0 : ieVersion.hashCode());
		result = prime * result + ((internalKeyword == null) ? 0 : internalKeyword.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((linkId == null) ? 0 : linkId.hashCode());
		result = prime * result + ((linkPosition == null) ? 0 : linkPosition.hashCode());
		result = prime * result + ((moduleId == null) ? 0 : moduleId.hashCode());
		result = prime * result + ((orderCode == null) ? 0 : orderCode.hashCode());
		result = prime * result + ((pageId == null) ? 0 : pageId.hashCode());
		result = prime * result + ((platform == null) ? 0 : platform.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((provinceId == null) ? 0 : provinceId.hashCode());
		result = prime * result + ((referer == null) ? 0 : referer.hashCode());
		result = prime * result + ((resultSum == null) ? 0 : resultSum.hashCode());
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((sessionViewNo == null) ? 0 : sessionViewNo.hashCode());
		result = prime * result + ((trackTime == null) ? 0 : trackTime.hashCode());
		result = prime * result + ((trackerSrc == null) ? 0 : trackerSrc.hashCode());
		result = prime * result + ((trackerType == null) ? 0 : trackerType.hashCode());
		result = prime * result + ((trackerU == null) ? 0 : trackerU.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogBean other = (LogBean) obj;
		if (attachedInfo == null) {
			if (other.attachedInfo != null)
				return false;
		} else if (!attachedInfo.equals(other.attachedInfo))
			return false;
		if (buttonPosition == null) {
			if (other.buttonPosition != null)
				return false;
		} else if (!buttonPosition.equals(other.buttonPosition))
			return false;
		if (cityId == null) {
			if (other.cityId != null)
				return false;
		} else if (!cityId.equals(other.cityId))
			return false;
		if (cookie == null) {
			if (other.cookie != null)
				return false;
		} else if (!cookie.equals(other.cookie))
			return false;
		if (curMerchantId == null) {
			if (other.curMerchantId != null)
				return false;
		} else if (!curMerchantId.equals(other.curMerchantId))
			return false;
		if (currentPage == null) {
			if (other.currentPage != null)
				return false;
		} else if (!currentPage.equals(other.currentPage))
			return false;
		if (edmActivity == null) {
			if (other.edmActivity != null)
				return false;
		} else if (!edmActivity.equals(other.edmActivity))
			return false;
		if (edmEmail == null) {
			if (other.edmEmail != null)
				return false;
		} else if (!edmEmail.equals(other.edmEmail))
			return false;
		if (edmJobId == null) {
			if (other.edmJobId != null)
				return false;
		} else if (!edmJobId.equals(other.edmJobId))
			return false;
		if (endUserId == null) {
			if (other.endUserId != null)
				return false;
		} else if (!endUserId.equals(other.endUserId))
			return false;
		if (fee == null) {
			if (other.fee != null)
				return false;
		} else if (!fee.equals(other.fee))
			return false;
		if (firstLink == null) {
			if (other.firstLink != null)
				return false;
		} else if (!firstLink.equals(other.firstLink))
			return false;
		if (guid == null) {
			if (other.guid != null)
				return false;
		} else if (!guid.equals(other.guid))
			return false;
		if (id != other.id)
			return false;
		if (ieVersion == null) {
			if (other.ieVersion != null)
				return false;
		} else if (!ieVersion.equals(other.ieVersion))
			return false;
		if (internalKeyword == null) {
			if (other.internalKeyword != null)
				return false;
		} else if (!internalKeyword.equals(other.internalKeyword))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		if (linkId == null) {
			if (other.linkId != null)
				return false;
		} else if (!linkId.equals(other.linkId))
			return false;
		if (linkPosition == null) {
			if (other.linkPosition != null)
				return false;
		} else if (!linkPosition.equals(other.linkPosition))
			return false;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		if (orderCode == null) {
			if (other.orderCode != null)
				return false;
		} else if (!orderCode.equals(other.orderCode))
			return false;
		if (pageId == null) {
			if (other.pageId != null)
				return false;
		} else if (!pageId.equals(other.pageId))
			return false;
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (provinceId == null) {
			if (other.provinceId != null)
				return false;
		} else if (!provinceId.equals(other.provinceId))
			return false;
		if (referer == null) {
			if (other.referer != null)
				return false;
		} else if (!referer.equals(other.referer))
			return false;
		if (resultSum == null) {
			if (other.resultSum != null)
				return false;
		} else if (!resultSum.equals(other.resultSum))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (sessionViewNo == null) {
			if (other.sessionViewNo != null)
				return false;
		} else if (!sessionViewNo.equals(other.sessionViewNo))
			return false;
		if (trackTime == null) {
			if (other.trackTime != null)
				return false;
		} else if (!trackTime.equals(other.trackTime))
			return false;
		if (trackerSrc == null) {
			if (other.trackerSrc != null)
				return false;
		} else if (!trackerSrc.equals(other.trackerSrc))
			return false;
		if (trackerType == null) {
			if (other.trackerType != null)
				return false;
		} else if (!trackerType.equals(other.trackerType))
			return false;
		if (trackerU == null) {
			if (other.trackerU != null)
				return false;
		} else if (!trackerU.equals(other.trackerU))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return id + "+\t+" + url + "+\t+" + referer + "+\t+" + keyword + "+\t+" + type + "+\t+" + guid + "+\t+"
				+ pageId + "+\t+" + moduleId + "+\t+" + linkId + "+\t+" + attachedInfo + "+\t+" + sessionId
				+ "+\t+" + trackerU + "+\t+" + trackerType + "+\t+" + ip + "+\t+" + trackerSrc + "+\t+" + cookie
				+ "+\t+" + orderCode + "+\t+" + trackTime + "+\t+" + endUserId + "+\t+" + firstLink + "+\t+"
				+ sessionViewNo + "+\t+" + productId + "+\t+" + curMerchantId + "+\t+" + provinceId + "+\t+"
				+ cityId + "+\t+" + fee + "+\t+" + edmActivity + "+\t+" + edmEmail + "+\t+" + edmJobId + "+\t+"
				+ ieVersion + "+\t+" + platform + "+\t+" + internalKeyword + "+\t+" + resultSum + "+\t+"
				+ currentPage + "+\t+" + linkPosition + "+\t+" + buttonPosition + "\t" + getTs();
	}

}
