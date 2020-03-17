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


@Form.create()
class PcMenuModal extends PureComponent {

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

  /**
   *  菜单表单
   */
  renderMenuForm = () => {
    const {
      openType,
      dataSource,
      form: {
        getFieldDecorator
      }
    } = this.props;

    const {menuName, route, description} = dataSource;

    return <Form {...formItemLayout} style={{textAlign: "center"}}>
      <Form.Item label="菜单名称">
        {getFieldDecorator('menuName', {
          initialValue: menuName,
          rules: [
            {
              required: true,
              message: '菜单名称必填',
            },
            {
              max: 64,
              message: '长度不能超过64',
            },
          ],
        })(<Input disabled={openType === 'view'}/>)}
      </Form.Item>

      <Form.Item label="路由">
        {getFieldDecorator('route', {
          initialValue: route,
          rules: [
            {
              required: true,
              message: '路由必填',
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
              max: 525,
              message: '长度不能超过525',
            },
          ]
        })(<TextArea disabled={openType === 'view'} rows={4}/>)}
      </Form.Item>
    </Form>;
  };

  /**
   *  操作表单
   */
  renderOperateForm = () => {
    const {
      openType,
      dataSource,
      form: {
        getFieldDecorator
      }
    } = this.props;

    const {operateName, operateCode, description} = dataSource;

    return <Form {...formItemLayout} style={{textAlign: "center"}}>
      <Form.Item label="操作名称">
        {getFieldDecorator('operateName', {
          initialValue: operateName,
          rules: [
            {
              required: true,
              message: '操作名称必填',
            },
            {
              max: 64,
              message: '长度不能超过64',
            },
          ],
        })(<Input disabled={openType === 'view'}/>)}
      </Form.Item>

      <Form.Item label="操作编码">
        {getFieldDecorator('operateCode', {
          initialValue: operateCode,
          rules: [
            {
              required: true,
              message: '操作编码必填',
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
              max: 525,
              message: '长度不能超过525',
            },
          ]
        })(<TextArea disabled={openType === 'view'} rows={4}/>)}
      </Form.Item>
    </Form>;
  };

  render() {
    const {
      visible,
      openType,
      dataSource,
      onOk,
      onCancel
    } = this.props;

    const {menuLevel} = (dataSource || {});

    const form = menuLevel === 4 ? this.renderMenuForm() : this.renderOperateForm();

    return <Modal
      title={enums.OperatorType[openType]}
      destroyOnClose={true}
      visible={visible}
      onOk={() => this.onSubmit(onOk)}
      width={window.innerWidth * 0.5}
      onCancel={onCancel}
      okText="确认"
      cancelText="取消"
    >
      {form}
    </Modal>;
  }
}

PcMenuModal.propTypes = {
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

PcMenuModal.defaultProps = {};

export default PcMenuModal;
