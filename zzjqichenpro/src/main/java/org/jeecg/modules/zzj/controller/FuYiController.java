package org.jeecg.modules.zzj.controller;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.modules.zzj.util.Card.SetResultUtil;
import org.jeecg.modules.zzj.util.Http.HttpUtil;
import org.jeecg.modules.zzj.util.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Api(tags = "富驿接口")
@RestController
@RequestMapping("/FuYi")
public class FuYiController {
    @Value("${fuyi.appKey}")
    private String appKey;
    @Value("${fuyi.appSecret}")
    private String appSecret;
    @Value("${fuyi.MobileNo}")
    private String MobileNo;
    @Value("${fuyi.Password}")
    private String Password;
    @Value("${fuyi.HotelNo}")
    private String HotelNo;
    @Value("${fuyiaddress}")
    private String fuyiaddress;

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    /**
     * 获取ticketId
     */
    @RequestMapping(value = "/getTicketId", method = RequestMethod.GET)
    @ApiOperation(value = "获取ticketId", httpMethod = "GET")
    public Result<JSONObject> getTicketId() {
        log.info("getTicketId()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        Map<String,String> map=new HashMap<>();
        map.put("appKey",appKey);
        map.put("appSecret",appSecret);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Ticket/GetTicketID";
        String returnResult= HttpUtil.sendGet(url,param);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }



    /**
     * 会员登录，获取会员信息
     * 该接口暂时手机号 密码 写死在配置文件里了
     * @param mobileNo
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    @ApiOperation(value = "Login", httpMethod = "GET")
    public Result<JSONObject> Login(String mobileNo,String password) throws Exception {
        log.info("Login()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("MobileNo",MobileNo);
        map.put("Password",Password);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Membership/MemberLogin";
        String returnResult= HttpUtil.sendPost(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }












    /**
     * 获取房价代码销售条件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/GetRateCodeRestriction", method = RequestMethod.GET)
    @ApiOperation(value = "获取房价代码销售条件", httpMethod = "GET")
    public Result<JSONObject> LoGetRateCodeRestrictiongin() throws Exception {
        log.info("LoGetRateCodeRestrictiongin()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Restriction/GetRateCodeRestriction";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    /**
     *
     * @param ArrDate
     * @param DepDate
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/GetHotelRestriction", method = RequestMethod.GET)
    @ApiOperation(value = "获取酒店销售条件", httpMethod = "GET")
    public Result<JSONObject> LoGetRateCodeRestrictiongin(String ArrDate,String DepDate) throws Exception {
        log.info("LoGetRateCodeRestrictiongin()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("ArrDate",ArrDate);
        map.put("DepDate",DepDate);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Restriction/GetHotelRestriction";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    /**
     *
     * @param CheckInDate 入住日期
     * @param CheckInTime 入住时间（分钟小时）
     * @param CheckOutDate 离店日期
     * @param RateCode 房价代码
     * @param RoomRate 房价
     * @param RoomTypeNo 房型code
     * @param RoomCount 房间数
     * @param TotalPrice 总房价
     * @param BookerName 订房人姓名
     * @param BookerTel 订房人电话
     * @param BookerEmail 订房人邮箱
     * @param BookerMebNo 订房人会员号
     * @param BookerCardNo 订房人卡号
     * @param Gender 性别
     * @param GuestEmail 客人邮箱
     * @param GuestMebCardNo 客人会员卡号
     * @param GuestName 客人姓名
     * @param GuestTel 客人电话
     * @param GuestAddress 客人地址
     * @param DocumentNo
     * @param DocumentType
     * @param AdultsCount 成人数
     * @param ChildrenCount 儿童数
     * @param IsConfirmBySms
     * @param Remarks 备注
     * @param VoucherIDList 凭证编号清单
     * @param PromotionCode 促销号
     * @param Point
     * @param SalePoint
     * @param OuterConfirmNo
     * @param OTANo
     * @param PaymentType 支付方式
     * @param ResType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/AddReservation", method = RequestMethod.GET)
    @ApiOperation(value = "生成订单", httpMethod = "GET")
    public Result<JSONObject> AddReservation(String CheckInDate,String CheckInTime,String CheckOutDate,String RateCode,String RoomRate,
                                             String RoomTypeNo,String RoomCount,String TotalPrice,String BookerName,String BookerTel,
                                             String BookerEmail,String BookerMebNo,String BookerCardNo,String Gender,String GuestEmail,
                                             String GuestMebCardNo,String GuestName,String GuestTel,String GuestAddress,String DocumentNo,
                                             String DocumentType,String AdultsCount,String ChildrenCount,String IsConfirmBySms,String Remarks,
                                             String VoucherIDList,String PromotionCode,String Point,String SalePoint,String OuterConfirmNo,
                                             String OTANo,String PaymentType,String ResType
                                             ) throws Exception {
        log.info("AddReservation()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("CheckInDate",CheckInDate);
        map.put("CheckInTime",CheckInTime);
        map.put("CheckOutDate",CheckOutDate);
        map.put("RateCode",RateCode);
        map.put("RoomRate",RoomRate);
        map.put("RoomTypeNo",RoomTypeNo);
        map.put("RoomCount",RoomCount);
        map.put("TotalPrice",TotalPrice);
        map.put("BookerName",BookerName);
        map.put("BookerTel",BookerTel);
        map.put("BookerEmail",BookerEmail);
        map.put("BookerMebNo",BookerMebNo);
        map.put("BookerCardNo",BookerCardNo);
        map.put("Gender",Gender);
        map.put("GuestEmail",GuestEmail);
        map.put("GuestMebCardNo",GuestMebCardNo);
        map.put("GuestName",GuestName);
        map.put("GuestTel",GuestTel);
        map.put("GuestAddress",GuestAddress);
        map.put("DocumentNo",DocumentNo);
        map.put("DocumentType",DocumentType);
        map.put("AdultsCount",AdultsCount);
        map.put("ChildrenCount",ChildrenCount);
        map.put("IsConfirmBySms",IsConfirmBySms);
        map.put("Remarks",Remarks);
        map.put("VoucherIDList",VoucherIDList);
        map.put("PromotionCode",PromotionCode);
        map.put("Point",Point);
        map.put("SalePoint",SalePoint);
        map.put("OuterConfirmNo",OuterConfirmNo);
        map.put("OTANo",OTANo);
        map.put("PaymentType",PaymentType);
        map.put("ResType",ResType);
        for (Map.Entry<String,String> entry : map.entrySet()){
            System.out.println("key:"+entry.getKey()+",value:"+entry.getValue());
        }
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/AddReservation";
        String returnResult= HttpUtil.sendPost(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }


    /**
     *
     * @param CheckInDate
     * @param CheckOutDate
     * @param MemberType
     * @param RoomTypeCode
     * @param RateTypeCode
     * @param PromotionCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/GetRoomStatusByHotelNoOnSaleRestriction", method = RequestMethod.GET)
    @ApiOperation(value = "查询房价", httpMethod = "GET")
    public Result<JSONObject> GetRoomStatusByHotelNoOnSaleRestriction(String CheckInDate,String CheckOutDate,String MemberType,
                                                                      String RoomTypeCode,String RateTypeCode,String PromotionCode) throws Exception {
        log.info("GetRoomStatusByHotelNoOnSaleRestriction()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("CheckInDate",CheckInDate);
        map.put("CheckOutDate",CheckOutDate);
        map.put("MemberType",MemberType);
        map.put("RoomTypeCode",RoomTypeCode);
        map.put("RateTypeCode",RateTypeCode);
        map.put("PromotionCode",PromotionCode);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/GetRoomStatusByHotelNoOnSaleRestriction";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    /**
     *
     * @param SellChannel
     * @param SpecialCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/GetRateCodeForHotelByHotelNo", method = RequestMethod.GET)
    @ApiOperation(value = "获取酒店房价代码信息", httpMethod = "GET")
    public Result<JSONObject> GetRateCodeForHotelByHotelNo(String SellChannel,String SpecialCode) throws Exception {
        log.info("GetRateCodeForHotelByHotelNo()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("SellChannel",SellChannel);
        map.put("SpecialCode",SpecialCode);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Restriction/GetRateCodeForHotelByHotelNo";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    /**
     *
     * @param SpecialCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/GetRoomTypesByHotelNo", method = RequestMethod.GET)
    @ApiOperation(value = "获取房型信息请求参数", httpMethod = "GET")
    public Result<JSONObject> GetRoomTypesByHotelNo(String SpecialCode) throws Exception {
        log.info("GetRoomTypesByHotelNo()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("SpecialCode",SpecialCode);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Restriction/GetRoomTypesByHotelNo";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    @RequestMapping(value = "/AddTrans", method = RequestMethod.GET)
    @ApiOperation(value = "入账", httpMethod = "GET")
    public Result<JSONObject> AddTrans(String TransCodeID, String ResID, String Payment, String RefNo, String Remark) throws Exception {
        log.info("AddTrans()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("TransCodeID",TransCodeID);
        map.put("ResID",ResID);
        map.put("Payment",Payment);
        map.put("RefNo",RefNo);
        map.put("Remark",Remark);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/AddTrans";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    /**
     *
     * @param ConfirmNo PMS系统确认号
     * @param ResStates 订单状态
                         * R:预定
                         * I：在住
                         * X：取消
                         * N：noshow
                         * O：离店
                         * (不填则默认R)
     * @param CurrencyNo 通用号码 (证件号码/预定人手机号/入住人手机号/会员卡号都可以)
     * @param GuestsName 客人姓名
     * @param RoomNo 房号
     * @param BeginArrivalBizDate 开始抵店营业日期（与离店日期区间二选一）
     * @param EndArrivalBizDate  结束抵店营业日期（与离店日期区间二选一）
     * @param BeginDepartBizDate  开始抵店营业日期 （与离店日期区间二选一）
     * @param EndDepartBizDate 结束抵店营业日期 （与离店日期区间二选一）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/SSMForQueryReservation", method = RequestMethod.GET)
    @ApiOperation(value = "查询订单", httpMethod = "GET")
    public Result<JSONObject> SSMForQueryReservation(String ConfirmNo, String ResStates, String CurrencyNo, String GuestsName,
                                                     String RoomNo, String BeginArrivalBizDate, String EndArrivalBizDate,
                                                     String BeginDepartBizDate, String EndDepartBizDate) throws Exception {
        log.info("SSMForQueryReservation()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("ConfirmNo",ConfirmNo);
        map.put("ResStates",ResStates);
        map.put("CurrencyNo",CurrencyNo);
        map.put("GuestsName",GuestsName);
        map.put("RoomNo",RoomNo);
        map.put("BeginArrivalBizDate",BeginArrivalBizDate);
        map.put("EndArrivalBizDate",EndArrivalBizDate);
        map.put("BeginDepartBizDate",BeginDepartBizDate);
        map.put("EndDepartBizDate",EndDepartBizDate);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/SSMForQueryReservation";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        String fuyiResult=jsonObj.getString("result");
        String data=jsonObj.getString("data");
        System.out.println("data:"+data);
        System.out.println("dataSize:"+data.length());
        if ("true".equals(fuyiResult) && null!=data && data.length()>2){
            return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
        }else{
            return SetResultUtil.setErrorMsgResult(result,"查无此订单");
        }

    }

    @RequestMapping(value = "/GetQueryAvailableRoom", method = RequestMethod.GET)
    @ApiOperation(value = "可用房号查询", httpMethod = "GET")
    public Result<JSONObject> GetQueryAvailableRoom(String RoomTypeCode, String ArrivalBizDate, String DepartBizDate) throws Exception {
        log.info("GetQueryAvailableRoom()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("RoomTypeCode",RoomTypeCode);
        map.put("ArrivalBizDate",ArrivalBizDate);
        map.put("DepartBizDate",DepartBizDate);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/RoomRate/GetQueryAvailableRoom";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    @RequestMapping(value = "/GetQueryAvailableRoomType", method = RequestMethod.GET)
    @ApiOperation(value = "可用房型查询", httpMethod = "GET")
    public Result<JSONObject> GetQueryAvailableRoomType(String ArrivalBizDate, String DepartBizDate) throws Exception {
        log.info("GetQueryAvailableRoomType()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("ArrivalBizDate",ArrivalBizDate);
        map.put("DepartBizDate",DepartBizDate);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/RoomRate/GetQueryAvailableRoomType";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    @RequestMapping(value = "/GetTransList", method = RequestMethod.GET)
    @ApiOperation(value = "交易项目查询", httpMethod = "GET")
    public Result<JSONObject> GetTransList() throws Exception {
        log.info("GetTransList()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/GetTransList";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    /**
     *
     * @param RoomTypeNo 房型编号
     * @param RoomNo 房间号
     * @param ResID 订单ID
     * @param Gender 性别
     * @param GuestEmail 邮箱
     * @param GuestMebCardNo 会员卡
     * @param GuestName 客人姓名
     * @param GuestAddress 地址
     * @param DocumentNo 证件号码
     * @param DocumentType 证件类型
     * @param AdultsCount 成人数量
     * @param ChildrenCount 儿童数量
     * @param ConfirmNo 外部确认号，用于外部PMS对接
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/EditReservation", method = RequestMethod.GET)
    @ApiOperation(value = "修改订单预定", httpMethod = "GET")
    public Result<JSONObject> EditReservation(String RoomTypeNo,String RoomNo,String ResID,String Gender,String GuestEmail,String GuestMebCardNo,
                                           String GuestName,String GuestAddress,String DocumentNo,String DocumentType,String AdultsCount,
                                           String ChildrenCount,String ConfirmNo,String GuestTel) throws Exception {
        log.info("EditReservation()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("RoomTypeNo",RoomTypeNo);
        map.put("RoomNo",RoomNo);
        map.put("ResID",ResID);
        map.put("Gender",Gender);
        map.put("GuestEmail",GuestEmail);
        map.put("GuestMebCardNo",GuestMebCardNo);
        map.put("GuestName",GuestName);
        map.put("GuestAddress",GuestAddress);
        map.put("DocumentNo",DocumentNo);
        map.put("DocumentType",DocumentType);
        map.put("AdultsCount",AdultsCount);
        map.put("ChildrenCount",ChildrenCount);
        map.put("ConfirmNo",ConfirmNo);
        map.put("GuestTel",GuestTel);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/EditReservation";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    /**
     * 修改抵店/离店日期
     * @param ResID
     * @param overridRatePlan
     * @param CheckInDate
     * @param CheckOutDate
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/EditCheckInOrCheckOutDate", method = RequestMethod.GET)
    @ApiOperation(value = "修改抵店/离店日期", httpMethod = "GET")
    public Result<JSONObject> EditCheckInOrCheckOutDate(String ResID,String overridRatePlan,String CheckInDate,String CheckOutDate) throws Exception {
        log.info("EditReservation()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("ResID",ResID);
        map.put("overridRatePlan",overridRatePlan);
        map.put("CheckInDate",CheckInDate);
        map.put("CheckOutDate",CheckOutDate);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/EditCheckInOrCheckOutDate";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }


    /**
     * 入住
     * @param confirmNo
     * @param roomNo
     * @param documentType
     * @param documentNo
     * @param sourceArea
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/CheckIn", method = RequestMethod.GET)
    @ApiOperation(value = "入住", httpMethod = "GET")
    public Result<JSONObject> CheckIn(String confirmNo,String roomNo,String documentType,String documentNo,String sourceArea) throws Exception {
        log.info("CheckIn()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("confirmNo",confirmNo);
        map.put("roomNo",roomNo);
        map.put("documentType",documentType);
        map.put("documentNo",documentNo);
        map.put("sourceArea",sourceArea);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/CheckIn";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }


    @RequestMapping(value = "/AddResGuest", method = RequestMethod.GET)
    @ApiOperation(value = "添加同住", httpMethod = "GET")
    public Result<JSONObject> AddResGuest(String ResID,String ConfirmNo,String ResGuestID,String GuestName,String EnFamilyName,String EnFirstName,
                                          String Gender,String DocumentType,String DocumentNo,String Birthday,String MebCardNo,String Adrress,
                                          String Flag,String MobilePhone,String CustomerID,String EMail,String Nationality) throws Exception {
        log.info("AddResGuest()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("ResID",ResID);
        map.put("ConfirmNo",ConfirmNo);
        map.put("ResGuestID",ResGuestID);
        map.put("GuestName",GuestName);
        map.put("EnFamilyName",EnFamilyName);
        map.put("EnFirstName",EnFirstName);
        map.put("Gender",Gender);
        map.put("DocumentType",DocumentType);
        map.put("DocumentNo",DocumentNo);
        map.put("Birthday",Birthday);
        map.put("MebCardNo",MebCardNo);
        map.put("Adrress",Adrress);
        map.put("Flag",Flag);// 1 启动 0  禁用
        map.put("MobilePhone",MobilePhone);
        map.put("CustomerID",CustomerID);
        map.put("EMail",EMail);
        map.put("Nationality",Nationality);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/AddResGuest";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    @RequestMapping(value = "/UpdateResGuest", method = RequestMethod.GET)
    @ApiOperation(value = "修改同住", httpMethod = "GET")
    public Result<JSONObject> UpdateResGuest(String ResID,String ConfirmNo,String ResGuestID,String GuestName,String EnFamilyName,String EnFirstName,
                                          String Gender,String DocumentType,String DocumentNo,String Birthday,String MebCardNo,String Adrress,
                                          String Flag,String MobilePhone,String CustomerID,String EMail,String Nationality) throws Exception {
        log.info("UpdateResGuest()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("ResID",ResID);
        map.put("ConfirmNo",ConfirmNo);
        map.put("ResGuestID",ResGuestID);
        map.put("GuestName",GuestName);
        map.put("EnFamilyName",EnFamilyName);
        map.put("EnFirstName",EnFirstName);
        map.put("Gender",Gender);
        map.put("DocumentType",DocumentType);
        map.put("DocumentNo",DocumentNo);
        map.put("Birthday",Birthday);
        map.put("MebCardNo",MebCardNo);
        map.put("Adrress",Adrress);
        map.put("Flag",Flag);
        map.put("MobilePhone",MobilePhone);
        map.put("CustomerID",CustomerID);
        map.put("EMail",EMail);
        map.put("Nationality",Nationality);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/UpdateResGuest";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    /**
     *
     * @param RoomNo 房号
     * @param ConfirmNo 确认号
     * @param RoomTypeCode 房型编号
     * @param CheckOutDate 新的离店日期
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ChangeRoomsToStay", method = RequestMethod.GET)
    @ApiOperation(value = "续住", httpMethod = "GET")
    public Result<JSONObject> ChangeRoomsToStay(String RoomNo,String ConfirmNo,String RoomTypeCode,String CheckOutDate) throws Exception {
        log.info("ChangeRoomsToStay()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("RoomNo",RoomNo);
        map.put("ConfirmNo",ConfirmNo);
        map.put("RoomTypeCode",RoomTypeCode);
        map.put("CheckOutDate",CheckOutDate);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/ChangeRoomsToStay";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }


    /**
     *
     * @param ResId 订单号
     * @param ConfirmNo 确认号
     * @param state 0代表入住 1代表续住  2代表退房
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/CalculateTheAmountToBePaid", method = RequestMethod.GET)
    @ApiOperation(value = "计算需支付金额", httpMethod = "GET")
    public Result<JSONObject> CalculateTheAmountToBePaid(String ResId,String ConfirmNo,String state) throws Exception {
        log.info("CalculateTheAmountToBePaid()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("ResId",ResId);
        map.put("ConfirmNo",ConfirmNo);
        map.put("state",state);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/CalculateTheAmountToBePaid";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }

    @RequestMapping(value = "/CheckOut", method = RequestMethod.GET)
    @ApiOperation(value = "退房不结账", httpMethod = "GET")
    public Result<JSONObject> CheckOut(String ResId,String ConfirmNo) throws Exception {
        log.info("CheckOut()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("ResId",ResId);
        map.put("ConfirmNo",ConfirmNo);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/CheckOut";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }


    /**
     *
     * @param RoomNo 房间号
     * @param ConfirmNo 确定号
     * @param LockState L 锁房 N 表示解锁
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/LockRoom", method = RequestMethod.GET)
    @ApiOperation(value = "LockRoom", httpMethod = "GET")
    public Result<JSONObject> LockRoom(String RoomNo,String ConfirmNo,String LockState) throws Exception {
        log.info("LockRoom()方法");
        Result<JSONObject> result = new Result<JSONObject>();
        String nowDate=new Date().getTime()+"";
        Map<String,String> map=getMap(nowDate);
        map.put("HotelNo",HotelNo);
        map.put("RoomNo",RoomNo);
        map.put("ConfirmNo",ConfirmNo);
        map.put("LockState",LockState);
        String param= HttpUtil.getMapToString(map);
        System.out.println("param:"+param);
        String url=fuyiaddress+"/api/Reservation/LockRoom";
        String returnResult= HttpUtil.sendGet(url,param,map,nowDate);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        return SetResultUtil.setSuccessResult(result,"成功",jsonObj);
    }






    //获取ticketId
    public String getTicketIdString() {
        log.info("getTicketId()方法");
        Map<String,String> map=new HashMap<>();
        map.put("appKey",appKey);
        map.put("appSecret",appSecret);
        String param= HttpUtil.getMapToString(map);
        System.out.println(param);
        String url=fuyiaddress+"/api/Ticket/GetTicketID";
        String returnResult= HttpUtil.sendGet(url,param);
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        System.out.println(jsonObj);
        JSONObject data=jsonObj.getJSONObject("data");
        String ticketId=data.getString("TicketID");
        return ticketId;
    }

    //每个请求中都包含该 map集合中的参数
    public Map<String,String> getMap(String nowDate) throws Exception {
        Map<String,String> map=new HashMap<>();
        String ticketId=getTicketIdString();
        String sign= SignUtils.CreateSign(ticketId,nowDate);
        String ip= InetAddress.getLocalHost().getHostAddress();
        map.put("TicketID",ticketId);
        map.put("Sign",sign);
        map.put("InvokeIp",ip);
        return map;
    }
}
