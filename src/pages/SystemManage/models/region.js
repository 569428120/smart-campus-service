import appConfig from "@/config/appConfig";
import * as regionService from '@/pages/SystemManage/services/regionService'

export default {
  namespace: 'region',
  state: {
    text: '教育局管理页面',
    regionList: [],// 教育局列表
    total: 0, // 数量
    current: 1, // 当前页
    pageSize: appConfig.PAGE_SIZE
  },
  effects: {

    /**
     *  根据搜索条件查询教育局列表
     * @param searchValue
     * @param call
     * @param put
     * @returns {Generator<*, void, ?>}
     */* getRegionList({payload: {searchValue, current, pageSize}}, {call, put}) {
      const {data: regionList, total, totalPage} = yield call(regionService.getRegionList, searchValue, current, pageSize);
      yield put({
        type: "setState",
        payload: {
          total,
          current,
          pageSize,
          regionList
        }
      })
    },

    /**
     *  保存教育局的数据
     * @param values
     * @param call
     * @param put
     * @returns {Generator<*, void, ?>}
     */* saveRegionData({payload: {values}}, {call, put}) {
      yield call(regionService.saveRegionData, values);
    },

    /**
     * 删除教育局数据
     * @param regionIds
     * @param call
     * @param put
     * @returns {Generator<*, void, ?>}
     */* deleteRegionByIds({payload: {regionIds}}, {call, put}) {
      yield call(regionService.deleteRegionByIds, regionIds);
    },

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
