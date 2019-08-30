package cn.itcast.dw.realtime.bean;

/**
 * 订单商品 DTO
 * Created by: mengyao
 * 2019年7月2日
 */
public class OrderGoodsBean extends Bean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8618475370959590242L;
	private long id;
	private long orderId;			// 订单id
	private long goodsId;			// 商品id
	private long goodsNum;			// 商品数量
	private double goodsPrice;		// 商品价格
	private int goodsSpecId;		// 商品规格id
	private String goodsSpecNames;	// 商品规格列表
	private String goodsName;		// 商品名称
	private String goodsImg;		// 商品图片
	private String extraJson;		// 额外信息
	private int goodsType;
	private double commissionRate;	// 商品佣金比率
	private String goodsCode;
	private String promotionJson;
	public OrderGoodsBean() {
		super();
	}
	public OrderGoodsBean(long id, long orderId, long goodsId, long goodsNum, double goodsPrice, int goodsSpecId,
			String goodsSpecNames, String goodsName, String goodsImg, String extraJson, int goodsType,
			double commissionRate, String goodsCode, String promotionJson) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.goodsNum = goodsNum;
		this.goodsPrice = goodsPrice;
		this.goodsSpecId = goodsSpecId;
		this.goodsSpecNames = goodsSpecNames;
		this.goodsName = goodsName;
		this.goodsImg = goodsImg;
		this.extraJson = extraJson;
		this.goodsType = goodsType;
		this.commissionRate = commissionRate;
		this.goodsCode = goodsCode;
		this.promotionJson = promotionJson;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public long getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(long goodsNum) {
		this.goodsNum = goodsNum;
	}

	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public int getGoodsSpecId() {
		return goodsSpecId;
	}

	public void setGoodsSpecId(int goodsSpecId) {
		this.goodsSpecId = goodsSpecId;
	}

	public String getGoodsSpecNames() {
		return goodsSpecNames;
	}

	public void setGoodsSpecNames(String goodsSpecNames) {
		this.goodsSpecNames = goodsSpecNames;
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

	public String getExtraJson() {
		return extraJson;
	}

	public void setExtraJson(String extraJson) {
		this.extraJson = extraJson;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getPromotionJson() {
		return promotionJson;
	}

	public void setPromotionJson(String promotionJson) {
		this.promotionJson = promotionJson;
	}

	@Override
	public String toString() {
		return id + "\t" + orderId + "\t" + goodsId + "\t" + goodsNum + "\t" + goodsPrice + "\t" + goodsSpecId + "\t"
				+ goodsSpecNames + "\t" + goodsName + "\t" + goodsImg + "\t" + extraJson + "\t" + goodsType + "\t"
				+ commissionRate + "\t" + goodsCode + "\t" + promotionJson;
	}

}
