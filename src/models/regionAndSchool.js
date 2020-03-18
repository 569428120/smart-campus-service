import regionAndSchoolService from '@/services/regionAndSchoolService';

export default {
  namespace: 'regionAndSchool',

  state: {
    regionList: [], // 教育局列表
    schoolList: [], // 学校列表
    isRegionShow: false, // 是否显示教育局选择框
    isSchoolShow: false, // 是否显示学校
  },

  effects: {
    /**
     *  查询教育局列表
     * @param call
     * @param put
     * @returns {Generator<*, void, ?>}
     */* getRegionList({payload: {}}, {call, put}) {
      const {regionList, isRegionShow} = yield call(regionAndSchoolService.getRegionList);
      yield put({
        type: 'setState',
        payload: {
          regionList,
          isRegionShow
        }
      });
    },

    * fetchNotice(_, {call, put}) {
      const response = yield call(queryProjectNotice);
      yield put({
        type: 'saveNotice',
        payload: Array.isArray(response) ? response : [],
      });
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
