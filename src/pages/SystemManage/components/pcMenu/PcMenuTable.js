import React, {Fragment, PureComponent} from 'react';
import DataTable from '@/components/SmartCampus/Table/DataTable';
import PropTypes from 'prop-types';

/**
 *  表格列
 * @returns {*[]}
 */
const tableColumns = onShowView => [
  {
    title: '菜单名称',
    dataIndex: 'menu_name',
    width: '20%',
  },
  {
    title: '菜单级别',
    dataIndex: 'menu_level',
    width: '10%',
  },
  {
    title: '路由',
    dataIndex: 'route',
    width: '12%',
  },
  {
    title: '操作名称',
    dataIndex: 'operate_name',
    width: '20%',
  },
  {
    title: '操作编码',
    dataIndex: 'operate_code',
    width: '10%',
  },
  {
    title: '描述',
    dataIndex: 'desc',
  },
  {
    title: '操作',
    width: '10%',
    render: (text, record) => (
      <Fragment>
        <a onClick={() => onShowView(record)}>查看详情</a>
      </Fragment>
    ),
  },
];

/**
 *  区域管理表格展示组件
 */
class RegionTable extends PureComponent {
  render() {
    const {dataSource, loading, selectedRowKeys, onTableSelectChange, onShowView} = this.props;

    const rowSelection = {
      columnTitle: '选择',
      columnWidth: 80,
      selectedRowKeys,
      onChange: onTableSelectChange,
    };

    // 表格参数
    const dataTableProps = {
      rowKey: 'id',
      rowSelection,
      dataSource,
      loading,
      columns: tableColumns(onShowView),
      pagination: false,
    };

    return <DataTable {...dataTableProps} />;
  }
}

RegionTable.propTypes = {
  dataSource: PropTypes.array,
  loading: PropTypes.bool,
  selectedRowKeys: PropTypes.array,
  onTableSelectChange: PropTypes.func,
  onShowView: PropTypes.func,
};
RegionTable.defaultProps = {};

export default RegionTable;
