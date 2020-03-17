import React, {Fragment, PureComponent} from 'react';
import DataTable from '@/components/SmartCampus/Table/DataTable';
import PropTypes from 'prop-types';
import enums from '@/pages/SystemManage/config/enums';

/**
 *  表格列
 * @returns {*[]}
 */
const tableColumns = onShowView => [
  {
    title: '所属教育局',
    dataIndex: 'regionName',
    width: '15%',
  },
  {
    title: '学校名称',
    dataIndex: 'schoolName',
    width: '15%',
  },
  {
    title: '级别',
    dataIndex: 'schoolLevel',
    width: '10%',
    render: (text) => {
      return (text || "").split("##").map(key => enums.SchoolLevel[key]).join(",");
    }
  },
  {
    title: '学校类型',
    dataIndex: 'schoolType',
    width: '10%',
    render: (text) => enums.SchoolType[text]
  },
  {
    title: '描述',
    dataIndex: 'description',
    width: '35%',
  },
  {
    title: '操作',
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
    const {
      dataSource,
      loading,
      selectedRowKeys,
      onTableSelectChange,
      onShowView,

      current,
      pageSize,
      total,
      onTablePageChange,
      onTablePageSizeChange,
    } = this.props;

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
      current,
      pageSize,
      total,
      onChange: onTablePageChange,
      onShowSizeChange: onTablePageSizeChange,
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
  // 分页
  current: PropTypes.number,
  pageSize: PropTypes.number,
  total: PropTypes.number,
  onTablePageChange: PropTypes.func,
  onTablePageSizeChange: PropTypes.func,
};
RegionTable.defaultProps = {};

export default RegionTable;
