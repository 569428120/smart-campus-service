import React, {PureComponent} from 'react';
import {Form, Input, Modal, Checkbox, Radio, Select} from "antd";
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
 *  格式化数据
 * @param values
 */
const formatValues = values => {
  const {schoolLevel} = values;
  let schoolLevelStr = "";
  if ((schoolLevel || []).length > 0) {
    schoolLevelStr = schoolLevel.join("##")
  }
  values.schoolLevel = schoolLevelStr;
  return values;
};

@Form.create()
class SchoolModal extends PureComponent {

  /**
   *  提交数据
   * @param onOk
   */
  onSubmit = (onOk) => {
    const {form: {validateFields}, openType} = this.props;
    validateFields((errors, values) => {
      if (errors === null) {
        onOk && onOk(formatValues(values), openType);
      }
    });
  };

  render() {
    const {
      visible,
      openType,
      dataSource,
      regionList,
      onOk,
      onCancel,
      form: {
        getFieldDecorator
      }
    } = this.props;

    const {regionId, schoolName, schoolLevel, schoolType, contact, address, description} = (dataSource || {});

    // 教育局下拉列表
    const regionSelectOptions = (regionList || []).map(({id, regionName}) => (
      <Select.Option key={id} value={id}>{regionName}</Select.Option>
    ));

    const schoolLevelArr = (schoolLevel || "").split("##");

    const schoolLevelCheckboxs = Object.keys(enums.SchoolLevel).map(key =>
      <Checkbox key={key} value={key}>{enums.SchoolLevel[key]}</Checkbox>);

    const schoolTypeRadios = Object.keys(enums.SchoolType).map(key =>
      <Radio key={key} value={key}>{enums.SchoolType[key]}</Radio>
    );

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
      <Form {...formItemLayout} style={{textAlign: "center"}}>
        <Form.Item label="所属教育局">
          {getFieldDecorator('regionId', {
            initialValue: regionId,
            rules: [
              {
                required: true,
                message: '所属教育局必填',
              }
            ],
          })(<Select disabled={openType === 'view'}>
            {regionSelectOptions}
          </Select>)}
        </Form.Item>

        <Form.Item label="学校名称">
          {getFieldDecorator('schoolName', {
            initialValue: schoolName,
            rules: [
              {
                required: true,
                message: '学校名称必填',
              },
              {
                max: 64,
                message: '长度不能超过64',
              },
            ],
          })(<Input disabled={openType === 'view'}/>)}
        </Form.Item>

        <Form.Item label="学校级别">
          {getFieldDecorator('schoolLevel', {
            initialValue: schoolLevelArr,
            rules: [
              {
                required: true,
                message: '学校级别必填',
              }
            ],
          })(<Checkbox.Group disabled={openType === 'view'}>
            {schoolLevelCheckboxs}
          </Checkbox.Group>)}
        </Form.Item>

        <Form.Item label="学校类型">
          {getFieldDecorator('schoolType', {
            initialValue: schoolType,
            rules: [
              {
                required: true,
                message: '学校类型必填',
              }
            ],
          })(<Radio.Group disabled={openType === 'view'}>
            {schoolTypeRadios}
          </Radio.Group>)}
        </Form.Item>

        <Form.Item label="联系方式">
          {getFieldDecorator('contact', {
            initialValue: contact,
            rules: [
              {
                max: 128,
                message: '长度不能超过128',
              },
            ],
          })(<Input disabled={openType === 'view'}/>)}
        </Form.Item>

        <Form.Item label="地址">
          {getFieldDecorator('address', {
            initialValue: address,
            rules: [
              {
                max: 128,
                message: '长度不能超过128',
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

      </Form>
    </Modal>;
  }
}

SchoolModal.propTypes = {
  // 是否显示
  visible: PropTypes.bool.isRequired,
  // 操作类型 edit  add
  openType: PropTypes.string,
  // 回显的数据
  dataSource: PropTypes.object,
  // 教育局列表
  regionList: PropTypes.array,
  // 确认方法
  onOk: PropTypes.func,
  // 取消方法
  onCancel: PropTypes.func,
};

SchoolModal.defaultProps = {};

export default SchoolModal;
