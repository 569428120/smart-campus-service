import React, {PureComponent} from 'react';
import {Pagination, Table} from 'antd';
import styles from './DataTable.less';
import PropTypes from 'prop-types';

/**
 *  数据表格
 */
class DataTable extends PureComponent {
  render() {
    const {
      current,
      pageSize,
      total,
      pagination,
      onTablePageChange,
      onTablePageSizeChange,
      ...ret
    } = this.props;

    // 表格属性
    const tableProps = {
      className: styles.table,
      pagination: false,
      scroll: {y: 10 * 38},
      ...ret,
    };

    // 分页组件
    const paginationProps = {
      defaultPageSize: 15,
      pageSizeOptions: ['15', '50', '100'],
      showQuickJumper: true,
      showSizeChanger: true,
      current,
      pageSize,
      total,
      onChange: onTablePageChange,
      onShowSizeChange: onTablePageChange,
    };

    return (
      <div style={{textAlign: 'right'}}>
        <Table {...tableProps} />
        {pagination === true ? <Pagination style={{marginTop: 10}} {...paginationProps} /> : null}
      </div>
    );
  }
}

DataTable.propTypes = {
  pagination: PropTypes.bool,
};
DataTable.defaultProps = {
  pagination: true,
};

export default DataTable;
