import request from "@/utils/request";
import config from "@/pages/SystemManage/config/config";

/**
 *  保存教育局的数据
 * @param values
 * @returns {Promise<void>}
 */
export async function saveRegionData(values) {
  return request(config.regionApi.saveRegionData, {
    method: 'POST',
    body: {
      ...values
    },
    params: {}
  });
}


/**
 *  删除教育局数据
 * @param regionIds 教育局id列表
 * @returns {Promise<void>}
 */
export async function deleteRegionByIds(regionIds) {
  return request(config.regionApi.deleteRegionByIds, {
    method: 'GET',
    body: {},
    params: {
      regionIds: (regionIds || []).join(",")
    }
  });
}


/**
 *  分页搜索查询教育局数据
 * @param searchValue 搜索条件
 * @param current 当前页
 * @param pageSize 每页现实的数量
 * @returns {Promise<Promise<any> | Promise<*>>}
 */
export async function getRegionList(searchValue, current, pageSize) {
  return request(config.regionApi.getRegionList, {
    method: 'GET',
    body: {},
    params: {
      ...searchValue,
      current,
      pageSize
    }
  });
}
