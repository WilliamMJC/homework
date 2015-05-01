package com.jj.tmdemo.xlistview;

import com.jj.tmdemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;


public class XListView extends ListView implements OnScrollListener {
	// 保存y时间的变量
	private float mLastY = -1; // save event y
	//Android里Scroller类是为了实现View平滑滚动的一个Helper类。
	//通常在自定义的View时使用，在View中定义一个私有成员mScroller = new Scroller(context)。
	//设置mScroller滚动的位置时，并不会导致View的滚动，通常是用mScroller记录/计算View滚动的位置，
	//再重写View的computeScroll()，完成实际的滚动。
	private Scroller mScroller; // used for scroll back
	//用户的滚动监听器
	private OnScrollListener mScrollListener; // user's scroll listener
	//自定义的监听器类？
	// the interface to trigger refresh and load more.
	private IXListViewListener mListViewListener;
	//下拉时出现的头部布局对象
	// -- header view
	private XListViewHeader mHeaderView;
	// header view content, use it to calculate the Header's height. And hide it
	// when disable pull refresh.
	//存放头布局的相对布局对象
	private RelativeLayout mHeaderViewContent;
	//存放头布局的textview对象
	private TextView mHeaderTimeView;
	//存放头布局的高度数值对象
	private int mHeaderViewHeight; // header view's height
	//能下拉刷新的布尔变量?
	private boolean mEnablePullRefresh = true;
	//下拉刷新中的布尔变量？
	private boolean mPullRefreshing = false; // is refreashing.

	// -- footer view
	//底部自定义布局对象
	private XListViewFooter mFooterView;
	//上拉加载布尔变量？
	private boolean mEnablePullLoad;
	//上拉加载中布尔变量？
	private boolean mPullLoading;
	//底部下拉共功能准备好了？布尔变量
	private boolean mIsFooterReady = false;
	//所有item的计数变量
	// total list items, used to detect is at the bottom of listview.
	private int mTotalItemCount;
	
	//滚回去整型变量？
	// for mScroller, scroll back from header or footer.
	private int mScrollBack;
	//滚回去头部变量0
	private final static int SCROLLBACK_HEADER = 0;
	//滚回去底部变量1
	private final static int SCROLLBACK_FOOTER = 1;
	//滚动时间？滚动持续时间？
	private final static int SCROLL_DURATION = 400; // scroll back duration
	//一个变量，上拉超过这个变量就加载
	private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
														// at bottom, trigger
														// load more.
	//不懂的偏移变量
	private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
													// feature.

	/**
	 * @param context

	 */
	//该自定义类的一参构造函数、双参构造函数、三参构造函数
	public XListView(Context context) {
		super(context);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}

	//根据context来初始化，要传入一个context
	private void initWithContext(Context context) {
		//实例化滚动对象
		mScroller = new Scroller(context, new DecelerateInterpolator());
		// XListView need the scroll event, and it will dispatch the event to
		// user's listener (as a proxy).
		super.setOnScrollListener(this);

		// init header view
		//实例化一个头部布局对象
		mHeaderView = new XListViewHeader(context);
		//实例化一个头部相对布局对象
		mHeaderViewContent = (RelativeLayout) mHeaderView
				.findViewById(R.id.xlistview_header_content);
		//实例化一个里面的textivew对象
		mHeaderTimeView = (TextView) mHeaderView
				.findViewById(R.id.xlistview_header_time);
		//调用添加头控件的函数添加这个头布局
		//官方函数
		addHeaderView(mHeaderView);

		// init footer view
		//实例化底部布局对象
		mFooterView = new XListViewFooter(context);

		//初始化头部高度
		// init header height
		mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						mHeaderViewHeight = mHeaderViewContent.getHeight();
						getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// make sure XListViewFooter is the last footer view, and only add once.
		//确定底部布局是最底部的控件，并且只添加了一次。
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}

