import React, { PureComponent } from 'react';
import { Card } from 'antd';
import { connect } from 'dva';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import styles from '@/pages/common.less';
import SearchForm from '@/pages/SystemManage/components/pcMenu/SearchForm';
import OperatorButton from '@/components/SmartCampus/AuthorityToolbar/OperatorButton';
import AppMenuTable from '@/pages/SystemManage/components/appMenu/AppMenuTable';

/**
 *  区域管理页面
 */
@connect(({ loading, appMenu }) => ({
  loading,
  appMenu,
}))
class AppMenu extends PureComponent {
  state = {
    selectedRowKeys: [],
  };

  /**
   * 查询
   * @param values
   */
  onSearchFormSearch = values => {
    console.log(values);
  };

  /**
   * 重置
   * @param values
   */
  onSearchFormReset = values => {
    console.log(values);
  };

  /**
   *  选择
   * @param selectedRowKeys
   * @param selectedRows
   */
  onTableSelectChange = (selectedRowKeys, selectedRows) => {
    this.setState({
      selectedRowKeys,
    });
  };

  /**
   *  新增
   */
  openCreateAppMenuModal = rowKey => {};

  /**
   *  操作按钮
   */
  getOperatorButtonProps = () => {
    const { selectedRowKeys } = this.state;
    const buttonList = [];
    // 新增按钮
    buttonList.push({
      icon: 'plus',
      type: 'primary',
      text: '新建',
      operatorKey: 'test-key',
      onClick: this.openCreateAppMenuModal,
    });
    // 更新按钮，选择一个的时候显示
    if ((selectedRowKeys || []).length === 1) {
      buttonList.push({
        icon: '',
        type: '',
        text: '更新',
        operatorKey: 'test-key1',
        onClick: () => this.openCreateAppMenuModal(selectedRowKeys[0]),
      });
    }

    // 下拉菜单按钮
    const dropdownList = [];
    // 大于0 就显示
    if ((selectedRowKeys || []).length > 0) {
      dropdownList.push({
        operatorKey: 'aaaaa',
        text: '删除',
        onClick: () => {},
      });
    }
    return { buttonList, dropdownList };
  };

  render() {
    // model里面的数据
    const { text } = this.props.appMenu;

    // 搜索框参数
    const searchFormProps = {
      onSearch: this.onSearchFormSearch,
      onFormReset: this.onSearchFormReset,
    };

    // 操作按钮参数
    const operatorButtonProps = this.getOperatorButtonProps();

    // 表格组件参数
    const appMenuTableProps = {
      dataSource: [{ id: 1, name: text }, { id: 2, name: 'dsadsa' }],
      loading: false,
      selectedRowKeys: this.state.selectedRowKeys,
      onTableSelectChange: this.onTableSelectChange,
      onShowView: () => {},
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
            <AppMenuTable {...appMenuTableProps} />
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default AppMenu;
