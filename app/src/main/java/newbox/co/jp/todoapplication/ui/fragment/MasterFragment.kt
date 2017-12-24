package newbox.co.jp.todoapplication.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import newbox.co.jp.todoapplication.R

import newbox.co.jp.todoapplication.dummy.DummyContent
import newbox.co.jp.todoapplication.dummy.DummyContent.DummyItem

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class MasterFragment:Fragment() {
    // TODO: Customize parameters
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null

    public override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

        if (getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT)
        }
    }

    public override fun onCreateView(inflater:LayoutInflater?, container:ViewGroup?,
                                     savedInstanceState:Bundle?):View? {
        val view = inflater!!.inflate(R.layout.fragment_master_list, container, false)

        // Set the adapter
        if (view is RecyclerView)
        {
            val context = view.getContext()
            val recyclerView = view
            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(LinearLayoutManager(context))
            }
            else
            {
                recyclerView.setLayoutManager(GridLayoutManager(context, mColumnCount))
            }
            recyclerView.setAdapter(MyMasterRecyclerViewAdapter(DummyContent.ITEMS, mListener))
        }
        return view
    }


    public override fun onAttach(context:Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener)
        {
            mListener = context
        }
        else
        {
            throw RuntimeException((context!!.toString() + " must implement OnListFragmentInteractionListener"))
        }
    }

    public override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item:DummyItem)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount:Int): MasterFragment {
            val fragment = MasterFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.setArguments(args)
            return fragment
        }
    }
}
