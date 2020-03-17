import request from "@/utils/request";
import config from "@/pages/SystemManage/config/config";

/**
 *  保存学校数据
 * @param values
 * @returns {Promise<void>}
 */
export async function saveSchoolData(values) {
  return request(config.schoolApi.saveSchoolData, {
    method: 'POST',
    body: {
      ...values
    },
    params: {}
  });
}


/**
 *  删除学校数据
 * @param schoolIds 学校id
 * @returns {Promise<void>}
 */
export async function deleteSchoolByIds(schoolIds) {
  return request(config.schoolApi.deleteSchoolByIds, {
    method: 'GET',
    body: {},
    params: {
      regionIds: (schoolIds || []).join(",")
    }
  });
}


/**
 *  分页搜索学校数据
 * @param searchValue 搜索条件
 * @param current 当前页
 * @param pageSize 每页现实的数量
 * @returns {Promise<Promise<any> | Promise<*>>}
 */
export async function getSchoolList(searchValue, current, pageSize) {
  return request(config.schoolApi.getSchoolList, {
    method: 'GET',
    body: {},
    params: {
      ...searchValue,
      current,
      pageSize
    }
  });
}
