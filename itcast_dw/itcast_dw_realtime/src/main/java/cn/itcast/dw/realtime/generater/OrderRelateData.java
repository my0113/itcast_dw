package cn.itcast.dw.realtime.generater;


import cn.itcast.dw.realtime.bean.OrderBean;
import cn.itcast.dw.realtime.bean.OrderGoodsBean;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class OrderRelateData {

    //准备order数据
    static Random random = new Random();
    //orderId	自增ID
    static AtomicInteger orderId = new AtomicInteger(0);
    //	orderNo	订单号
    static ArrayList<String> orderNoList = new ArrayList<>();
    //userId	用户ID
    static ArrayList<Integer> userIdList = new ArrayList<Integer>();

    static {
        for (int i = 0; i < 100; i++) {
            orderNoList.add((int)(Math.random()*9+1)*1000000 + "");
            if (i % 2 == 0) {
                userIdList.add(i);
            }
        }
    }

    //门店ID shopId
    public static int[] shopIdArr = new int[]{0, 1, 2, 3, 4, 5, 6};

    //订单状态  -3:用户拒收 -2:未付款的订单 -1：用户取消 0:待发货 1:配送中 2:用户确认收货
    public static int[] orderStatusArr = new int[]{-3, -2, -1, 0, 1, 2};

    //deliverType	收货方式	0:送货上门 1:自提
    public static int[] deliverTypeArr = new int[]{0, 1};

    //支付方式 0:货到付款 1:在线支付
    public static int[] paryTypeArr = new int[]{0, 1};
    //支付来源 1:支付宝，2：微信
    public static int[] payFromArr = new int[]{1, 2};
    //是否支付 0:未支付 1:已支付
    public static int[] isPayArr = new int[]{0, 1};
    //userName	收货人名称
    public static String[] userNameArr = new String[]{"zhangsan", "lisi", "wangwu", "zhaoliu"};
    //userAddress	收件人地址
    public static String[] userAddressArr = new String[]{"beijing", "shanghai", "tianjin", "shanghai"};
    //userPhone	收件人手机
    public static String[] userPhoneArr = new String[]{"138", "157", "189", "150"};
    //orderScore	所得积分
    public static int[] orderScoreArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    //isInvoice	是否需要发票	1:需要 0:不需要
    public static int[] isInvoiceArr = new int[]{1};
    //orderRemarks	订单备注
    public static String[] orderRemarksArr = new String[]{"周末不送货", "加急，加急"};
    //订单来源  0:商城 1:微信 2:手机版 3:安卓App 4:苹果App
    public static int[] orderSrcArr = new int[]{0, 1, 2, 3, 4};
    //needPay	需缴费用
    public static double[] needPayArr = new double[]{12.5, 10.5};
    //isRefund	是否退款	0:否 1：是
    public static int[] isRefundArr = new int[]{0, 1};
    //isAppraise	是否点评	0:未点评 1:已点评
    public static int[] isAppraiseArr = new int[]{0, 1};
    //cancelReason	取消原因ID		int	11	0
    public static int[] cancelReasonArr = new int[]{0, 1, 2, 3, 4, 5, 6};
    //rejectReason	拒收原因ID
    public static int[] rejectReasonArr = new int[]{0, 1, 2, 3, 4, 5};
    //rejectOtherReason	拒收原因
    public static String[] rejectOtherReasonArr = new String[]{"不喜欢", "损坏"};
    //isClosed	是否订单已完结	0：未完结 1:已完结
    public static int[] isClosedArr = new int[]{0, 1};
    //goodsSearchKeys 商品对应搜索关键字
    public static String[] goodsSearchKeysArr = new String[]{"节日礼品", "养生", "美白", "防晒"};
    //settlementId	是否结算，大于0的话则是结算ID
    public static int[] settlementIdArr = new int[]{0, 1, 2, 3, 4, 5};
    //提醒发货 0:未提醒，1：已提醒
    public static int[] noticeDeliverdArr = new int[]{0, 1};
    //expressId	快递公司ID
    public static int[] expressIdArr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    //areaId	最后一级区域Id,县级id,要根据areas表中查询，
    //区域id路径 areaIdPath,需要查询出省id,市id,县id;然后按照下划线拼接
    public static int[] provinceIds = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    public static int[] cityIds = new int[]{36, 37, 38, 39, 40, 41, 42, 80, 81, 82, 83};
    public static int[] countyIds = new int[]{36, 37, 38, 39, 40, 41, 42, 80, 81, 82, 83};

    static ArrayList<String> areaIdPathList = new ArrayList<String>();

    static {
        for (int i = 0; i < 100; i++) {
            areaIdPathList.add(provinceIds[random.nextInt(provinceIds.length)] + "_" +
                    cityIds[random.nextInt(cityIds.length)] + "_" + countyIds[random.nextInt(countyIds.length)]);
        }
    }

    //标识
    public static int[] dataFlagArr = new int[]{-1, 1};
    //orderIds 订单id集合
    final static ArrayList<String> orderIds = new ArrayList<>();

    final ArrayList<OrderBean> orders = new ArrayList<>();

    //order数据生成方法
    public ArrayList<OrderBean> generaterOrder() throws ParseException {

        FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < 10; i++) {
            final OrderBean order = new OrderBean();
            //订单id
            order.setOrderId(orderId.getAndIncrement());
            //订单编号
            order.setOrderNo(orderNoList.get(random.nextInt(orderNoList.size())));
            //门店ID
            order.setShopId(shopIdArr[random.nextInt(shopIdArr.length)]);
            //用户ID
            order.setUserId(userIdList.get(random.nextInt(userIdList.size())));
            //商品总金额
            order.setGoodsMoney(random.nextDouble() * 10 + 500);
            //收货方式	0:送货上门 1:自提
            order.setDeliverType(deliverTypeArr[random.nextInt(deliverTypeArr.length)]);
            if (order.getDeliverType() == 0) {
                //运费
                order.setDeliverMoney(order.getGoodsMoney() * 0.02);
            }
            //订单总金额
            order.setTotalMoney(order.getGoodsMoney() + order.getDeliverMoney());
            //实际订单金额
            final double totalMoney = order.getTotalMoney();
            final double[] discounts = new double[]{0.98, 0.88, 1};
            order.setRealTotalMoney(totalMoney * discounts[random.nextInt(discounts.length)]);
            //支付来源
            order.setPayFrom(payFromArr[random.nextInt(payFromArr.length)] + "");
            //订单状态
            order.setOrderStatus(orderStatusArr[random.nextInt(orderStatusArr.length)]);
            //支付方式 货到付款，在线支付
            if (i % 2 == 0) {
                order.setPayType(1);
            } else {
                order.setPayType(0);
            }
            //获取当前支付状态与支付方式
            final int orderStatus = order.getOrderStatus();
            final int payType = order.getPayType();
            //在线支付：
            //如果是未支付，该订单就是未支付；
            if (payType == 1 && orderStatus == -2) {
                //未支付
                order.setIsPay(0);
            } else {
                //已支付
                order.setIsPay(1);
            }
            //货到付款
            //只有用户确认收货时才是支付状态；
            if (payType == 0 && orderStatus == 2) {
                //已支付
                order.setIsPay(1);
            } else {
                //未支付
                order.setIsPay(0);
            }
            //收货人名称
            order.setUserName(userNameArr[random.nextInt(userNameArr.length)]);
            //收货人地址
            order.setUserAddress(userAddressArr[random.nextInt(userAddressArr.length)]);
            //收货人电话
            order.setUserPhone(userPhoneArr[random.nextInt(userPhoneArr.length)]);
            //订单所得积分
            order.setOrderScore(orderScoreArr[random.nextInt(orderScoreArr.length)]);
            //是否需要发票
            order.setIsInvoice(0);
            //订单备注
            order.setOrderRemarks(orderRemarksArr[random.nextInt(orderRemarksArr.length)]);
            //订单来源
            order.setOrderSrc(orderSrcArr[random.nextInt(orderSrcArr.length)]);
            //需缴费用
            order.setNeedPay(needPayArr[random.nextInt(needPayArr.length)]);
            //是否退款，需要判断当前订单状态只有是确认收货状态时或者在线支付后订单状态是取消状态才可以退款
            if(orderStatus==-1&& order.getIsPay()==1){
                order.setIsRefund(1);
            }
             else if (orderStatus == 2) {
                //	isRefund	是否退款	0:否 1：是
                order.setIsRefund(isRefundArr[random.nextInt(isRefundArr.length)]);
                //isAppraise	是否点评	0:未点评 1:已点评
                order.setIsAppraise(isAppraiseArr[random.nextInt(isAppraiseArr.length)]);
            } else {
                order.setIsRefund(0);
                order.setIsAppraise(0);
            }
            //只有已点评的订单可以是完结状态
            if (order.getIsAppraise() == 1 && order.getIsRefund() == 0) {
                //isClosed	是否订单已完结	0：未完结 1:已完结
                order.setIsClosed(1);
            } else {
                order.setIsClosed(0);
            }

            //goodsSearchKeys	物品搜索关键词
            order.setGoodsSearchKeys(goodsSearchKeysArr[random.nextInt(goodsSearchKeysArr.length)]);
            //        orderunique	订单流水号
            order.setOrderunique(UUID.randomUUID().toString());
            //        settlementId	是否结算，大于0的话则是结算ID
            order.setSettlementId(settlementIdArr[random.nextInt(settlementIdArr.length)]);
            //createTime	下单时间
            order.setCreateTime(fastDateFormat.format(new Date()));

            //  deliveryTime	发货时间，下单时间+1天
            final long dateTime = fastDateFormat.parse(order.getCreateTime()).getTime() + (3600 * 1000 * 32);
            final String deliverTime = fastDateFormat.format(dateTime);
            order.setDeliveryTime(deliverTime);

//        receiveTime	收货时间 发货时间基础上+3天
            final long receiveDateTime = fastDateFormat.parse(order.getDeliveryTime()).getTime() + (3600 * 1000 * 24 * 3);
            final String receiveTime = fastDateFormat.format(receiveDateTime);
            order.setReceiveTime(receiveTime);

            //tradeNo	在线支付交易流水
            order.setTradeNo(UUID.randomUUID().toString());
//        dataFlag	订单有效标志	-1：删除 1:有效
            order.setDataFlag(1);
            //commissionFee	订单应收佣金
            order.setCommissionFee(order.getRealTotalMoney() * 0.01);
//        areaId	最后一级区域Id
            order.setAreaId(countyIds[random.nextInt(countyIds.length)]);
            //区域idpath
            order.setAreaIdPath(areaIdPathList.get(random.nextInt(areaIdPathList.size())));

            System.out.println(order);
            orderIds.add(order.getOrderId() + "");
            orders.add(order);
        }
        System.out.println(orderIds);
        return orders;
    }

    //商品表中goodsId 1-127
    //订单id,orderIds
    //商品id,goodsId:1-127
    //商品数量 goodsNum
    public static int[] goodsNumArr = new int[]{5, 10, 15, 20};
    //商品金额
    public static double[] goodsPriceArr = new double[]{5.5, 15.8, 20.9, 11.6};
    //商品规格id
    public static int[] goodsSpecIdArr = new int[]{11, 22, 33, 44};
    //商品规格值列表
    public static String[] goodsSpecNamesArr = new String[]{"aaa", "bbbb", "cccc", "dddd"};
    //商品名称
    public static String[] goodsNameArr = new String[]{"四川果冻橙6个约180g/个",
            "鲜丰水果 秭归脐橙 中华红橙 9斤家庭装 单果130g—220g 4500g", "红颜奶油草莓 约重500g/15-20颗 新鲜水果",
            "智利进口车厘子J级 1磅装 果径约26-28mm 新鲜水果", "花果山 云南甜玉米 水果玉米 约2.5kg 6-9根 烧烤食材 新鲜蔬菜",
            "花果山 农家新鲜大蒜 2500g装 蒜头"};
    //商品图
    public static String[] goodsImgArr = new String[]{"a.img", "b.img", "c.img", "d.img"};
    //商品佣金率
    public static double[] commissionRateArr = new double[]{0.01, 0.02, 0.015, 0.03};

    //订单商品表
    final ArrayList<OrderGoodsBean> orderGoods = new ArrayList<>();

    //生成订单商品表数据
    public ArrayList<OrderGoodsBean> generaterOrderGood() {
        for (int i = 0; i < orderIds.size(); i++) {
            final OrderGoodsBean good = new OrderGoodsBean();

            final int num = random.nextInt(3) + 1;
            for(int j=0;j<num;j++){
            //获取订单id,orderids
            final String orderId = orderIds.get(i);
            good.setOrderId(Integer.parseInt(orderId));
            //获取商品id
            int goodsId = OrderRelateData.random.nextInt(127) + 1;
            good.setGoodsId(goodsId);
            //商品数量
            good.setGoodsNum(goodsNumArr[OrderRelateData.random.nextInt(goodsNumArr.length)]);
            //商品金额
            good.setGoodsPrice(goodsPriceArr[OrderRelateData.random.nextInt(goodsPriceArr.length)]);
            //商品规格id
            good.setGoodsSpecId(goodsSpecIdArr[OrderRelateData.random.nextInt(goodsSpecIdArr.length)]);
            //商品规格值列表
            good.setGoodsSpecNames(goodsSpecNamesArr[OrderRelateData.random.nextInt(goodsSpecNamesArr.length)]);
            //商品名称
            good.setGoodsName(goodsNameArr[OrderRelateData.random.nextInt(goodsNameArr.length)]);
            //商品图
            good.setGoodsImg(goodsImgArr[OrderRelateData.random.nextInt(goodsImgArr.length)]);
            //商品佣金比率
            good.setCommissionRate(commissionRateArr[OrderRelateData.random.nextInt(commissionRateArr.length)]);
            System.out.println(good);
            orderGoods.add(good);
            }
        }
        return orderGoods;
    }


    //准备订单退款数据

    //long id; //自增id

    //long orderId;//订单id
    //long goodsId;//商品id
    //long refundTo;//接收退款的用户
    //订单表中id信息
    //int refundReson;//退款原因ID
    public static int[] refundReasonArr = new int[]{1, 2, 3, 4, 5, 6};
    //String refundOtherReson;//退款原因
    public static String[] refundOtherReasonArr = new String[]{"商品与页面描述不符", "七天无理由退", "卖家发错货",
            "发票问题", "质量问题", "其他"};
    //double backMoney;//退款金额,订单商品表中商品金额

    //String refundTradeNo;//退款流水号，随机生成

    //String refundRemark;//退款备注
    //String refundTime;//退款时间，用户申请退款时间的3天
    //String shopRejectReason;//店铺拒绝退款原因
    public static String[] rshopRejectReasonArr = new String[]{"坚决不退", "生鲜货物不支持退款", "按照规定不允许退"};
    //int refundStatus;//退款状态  0：退款中，1：已退款
    public static int[] refundStatusArr = new int[]{0, 1};
    //String createTime;//用户申请退款时间  当前时间


    //生成订单评价表：order_appraise
    //订单评价表准备数据
    //long		id;
    //long		shopId;订单商品表表中有商品id-->order.shopid

    //long		orderId; //订单表中有订单id-->order.orderid

    //long		goodsId; //订单商品表中有商品id->goodsId

    // int		goodsSpecId;//订单商品表中有商品规格id->goodsSpecId
    //long		userId;//订单表中有用户id
    // int		goodsScore;
    public static int[] goodsScoreArr = new int[]{1, 2, 3, 4, 5};
    //int			serviceScore;
    public static int[] serviceScoreArr = new int[]{1, 2, 3, 4, 5};
    // int		timeScore;
    public static int[] timeScoreArr = new int[]{1, 2, 3, 4, 5};
    //String 		content;
    public static String[] contentArr = new String[]{"不错不错", "非常好"};
    // String 	shopReply;
    public static String[] shopReplyArr = new String[]{"感谢购买", "祝您购物愉快"};
    //String 		images; 可空

    //int			isShow; 是否显示该条评论
    // int		dataFlag; 有效标识
    public static int[] isShowArr = new int[]{0, 1};
    // String		createTime;当前时间

    // String		replyTime;回复时间--大于createTime 可空

}