	/**
	 * enable or disable pull down refresh feature.
	 * 
	 * @param enable
	 */
	//能否下拉觉得了显不显示头部相对布局
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) { // disable, hide the content
			mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderViewContent.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * enable or disable pull up load more feature.
	 * 
	 * @param enable
	 */
	//除了上拉加载外，还有点击加载，如果能上拉，则可以显示，并且可以响应查看更多的单击事件。；
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
			// both "pull up" and "click" will invoke load more.
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	/**
	 * stop refresh, reset header view.
	 */
	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
	}

	/**
	 * stop load more, reset footer view.
	 */
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
		}
	}

	/**
	 * set last refresh time
	 * 
	 * @param time
	 */
	//传入时间，然后后下拉刷新的时候显示上次刷的时间？
	public void setRefreshTime(String time) {
		mHeaderTimeView.setText(time);
	}

	
	//不懂
	private void invokeOnScrolling() {
		if (mScrollListener instanceof OnXScrollListener) {
			OnXScrollListener l = (OnXScrollListener) mScrollListener;
			l.onXScrolling(this);
		}
	}

	//更新头部的高度
	private void updateHeaderHeight(float delta) {
		//头部布局设置看得到的高度delta+所获得高度。
		mHeaderView.setVisiableHeight((int) delta
				+ mHeaderView.getVisiableHeight());
		//如果拉下刷新可以行，且不在刷新状态，
		//如果看得到的高度大于头部布局的高度，即拉过头，
		//设置状态为准备
		if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				//拉完显示松开刷新数据
				mHeaderView.setState(XListViewHeader.STATE_READY);
			} else {
				//否则提示再拉
				mHeaderView.setState(XListViewHeader.STATE_NORMAL);
			}
		}
		//每次都滚到顶部？
		setSelection(0); // scroll to top each time
	}

	/**
	 * reset header view's height.
	 */
	//重置头布局高度
	
	private void resetHeaderHeight() {
		//获取头布局背看到的高度值
		int height = mHeaderView.getVisiableHeight();
		//如果看不到
		if (height == 0) // not visible.
			return;
		// refreshing and header isn't shown fully. do nothing.
		//如果可以刷新，并且看得到的高度还不足以显示松开刷新的提示
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		//
		int finalHeight = 0; // default: scroll back to dismiss header.
		// is refreshing, just scroll back to show all the header.
		//高度足够的时候，赋值finalheight。
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		//赋值滚回去是滚回去头部。
		mScrollBack = SCROLLBACK_HEADER;
		//开始滚
		mScroller.startScroll(0, height, 0, finalHeight - height,
				SCROLL_DURATION);
		// trigger computeScroll
		
		invalidate();
	}
	//一些底部函数
	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
													// more.
				mFooterView.setState(XListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);

		// setSelection(mTotalItemCount - 1); // scroll to bottom
	}

	
	//重置底部高度
	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
					SCROLL_DURATION);
			invalidate();
		}
	}
	

	//上拉加载true，底部布局设置状态。
	//如果监听器实例化了，就调用onLoadMore方法
	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(XListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}
	
	//点按还是拖动 的触摸事件函数。

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			System.out.println("数据监测：" + getFirstVisiblePosition() + "---->"
					+ getLastVisiblePosition());
			if (getFirstVisiblePosition() == 0
					&& (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
				// the first item is showing, header has shown or pull down.
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				invokeOnScrolling();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1
					&& (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
				// last item, already pulled up or want to pull up.
				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}
			break;
		default:
			mLastY = -1; // reset
			if (getFirstVisiblePosition() == 0) {
				// invoke refresh
				if (mEnablePullRefresh
						&& mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
					mPullRefreshing = true;
					mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						mListViewListener.onRefresh();
					}
				}
				resetHeaderHeight();
			}
			if (getLastVisiblePosition() == mTotalItemCount - 1) {
				// invoke load more.
				if (mEnablePullLoad
						&& mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	//计算滚动
	public void computeScroll() {
		//computeScrollOffset如果想知道位置就调用它，它返回true说明未结束，返回新的location？
		if (mScroller.computeScrollOffset()) {
			//滚回去，一个整型变量，如果等于头了，设置头部看得到的高度为滚的目前的y高度。
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				//否则底部设置。
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
		}
		super.computeScroll();
	}
	//滚动监听器，这是监听器对象变量=1

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}
	//滚动状态改变的时候，如果，监听器对象不为空，即有值，则设置滚动状态改变。

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}
	//onscroll函数，所有项目数目值登入传入的，

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// send to user's listener
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}
	//设置自定义的监听器函数，
	//设置为1

	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
	}

	/**
	 * you can listen ListView.OnScrollListener or this one. it will invoke
	 * onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface IXListViewListener {
		public void onRefresh();

		public void onLoadMore();
	}
}


