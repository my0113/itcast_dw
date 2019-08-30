package cn.itcast.dw.realtime.bean;

/**
 * 商品品类 DTO
 * Created by: mengyao
 * 2019年7月2日
 */
public class GoodsTypeBean extends Bean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1230584237791239364L;
	private int catId;
	private int parentId;				// 父ID
	private String catName;				// 分类名称
	private int isShow;					// 是否显示 0:隐藏 1:显示
	private int isFloor;				// 是否显示楼层 0:不显示 1:显示
	private int catSort;				// 排序号
	private int dataFlag;				// 删除标志 1:有效 -1：删除
	private String createTime;;			// 建立时间
	private double commissionRate;		// 商品佣金比例 -1代表使用上级父类的佣金设置
	private String catImg;
	private String subTitle;			// 楼层副标题
	private String simpleName;			// 简写名称
	private String seoTitle;			// 分类SEO标题
	private String seoKeywords;			// 分类SEO关键字
	private String seoDes;				// 分类SEO描述
	private String catListTheme;		// 商品分类列表风格
	private String detailTheme;			// 商品详情风格
	private String mobileCatListTheme;	// 移动端商品分类列表风格
	private String mobileDetailTheme;	// 移动端商品详情风格
	private String wechatCatListTheme;	// 微信端商品分类列表风格
	private String wechatDetailTheme;	// 微信端商品详情风格

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public int getIsFloor() {
		return isFloor;
	}

	public void setIsFloor(int isFloor) {
		this.isFloor = isFloor;
	}

	public int getCatSort() {
		return catSort;
	}

	public void setCatSort(int catSort) {
		this.catSort = catSort;
	}

	public int getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(int dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getCatImg() {
		return catImg;
	}

	public void setCatImg(String catImg) {
		this.catImg = catImg;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDes() {
		return seoDes;
	}

	public void setSeoDes(String seoDes) {
		this.seoDes = seoDes;
	}

	public String getCatListTheme() {
		return catListTheme;
	}

	public void setCatListTheme(String catListTheme) {
		this.catListTheme = catListTheme;
	}

	public String getDetailTheme() {
		return detailTheme;
	}

	public void setDetailTheme(String detailTheme) {
		this.detailTheme = detailTheme;
	}

	public String getMobileCatListTheme() {
		return mobileCatListTheme;
	}

	public void setMobileCatListTheme(String mobileCatListTheme) {
		this.mobileCatListTheme = mobileCatListTheme;
	}

	public String getMobileDetailTheme() {
		return mobileDetailTheme;
	}

	public void setMobileDetailTheme(String mobileDetailTheme) {
		this.mobileDetailTheme = mobileDetailTheme;
	}

	public String getWechatCatListTheme() {
		return wechatCatListTheme;
	}

	public void setWechatCatListTheme(String wechatCatListTheme) {
		this.wechatCatListTheme = wechatCatListTheme;
	}

	public String getWechatDetailTheme() {
		return wechatDetailTheme;
	}

	public void setWechatDetailTheme(String wechatDetailTheme) {
		this.wechatDetailTheme = wechatDetailTheme;
	}

	@Override
	public String toString() {
		return catId + "\t" + parentId + "\t" + catName + "\t" + isShow + "\t" + isFloor + "\t" + catSort + "\t"
				+ dataFlag + "\t" + createTime + "\t" + commissionRate + "\t" + catImg + "\t" + subTitle + "\t"
				+ simpleName + "\t" + seoTitle + "\t" + seoKeywords + "\t" + seoDes + "\t" + catListTheme + "\t"
				+ detailTheme + "\t" + mobileCatListTheme + "\t" + mobileDetailTheme + "\t" + wechatCatListTheme + "\t"
				+ wechatDetailTheme;
	}

}
