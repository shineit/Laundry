package cn.fuego.laundry.webservice.up.model.base;

import java.io.Serializable;
import java.util.Date;

import cn.fuego.laundry.constant.PayOptionEnum;

public class OrderJson implements Serializable
{
	private String order_id;
	private String order_name;
	private int user_id;
	private Date create_time;
	private Date confirm_time;
	private Date end_time;
	private String pay_option = PayOptionEnum.OFFLINE_PAY.getStrValue();
	private String order_status;
	private int delivery_info_id;
	private int handler_id;
	private String operater_name;
	private String order_type;
	private String order_note;
	private float total_price;
	private float total_count;
	public String getOrder_id()
	{
		return order_id;
	}
	public void setOrder_id(String order_id)
	{
		this.order_id = order_id;
	}
	public String getOrder_name()
	{
		return order_name;
	}
	public void setOrder_name(String order_name)
	{
		this.order_name = order_name;
	}
	public int getUser_id()
	{
		return user_id;
	}
	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}
	public Date getCreate_time()
	{
		return create_time;
	}
	public void setCreate_time(Date create_time)
	{
		this.create_time = create_time;
	}
	public Date getConfirm_time()
	{
		return confirm_time;
	}
	public void setConfirm_time(Date confirm_time)
	{
		this.confirm_time = confirm_time;
	}
	public Date getEnd_time()
	{
		return end_time;
	}
	public void setEnd_time(Date end_time)
	{
		this.end_time = end_time;
	}
	public String getPay_option()
	{
		return pay_option;
	}
	public void setPay_option(String pay_option)
	{
		this.pay_option = pay_option;
	}
	public String getOrder_status()
	{
		return order_status;
	}
	public void setOrder_status(String order_status)
	{
		this.order_status = order_status;
	}
	public int getDelivery_info_id()
	{
		return delivery_info_id;
	}
	public void setDelivery_info_id(int delivery_info_id)
	{
		this.delivery_info_id = delivery_info_id;
	}
	public int getHandler_id()
	{
		return handler_id;
	}
	public void setHandler_id(int handler_id)
	{
		this.handler_id = handler_id;
	}
	public String getOperater_name()
	{
		return operater_name;
	}
	public void setOperater_name(String operater_name)
	{
		this.operater_name = operater_name;
	}
 
	public float getTotal_price()
	{
		return total_price;
	}
	public void setTotal_price(float total_price)
	{
		this.total_price = total_price;
	}
	public float getTotal_count()
	{
		return total_count;
	}
	public void setTotal_count(float total_count)
	{
		this.total_count = total_count;
	}
	public String getOrder_type()
	{
		return order_type;
	}
	public void setOrder_type(String order_type)
	{
		this.order_type = order_type;
	}
	public String getOrder_note()
	{
		return order_note;
	}
	public void setOrder_note(String order_note)
	{
		this.order_note = order_note;
	}
 




}
