---
title: HH微服务版 v1.0.0
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.17"

---

# HH微服务版

> v1.0.0

Base URLs:

# 个人信息板块

## POST 获取用户个人信息

POST /api/UserInfo/Details

增加了“isLocked"
获取更加详细的个人信息，给个人信息界面使用。

要获取个人信息的用户id，为0代表获取当前登录用户的信息。

注意：一些敏感的个人信息应只有用户本人才能看到（即user_id=0时），请留意。

> Body 请求参数

```json
{
  "user_id": 28
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» user_id|body|integer| 是 |要获取个人信息的用户id，为-1代表获取当前登录用户的信息|

> 返回示例

> 成功

```json
{
  "errorCode": "200",
  "data": {
    "certification": {
      "date": "2009-11-26",
      "professionTitle": "相类东价满保"
    },
    "userInfo": {
      "userID": 35,
      "userName": "魏霞",
      "gender": "女",
      "birthday": "1981-12-16",
      "telephone": "18112797744",
      "email": "s.dronwr@qq.com",
      "description": "于三及于即积都严教认放大基入周。复出物争白分际展民切用习据水成。些拉几集克场受备济资红身业矿。老所支置量力用心快器不毛越属美道。展每么用然细史照少动那于油向物律半。三江却化省建较低电级还发响公。几话通联器些体选里最红百济与专。",
      "numOfCoin": 52,
      "isCertified": true,
      "avatarUrl": "/src/assets/10.png"
    },
    "isLogin": false,
    "followList": [
      {
        "user_id": 8,
        "user_name": "谭磊",
        "user_group": "cillum",
        "avatar_url": "http://dummyimage.com/100x100",
        "verified": true
      },
      {
        "user_id": 77,
        "user_name": "文明",
        "user_group": "in",
        "avatar_url": "http://dummyimage.com/100x100",
        "verified": false
      },
      {
        "user_id": 49,
        "user_name": "吴洋",
        "user_group": "laborum veniam",
        "avatar_url": "http://dummyimage.com/100x100",
        "verified": false
      },
      {
        "user_id": 88,
        "user_name": "韩洋",
        "user_group": "dolor quis nulla",
        "avatar_url": "http://dummyimage.com/100x100",
        "verified": true
      },
      {
        "user_id": 20,
        "user_name": "韩涛",
        "user_group": "Excepteur sit eiusmod aliquip",
        "avatar_url": "http://dummyimage.com/100x100",
        "verified": false
      }
    ]
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|
|» data|object|true|none||none|
|»» certification|object|true|none|存储用户的医师认证信息|none|
|»»» date|string|true|none|认证日期|none|
|»»» professionTitle|string|true|none|职称|none|
|»»» department|string|true|none|部门|none|
|»»» hospital_rank|string|true|none|医院等级|none|
|»» userInfo|object|true|none|存储用户的基本信息|none|
|»»» userID|integer|true|none|ID|none|
|»»» isCertified|boolean|true|none|用户是否进行了认证|none|
|»»» numOfCoin|integer|true|none|用户所拥有的杏仁币数目|none|
|»»» userName|string|true|none|用户名|none|
|»»» gender|string|true|none|性别|none|
|»»» birthday|string|true|none|生日|none|
|»»» telephone|string|true|none|电话|none|
|»»» email|string|true|none|邮箱|none|
|»»» description|string|true|none|个人简介|none|
|»»» avatarUrl|string|true|none|用户头像的URL|none|
|»» isFollowed|boolean|true|none||body里的user_id是被关注的|
|»» followList|[object]|true|none||none|
|»»» user_id|integer|true|none||none|
|»»» user_name|string|true|none||none|
|»»» user_group|string|true|none||none|
|»»» avatar_url|string|true|none||none|
|»»» verified|boolean|true|none||none|
|»» fansList|[object]|true|none||粉丝列表|
|»»» user_id|integer|true|none||none|
|»»» user_name|string|true|none||none|
|»»» user_group|string|true|none||none|
|»»» avatar_url|string|true|none||none|
|»»» verified|boolean|true|none||none|

## GET 获取硬币变动记录

GET /api/HB/record

获取近七天的硬币变动记录
现修改为不用获取用户id，而是从session中获取用户id。已成功
现问题在于，如果有多个用户，是否能够分辨（理论上能）
以及接口名字要修改

> 返回示例

> 成功

```json
{
  "data": {
    "coinRecordList": [
      {
        "Reason": "今油认严看为而共它得精经一则。",
        "Time": "1995-10-22 12:02:15",
        "Num": -513
      },
      {
        "Reason": "安该立细七办气一正口议果以的。",
        "Time": "2023-05-29 00:03:26",
        "Num": 689
      },
      {
        "Reason": "社压支政此流政点水家地转党将民示。",
        "Time": "2006-11-14 21:01:24",
        "Num": 160
      },
      {
        "Reason": "在想价有门加同后按同水矿内。",
        "Time": "1981-01-16 09:32:48",
        "Num": -740
      },
      {
        "Reason": "劳前带矿眼美六资验又带人清个。",
        "Time": "1971-10-03 12:01:10",
        "Num": 521
      }
    ]
  },
  "errorCode": 200
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|false|none||none|
|» data|object|true|none||none|
|»» coinRecordList|[object]|true|none||none|
|»»» Reason|string|true|none||变动原因|
|»»» Num|integer|false|none||变动数量，不能等于0（+1、-1）|
|»»» Time|string|true|none||变动时间（2023-07-13 09:45:11）|
|»» coinNum|integer|true|none|杏仁币数|none|

## POST 根据用户id获取他发布过的帖子

POST /api/UserInfo/fetchUserPosts

> Body 请求参数

```json
{
  "user_id": 3
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|user_id|query|integer| 否 |none|
|body|body|object| 否 |none|

> 返回示例

> 成功

```json
{
  "errorCode": 200,
  "data": {
    "post_list": [
      {
        "post_pic": "http://dummyimage.com/200x200",
        "author_name": "崔平",
        "author_portrait": "http://dummyimage.com/200x200",
        "post_tag": [
          "存流复起周"
        ],
        "post_id": 500000200008137150,
        "post_title": "工始话也",
        "post_first_comment_id": 140000197003306910,
        "reward": {
          "like": {
            "status": false,
            "num": 15
          },
          "coin": {
            "status": true,
            "num": 5
          }
        }
      },
      {
        "post_pic": "http://dummyimage.com/200x200",
        "author_name": "金杰",
        "author_portrait": "http://dummyimage.com/200x200",
        "post_tag": [
          "业统元交毛话",
          "亲斯话",
          "地标型",
          "总实一状",
          "业选活"
        ],
        "post_id": 310000202006297660,
        "post_title": "收切机油共",
        "post_first_comment_id": 530000197704183040,
        "reward": {
          "like": {
            "status": false,
            "num": 61
          },
          "coin": {
            "status": false,
            "num": 56
          }
        }
      },
      {
        "post_pic": "http://dummyimage.com/200x200",
        "author_name": "赵娟",
        "author_portrait": "http://dummyimage.com/200x200",
        "post_tag": [
          "看物育商",
          "发效斗求",
          "斗般候九位还容"
        ],
        "post_id": 120000200402259310,
        "post_title": "队就别界非八",
        "post_first_comment_id": 820000201112115300,
        "reward": {
          "like": {
            "status": false,
            "num": 17
          },
          "coin": {
            "status": true,
            "num": 13
          }
        }
      },
      {
        "post_pic": "http://dummyimage.com/200x200",
        "author_name": "毛敏",
        "author_portrait": "http://dummyimage.com/200x200",
        "post_tag": [
          "在切五离照",
          "类品持身日拉",
          "新里队后条"
        ],
        "post_id": 520000199503301570,
        "post_title": "一提求研人",
        "post_first_comment_id": 130000202202050930,
        "reward": {
          "like": {
            "status": false,
            "num": 48
          },
          "coin": {
            "status": false,
            "num": 35
          }
        }
      }
    ]
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|
|» data|object|true|none||none|
|»» post_list|[object]|true|none||none|
|»»» post_pic|string|true|none||帖子若有图片则为第一张图片的地址，没有则返回作者头像存储地址|
|»»» author_name|string|true|none||作者昵称|
|»»» author_portrait|string|true|none||作者头像|
|»»» post_tag|[string]|true|none||帖子的标签|
|»»» post_id|integer|true|none||帖子的id|
|»»» post_title|string|true|none||帖子的标题|
|»»» post_first_comment_id|integer|true|none||帖子一楼评论的id|
|»»» reward|object|true|none||none|
|»»»» like|object|true|none||none|
|»»»»» status|boolean|true|none||none|
|»»»»» num|integer|true|none||none|
|»»»» coin|object|true|none||none|
|»»»»» status|boolean|true|none||none|
|»»»»» num|integer|true|none||none|

## GET 获取管理员个人信息

GET /api/Administrator/Details

原名为/api/AdministratorInfo/Details
需修改为/api/Administrator/Details
以及正常响应的errorcode应设为200
isAdmin 如果其他地方不会用到的话可以去掉

> 返回示例

> 200 Response

```json
{
  "errorCode": "200",
  "data": {
    "administrator": {
      "id": 0,
      "name": "string",
      "telephone": "string",
      "email": "string",
      "portrait": "/src/assets/10.png"
    },
    "isAdministrator": true,
    "isLogin": true
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|
|» data|object|true|none||none|
|»» administrator|object|true|none||none|
|»»» id|integer|true|none||none|
|»»» name|string|true|none||none|
|»»» telephone|string|true|none||none|
|»»» email|string|true|none||none|
|»»» portrait|string|true|none|管理员头像|none|
|»» isAdministrator|boolean|true|none||none|
|»» isLogin|boolean|true|none||none|

## POST 上传头像

POST /api/UserInfo/uploadAvatar

登录状态下，用户或者管理员上传头像，从前端获取到图片后上传到sm.ms，然后返回图片的链接

> Body 请求参数

```yaml
image: string

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» image|body|string(binary)| 否 |用户上传的头像|

> 返回示例

> 200 Response

```json
{
  "errorCode": "200",
  "data": {
    "status": true,
    "url": "string"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|
|» data|object|true|none||none|
|»» status|boolean|true|none||none|
|»» url|string|true|none|图片的url|none|

## POST 上传医师资格证

POST /api/UserInfo/uploadDoctorApproval

用户上传医师资格证和执业证两张照片，前端获取图片，发给后端，后端不用返回图片的url

> Body 请求参数

```yaml
image:
  - file://C:\Users\17291\Pictures\Snipaste_2023-08-01_19-37-18.png
  - file://C:\Users\17291\Pictures\Snipaste_2023-08-02_23-44-37.png

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» image|body|string(binary)| 否 |用户上传的医师资格证照片|

> 返回示例

> 200 Response

```json
{
  "errorCode": "200",
  "data": {
    "status": true,
    "url": "string"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|
|» data|object|true|none||none|
|»» status|boolean|true|none||none|
|»» url|string|true|none|图片的url|none|

## POST 修改用户个人信息

POST /api/UserInfo/modifyUserInfo

修改用户个人信息时，不管有没有修改都向后端传递一个json数据，包括姓名、性别、邮箱地址、生日、电话、个人简介，其中用户名和联系电话不可修改

> Body 请求参数

```json
{
  "userName": "李超",
  "gender": "女",
  "birthday": "2017-11-08",
  "telephone": "13446754484",
  "email": "w.xdedwtkjwg@qq.com",
  "description": "新该油团深难类革好压相查意。通相二京切听率物同速就东酸律表四值。研向机四火能水现许专龙花状明料通作。只许将代记算林资当院除他品象白家。"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|

> 返回示例

> 成功

```json
{
  "errorCode": "200",
  "data": {
    "status": true,
    "userInfo": {
      "userID": 38,
      "userName": "李超",
      "gender": "女",
      "birthday": "2017-11-08",
      "telephone": "13446754484",
      "email": "w.xdedwtkjwg@qq.com",
      "description": "新该油团深难类革好压相查意。通相二京切听率物同速就东酸律表四值。研向机四火能水现许专龙花状明料通作。只许将代记算林资当院除他品象白家。"
    }
  }
}
```

```json
{
  "errorCode": "200",
  "data": {
    "status": true,
    "errorType": "Preserve failed"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|
|» data|object|true|none||none|
|»» status|boolean|true|none||none|
|»» errorType|string¦null|true|none||none|

## POST 修改管理员个人信息

POST /api/Administrator/modifyAdministratorInfo

这里管理员能修改的应该只有email一个属性？

> Body 请求参数

```json
{
  "email": "测试"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|

> 返回示例

> 成功

```json
{
  "errorCode": "200",
  "data": {
    "administrator": {
      "id": 0,
      "name": "string",
      "telephone": "string",
      "email": "string",
      "portrait": "string"
    },
    "status": true
  }
}
```

```json
{
  "errorCode": "200",
  "data": {
    "statue": true,
    "errorType": "Perserve fail"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|
|» data|object|true|none||none|
|»» administrator|object|true|none||none|
|»»» id|integer|true|none||none|
|»»» name|string|true|none||none|
|»»» telephone|string|true|none||none|
|»»» email|string|true|none||none|
|»»» portrait|string|true|none||none|
|»» status|boolean|true|none||none|

## POST 按照id关注用户

POST /api/UserInfo/followUser

> Body 请求参数

```json
{
  "followUserID": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» followUserID|body|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "errorCode": 200
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|

#### 枚举值

|属性|值|
|---|---|
|errorCode|200|
|errorCode|118|

## POST 按照id取消关注

POST /api/UserInfo/unfollowUser

> Body 请求参数

```json
{
  "followUserID": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» followUserID|body|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "errorCode": 200
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|

#### 枚举值

|属性|值|
|---|---|
|errorCode|200|
|errorCode|118|

## GET 根据用户id获取举报信息

GET /api/UserInfo/Report

> 返回示例

> 成功

```json
{
  "data": {
    "reportList": [
      {
        "report_status": "成功",
        "respond_date": "1993-03-11",
        "report_date": "1973-10-21",
        "report_id": "38"
      },
      {
        "report_status": "驳回",
        "respond_date": "1982-08-22",
        "report_date": "1992-06-02",
        "report_id": "11"
      },
      {
        "report_status": "审核中",
        "respond_date": "1984-12-28",
        "report_date": "2010-07-02",
        "report_id": "67"
      },
      {
        "report_status": "驳回",
        "respond_date": "1997-09-21",
        "report_date": "1982-06-08",
        "report_id": "100"
      }
    ]
  },
  "errorCode": 200
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|
|» data|object|true|none||none|
|»» reportList|[object]|true|none||none|
|»»» report_id|string|true|none||none|
|»»» report_status|integer|true|none||012 只有审核中、驳回、通过|
|»»» report_time|string|true|none||前端改|
|»»» respond_time|string¦null|true|none||前端改|
|»»» floor_number|string|true|none||none|
|»»» post_id|string|true|none||none|

## POST 充值杏仁币

POST /api/HB/order

此接口用于个人杏仁币的充值，前端向后端发送充值杏仁币金额。
后端返回支付请求的json.body
<input type="hidden" name="biz_content" value="{&quot;subject&quot;:&quot;test_name&quot;,&quot;out_trade_no&quot;:&quot;2023T8T1215T575U3&quot;,&quot;total_amount&quot;:&quot;1&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}">
<input type="submit" value="立即支付" style="display:none" >
</form>
<script>document.forms[0].submit();</script>

> Body 请求参数

```json
{
  "num": 3
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» num|body|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "errorCode": 0,
  "data": {
    "pay_page": "string"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» errorCode|integer|true|none||none|
|» data|object|true|none||none|
|»» pay_page|string|true|none||none|

# 数据模型

<h2 id="tocS_CoinInfo">CoinInfo</h2>

<a id="schemacoininfo"></a>
<a id="schema_CoinInfo"></a>
<a id="tocScoininfo"></a>
<a id="tocscoininfo"></a>

```json
{
  "coin": {
    "status": true,
    "num": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|coin|object|true|none||none|
|» status|boolean|true|none||none|
|» num|integer|true|none||none|

<h2 id="tocS_LikeInfo">LikeInfo</h2>

<a id="schemalikeinfo"></a>
<a id="schema_LikeInfo"></a>
<a id="tocSlikeinfo"></a>
<a id="tocslikeinfo"></a>

```json
{
  "like": {
    "status": true,
    "num": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|like|object|true|none||none|
|» status|boolean|true|none||none|
|» num|integer|true|none||none|

