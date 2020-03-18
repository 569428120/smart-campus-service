import React, {PureComponent} from 'react';
import {Form, Input, Modal} from "antd";
import PropTypes from "prop-types";
import enums from '@/pages/SystemManage/config/enums';

const {TextArea} = Input;

const formItemLayout = {
  labelCol: {
    xs: {span: 24},
    sm: {span: 6},
  },
  wrapperCol: {
    xs: {span: 24},
    sm: {span: 16},
  },
};

/**
 *  新增修改弹窗
 */
@Form.create()
class RegionModal extends PureComponent {
  /**
   *  提交数据
   * @param onOk
   */
  onSubmit = (onOk) => {
    const {form: {validateFields}, openType} = this.props;
    validateFields((errors, values) => {
      if (errors === null) {
        onOk && onOk(values, openType);
      }
    });

  };

  render() {
    const {
      visible,
      openType,
      dataSource,
      onOk,
      onCancel,
      form: {
        getFieldDecorator
      }
    } = this.props;

    // 回显的数据
    const {regionName, educationName, description} = (dataSource || {});

    return <Modal
      title={enums.OperatorType[openType]}
      destroyOnClose={true}
      visible={visible}
      onOk={() => this.onSubmit(onOk)}
      onCancel={onCancel}
      okText="确认"
      cancelText="取消"
    >
      <Form {...formItemLayout} style={{textAlign: "center"}}>
        <Form.Item label="区域名称">
          {getFieldDecorator('regionName', {
            initialValue: regionName,
            rules: [
              {
                required: true,
                message: '区域名称必填',
              },
              {
                max: 64,
                message: '长度不能超过64',
              },
            ],
          })(<Input disabled={openType === 'view'}/>)}
        </Form.Item>
        <Form.Item label="教育局名称">
          {getFieldDecorator('educationName', {
            initialValue: educationName,
            rules: [
              {
                required: true,
                message: '教育局名称必填',
              },
              {
                max: 64,
                message: '长度不能超过64',
              },
            ],
          })(<Input disabled={openType === 'view'}/>)}
        </Form.Item>
        <Form.Item label="描述">
          {getFieldDecorator('description', {
            initialValue: description,
            rules: [
              {
                max: 255,
                message: '长度不能超过255',
              },
            ]
          })(<TextArea disabled={openType === 'view'} rows={4}/>)}
        </Form.Item>
      </Form>
    </Modal>;
  }
}

RegionModal.propTypes = {
  // 是否显示
  visible: PropTypes.bool.isRequired,
  // 操作类型 edit  add
  openType: PropTypes.string,
  // 回显的数据
  dataSource: PropTypes.object,
  // 确认方法
  onOk: PropTypes.func,
  // 取消方法
  onCancel: PropTypes.func,
};

RegionModal.defaultProps = {};

export default RegionModal;
