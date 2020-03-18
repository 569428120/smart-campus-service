import React from 'react';
import RenderAuthorized from '@/components/Authorized';
import { getAuthority } from '@/utils/authority';
import Redirect from 'umi/redirect';

const Authority = getAuthority();
const Authorized = RenderAuthorized(Authority);
// TODO 在改处获取权限列表
export default ({ children }) => (
  <Authorized authority={[]} noMatch={<Redirect to="/user/login" />}>
    {children}
  </Authorized>
);
