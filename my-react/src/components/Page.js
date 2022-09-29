import React, { forwardRef } from 'react';
import { Helmet } from 'react-helmet';

//Page的作用是什么呢？
const Page = forwardRef(({//forwardRef将ref向子组件传递
  children,
  title = '',
  ...rest
}, ref) => {
  return (
    <div
      ref={ref}
      {...rest}
    >
      <Helmet>
        <title>{title}</title>
      </Helmet>
      {children}
    </div>
  );
});


export default Page;
