直播中悬浮小窗口实现方案。

https://juejin.cn/post/6931133158230491143

<img src="https://github.com/dawangzai/FloatWindow/blob/master/gif_1.gif" width=50%>

## 实现思路

其实很容易想到，可以通过 `WindowManager`的 `addView()`实现，不过需要用户同意悬浮窗的权限，如果你的项目是应用内的悬浮窗，也可以去获取 `DecorView`给它添加一个`View`，这样就不用去获取权限了

## 使用介绍

	// 创建 FloatView 设置 Adapter
	val floatView = FloatView(this).apply {
        setAdapter(SimpleAdapter())
    }
    // 创建 Window
    FloatWindow.getInstance(this).bindView(floatView)
    // 移除 Window
    FloatWindow.getInstance(this).removeView()
    
    // 自定义 Adapter 实现自己的业务逻辑
    class SimpleAdapter : FloatView.FloatViewAdapter {

	    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
	    	// 指定悬浮窗布局
	        return inflater.inflate(R.layout.layout_test, container)
	    }
	
	    override fun onBindView(itemView: View) {
	        itemView.findViewById<ImageView>(R.id.iv_close).setOnClickListener {
	            FloatWindow.getInstance(itemView.context).removeView()
	        }
	    }
	}