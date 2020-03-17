import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Button, Dropdown, Icon, Menu } from 'antd';
import PropTypes from 'prop-types';

/**
 *  权限按钮
 */
@connect(({ menu, loading }) => ({
  menu,
  loading,
}))
class OperatorButton extends PureComponent {
  /**
   *  下拉操作按钮
   * @param dropdown
   * @param authorityMenus
   */
  renderDropdown(dropdownList, authorityMenus) {
    if ((dropdownList || []).length <= 0) {
      return null;
    }
    // TODO 是否在权限列表中,如果没在则不显示
    const menu = (
      <Menu selectedKeys={[]}>
        {dropdownList.map(dropdown => {
          const { onClick, operatorKey, text, ...ret } = dropdown;
          return (
            <Menu.Item onClick={onClick} key={operatorKey} {...ret}>
              {text}
            </Menu.Item>
          );
        })}
      </Menu>
    );

    return (
      <Dropdown overlay={menu}>
        <Button>
          更多操作 <Icon type="down" />
        </Button>
      </Dropdown>
    );
  }

  render() {
    /**
     *
     *  dropdowns:
     */
    const { buttonList, dropdownList, menu } = this.props;

    // 操作按钮
    const operatorButtons = buttonList.map(item => {
      const { icon, type, isShow, operatorKey, text, onClick } = item;
      // 是否在权限列表中,如果没在则不显示
      if (isShow && !isShow(operatorKey)) {
        return null;
      }
      return (
        <Button icon={icon} key={operatorKey} type={type} onClick={onClick}>
          {text}
        </Button>
      );
    });

    // 操作菜单
    const dropdown = this.renderDropdown(dropdownList, []);
    return [...operatorButtons, dropdown];
  }
}

// 属性说明
OperatorButton.propTypes = {
  // 操作按钮
  buttonList: PropTypes.array,
  // 下拉操作按钮
  dropdownList: PropTypes.array,
};

// 默认值
OperatorButton.defaultProps = {
  /*
      [{
     *    icon:
     *    type:
     *    text:
     *    operatorKey:
     *    isShow
     *    onClick,
     *  }]
   */
  buttonList: [],
  /**
   [
   *      {
   *        onClick,
   *        items:[
   *          operatorKey:
   *          isShow:
   *          text:
   *        ]
   *      }
   *    ]
   */
  dropdownList: [],
};

export default OperatorButton;
