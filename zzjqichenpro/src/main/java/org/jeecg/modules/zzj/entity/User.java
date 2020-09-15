package org.jeecg.modules.zzj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="user对象", description="用户表")
public class User {
    /**表id*/
    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "表id")
    private String id;
    //账号
    private String username;
    //密码
    private String password;
}
