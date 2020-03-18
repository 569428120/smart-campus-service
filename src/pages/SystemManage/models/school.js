import appConfig from "@/config/appConfig";
import * as schoolService from '@/pages/SystemManage/services/schoolService'

export default {
  namespace: 'school',
  state: {
    text: '学校管理页面',
    schoolList: [],// 学校列表
    current: 1,// 当前页
    pageSize: appConfig.PAGE_SIZE, //每页显示的大小
    total: 0,// 总数
  },
  effects: {

    /**
     *  查询学校列表
     * @param searchValue
     * @param current
     * @param pageSize
     * @param call
     * @param put
     * @returns {Generator<*, void, ?>}
     */* getSchoolList({payload: {searchValue, current, pageSize}}, {call, put}) {
      const {data: schoolList, total, totalPage} = yield call(schoolService.getSchoolList, searchValue, current, pageSize);
      yield put({
        type: "setState",
        payload: {
          total,
          current,
          pageSize,
          schoolList
        }
      })
    },

    /**
     *   删除
     * @param schoolIds
     * @param call
     * @param put
     * @returns {Generator<*, void, ?>}
     */* deleteSchoolByIds({payload: {schoolIds}}, {call, put}) {
      yield call(schoolService.deleteSchoolByIds, schoolIds);
    },

    /**
     *  保存数据
     * @param values
     * @param call
     * @param put
     * @returns {Generator<*, void, ?>}
     */* saveSchoolData({payload: {values}}, {call, put}) {
      yield call(schoolService.saveSchoolData, values);
    }

  },

  reducers: {
    setState(state, {payload}) {
      return {
        ...state,
        ...payload,
      };
    },
  },
};
