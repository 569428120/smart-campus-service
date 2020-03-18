import React, {PureComponent} from 'react';
import {connect} from 'dva';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import {Card, message, Modal} from 'antd';
import styles from '@/pages/common.less';
import SearchForm from '@/pages/SystemManage/components/region/SearchForm';
import OperatorButton from '@/components/SmartCampus/AuthorityToolbar/OperatorButton';
import RegionTable from '@/pages/SystemManage/components/region/RegionTable';
import appConfig from "@/config/appConfig";
import RegionModal from "@/pages/SystemManage/components/region/RegionModal";

/**
 *  区域管理页面
 */
@connect(({loading, region}) => ({
  loading,
  region,
}))
class Region extends PureComponent {
  state = {
    searchValue: {},//  搜索条件
    selectedRowKeys: [],
    selectedRows: [],
    regionModalVisible: false,// 新增修改窗口
    regionModalOpenType: 'add',// 操作类型
    regionModel: {}, // 弹窗数据
  };

  componentDidMount() {
    const {dispatch} = this.props;
    dispatch({
      type: "region/getRegionList",
      payload: {
        searchValue: {},
        current: 1,
        pageSize: appConfig.PAGE_SIZE
      }
    });
  };

  /**
   * 查询
   * @param searchValue
   */
  onSearchFormSearch = searchValue => {
    const {dispatch, region: {pageSize}} = this.props;
    dispatch({
      type: "region/getRegionList",
      payload: {
        searchValue,
        current: 1,
        pageSize
      }
    });
    this.setState({
      searchValue
    });
  };

  /**
   * 重置
   * @param values
   */
  onSearchFormReset = searchValue => {
    this.onSearchFormSearch(searchValue)
  };

  /**
   *  选择
   * @param selectedRowKeys
   * @param selectedRows
   */
  onTableSelectChange = (selectedRowKeys, selectedRows) => {
    this.setState({
      selectedRowKeys,
      selectedRows,
    });
  };

  /**
   *  分页
   * @param current
   * @param pageSize
   */
  onTablePageChange = (current, pageSize) => {
    const {dispatch} = this.props;
    const {searchValue} = this.state;
    debugger
    dispatch({
      type: "region/getRegionList",
      payload: {
        searchValue,
        current,
        pageSize
      }
    });
  };

  /**
   *  页容量编号
   * @param current
   * @param pageSize
   */
  onTablePageSizeChange = (current, pageSize) => {
    const {dispatch} = this.props;
    const {searchValue} = this.state;
    dispatch({
      type: "region/getRegionList",
      payload: {
        searchValue,
        current: 1,
        pageSize
      }
    });
  };

  /**
   *  打开新增弹窗
   */
  openRegionModal = (regionModalOpenType, regionModel) => {
    // 打开弹窗
    this.setState({
      regionModalOpenType,
      regionModel,
      regionModalVisible: true,
    })
  };

  /**
   *  删除教育局数据
   */
  onDeleteRegion = () => {
    const {selectedRowKeys} = this.state;
    if ((selectedRowKeys || []).length <= 0) {
      message.warn("请选择需要删除的数据");
      return
    }
    const {dispatch, region: {current, pageSize}} = this.props;
    // 删除
    const deleteRegion = () => {
      dispatch({
        type: "region/deleteRegionByIds",
        payload: {
          regionIds: selectedRowKeys
        }
      }).then(() => {
        this.setState({
          selectedRowKeys: [],
          selectedRows: [],
        });
        this.onTablePageChange(current, pageSize);
      });
    };
    Modal.confirm({
      title: '删除确认',
      content: '是否删除选择的数据',
      onOk: deleteRegion,
      okText: '确认',
      cancelText: '取消',
    });
  };

  /**
   *  保存按钮
   * @param values
   */
  onRegionModalOk = (values, openType) => {
    const {dispatch, region: {current, pageSize}} = this.props;
    const {regionModel} = this.state;
    if (['add', 'edit'].includes(openType)) {
      dispatch({
        type: "region/saveRegionData",
        payload: {
          values: {
            ...values,
            id: (regionModel || {}).id
          }
        }
      }).then(() => {
        this.onTablePageChange(openType === 'edit' ? current : 1, pageSize);
      });
    }
    // 关闭窗口
    this.closeRegionModal();
  };

  /**
   *  关闭新增查看弹窗
   */
  closeRegionModal = () => {
    this.setState({
      regionModalOpenType: '',
      regionModalVisible: false,
      selectedRowKeys: [],
      selectedRows: [],
    })
  };

  /**
   *  操作按钮
   */
  getOperatorButtonProps = () => {
    const {selectedRowKeys, selectedRows} = this.state;
    const buttonList = [];
    // 新增按钮
    buttonList.push({
      icon: 'plus',
      type: 'primary',
      text: '新建',
      operatorKey: 'region-add',
      onClick: () => this.openRegionModal('add', {}),
    });
    // 更新按钮，选择一个的时候显示
    if ((selectedRows || []).length === 1) {
      buttonList.push({
        icon: '',
        type: '',
        text: '更新',
        operatorKey: 'region-update',
        onClick: () => this.openRegionModal('edit', selectedRows[0]),
      });
    }

    // 下拉菜单按钮
    const dropdownList = [];
    // 大于0 就显示
    if ((selectedRowKeys || []).length > 0) {
      dropdownList.push({
        operatorKey: 'region-delete',
        text: '删除',
        onClick: this.onDeleteRegion,
      });
    }
    return {buttonList, dropdownList};
  };

  render() {
    // model里面的数据
    const {
      region: {
        regionList,
        total,
        current,
        pageSize
      },
      loading
    } = this.props;

    // 搜索框参数
    const searchFormProps = {
      onSearch: this.onSearchFormSearch,
      onFormReset: this.onSearchFormReset,
    };

    // 操作按钮参数
    const operatorButtonProps = this.getOperatorButtonProps();

    // 表格组件参数
    const regionTableProps = {
      dataSource: regionList,
      loading: loading.effects['region/getRegionList'],
      selectedRowKeys: this.state.selectedRowKeys,
      onTableSelectChange: this.onTableSelectChange,
      onShowView: (record) => this.openRegionModal('view', record),
      current,
      pageSize,
      total,
      onTablePageChange: this.onTablePageChange,
      onTablePageSizeChange: this.onTablePageSizeChange,
    };

    // 新增修改弹窗参数
    const regionModalProps = {
      visible: this.state.regionModalVisible,
      openType: this.state.regionModalOpenType,
      dataSource: this.state.regionModel,
      onOk: this.onRegionModalOk,
      onCancel: this.closeRegionModal,
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
            <RegionTable {...regionTableProps} />
          </div>
        </Card>
        <RegionModal {...regionModalProps} />
      </PageHeaderWrapper>
    );
  }
}

export default Region;
