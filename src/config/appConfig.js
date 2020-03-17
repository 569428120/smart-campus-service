//后台服务路径
const API = 'http://localhost:9999';
// 默认的也容量
const PAGE_SIZE = 15;

export default {
  API,
  PAGE_SIZE,
  serviceApi: {
    login: `${API}/auth/user/login`,
  }
}
