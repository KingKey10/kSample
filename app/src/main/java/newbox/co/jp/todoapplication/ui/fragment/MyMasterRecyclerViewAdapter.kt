package newbox.co.jp.todoapplication.ui.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import newbox.co.jp.todoapplication.R
import newbox.co.jp.todoapplication.dummy.DummyContent.DummyItem
import newbox.co.jp.todoapplication.ui.fragment.MasterFragment.OnListFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyMasterRecyclerViewAdapter(private val mValues:List<DummyItem>, private val mListener:OnListFragmentInteractionListener?):RecyclerView.Adapter<MyMasterRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_master, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        holder.mItem = mValues.get(position)
//        holder.mIdView.setText(mValues.get(position).id)
//        holder.mContentView.setText(mValues.get(position).content)

        holder.mView.setOnClickListener(object:View.OnClickListener {
            override fun onClick(v:View) {
                if (null != mListener)
                {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem!!)
                }
            }
        })
    }

    override fun getItemCount():Int {
        return mValues.size
    }

    inner class ViewHolder( val mView:View):RecyclerView.ViewHolder(mView) {
//        val mIdView:TextView
//        val mContentView:TextView
        var mItem:DummyItem? = null

//        init{
//            mIdView = mView.findViewById<View>(R.id.id) as TextView
//            mContentView = mView.findViewById<View>(R.id.content) as TextView
//        }

//        override fun toString():String {
//            return super.toString() + " '" + mContentView.getText() + "'"
//        }
    }
}