import request from "@/utils/request";
import config from "@/pages/SystemManage/config/config";

/**
 *  报错数据
 * @returns {Promise<void>}
 */
export async function savePcMenuData(values) {
  return request(config.pcMenuApi.savePcMenuData, {
    method: 'POST',
    body: {
      ...values
    },
    params: {}
  });
}


/**
 *   删除
 * @param menuIds
 * @returns {Promise<void>}
 */
export async function deletePcMenuByIds(menuIds) {
  return request(config.pcMenuApi.deletePcMenuByIds, {
    method: 'GET',
    body: {},
    params: {
      menuIds: (menuIds || []).join(",")
    }
  });
}


/**
 *  查询菜单列表
 * @param values
 * @returns {Promise<*>}
 */
export async function getMenuList(values) {
  return request(config.pcMenuApi.getMenuList, {
    method: 'GET',
    body: {},
    params: {
      ...values
    }
  });
}
