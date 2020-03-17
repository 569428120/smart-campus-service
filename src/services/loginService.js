import request from '@/utils/request';
import appConfig from "@/config/appConfig";

/**
 *  登录方法
 * @param params
 * @returns {Promise<void>}
 */
export async function login(params) {
  return request(appConfig.serviceApi.login, {
    method: 'POST',
    body: params,
  });
}


