package com.dcits.scott.gateway.pojo;


import java.util.Date;
import java.util.List;
import java.util.Set;

import com.dcits.scott.common.CommonDO;
import com.dcits.scott.tool.DateUtil;
import lombok.experimental.FieldNameConstants;
import org.apache.solr.client.solrj.beans.Field;

/**
 *
 * API-DO对象
 */
public class GatewayApiDO extends CommonDO {
  private static final long serialVersionUID = -3240563338269708158L;

  
  /**
   * @mbg.generated
   */

  private Long id;
  @Field("id")
  private String pid;
  /**
   * @mbg.generated
   */
  @Field("group_id")
  private Long groupId;
  /**
   * @mbg.generated
   */
  @Field("api_name")
  private String name;
  /**
   * @mbg.generated
   */
  @Field("api_description")
  private String description;

  /**
   * @mbg.generated
   */
  private Integer special;

  /**
   * @mbg.generated
   */
  @Field("api_status")
  private Integer status;
  /**
   * @mbg.generated
   */
  private Integer httpMethod;
  /**
   * @mbg.generated
   */
  @Field("api_is_auth")
  private Integer isAuth;
  /**
   * @mbg.generated
   */
  @Field("api_is_login")
  private Integer isLogin;
  /**
   * @mbg.generated
   */
  @Field("api_auth_version")
  private Integer authVersion;
  /**
   * @mbg.generated
   */
  @Field("serviceName")
  private String serviceName;
  /**
   * @mbg.generated
   */
  @Field("serviceMethod")
  private String serviceMethod;
  /**
   * @mbg.generated
   */
  @Field("api_serice_version")
  private String serviceVersion;
  /**
   * @mbg.generated
   */
  private Integer timeOut;
  /**
   * @mbg.generated
   */
  @Field("createBy")
  private String createBy;
  /**
   * @mbg.generated
   */
  private String updateBy;

  private Date testTime;
  private Date documentTime;

  public String getTestTime() {
    return DateUtil.getFormatDate(testTime);
  }

  public void setTestTime(Date testTime) {
    this.testTime = testTime;
  }

  public String getDocumentTime() {
    return DateUtil.getFormatDate(testTime);
  }

  public void setDocumentTime(Date documentTime) {
    this.documentTime = documentTime;
  }

  /**
   * @mbg.generated
   */
  @Field("cdt")
  private Date cdt;
  /**
   * @mbg.generated
   */
  private Date udt;
  /**
   * @mbg.generated
   */
  private Integer version;
 @Field("interfaceName")
  private String interfaceName;

  private Set<GatewayServiceRequestDO> gatewayServiceRequestDOS;

  public Set<GatewayServiceRequestDO> getGatewayServiceRequestDOS() {
    return gatewayServiceRequestDOS;
  }

  public void setGatewayServiceRequestDOS(Set<GatewayServiceRequestDO> gatewayServiceRequestDOS) {
    this.gatewayServiceRequestDOS = gatewayServiceRequestDOS;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  /**
   * 回调响应头信息
   */
  private String responseHeader;
@Field("zookeeper")
  private String zookeeper;

  public void setVersion(Integer version) {
    this.version = version;
  }

  public String getZookeeper() {
    return zookeeper;
  }

  public void setZookeeper(String zookeeper) {
    this.zookeeper = zookeeper;
  }

  public String getResponseHeader() {
    return responseHeader;
  }

  public void setResponseHeader(String responseHeader) {
    this.responseHeader = responseHeader;
  }

  /**
   * 输入参数
   */

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /**
   * 分组名称
   */
  @Field("group_name")
  private String groupName;

  private Boolean Authorized;
  private Boolean Groupauthorized;

  public Boolean getGroupauthorized() {
    return Groupauthorized;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public void setGroupauthorized(Boolean groupauthorized) {
    Groupauthorized = groupauthorized;
  }

  public Boolean getAuthorized() {
    return Authorized;
  }

  public void setAuthorized(Boolean authorized) {
    Authorized = authorized;
  }

  /**
   * @mbg.generated
   */
  public Long getId() {
    return id;
  }

  /**
   * @mbg.generated
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @mbg.generated
   */
  public Long getGroupId() {
    return groupId;
  }

  /**
   * @mbg.generated
   */
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  /**
   * @mbg.generated
   */
  public String getName() {
    return name;
  }

  /**
   * @mbg.generated
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @mbg.generated
   */
  public String getDescription() {
    return description;
  }

  /**
   * @mbg.generated
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @mbg.generated
   */
  public Integer getSpecial() {
    return special;
  }

  /**
   * @mbg.generated
   */
  public void setSpecial(Integer special) {
    this.special = special;
  }

  /**
   * @mbg.generated
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @mbg.generated
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * @mbg.generated
   */
  public Integer getHttpMethod() {
    return httpMethod;
  }

  /**
   * @mbg.generated
   */
  public void setHttpMethod(Integer httpMethod) {
    this.httpMethod = httpMethod;
  }

  /**
   * @mbg.generated
   */
  public Integer getIsAuth() {
    return isAuth;
  }

  /**
   * @mbg.generated
   */
  public void setIsAuth(Integer isAuth) {
    this.isAuth = isAuth;
  }

  /**
   * @mbg.generated
   */
  public Integer getIsLogin() {
    return isLogin;
  }

  /**
   * @mbg.generated
   */
  public void setIsLogin(Integer isLogin) {
    this.isLogin = isLogin;
  }

  /**
   * @mbg.generated
   */
  public Integer getAuthVersion() {
    return authVersion;
  }

  /**
   * @mbg.generated
   */
  public void setAuthVersion(Integer authVersion) {
    this.authVersion = authVersion;
  }

  /**
   * @mbg.generated
   */
  public String getServiceName() {
    return serviceName;
  }

  /**
   * @mbg.generated
   */
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  /**
   * @mbg.generated
   */
  public String getServiceMethod() {
    return serviceMethod;
  }

  /**
   * @mbg.generated
   */
  public void setServiceMethod(String serviceMethod) {
    this.serviceMethod = serviceMethod;
  }

  /**
   * @mbg.generated
   */
  public String getServiceVersion() {
    return serviceVersion;
  }

  /**
   * @mbg.generated
   */
  public void setServiceVersion(String serviceVersion) {
    this.serviceVersion = serviceVersion;
  }

  /**
   * @mbg.generated
   */
  public Integer getTimeOut() {
    return timeOut;
  }

  /**
   * @mbg.generated
   */
  public void setTimeOut(Integer timeOut) {
    this.timeOut = timeOut;
  }

  /**
   * @mbg.generated
   */
  public String getCreateBy() {
    return createBy;
  }

  /**
   * @mbg.generated
   */
  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  /**
   * @mbg.generated
   */
  public String getUpdateBy() {
    return updateBy;
  }

  /**
   * @mbg.generated
   */
  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }

  /**
   * @mbg.generated
   */
  public String getCdt() {
    return DateUtil.getFormatDate(cdt);
  }

  /**
   * @mbg.generated
   */
  public void setCdt(Date cdt) {
    this.cdt = cdt;
  }

  /**
   * @mbg.generated
   */
  public String getUdt() {
    return DateUtil.getFormatDate(udt);
  }

  /**
   * @mbg.generated
   */
  public void setUdt(Date udt) {
    this.udt = udt;
  }

  /**
   * @mbg.generated
   */
  public Integer getVersion() {
    return version;
  }


}
