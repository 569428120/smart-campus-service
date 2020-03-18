import React, {PureComponent} from 'react';
import {Card} from 'antd';
import {connect} from 'dva';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import styles from '@/pages/common.less';
import SearchForm from '@/pages/SystemManage/components/pcMenu/SearchForm';
import OperatorButton from '@/components/SmartCampus/AuthorityToolbar/OperatorButton';
import PcMenuTable from '@/pages/SystemManage/components/pcMenu/PcMenuTable';
import appConfig from "@/config/appConfig";
import PcMenuModal from "@/pages/SystemManage/components/pcMenu/PcMenuModal";

/**
 *  区域管理页面
 */
@connect(({loading, pcMenu}) => ({
  loading,
  pcMenu,
}))
class PcMenu extends PureComponent {
  state = {
    searchValue: {},//  搜索条件
    selectedRowKeys: [],
    selectedRows: [],
    menuModalVisible: false,
    menuModel: {},
    openType: "",
  };

  componentDidMount() {
    this.onRefreshPcMenuList({});
  }

  /**
   *   刷新菜单列表
   * @param searchValue
   */
  onRefreshPcMenuList = (searchValue) => {
    const {dispatch} = this.props;
    dispatch({
      type: "pcMenu/getMenuList",
      payload: {
        searchValue,
      }
    });
    this.setState({
      selectedRowKeys: [],
      selectedRows: [],
      searchValue
    })
  };

  /**
   * 查询
   * @param values
   */
  onSearchFormSearch = values => {
    this.onRefreshPcMenuList(values);
  };

  /**
   * 重置
   * @param searchValue
   */
  onSearchFormReset = searchValue => {
    this.onRefreshPcMenuList(searchValue);
  };

  /**
   *  选择
   * @param selectedRowKeys
   * @param selectedRows
   */
  onTableSelectChange = (selectedRowKeys, selectedRows) => {
    this.setState({
      selectedRowKeys,
      selectedRows
    });
  };

  /**
   *  新增
   */
  openCreatePcMenuModal = (record, openType, pRecord) => {
    this.setState({
      menuModalVisible: true,
      openType,
      menuModel: record,
      pMenuModel: pRecord
    });
  };

  /**
   *  删除
   */
  deletePcMenus = (menuIds) => {
    const {dispatch} = this.props;
    const {searchValue} = this.state;
    dispatch({
      type: "pcMenu/deletePcMenuByIds",
      payload: {
        menuIds,
      }
    }).then(() => {
      this.onRefreshPcMenuList(searchValue);
    });
  };

  /**
   *   确认事件
   * @param values
   * @param openType
   */
  onPcMenuModalOk = (values, openType) => {
    const {dispatch} = this.props;
    const {searchValue, menuModel} = this.state;
    if (['add', 'edit'].includes(openType)) {
      dispatch({
        type: "pcMenu/savePcMenuData",
        payload: {
          values: {
            ...values,
            id: (menuModel || {}).id
          }
        }
      }).then(() => {
        this.onRefreshPcMenuList(searchValue);
      });
    }
    this.closePcMenuModal();
  };


  /**
   *  关闭弹窗
   */
  closePcMenuModal = () => {
    this.setState({
      menuModalVisible: false,
    });
  };

  /**
   *  操作按钮
   */
  getOperatorButtonProps = () => {
    const {selectedRowKeys, selectedRows} = this.state;
    const buttonList = [];
    let addText = '新增一级菜单';
    if ((selectedRowKeys || []).length === 1) {
      addText = '新增子菜单';
    }
    if ((selectedRows || []).length === 1 && selectedRows[0].menuLevel === 3) {
      addText = '新增操作';
    }
    // 新增按钮
    buttonList.push({
      icon: 'plus',
      type: 'primary',
      text: addText,
      operatorKey: 'test-key',
      onClick: () => this.openCreatePcMenuModal({}, 'add', (selectedRows || []).length === 1 ? selectedRows[0] : {}),
    });
    // 更新按钮，选择一个的时候显示
    if ((selectedRows || []).length === 1) {
      buttonList.push({
        icon: '',
        type: '',
        text: '更新',
        operatorKey: 'test-key1',
        onClick: () => this.openCreatePcMenuModal(selectedRows[0], 'edit'),
      });
    }

    // 下拉菜单按钮
    const dropdownList = [];
    // 大于0 就显示
    if ((selectedRowKeys || []).length > 0) {
      dropdownList.push({
        operatorKey: 'aaaaa',
        text: '删除',
        onClick: () => this.deletePcMenus(selectedRowKeys),
      });
    }
    return {buttonList, dropdownList};
  };

  render() {
    // model里面的数据
    const {
      loading,
      pcMenu: {
        menuList
      }
    } = this.props;

    // 搜索框参数
    const searchFormProps = {
      onSearch: this.onSearchFormSearch,
      onFormReset: this.onSearchFormReset,
    };

    // 操作按钮参数
    const operatorButtonProps = this.getOperatorButtonProps();

    // 表格组件参数
    const pcMenuTableProps = {
      dataSource: [{id: 1, name: ""}, {id: 2, name: 'dsadsa'}],
      loading: false,
      selectedRowKeys: this.state.selectedRowKeys,
      onTableSelectChange: this.onTableSelectChange,
      onShowView: (record) => this.openCreatePcMenuModal(record, 'view'),
    };

    const pcMenuModalProps = {
      visible: this.state.menuModalVisible,
      openType: this.state.openType,
      dataSource: this.state.menuModel,
      pDataSource: this.state.pMenuModel,
      onOk: this.onPcMenuModalOk,
      onCancel: this.closePcMenuModal
    };

    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>
              <SearchForm {...searchFormProps} />
            </div>
            <div className={styles.tableListOperator}>
              <OperatorButton {...operatorButtonProps} />
            </div>
            <PcMenuTable {...pcMenuTableProps} />
          </div>
        </Card>
        <PcMenuModal {...pcMenuModalProps}/>
      </PageHeaderWrapper>
    );
  }
}

export default PcMenu;
