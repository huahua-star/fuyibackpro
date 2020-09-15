package org.jeecg.modules.zzj.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.MD5Util;
import org.jeecg.modules.zzj.entity.*;
import org.jeecg.modules.zzj.service.*;
import org.jeecg.modules.zzj.service.impl.TblTxnpServiceImpl;
import org.jeecg.modules.zzj.util.Card.SetResultUtil;
import org.jeecg.modules.zzj.util.Http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Slf4j
@Api(tags = "后台")
@RestController
@RequestMapping("/zzj/back")
public class BackController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ITblTxnpService tblTxnpService;

    @Autowired
    private UserService userService;

    @Autowired
    private OperationRecordService operationRecordService;

    @Autowired
    private InvoiceService invoiceService;

    @ApiOperation(value = "查询发票配置参数")
    @RequestMapping(value = "/queryInvoice", method = RequestMethod.GET)
    public Result queryInvoice(){
        Result<Invoice> result = new Result<>();
        Invoice invoice=invoiceService.getById("fuyi");
        return  SetResultUtil.setSuccessResult(result,"成功查询",invoice);
    }
    @ApiOperation(value = "设置发票配置参数")
    @PostMapping(value = "/setInvoice")
    public Result setInvoice(@RequestBody Invoice invoice){
        Result<Invoice> result = new Result<>();
        invoiceService.updateById(invoice);
        return  SetResultUtil.setSuccessResult(result,"设置成功",invoice);
    }


    @ApiOperation(value = "查询订单")
    @RequestMapping(value = "/queryReservation", method = RequestMethod.GET)
    public Result<IPage<Reservation>> queryReservation(String  ConfirmNo,String beginTime,String endTime,@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                       HttpServletRequest req) {
        Result<IPage<Reservation>> result = new Result<IPage<Reservation>>();
        QueryWrapper<Reservation> queryWrapper =null;
        if ( null==ConfirmNo ||"".equals(ConfirmNo)){
            queryWrapper=new QueryWrapper<Reservation>().between("create_time",beginTime,endTime)
                    .orderByDesc("create_time");
        }else{
            queryWrapper=new QueryWrapper<Reservation>().eq("Confirm_no",ConfirmNo);
        }
        Page<Reservation> page = new Page<Reservation>(pageNo, pageSize);
        IPage<Reservation> pageList = reservationService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @ApiOperation(value = "修改订单")
    @RequestMapping(value = "/updateReservation", method = RequestMethod.GET)
    public Result<?> updateReservation(String ConfirmNo,String state,String gonganstate) {
        log.info("updateReservation()方法");
        if (StringUtils.isEmpty(ConfirmNo)){
            return Result.error("缺少参数");
        }
        Reservation reservation=reservationService.getOne(new QueryWrapper<Reservation>().eq("Confirm_no",ConfirmNo));
        if (gonganstate!=null&&!"".equals(gonganstate)){
            reservation.setGonganstate(gonganstate);
        }
        if (state!=null&&!"".equals(state)){
            reservation.setState(state);
        }
        reservationService.updateById(reservation);
        return Result.ok("修改成功");
    }


    @ApiOperation(value = "查询流水")
    @RequestMapping(value = "/queryTblTxnp", method = RequestMethod.GET)
    public Result<IPage<TblTxnp>> queryTblTxnp(String  ConfirmNo,String beginTime,String endTime, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                               @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                               HttpServletRequest req) {
        Result<IPage<TblTxnp>> result = new Result<IPage<TblTxnp>>();
        QueryWrapper<TblTxnp> queryWrapper =null;
        if ( null==ConfirmNo ||"".equals(ConfirmNo)){
            queryWrapper=new QueryWrapper<TblTxnp>().between("create_time",beginTime,endTime)
                    .orderByDesc("create_time");
        }else{
            queryWrapper=new QueryWrapper<TblTxnp>().eq("pre_orderid",ConfirmNo);
        }
        Page<TblTxnp> page = new Page<TblTxnp>(pageNo, pageSize);
        IPage<TblTxnp> pageList = tblTxnpService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result<Object> login(String userName,String passWord) {
        String md5= MD5Util.MD5Encode(passWord,null);
        User user=userService.getOne(new QueryWrapper<User>().eq("username",userName));
        if (null!=user && user.getPassword().equals(md5)){
            return  Result.ok("登录成功");
        }else{
            return  Result.error("登录失败，密码不正确或该账号不存在");
        }
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public Result<Object> updateUser(String userName,String newPassWord) {
        System.out.println("userName:"+userName);
        System.out.println("newPassWord:"+newPassWord);
        String md5= MD5Util.MD5Encode(newPassWord,null);
        User user=userService.getOne(new QueryWrapper<User>().eq("username",userName));
        if (null!=user){
            user.setPassword(md5);
            boolean flag=userService.updateById(user);
            if (flag){
                return  Result.ok("修改密码成功");
            }else{
                return  Result.error("修改密码失败");
            }
        }else{
            return  Result.error("修改密码失败");
        }
    }

    @ApiOperation(value = "新增用户")
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public Result<Object> addUser(String userName,String passWord) {
        System.out.println("userName:"+userName);
        System.out.println("newPassWord:"+passWord);
        String md5= MD5Util.MD5Encode(passWord,null);
        User user=new User();
        user.setUsername(userName);
        user.setPassword(md5);
        boolean flag=userService.save(user);
        if (flag){
            return  Result.ok("新增用户成功");
        }else{
            return  Result.error("新增用户失败");
        }
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public Result<Object> deleteUser(String id) {
        boolean flag=userService.removeById(id);
        if (flag){
            return  Result.ok("删除用户成功");
        }else {
            return  Result.error("删除用户失败");
        }
    }

    @ApiOperation(value = "分页查询用户")
    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    public Result<IPage<User>> queryUser(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                         @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                         HttpServletRequest req) {
        Result<IPage<User>> result = new Result<IPage<User>>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        Page<User> page = new Page<User>(pageNo, pageSize);
        IPage<User> pageList = userService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @ApiOperation(value = "分页查询订单操作记录")
    @RequestMapping(value = "/queryReservationRecord", method = RequestMethod.GET)
    public Result<IPage<OperationRecord>> queryReservationRecord(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                                 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                                 HttpServletRequest req,String resno,String beginTime,String endTime) {
        Result<IPage<OperationRecord>> result = new Result<IPage<OperationRecord>>();
        QueryWrapper<OperationRecord> queryWrapper =null;
        if ( null==resno ||"".equals(resno)){
            queryWrapper=new QueryWrapper<OperationRecord>().between("create_time",beginTime,endTime)
                    .orderByDesc("create_time");
        }else{
            queryWrapper=new QueryWrapper<OperationRecord>().eq("resno",resno);
        }
        Page<OperationRecord> page = new Page<OperationRecord>(pageNo, pageSize);
        IPage<OperationRecord> pageList = operationRecordService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }




}
