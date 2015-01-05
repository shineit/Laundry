package cn.fuego.laundry.ui.cart;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.OnWheelClickedListener;
import antistatic.spinnerwheel.OnWheelScrollListener;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;
import cn.fuego.common.util.validate.ValidatorUtil;
import cn.fuego.laundry.R;
import cn.fuego.laundry.ui.home.HomeProductActivity;
import cn.fuego.laundry.ui.order.OrderActivity;
import cn.fuego.laundry.webservice.up.model.CreateOrderReq;
import cn.fuego.laundry.webservice.up.model.base.OrderDetailJson;
import cn.fuego.laundry.webservice.up.model.base.OrderJson;
import cn.fuego.misp.ui.list.MispListFragment;

public class MyCartFragment extends MispListFragment<OrderDetailJson> implements OnClickListener
{
	private OrderJson order = new OrderJson();
	private PopupWindow popupWindow=null;  
	
	private TextView totalPriceView;
	private TextView totalCountView;
	private View view;  
 
	private  int  curNum= 1;
	private static final String ORDER_INFO = "order_info";
	@Override
	public void initRes()
	{
		this.fragmentRes.setImage(R.drawable.tab_icon_cart);
		this.fragmentRes.setName(R.string.tabbar_cart);
		this.fragmentRes.setFragmentView(R.layout.chart_fragment);
 
		listViewRes.setListView(R.id.chart_list);
		listViewRes.setListItemView(R.layout.chart_list_item);
		listViewRes.setClickActivityClass(HomeProductActivity.class);
		
		this.dataList.clear();
		List<OrderDetailJson> selectList = CartProduct.getInstance().getOrderDetailList();
		if(!ValidatorUtil.isEmpty(selectList))
		{
			this.dataList.addAll(selectList);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = super.onCreateView(inflater, container, savedInstanceState);
		
		totalPriceView = (TextView) rootView.findViewById(R.id.chart_total_price);
		
		totalCountView = (TextView) rootView.findViewById(R.id.chart_total_count);

		Button submitButton = (Button) rootView.findViewById(R.id.chart_submit);
		submitButton.setOnClickListener(this);
		super.adapterForScrollView();
 
		return rootView;
	}




	@Override
	public void loadSendList()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public View getListItemView(View view, final OrderDetailJson item)
	{
		final TextView nameView = (TextView) view.findViewById(R.id.chart_list_item_name);
		nameView.setText(item.getProduct_name());
		
		TextView priceView = (TextView) view.findViewById(R.id.chart_list_item_price);
		priceView.setText(String.valueOf(item.getProduct_price()));
		
		 EditText  amountView = (EditText) view.findViewById(R.id.chart_list_item_quantity);
		//强制关闭软键盘
		InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(amountView.getWindowToken(), 0);
		amountView.setInputType(InputType.TYPE_NULL);
		amountView.setText(String.valueOf(item.getQuantity()));
		amountView.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{

				showWindow(v,item);

			}
		});

		return view;
	}
	
	private void refreshView()
	{
		int totalCnt = 0;
		float totalPrice = 0;
		for(OrderDetailJson e : this.dataList)
		{
			totalPrice += e.getProduct_price() * e.getQuantity();
			totalCnt += e.getQuantity();
		}
		this.totalCountView.setText(String.valueOf(totalCnt));
		this.totalPriceView.setText(String.valueOf(totalPrice));
		repaint();
	}

	@Override
	public List<OrderDetailJson> loadListRecv(Object obj)
	{
		return null;
	}


	@Override
	public void onClick(View v)
	{
	 
		CreateOrderReq req = new CreateOrderReq();
		req.setOrderDetailList(this.dataList);
		req.setOrder(this.order);
		Intent intent = new Intent(this.getActivity(),OrderActivity.class);
		intent.putExtra(ORDER_INFO, req);
		this.startActivity(intent);

		
	}
	private void showWindow(View parent,final OrderDetailJson orderDetail) 
	{  

		Button sure_btn =null;

        if (popupWindow == null)
        {  
            LayoutInflater layoutInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
  
            view = layoutInflater.inflate(R.layout.pop_window_num, null);  

            TextView  title= (TextView) view.findViewById(R.id.pop_window_title);
            title.setText(orderDetail.getProduct_name());
            sure_btn = (Button) view.findViewById(R.id.pop_window_sure_btn);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }  
  
        // 使其聚集  
        popupWindow.setFocusable(true);  
 
        popupWindow.setOutsideTouchable(true);  

 
        ColorDrawable dw = new ColorDrawable(0xb0000000);  
        popupWindow.setBackgroundDrawable(dw);
 
		final AbstractWheel num = (AbstractWheel)view.findViewById(R.id.chart_list_item_num);
		NumericWheelAdapter numAdapter = new NumericWheelAdapter(this.getActivity(), 1, 20);
		num.setViewAdapter(numAdapter);
		num.setCurrentItem(0);
		num.setVisibleItems(5);
		num.addChangingListener(new OnWheelChangedListener()
		{
			
			@Override
			public void onChanged(AbstractWheel wheel, int oldValue, int newValue)
			{
				//Toast.makeText(ctx, "newValue:"+String.valueOf(newValue), Toast.LENGTH_SHORT).show();
				curNum = newValue+1;
				
			}
		});
		sure_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				orderDetail.setQuantity(curNum);
				refreshView();
				popupWindow.dismiss();
				
			}
		});

        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);//在屏幕居中，无偏移
        //监听popwindow消失事件，并对radioGroup清零
        popupWindow.setOnDismissListener(new OnDismissListener()
		{		
			@Override
			public void onDismiss()
			{
				popupWindow=null;
			}
		});

    } 





}
