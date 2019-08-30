package cn.itcast.dw.realtime.bean;

/**
 * 商品 DTO
 * Created by: mengyao
 * 2019年7月2日
 */
public class GoodsBean extends Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5150225358177246709L;
	private long goodsId;					// 商品id
	private String goodsSn;					// 商品编号
	private String productNo;				// 商品货号
	private String goodsName;				// 商品名称
	private String goodsImg;				// 商品图片
	private long shopId;					// 门店ID
	private int goodsType;					// 货物类型
	private double marketPrice;				// 市场价
	private double shopPrice;				// 门店价
	private long warnStock;					// 预警库存
	private long goodsStock;				// 商品总库存
	private String goodsUnit;				// 单位
	private double goodsTips;				// 促销信息
	private int isSale;						// 是否上架 0:不上架 1:上架
	private int isBest;						// 是否精品 0:否 1:是
	private int isHot;						// 是否热销产品 0:否 1:是
	private int isNew;						// 是否新品 0:否 1:是
	private int isRecom;					// 是否推荐 0:否 1:是
	private String goodsCatIdPath;			// 商品分类ID路径 catId1_catId2_catId3
	private int goodsCatId;					// goodsCatId 最后一级商品分类ID
	private int shopCatId1;					// 门店商品分类第一级ID
	private int shopCatId2;					// 门店商品第二级分类ID
	private int brandId;					// 品牌ID
	private String goodsDesc;				// 商品描述
	private int goodsStatus;				// 商品状态 -1:违规 0:未审核 1:已审核
	private int saleNum;					// 总销售量
	private String saleTime;				// 上架时间
	private int visitNum;					// 访问数
	private int appraiseNum;				// 评价书
	private int isSpec;						// 是否有规格 0:没有 1:有
	private String gallery;					// 商品相册
	private String goodsSeoKeywords;		// 商品SEO关键字
	private String illegalRemarks;			// 状态说明 一般用于说明拒绝原因
	private int dataFlag;					// 删除标志 -1:删除 1:有效
	private String createTime;
	private int isFreeShipping;
	private String goodsSerachKeywords;

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public double getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(double shopPrice) {
		this.shopPrice = shopPrice;
	}

	public long getWarnStock() {
		return warnStock;
	}

	public void setWarnStock(long warnStock) {
		this.warnStock = warnStock;
	}

	public long getGoodsStock() {
		return goodsStock;
	}

	public void setGoodsStock(long goodsStock) {
		this.goodsStock = goodsStock;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public double getGoodsTips() {
		return goodsTips;
	}

	public void setGoodsTips(double goodsTips) {
		this.goodsTips = goodsTips;
	}

	public int getIsSale() {
		return isSale;
	}

	public void setIsSale(int isSale) {
		this.isSale = isSale;
	}

	public int getIsBest() {
		return isBest;
	}

	public void setIsBest(int isBest) {
		this.isBest = isBest;
	}

	public int getIsHot() {
		return isHot;
	}

	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	public int getIsRecom() {
		return isRecom;
	}

	public void setIsRecom(int isRecom) {
		this.isRecom = isRecom;
	}

	public String getGoodsCatIdPath() {
		return goodsCatIdPath;
	}

	public void setGoodsCatIdPath(String goodsCatIdPath) {
		this.goodsCatIdPath = goodsCatIdPath;
	}

	public int getGoodsCatId() {
		return goodsCatId;
	}

	public void setGoodsCatId(int goodsCatId) {
		this.goodsCatId = goodsCatId;
	}

	public int getShopCatId1() {
		return shopCatId1;
	}

	public void setShopCatId1(int shopCatId1) {
		this.shopCatId1 = shopCatId1;
	}

	public int getShopCatId2() {
		return shopCatId2;
	}

	public void setShopCatId2(int shopCatId2) {
		this.shopCatId2 = shopCatId2;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public int getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(int goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public int getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}

	public String getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public int getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}

	public int getAppraiseNum() {
		return appraiseNum;
	}

	public void setAppraiseNum(int appraiseNum) {
		this.appraiseNum = appraiseNum;
	}

	public int getIsSpec() {
		return isSpec;
	}

	public void setIsSpec(int isSpec) {
		this.isSpec = isSpec;
	}

	public String getGallery() {
		return gallery;
	}

	public void setGallery(String gallery) {
		this.gallery = gallery;
	}

	public String getGoodsSeoKeywords() {
		return goodsSeoKeywords;
	}

	public void setGoodsSeoKeywords(String goodsSeoKeywords) {
		this.goodsSeoKeywords = goodsSeoKeywords;
	}

	public String getIllegalRemarks() {
		return illegalRemarks;
	}

	public void setIllegalRemarks(String illegalRemarks) {
		this.illegalRemarks = illegalRemarks;
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

	public int getIsFreeShipping() {
		return isFreeShipping;
	}

	public void setIsFreeShipping(int isFreeShipping) {
		this.isFreeShipping = isFreeShipping;
	}

	public String getGoodsSerachKeywords() {
		return goodsSerachKeywords;
	}

	public void setGoodsSerachKeywords(String goodsSerachKeywords) {
		this.goodsSerachKeywords = goodsSerachKeywords;
	}

	@Override
	public String toString() {
		return goodsId + "\t" + goodsSn + "\t" + productNo + "\t" + goodsName + "\t" + goodsImg + "\t" + shopId + "\t"
				+ goodsType + "\t" + marketPrice + "\t" + shopPrice + "\t" + warnStock + "\t" + goodsStock + "\t"
				+ goodsUnit + "\t" + goodsTips + "\t" + isSale + "\t" + isBest + "\t" + isHot + "\t" + isNew + "\t"
				+ isRecom + "\t" + goodsCatIdPath + "\t" + goodsCatId + "\t" + shopCatId1 + "\t" + shopCatId2 + "\t"
				+ brandId + "\t" + goodsDesc + "\t" + goodsStatus + "\t" + saleNum + "\t" + saleTime + "\t" + visitNum
				+ "\t" + appraiseNum + "\t" + isSpec + "\t" + gallery + "\t" + goodsSeoKeywords + "\t" + illegalRemarks
				+ "\t" + dataFlag + "\t" + createTime + "\t" + isFreeShipping + "\t" + goodsSerachKeywords;
	}
	

}
