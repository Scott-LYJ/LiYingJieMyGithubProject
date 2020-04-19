
import adminApi from './adminApi';
import shiroApi from './shiroApi';
import solrApi from './solrApi';
import msgApi from './msgApi';
/**
 * 接口汇总
 */
export default {
    /**用户,角色,组织等管理接口 */
    adminApi: adminApi,
    /**认证接口 */
    shiroApi: shiroApi,
    /**solr接口 */
    solrApi:solrApi,
    /**消息接口 */
    msgApi:msgApi
}
