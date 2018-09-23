import React from 'react';
import { Button, Modal, Form, Input, Radio } from 'antd';
const FormItem = Form.Item;
const StockForm = Form.create()(
    class extends React.Component {
        render () {
            const { visible, onCancel, onCreate, form } = this.props;
            const { getFieldDecorator } = form;
            return (
                <Modal
                  visible={visible}
                  title="Add Stock"
                  okText="Add"
                  onCancel={onCancel}
                  onOk={onCreate}
                >
                  <Form layout="vertical">
                    <FormItem label="Stock">
                      {getFieldDecorator('name', {
                        rules: [{ required: true, message: 'Please input the stock of collection!' }],
                      })(
                        <Input />
                      )}
                    </FormItem>
                    <FormItem label="Price">
                      {getFieldDecorator('price', {
                        rules: [{ required: true, message: 'Please input the price of collection!' }],
                      })(<Input/>)}
                    </FormItem>
                    <FormItem label="Percent">
                      {getFieldDecorator('percent', {
                        rules: [{ required: true, message: 'Please input the percent of collection!' }],
                      })(<Input/>)}
                    </FormItem>
                    <FormItem className="collection-create-form_last-form-item">
                      {getFieldDecorator('operation', {
                        initialValue: 'buy',
                      })(
                        <Radio.Group>
                          <Radio value="buy">BUY</Radio>
                          <Radio value="sell">SELL</Radio>
                        </Radio.Group>
                      )}
                    </FormItem>
                  </Form>
                </Modal>
              );
        }
    }
)

export { StockForm as StockForm };