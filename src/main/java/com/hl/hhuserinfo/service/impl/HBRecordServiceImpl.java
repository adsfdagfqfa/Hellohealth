package com.hl.hhuserinfo.service.impl;



import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.hl.hhuserinfo.entity.ResultData;
import com.hl.hhuserinfo.entity.dto.CreateOrderParam;
import com.hl.hhuserinfo.entity.dto.RecordInfo;
import com.hl.hhuserinfo.entity.po.HBRecord;
import com.hl.hhuserinfo.entity.po.Notification;
import com.hl.hhuserinfo.entity.po.UserInfo;
import com.hl.hhuserinfo.repository.HBRecordRepository;
import com.hl.hhuserinfo.repository.NotificationRepository;
import com.hl.hhuserinfo.repository.UserInfoRepository;
import com.hl.hhuserinfo.service.HBRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;



@Service
public class HBRecordServiceImpl implements HBRecordService {
    private HBRecordRepository hbRecordRepository;
    private UserInfoRepository userInfoRepository;
    private NotificationRepository notificationRepository;
    @Autowired
    HBRecordServiceImpl(HBRecordRepository hbRecordRepository,
                        UserInfoRepository userInfoRepository,
                        NotificationRepository notificationRepository){
        this.hbRecordRepository=hbRecordRepository;
        this.userInfoRepository=userInfoRepository;
        this.notificationRepository=notificationRepository;
    }

    @Override
    public ResultData getRecord(HttpSession session) {
        HashMap<String,Object> data=new HashMap<>();
        try{
            //Integer user_id = (Integer) session.getAttribute("UserID");
            Integer user_id=121;
            if (user_id != null) {
                System.out.println(user_id);
                List<RecordInfo> recordInfo = new ArrayList<>();
                List<HBRecord> lists = hbRecordRepository.findByUSER_ID(user_id);
                if(lists.isEmpty()){
                    return ResultData.fail("查找数据库为空");
                }
                Optional<UserInfo> userInfoOrNULL = userInfoRepository.findById(user_id);
                if(userInfoOrNULL.isEmpty())
                    return ResultData.fail("查找数据库为空");
                UserInfo user = userInfoOrNULL.get();
                //message.data["coinNum"] = user.HbNumber;
                data.put("coinNum",user.getHB_number());
                for (HBRecord item:lists)
                {
                    RecordInfo r = new RecordInfo();
                    LocalDateTime dd = item.getCHANGE_TIME();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    r.setTime(dd.format(formatter));
                    r.setNum(item.getCHANGE_NUM());
                    r.setReason( item.getCHANGE_REASON());
                    //向序列中加入元素
                    recordInfo.add(r);
                }
                data.put("coinRecordList",recordInfo.toArray());
            }
            else{
                return ResultData.fail("未登录");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return ResultData.ok(data);
    }

    @Override
    public ResultData createOrder(HttpServletRequest request) {
        try {
            System.out.println("开始调用");
            String out_trade_num =request.getParameter("out_trade_no");//获得订单号
            Integer user_id = Integer.parseInt(out_trade_num.split("U")[1]); // 从订单号中获得用户id
           // AlipayTradeQueryResponse alipayTradeQueryResponse = Factory.Payment.Common().Query(out_trade_num);
            AlipayTradeQueryResponse alipayTradeQueryResponse = Factory.Payment.Common().query(out_trade_num);
            System.out.println("支付状态为："+alipayTradeQueryResponse.tradeStatus);
            if (alipayTradeQueryResponse.tradeStatus.trim().equals( "TRADE_SUCCESS"))//如果支付成功
            {
                System.out.println("支付金额为：" + alipayTradeQueryResponse.totalAmount);
                Integer amount = Integer.parseInt(alipayTradeQueryResponse.totalAmount); // 将金额转换为decimal类型
                Integer HBs = (Integer) (amount * 10); // 将amount乘10，并转换为整数类型
                if (alipayTradeQueryResponse.tradeStatus.equals("TRADE_SUCCESS"))
                {

                    //修改HB记录表
                    HBRecord record = new HBRecord();
                    record.setUSER_ID(user_id);
                    record.setCHANGE_NUM(HBs);
                    record.setCHANGE_TIME(LocalDateTime.now());
                    record.setCHANGE_REASON( "充值" + HBs + "枚杏仁币");
                    hbRecordRepository.save(record);
                    //用户杏仁币持有数
                    //User user = _context.Users.Single(b => b.UserId == user_id);
                    Optional<UserInfo> userInfoOrNULL = userInfoRepository.findById(user_id);
                    if(userInfoOrNULL.isEmpty())
                        return ResultData.fail("查找数据库为空,未找到该用户");
                    UserInfo user = userInfoOrNULL.get();
                    user.setHB_number(user.getHB_number()+HBs);
                    //发送通知
                    Notification notification = new Notification();
                    notification.setUserId(user.getUserId());
                    notification.setText("恭喜您已经充值成功！HelloHealth感谢您对我们团队作品的肯定！您可以进入杏仁币界面查看详情。");
                    notification.setUrl("coinDetail");//转到杏仁币界面
                    notificationRepository.save(notification);

                    System.out.println("数据库变动结束");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();

        }
        return ResultData.ok();
    }

    @Override
    public ResultData checkOrder(CreateOrderParam front_end_data, HttpServletRequest request) {
        HashMap<String,Object> data=new HashMap<>();
        try
        {
            //Integer user_id = (Integer) request.getSession().getAttribute("UserID");
            Integer user_id = 3;
            if (user_id == null)
            {
                System.out.println("user ID为空！");

                return ResultData.fail("ID为空");
            }
            String num = String.valueOf(front_end_data.getNum());
            String name = "test_name";
            /*String order_num = LocalDateTime.now().Replace('/', 'T') + DateTime.Now.ToShortTimeString().Replace(':', 'T') + DateTime.Now.Second.ToString() + "U" + user_id.ToString();
            order_num = order_num.Replace(' ', 'T');*/
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String datePart = "T"+now.format(formatter);
            String order_num = datePart +  "U" + user_id;
            System.out.println("订单号为"+ order_num);
            AlipayTradePagePayResponse response = Factory.Payment.Page()
                    .asyncNotify("http://124.222.75.198:7217/api/HB/check")//要异步调用的接口。必须是公网的。
                    .pay(name, order_num, num, "https://21tongjisse.top/coinDetail");//结束后，返回到这里
            //  处理响应或异常
            if (ResponseChecker.success(response))//如果成功
            {

                data.put("pay_page",response.body);
            }
            else
            {
                System.out.println(response.body);
                return ResultData.fail("支付失败");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResultData.ok(data);


    }
}
