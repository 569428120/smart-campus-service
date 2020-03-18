import * as pcMenuService from '@/pages/SystemManage/services/pcMenuService'

export default {
  namespace: 'pcMenu',
  state: {
    text: 'PC端菜单管理',
    menuList: [], // 菜单列表
  },
  effects: {

    /**
     *  搜索
     * @param searchValue
     * @param call
     * @param put
     * @returns {Generator<*, void, ?>}
     */* getMenuList({payload: {searchValue}}, {call, put}) {
      const menuList = yield call(pcMenuService.getMenuList, searchValue);
      yield put({
        type: "setState",
        payload: {
          menuList
        }
      })
    },

    /**
     *   删除
     * @param menuIds
     * @param call
     * @param put
     * @returns {Generator<*, void, ?>}
     */* deletePcMenuByIds({payload: {menuIds}}, {call, put}) {
      yield call(pcMenuService.deletePcMenuByIds, menuIds);
    },

    /**
     *  报错数据
     * @param values
     * @param call
     * @param put
     * @returns {Generator<*, void, ?>}
     */* savePcMenuData({payload: {values}}, {call, put}) {
      yield call(pcMenuService.savePcMenuData, values);
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
