package com.dcits.scott.roleprovider.role.mapper;

import com.dcits.scott.admin.pojo.AuthRole;
import com.dcits.scott.roleprovider.common.ProtoMapper;
import org.springframework.stereotype.Repository;

/**角色
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Repository
public abstract interface RoleMapper extends ProtoMapper<AuthRole> {
}
