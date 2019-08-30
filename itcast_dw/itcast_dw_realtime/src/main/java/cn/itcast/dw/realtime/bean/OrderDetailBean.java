package cn.itcast.dw.realtime.bean;

/**
 * 订单商品明细 DTO
 * Created by: mengyao
 * 2019年7月2日
 */
public class OrderDetailBean extends Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3019547060944035942L;
	private long orderId;            //订单id
	private String orderNo;          //订单编号
    private long shopId;             //门店id
    private long userId;             //用户id
    private int orderStatus;         //订单状态 -3:用户拒收 -2:未付款的订单 -1：用户取消 0:待发货 1:配送中 2:用户确认收货
    private double goodsMoney;       //商品金额
    private int deliverType;         //收货方式 0:送货上门 1:自提
    private double deliverMoney;     //运费
    private double totalMoney;       //订单金额（包括运费）
    private double realTotalMoney;   //实际订单金额（折扣后金额）
    private int payType;             //支付方式
    private String payFrom;          //支付来源 1:支付宝，2：微信
    private int isPay;               //是否支付 0:未支付 1:已支付
    private int areaId;              //区域最低一级
    private String areaName;
    private String areaIdPath;       //区域idpath
    private String userName;         //收件人姓名
    private String userAddress;      //收件人地址
    private String userPhone;        //收件人电话
    private int orderScore;          //订单所得积分
    private int isInvoice;           //是否开发票 1:需要 0:不需要
    private String invoiceClient;     //发票抬头
    private String orderRemarks;     //订单备注
    private int orderSrc;            //订单来源 0:商城 1:微信 2:手机版 3:安卓App 4:苹果App
    private double needPay;          //需缴费用
    private int payRand;             //货币单位
    private int orderType;           //订单类型
    private int isRefund;            //是否退款 0:否 1：是
    private int isAppraise;          //是否点评 0:未点评 1:已点评
    private int cancelReason;        //取消原因ID
    private int rejectReason;        //用户拒绝原因ID
    private String rejectOtherReason;    //拒收原因
    private int isClosed;            //是否订单已完结 0：未完结 1:已完结
    private String goodsSearchKeys;    //                                                                                                              
    private String orderunique;      //订单流水号
    private String receiveTime;      //收货时间
    private String deliveryTime;     //发货时间
    private String tradeNo;          //在线支付交易流水
    private int dataFlag;            //订单有效标志 -1：删除 1:有效
    private String createTime;       //下单时间
    private int settlementId;        //是否结算，大于0的话则是结算ID
    private double commissionFee;    //订单应收佣金
    private double scoreMoney;       //积分抵扣金额
    private int useScore;            //花费积分
    private String orderCode;        //
    private String extraJson;        //额外信息
    private int orderCodeTargetID;   //
    private int noticeDeliver;       //提醒发货 0:未提醒 1:已提醒
    private String invoiceJson;      //发票信息
    private double lockCashMoney;    //锁定提现金额
    private String payTime;          //支付时间
    private int isBatch;             //是否拼单
    private int totalPayFee;         //总支付金额
    private long id;
	private long goodsId;			// 商品id
	private long goodsNum;			// 商品数量
	private double goodsPrice;		// 商品价格
	private int goodsSpecId;		// 商品规格id
	private String goodsSpecNames;	// 商品规格列表
	private String goodsName;		// 商品名称
	private String goodsImg;		// 商品图片
	private int goodsType;
	private double commissionRate;	// 商品佣金比率
	private String goodsCode;
	private String promotionJson;
	public OrderDetailBean() {
		super();
	}
	public OrderDetailBean(long orderId, String orderNo, long shopId, long userId, int orderStatus, double goodsMoney,
			int deliverType, double deliverMoney, double totalMoney, double realTotalMoney, int payType, String payFrom,
			int isPay, int areaId, String areaIdPath, String userName, String userAddress, String userPhone,
			int orderScore, int isInvoice, String invoiceClient, String orderRemarks, int orderSrc, double needPay,
			int payRand, int orderType, int isRefund, int isAppraise, int cancelReason, int rejectReason,
			String rejectOtherReason, int isClosed, String goodsSearchKeys, String orderunique, String receiveTime,
			String deliveryTime, String tradeNo, int dataFlag, String createTime, int settlementId,
			double commissionFee, double scoreMoney, int useScore, String orderCode, String extraJson,
			int orderCodeTargetID, int noticeDeliver, String invoiceJson, double lockCashMoney, String payTime,
			int isBatch, int totalPayFee, long id, long goodsId, long goodsNum, double goodsPrice, int goodsSpecId,
			String goodsSpecNames, String goodsName, String goodsImg, int goodsType, double commissionRate,
			String goodsCode, String promotionJson, String areaName) {
		super();
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.shopId = shopId;
		this.userId = userId;
		this.orderStatus = orderStatus;
		this.goodsMoney = goodsMoney;
		this.deliverType = deliverType;
		this.deliverMoney = deliverMoney;
		this.totalMoney = totalMoney;
		this.realTotalMoney = realTotalMoney;
		this.payType = payType;
		this.payFrom = payFrom;
		this.isPay = isPay;
		this.areaId = areaId;
		this.areaIdPath = areaIdPath;
		this.userName = userName;
		this.userAddress = userAddress;
		this.userPhone = userPhone;
		this.orderScore = orderScore;
		this.isInvoice = isInvoice;
		this.invoiceClient = invoiceClient;
		this.orderRemarks = orderRemarks;
		this.orderSrc = orderSrc;
		this.needPay = needPay;
		this.payRand = payRand;
		this.orderType = orderType;
		this.isRefund = isRefund;
		this.isAppraise = isAppraise;
		this.cancelReason = cancelReason;
		this.rejectReason = rejectReason;
		this.rejectOtherReason = rejectOtherReason;
		this.isClosed = isClosed;
		this.goodsSearchKeys = goodsSearchKeys;
		this.orderunique = orderunique;
		this.receiveTime = receiveTime;
		this.deliveryTime = deliveryTime;
		this.tradeNo = tradeNo;
		this.dataFlag = dataFlag;
		this.createTime = createTime;
		this.settlementId = settlementId;
		this.commissionFee = commissionFee;
		this.scoreMoney = scoreMoney;
		this.useScore = useScore;
		this.orderCode = orderCode;
		this.extraJson = extraJson;
		this.orderCodeTargetID = orderCodeTargetID;
		this.noticeDeliver = noticeDeliver;
		this.invoiceJson = invoiceJson;
		this.lockCashMoney = lockCashMoney;
		this.payTime = payTime;
		this.isBatch = isBatch;
		this.totalPayFee = totalPayFee;
		this.id = id;
		this.goodsId = goodsId;
		this.goodsNum = goodsNum;
		this.goodsPrice = goodsPrice;
		this.goodsSpecId = goodsSpecId;
		this.goodsSpecNames = goodsSpecNames;
		this.goodsName = goodsName;
		this.goodsImg = goodsImg;
		this.goodsType = goodsType;
		this.commissionRate = commissionRate;
		this.goodsCode = goodsCode;
		this.promotionJson = promotionJson;
		this.areaName = areaName;
	}
	public void add(OrderBean order) {
		this.orderId = order.getOrderId();
		this.orderNo = order.getOrderNo();
		this.shopId = order.getShopId();
		this.userId = order.getUserId();
		this.orderStatus = order.getOrderStatus();
		this.goodsMoney = order.getGoodsMoney();
		this.deliverType = order.getDeliverType();
		this.deliverMoney = order.getDeliverMoney();
		this.totalMoney = order.getTotalMoney();
		this.realTotalMoney = order.getRealTotalMoney();
		this.payType = order.getPayType();
		this.payFrom = order.getPayFrom();
		this.isPay = order.getIsPay();
		this.areaId = order.getAreaId();
		this.areaIdPath = order.getAreaIdPath();
		this.userName = order.getUserName();
		this.userAddress = order.getUserAddress();
		this.userPhone = order.getUserPhone();
		this.orderScore = order.getOrderScore();
		this.isInvoice = order.getIsInvoice();
		this.invoiceClient = order.getInvoiceClient();
		this.orderRemarks = order.getOrderRemarks();
		this.orderSrc = order.getOrderSrc();
		this.needPay = order.getNeedPay();
		this.payRand = order.getPayRand();
		this.orderType = order.getOrderType();
		this.isRefund = order.getIsRefund();
		this.isAppraise = order.getIsAppraise();
		this.cancelReason = order.getCancelReason();
		this.rejectReason = order.getRejectReason();
		this.rejectOtherReason = order.getRejectOtherReason();
		this.isClosed = order.getIsClosed();
		this.goodsSearchKeys = order.getGoodsSearchKeys();
		this.orderunique = order.getOrderunique();
		this.receiveTime = order.getReceiveTime();
		this.deliveryTime = order.getDeliveryTime();
		this.tradeNo = order.getTradeNo();
		this.dataFlag = order.getDataFlag();
		this.createTime = order.getCreateTime();
		this.settlementId = order.getSettlementId();
		this.commissionFee = order.getCommissionFee();
		this.scoreMoney = order.getScoreMoney();
		this.useScore = order.getUseScore();
		this.orderCode = order.getOrderCode();
		this.extraJson = order.getExtraJson();
		this.orderCodeTargetID = order.getOrderCodeTargetID();
		this.noticeDeliver = order.getNoticeDeliver();
		this.invoiceJson = order.getInvoiceJson();
		this.lockCashMoney = order.getLockCashMoney();
		this.payTime = order.getPayTime();
		this.isBatch = order.getIsBatch();
		this.totalPayFee = order.getTotalPayFee();
	}
	public void add(OrderGoodsBean orderGoods) {
		this.id=orderGoods.getId();			
		this.goodsId = orderGoods.getGoodsId();			
		this.goodsNum = orderGoods.getGoodsNum();			
		this.goodsPrice = orderGoods.getGoodsPrice();		
		this.goodsSpecId = orderGoods.getGoodsSpecId();		
		this.goodsSpecNames = orderGoods.getGoodsSpecNames();	
		this.goodsName = orderGoods.getGoodsName();		
		this.goodsImg = orderGoods.getGoodsImg();		
		this.extraJson = orderGoods.getExtraJson();		
		this.goodsType = orderGoods.getGoodsType();
		this.commissionRate = orderGoods.getCommissionRate();	
		this.goodsCode = orderGoods.getGoodsCode();
		this.promotionJson = orderGoods.getPromotionJson();
	}
	//订单
	public void set(long orderId, String orderNo, long shopId, long userId, int orderStatus, double goodsMoney,
			int deliverType, double deliverMoney, double totalMoney, double realTotalMoney, int payType, String payFrom,
			int isPay, int areaId, String areaIdPath, String userName, String userAddress, String userPhone,
			int orderScore, int isInvoice, String invoiceClient, String orderRemarks, int orderSrc, double needPay,
			int payRand, int orderType, int isRefund, int isAppraise, int cancelReason, int rejectReason,
			String rejectOtherReason, int isClosed, String goodsSearchKeys, String orderunique, String receiveTime,
			String deliveryTime, String tradeNo, int dataFlag, String createTime, int settlementId,
			double commissionFee, double scoreMoney, int useScore, String orderCode, String extraJson,
			int orderCodeTargetID, int noticeDeliver, String invoiceJson, double lockCashMoney, String payTime,
			int isBatch, int totalPayFee) {
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.shopId = shopId;
		this.userId = userId;
		this.orderStatus = orderStatus;
		this.goodsMoney = goodsMoney;
		this.deliverType = deliverType;
		this.deliverMoney = deliverMoney;
		this.totalMoney = totalMoney;
		this.realTotalMoney = realTotalMoney;
		this.payType = payType;
		this.payFrom = payFrom;
		this.isPay = isPay;
		this.areaId = areaId;
		this.areaIdPath = areaIdPath;
		this.userName = userName;
		this.userAddress = userAddress;
		this.userPhone = userPhone;
		this.orderScore = orderScore;
		this.isInvoice = isInvoice;
		this.invoiceClient = invoiceClient;
		this.orderRemarks = orderRemarks;
		this.orderSrc = orderSrc;
		this.needPay = needPay;
		this.payRand = payRand;
		this.orderType = orderType;
		this.isRefund = isRefund;
		this.isAppraise = isAppraise;
		this.cancelReason = cancelReason;
		this.rejectReason = rejectReason;
		this.rejectOtherReason = rejectOtherReason;
		this.isClosed = isClosed;
		this.goodsSearchKeys = goodsSearchKeys;
		this.orderunique = orderunique;
		this.receiveTime = receiveTime;
		this.deliveryTime = deliveryTime;
		this.tradeNo = tradeNo;
		this.dataFlag = dataFlag;
		this.createTime = createTime;
		this.settlementId = settlementId;
		this.commissionFee = commissionFee;
		this.scoreMoney = scoreMoney;
		this.useScore = useScore;
		this.orderCode = orderCode;
		this.extraJson = extraJson;
		this.orderCodeTargetID = orderCodeTargetID;
		this.noticeDeliver = noticeDeliver;
		this.invoiceJson = invoiceJson;
		this.lockCashMoney = lockCashMoney;
		this.payTime = payTime;
		this.isBatch = isBatch;
		this.totalPayFee = totalPayFee;
	}
	//订单
	public void set(long id, long goodsId, long goodsNum, double goodsPrice, int goodsSpecId, String goodsSpecNames,
			String goodsName, String goodsImg, int goodsType, double commissionRate, String goodsCode,
			String promotionJson) {
		this.id = id;
		this.goodsId = goodsId;
		this.goodsNum = goodsNum;
		this.goodsPrice = goodsPrice;
		this.goodsSpecId = goodsSpecId;
		this.goodsSpecNames = goodsSpecNames;
		this.goodsName = goodsName;
		this.goodsImg = goodsImg;
		this.goodsType = goodsType;
		this.commissionRate = commissionRate;
		this.goodsCode = goodsCode;
		this.promotionJson = promotionJson;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public long getShopId() {
		return shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public double getGoodsMoney() {
		return goodsMoney;
	}
	public void setGoodsMoney(double goodsMoney) {
		this.goodsMoney = goodsMoney;
	}
	public int getDeliverType() {
		return deliverType;
	}
	public void setDeliverType(int deliverType) {
		this.deliverType = deliverType;
	}
	public double getDeliverMoney() {
		return deliverMoney;
	}
	public void setDeliverMoney(double deliverMoney) {
		this.deliverMoney = deliverMoney;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public double getRealTotalMoney() {
		return realTotalMoney;
	}
	public void setRealTotalMoney(double realTotalMoney) {
		this.realTotalMoney = realTotalMoney;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public String getPayFrom() {
		return payFrom;
	}
	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}
	public int getIsPay() {
		return isPay;
	}
	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaIdPath() {
		return areaIdPath;
	}
	public void setAreaIdPath(String areaIdPath) {
		this.areaIdPath = areaIdPath;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public int getOrderScore() {
		return orderScore;
	}
	public void setOrderScore(int orderScore) {
		this.orderScore = orderScore;
	}
	public int getIsInvoice() {
		return isInvoice;
	}
	public void setIsInvoice(int isInvoice) {
		this.isInvoice = isInvoice;
	}
	public String getInvoiceClient() {
		return invoiceClient;
	}
	public void setInvoiceClien(String invoiceClient) {
		this.invoiceClient = invoiceClient;
	}
	public String getOrderRemarks() {
		return orderRemarks;
	}
	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}
	public int getOrderSrc() {
		return orderSrc;
	}
	public void setOrderSrc(int orderSrc) {
		this.orderSrc = orderSrc;
	}
	public double getNeedPay() {
		return needPay;
	}
	public void setNeedPay(double needPay) {
		this.needPay = needPay;
	}
	public int getPayRand() {
		return payRand;
	}
	public void setPayRand(int payRand) {
		this.payRand = payRand;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public int getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(int isRefund) {
		this.isRefund = isRefund;
	}
	public int getIsAppraise() {
		return isAppraise;
	}
	public void setIsAppraise(int isAppraise) {
		this.isAppraise = isAppraise;
	}
	public int getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(int cancelReason) {
		this.cancelReason = cancelReason;
	}
	public int getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(int rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getRejectOtherReason() {
		return rejectOtherReason;
	}
	public void setRejectOtherRa(String rejectOtherReason) {
		this.rejectOtherReason = rejectOtherReason;
	}
	public int getIsClosed() {
		return isClosed;
	}
	public void setIsClosed(int isClosed) {
		this.isClosed = isClosed;
	}
	public String getGoodsSearchKeys() {
		return goodsSearchKeys;
	}
	public void setGoodsSearchKeys(String goodsSearchKeys) {
		this.goodsSearchKeys = goodsSearchKeys;
	}
	public String getOrderunique() {
		return orderunique;
	}
	public void setOrderunique(String orderunique) {
		this.orderunique = orderunique;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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
	public int getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(int settlementId) {
		this.settlementId = settlementId;
	}
	public double getCommissionFee() {
		return commissionFee;
	}
	public void setCommissionFee(double commissionFee) {
		this.commissionFee = commissionFee;
	}
	public double getScoreMoney() {
		return scoreMoney;
	}
	public void setScoreMoney(double scoreMoney) {
		this.scoreMoney = scoreMoney;
	}
	public int getUseScore() {
		return useScore;
	}
	public void setUseScore(int useScore) {
		this.useScore = useScore;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getExtraJson() {
		return extraJson;
	}
	public void setExtraJson(String extraJson) {
		this.extraJson = extraJson;
	}
	public int getOrderCodeTargetID() {
		return orderCodeTargetID;
	}
	public void setOrderCodeTargetID(int orderCodeTargetID) {
		this.orderCodeTargetID = orderCodeTargetID;
	}
	public int getNoticeDeliver() {
		return noticeDeliver;
	}
	public void setNoticeDeliver(int noticeDeliver) {
		this.noticeDeliver = noticeDeliver;
	}
	public String getInvoiceJson() {
		return invoiceJson;
	}
	public void setInvoiceJson(String invoiceJson) {
		this.invoiceJson = invoiceJson;
	}
	public double getLockCashMoney() {
		return lockCashMoney;
	}
	public void setLockCashMoney(double lockCashMoney) {
		this.lockCashMoney = lockCashMoney;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public int getIsBatch() {
		return isBatch;
	}
	public void setIsBatch(int isBatch) {
		this.isBatch = isBatch;
	}
	public int getTotalPayFee() {
		return totalPayFee;
	}
	public void setTotalPayFee(int totalPayFee) {
		this.totalPayFee = totalPayFee;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
		return orderId + "\t" + orderNo + "\t" + shopId + "\t" + userId + "\t" + orderStatus + "\t" + goodsMoney
				+ "\t" + deliverType + "\t" + deliverMoney + "\t" + totalMoney + "\t" + realTotalMoney + "\t"
				+ payType + "\t" + payFrom + "\t" + isPay + "\t" + areaId + "\t" + areaIdPath + "\t" + userName
				+ "\t" + userAddress + "\t" + userPhone + "\t" + orderScore + "\t" + isInvoice + "\t"
				+ invoiceClient + "\t" + orderRemarks + "\t" + orderSrc + "\t" + needPay + "\t" + payRand + "\t"
				+ orderType + "\t" + isRefund + "\t" + isAppraise + "\t" + cancelReason + "\t" + rejectReason
				+ "\t" + rejectOtherReason + "\t" + isClosed + "\t" + goodsSearchKeys + "\t" + orderunique + "\t"
				+ receiveTime + "\t" + deliveryTime + "\t" + tradeNo + "\t" + dataFlag + "\t" + createTime + "\t"
				+ settlementId + "\t" + commissionFee + "\t" + scoreMoney + "\t" + useScore + "\t" + orderCode
				+ "\t" + extraJson + "\t" + orderCodeTargetID + "\t" + noticeDeliver + "\t" + invoiceJson + "\t"
				+ lockCashMoney + "\t" + payTime + "\t" + isBatch + "\t" + totalPayFee + "\t" + id + "\t" + goodsId
				+ "\t" + goodsNum + "\t" + goodsPrice + "\t" + goodsSpecId + "\t" + goodsSpecNames + "\t"
				+ goodsName + "\t" + goodsImg + "\t" + goodsType + "\t" + commissionRate + "\t" + goodsCode + "\t"
				+ promotionJson;
	}
	
}
